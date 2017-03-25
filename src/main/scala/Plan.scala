import Activity.{Place, PlaceTimePath, PlaceTimeStation}

import scala.collection.TraversableOnce.MonadOps

sealed trait Plan extends Monoid[Plan]{

  def startTime : Int
  def endTime : Int
  def startPlace : Place
  def endPlace : Place
  def flatten : List[Plan]
  def isPossibleBefore(block:Plan) : Boolean
  def parallel(block : Plan) : Boolean = if(this.isPossibleBefore(block) || block.isPossibleBefore(block)) false else true
  def isChainable(block : Plan) : Boolean = this.isPossibleBefore(block) || block.isPossibleBefore(block)

}

object Plan {

  case class SingleActivity(activity: Activity) extends Plan {
    override def startTime: Int = activity.getStartTime

    override def endTime: Int = activity.getEndTime

    override def startPlace: Place = activity.getStartPlace

    override def endPlace: Place = activity.getEndPlace

    override def flatten: List[Plan] = List(SingleActivity(activity))

    override def Zero: Plan = SingleActivity(null)

    override def op(t1:Plan,t2:Plan): Plan = ActivitySequence(List(t1,t2))

    override def isPossibleBefore(block: Plan): Boolean = this.endTime <= block.startTime
  }

  case class ActivitySequence(blocks: List[Plan]) extends Plan {
    override def startTime: Int = blocks.map(b => b.startTime).sortBy(a => a).head

    override def endTime: Int = blocks.map(b => b.endTime).sortBy(a => a).reverse.head

    override def startPlace: Place = blocks.sortBy(b => b.startTime).map(b => b.startPlace).head

    override def endPlace: Place =  blocks.sortBy(b => b.endTime).map(b => b.endPlace).reverse.head

    override def flatten: List[Plan] = blocks.flatMap(b => b.flatten)

    override def Zero: Plan = ActivitySequence(List())

    override def op(t1:Plan,t2:Plan): Plan = (t1,t2) match {
      case (SingleActivity(activity1),SingleActivity(activity2)) => new ActivitySequence(List(SingleActivity(activity1),SingleActivity(activity2)))
      case (ActivitySequence(activities),SingleActivity(activity2)) => new ActivitySequence(List(ActivitySequence(activities),SingleActivity(activity2)))
      case (ActivitySequence(activities),ActivitySequence(activity2)) => new ActivitySequence(List(ActivitySequence(activities),ActivitySequence(activities)))
    }

    override def isPossibleBefore(block: Plan): Boolean = this.endTime <= block.startTime

  }

}