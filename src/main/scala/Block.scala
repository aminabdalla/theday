import Activity.{Place, PlaceTimePath, PlaceTimeStation}

sealed trait Block{

  def startTime : Int
  def endTime : Int
  def startPlace : Place
  def endPlace : Place

}

object Block {

  case class SingleBlock(activity: Activity) extends Block {
    override def startTime: Int = activity.getStartTime

    override def endTime: Int = activity.getEndTime

    override def startPlace: Place = activity.getStartPlace

    override def endPlace: Place = activity.getEndPlace
  }

  case class BlockSequence(activities: List[Activity]) extends Block {
    override def startTime: Int = this.activities.sortBy(a => a.getTimeSpan._1).head.getStartTime

    override def endTime: Int = this.activities.sortBy(a => a.getTimeSpan._2).reverse.head.getEndTime

    override def startPlace: Place = this.activities.sortBy(a => a.getTimeSpan._1).head.getStartPlace

    override def endPlace: Place =  this.activities.sortBy(a => a.getTimeSpan._2).reverse.head.getEndPlace
  }

}