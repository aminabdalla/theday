sealed trait Block{

  def startTime : Int
  def endTime : Int


}


object Block {

  case class SingleBlock(activity: Activity) extends Block {
    override def startTime: Int = this.activity.getTimeSpan._1

    override def endTime: Int = this.activity.getTimeSpan._2
  }

  case class BlockSequence(activities: List[Activity]) extends Block {
    override def startTime: Int = this.activities.sortBy(a => a.getTimeSpan._1).head.getTimeSpan._1

    override def endTime: Int = this.activities.sortBy(a => a.getTimeSpan._2).reverse.head.getTimeSpan._2
  }

}