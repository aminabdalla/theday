package com.sila.theday.abstracts.construct

import com.sila.theday.abstracts.construct.Activity.PlaceTimeStation

sealed trait Plan {
  type Time = Int

  def empty: Plan

  def startPlace: Place

  def endPlace: Place

  def before(plan: Plan): Boolean = this.potentialEndTime.forall(endTime => !plan.potentialStartTime.exists(startTime => startTime < endTime))

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
    else
      plan match {
        case SingleActivity(_) => new ActivitySequence(List(this, plan).sortWith((p1, p2) => p1.before(p2)))
        case ActivitySequence(activities) => new ActivitySequence((plan.flatten :+ this).sortWith((p1, p2) => p1.before(p2)))

      }
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

  //    //TODO implement monoid methods properly
  override def combine(plan: Plan): Plan =
    if (this == plan) this
    else
      plan match {
        case SingleActivity(_) => new ActivitySequence((this.flatten :+ plan).sortWith((p1, p2) => p1.before(p2)))
        case ActivitySequence(activities) => new ActivitySequence(this.flatten :+ plan)
      }

  override def placeProjection: List[Place] = plan.flatMap(p => p.placeProjection).distinct
}

case class ActivityAlternatives(potentials: Set[Plan]) extends Plan {
  override def empty = ActivityAlternatives(Set.empty)

  override def startPlace = ???

  override def endPlace = ???

  override def description = ???

  override def placeProjection = ???

  override def flatten = ???

  override def combine(plan: Plan) = ???

  override def potentialStartTime = ???

  override def potentialEndTime = ???
}