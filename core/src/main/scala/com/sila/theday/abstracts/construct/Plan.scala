package com.sila.theday.abstracts.construct

import com.sila.theday.abstracts.construct.Activity.PlaceTimeStation

sealed trait Plan {
  type Time = Int

  def empty: Plan

  def potentialStartPlaces: Set[Place]

  def potentialEndPlaces: Set[Place]

  def temporalGapTo(plan : Plan) : Time = {
    val earliestPossibleEndTime : Int = this.potentialEndTime.min
    val latestPossibleStartTime = plan.potentialStartTime.max
    latestPossibleStartTime - earliestPossibleEndTime
  }

  def temporallyBefore(plan: Plan): Boolean = this.potentialEndTime.forall(endTime => !plan.potentialStartTime.exists(startTime => startTime < endTime))

  def temporallyAfter(plan: Plan): Boolean = this.potentialStartTime.forall(startTime => !plan.potentialEndTime.exists(endTime => startTime < endTime))

  def possibleBefore(plan: Plan): Boolean = this match {
    case _ if !this.temporallyBefore(plan) => false
    case _ => {
      val distance = for {
        endPlace <- this.potentialEndPlaces
        startPlace <- plan.potentialStartPlaces
      } yield endPlace.location.euclideanDistance(startPlace.location)

      val timeDifference = this.temporalGapTo(plan).toDouble
      println(s"The minimum distance is ${distance.min} and time between $timeDifference")
      distance.min <= timeDifference
    }
  }

  def possibleAfter(plan: Plan): Boolean = this.potentialStartTime.forall(startTime => !plan.potentialEndTime.exists(endTime => startTime < endTime))

  def potentialStartTime: List[Time]

  def potentialEndTime: List[Time]

  def description: String

  def temporalProjection: (List[Time], List[Time]) = (this.potentialStartTime, this.potentialEndTime)

  def placeProjection: List[Place]

  def coarsen(implicit geog: Geography): Plan = SingleActivity(
    PlaceTimeStation(geog.containingPlace(this.placeProjection), (this.potentialStartTime.head, this.potentialEndTime.head), this.description)
  )

  def flatten: List[Plan]

  def parallel(plan: Plan): Boolean = if (this.possibleBefore(plan) || plan.possibleBefore(this)) false else true

  def isChainable(plan: Plan): Boolean = this.possibleBefore(plan) || plan.possibleBefore(this)

  //  def during()

  def combine(plan: Plan): Plan

}

case class SingleActivity(activity: Activity) extends Plan {

  override def description: String = activity.name

  override def potentialStartTime: List[Time] = List(activity.getStartTime)

  override def potentialEndTime: List[Time] = List(activity.getEndTime)

  override def potentialStartPlaces: Set[Place] = Set(activity.getStartPlace)

  override def potentialEndPlaces: Set[Place] = Set(activity.getEndPlace)

  override def flatten: List[Plan] = List(SingleActivity(activity))

  override def empty: Plan = SingleActivity(null)

  override def placeProjection: List[Place] = activity.getPlaces

  override def combine(plan: Plan): Plan =
    if (this == plan) this
    else Plan.combine(this, plan)

}

case class ActivitySequence(plan: List[Plan]) extends Plan {

  override def description: String = this.flatten.map(_.description).mkString(",")

  override def potentialStartTime: List[Time] = {
    val potentialTimeOfActivities: Seq[Plan#Time] = plan.flatMap(_.potentialStartTime.sorted.headOption)
    List(potentialTimeOfActivities.sorted.head)
  }

  override def potentialEndTime: List[Time] = {
    val potentialTimeOfActivities: Seq[Plan#Time] = plan.flatMap(_.potentialEndTime.sorted.reverse.headOption)
    List(potentialTimeOfActivities.sorted.reverse.head)
  }

  override def potentialStartPlaces: Set[Place] = plan.sortBy(_.potentialStartTime.min).headOption.map(_.potentialStartPlaces).getOrElse(Set.empty)

  override def potentialEndPlaces: Set[Place] = plan.sortBy(-_.potentialEndTime.min).headOption.map(_.potentialEndPlaces).getOrElse(Set.empty)

  override def flatten: List[Plan] = plan.flatMap(b => b.flatten)

  override def empty: Plan = ActivitySequence(List())

  override def combine(otherPlan: Plan): Plan =
    if (this == otherPlan) this
    else Plan.combine(this, otherPlan)

  override def placeProjection: List[Place] = plan.flatMap(p => p.placeProjection).distinct
}

case class ActivityAlternatives(potentials: Set[Plan]) extends Plan {
  override def empty = ActivityAlternatives(Set.empty)

  override def potentialStartPlaces: Set[Place] = potentials.flatMap(plan => plan.potentialStartPlaces)

  override def potentialEndPlaces: Set[Place] = potentials.flatMap(plan => plan.potentialEndPlaces)

  override def description = ???

  override def placeProjection: List[Place] = potentials.flatMap(_.placeProjection).toList

  override def flatten: List[Plan] = potentials.toList

  override def combine(plan: Plan) = if (this == plan) this else Plan.combine(this, plan)

  override def potentialStartTime = potentials.flatMap(_.potentialStartTime).toList

  override def potentialEndTime = potentials.flatMap(_.potentialEndTime).toList
}

object Plan {
  def combine(planA: Plan, planB: Plan): Plan = (planA, planB) match {
    case (SingleActivity(_), ActivitySequence(s2)) if planA possibleBefore planB => ActivitySequence(planA :: s2)
    case (SingleActivity(_), ActivitySequence(s2)) if planB possibleBefore planA => ActivitySequence(s2 :+ planA)
    case (ActivitySequence(s1), ActivitySequence(s2)) => ActivitySequence((s1 ++ s2) sortWith ((p1, p2) => p1.possibleBefore(p2)))
    case (_, ActivitySequence(sequence)) => sequence.foldLeft[Plan](planA) { (acc, a) =>
      acc.combine(a)
    }
    case (ActivitySequence(_), SingleActivity(_)) => planB combine planA
    case (ActivitySequence(_), _) => planB combine planA
    case (_, _) if planA possibleBefore planB => ActivitySequence(List(planA, planB))
    case (_, _) if planB possibleBefore planA => ActivitySequence(List(planB, planA))

    case (ActivityAlternatives(a1), ActivityAlternatives(a2)) => ActivityAlternatives(a1 ++ a2)
    case (ActivityAlternatives(alts), SingleActivity(_)) => ActivityAlternatives(mergeWithAlternatives(alts, planB))
    case (SingleActivity(_), ActivityAlternatives(_)) => combine(planB, planA)
    case (_, _) => ActivityAlternatives(Set(planA, planB))
  }


  def mergeWithAlternatives(alternatives: Set[Plan], activity: Plan): Set[Plan] = {
    alternatives.foldLeft(Set[Plan]()) { case (acc, nextActivity) =>
      val combinedActivity = activity.combine(nextActivity)
      combinedActivity match {
        case ActivityAlternatives(_) => acc + nextActivity
        case _ => acc + combinedActivity
      }
    }
  }

}