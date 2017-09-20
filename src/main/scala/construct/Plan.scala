package construct


sealed trait Plan extends Monoid[Plan] with RelationalTemporalEvent{
  type Time = Int
  def startPlace : Place
  def endPlace : Place
  def temporalProjection : (Time,Time) = (this.getStartTime,this.getEndTime)
  def placeProjection : List[Place]
  def flatten : List[Plan]
  def parallel(plan : Plan) : Boolean = if(this.before(plan) || plan.before(this)) false else true
  def isChainable(plan : Plan) : Boolean = this.before(plan) || plan.before(this)
}

object Plan {
  case class SingleActivity(activity: Activity) extends Plan {
    override def getStartTime: Time = activity.getStartTime
    override def getEndTime: Time = activity.getEndTime
    override def startPlace: Place = activity.getStartPlace
    override def endPlace: Place = activity.getEndPlace
    override def flatten: List[Plan] = List(SingleActivity(activity))
    override def Zero: Plan = SingleActivity(null)
    override def op(t1:Plan,t2:Plan): Plan = ActivitySequence(List(t1,t2))
    override def placeProjection: List[Place] = activity.getPlaces
  }

  case class ActivitySequence(plan: List[Plan]) extends Plan {
    override def getStartTime: Time = plan.map(b => b.getStartTime).sortBy(a => a).head
    override def getEndTime: Time = plan.map(b => b.getEndTime).sortBy(a => a).reverse.head
    override def startPlace: Place = plan.sortBy(b => b.getStartTime).map(b => b.startPlace).head
    override def endPlace: Place =  plan.sortBy(b => b.getEndTime).map(b => b.endPlace).reverse.head
    override def flatten: List[Plan] = plan.flatMap(b => b.flatten)
    override def Zero: Plan = ActivitySequence(List())
    //TODO implement monoid methods properly
    override def op(t1:Plan,t2:Plan): Plan = (t1,t2) match {
      case (SingleActivity(activity1),SingleActivity(activity2)) => new ActivitySequence(List(SingleActivity(activity1),SingleActivity(activity2)))
      case (ActivitySequence(activities),SingleActivity(activity2)) => new ActivitySequence(List(ActivitySequence(activities),SingleActivity(activity2)))
      case (ActivitySequence(activities),ActivitySequence(activity2)) => new ActivitySequence(List(ActivitySequence(activities),ActivitySequence(activities)))
    }
    override def placeProjection: List[Place] = plan.flatMap(p => p.placeProjection).distinct
  }

}