package com.sila.theday.abstracts.construct

import com.sila.theday.abstracts.construct.Activity.PlaceTimeStation
import com.sila.theday.abstracts.construct.Plan.SingleActivity

sealed trait Plan extends RelationalTemporalEvent {
  type Time = Int

  def empty: Plan

  def startPlace: Place

  def endPlace: Place

  def description: String

  def temporalProjection: (Time, Time) = (this.getStartTime, this.getEndTime)

  def placeProjection: List[Place]

  def coarsen(implicit geog: Geography): Plan = SingleActivity(
    PlaceTimeStation(geog.containingPlace(this.placeProjection), (this.getStartTime, this.getEndTime), this.description)
  )

  def flatten: List[Plan]

  def parallel(plan: Plan): Boolean = if (this.before(plan) || plan.before(this)) false else true

  def isChainable(plan: Plan): Boolean = this.before(plan) || plan.before(this)

  def combine(plan: Plan): Plan

}

object Plan {

  case class SingleActivity(activity: Activity) extends Plan {

    override def description: String = activity.name

    override def getStartTime: Time = activity.getStartTime

    override def getEndTime: Time = activity.getEndTime

    override def startPlace: Place = activity.getStartPlace

    override def endPlace: Place = activity.getEndPlace

    override def flatten: List[Plan] = List(SingleActivity(activity))

    override def empty: Plan = SingleActivity(null)

    override def placeProjection: List[Place] = activity.getPlaces

    override def combine(plan: Plan): Plan =
      if (this.equal(plan)) this
      else
        plan match {
          case SingleActivity(_) => new ActivitySequence(List(this, plan).sortWith((p1, p2) => p1.before(p2)))
          case ActivitySequence(activities) =>  new ActivitySequence((plan.flatten :+ this).sortWith((p1, p2) => p1.before(p2)))

        }
  }

  case class ActivitySequence(plan: List[Plan]) extends Plan {

    override def description: String = this.flatten.map(_.description).mkString(",")

    override def getStartTime: Time = plan.map(b => b.getStartTime).sortBy(a => a).head

    override def getEndTime: Time = plan.map(b => b.getEndTime).sortBy(a => a).reverse.head

    override def startPlace: Place = plan.sortBy(b => b.getStartTime).map(b => b.startPlace).head

    override def endPlace: Place = plan.sortBy(b => b.getEndTime).map(b => b.endPlace).reverse.head

    override def flatten: List[Plan] = plan.flatMap(b => b.flatten)

    override def empty: Plan = ActivitySequence(List())

    //    //TODO implement monoid methods properly
    override def combine(plan: Plan): Plan =
      if (this.equal(plan)) this
      else
        plan match {
          case SingleActivity(_) => new ActivitySequence((this.flatten :+ plan).sortWith((p1, p2) => p1.before(p2)))
          case ActivitySequence(activities) => new ActivitySequence(this.flatten :+ plan)
        }

    override def placeProjection: List[Place] = plan.flatMap(p => p.placeProjection).distinct
  }

}