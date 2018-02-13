package com.sila.theday.abstracts.construct

import com.sila.theday.abstracts.construct.Activity.PlaceTimeStation

sealed trait Plan {
  type Time = Int

  def empty: Plan

  def startPlace: Place

  def endPlace: Place

  def before(plan: Plan): Boolean = this.potentialEndTime.forall(endTime => !plan.potentialStartTime.exists(startTime => startTime < endTime))

  def after(plan: Plan): Boolean = this.potentialStartTime.forall(startTime => !plan.potentialEndTime.exists(endTime => startTime < endTime))

  def potentialStartTime: List[Time]

  def potentialEndTime: List[Time]

  def description: String

  def temporalProjection: (List[Time], List[Time]) = (this.potentialStartTime, this.potentialEndTime)

  def placeProjection: List[Place]

  def coarsen(implicit geog: Geography): Plan = SingleActivity(
    PlaceTimeStation(geog.containingPlace(this.placeProjection), (this.potentialStartTime.head, this.potentialEndTime.head), this.description)
  )

  def flatten: List[Plan]

  def parallel(plan: Plan): Boolean = if (this.before(plan) || plan.before(this)) false else true

  def isChainable(plan: Plan): Boolean = this.before(plan) || plan.before(this)

  //  def during()

  def combine(plan: Plan): Plan

}

case class SingleActivity(activity: Activity) extends Plan {

  override def description: String = activity.name

  override def potentialStartTime: List[Time] = List(activity.getStartTime)

  override def potentialEndTime: List[Time] = List(activity.getEndTime)

  override def startPlace: Place = activity.getStartPlace

  override def endPlace: Place = activity.getEndPlace

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

  override def startPlace: Place = plan.sortBy(b => b.potentialStartTime.head).map(b => b.startPlace).head

  override def endPlace: Place = plan.sortBy(b => b.potentialEndTime.head).map(b => b.endPlace).reverse.head

  override def flatten: List[Plan] = plan.flatMap(b => b.flatten)

  override def empty: Plan = ActivitySequence(List())

  override def combine(otherPlan: Plan): Plan =
    if (this == otherPlan) this
    else Plan.combine(this, otherPlan)

  override def placeProjection: List[Place] = plan.flatMap(p => p.placeProjection).distinct
}

case class ActivityAlternatives(potentials: Set[Plan]) extends Plan {
  override def empty = ActivityAlternatives(Set.empty)

  override def startPlace = ???

  override def endPlace = ???

  override def description = ???

  override def placeProjection = potentials.flatMap(_.placeProjection).toList

  override def flatten = potentials.toList

  override def combine(plan: Plan) = if (this == plan) this else Plan.combine(this, plan)

  override def potentialStartTime = potentials.flatMap(_.potentialStartTime).toList

  override def potentialEndTime = potentials.flatMap(_.potentialEndTime).toList
}

object Plan {
  def combine(planA: Plan, planB: Plan): Plan = (planA, planB) match {
    case (SingleActivity(_), ActivitySequence(s2)) if planA before planB => ActivitySequence(planA :: s2)
    case (SingleActivity(_), ActivitySequence(s2)) if planB before planA => ActivitySequence(s2 :+ planA)
    case (_, ActivitySequence(sequence)) => sequence.foldLeft[Plan](planA) { (acc, a) => acc.combine(a) }
    case (ActivitySequence(s1), ActivitySequence(s2)) => ActivitySequence((s1 ++ s2) sortWith ((p1, p2) => p1.before(p2)))
    case (ActivitySequence(_), SingleActivity(_)) => planB.combine(planA)
    case (ActivitySequence(_), _) => Plan.combine(planB,planA)
    case (ActivityAlternatives(_), SingleActivity(_)) => planB.combine(planA)
    case (_, _) if planA before planB => ActivitySequence(List(planA, planB))
    case (_, _) if planB before planA => ActivitySequence(List(planB, planA))
    case (_, _) => ActivityAlternatives(Set(planA, planB))
  }
}