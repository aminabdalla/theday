import Activity.{Place, PlaceTimePath, PlaceTimeStation}

import scala.collection.TraversableOnce.MonadOps

sealed trait Block extends Monoid[Block]{

  def startTime : Int
  def endTime : Int
  def startPlace : Place
  def endPlace : Place
  def flatten : List[Block]

}

object Block {

  case class SingleBlock(activity: Activity) extends Block {
    override def startTime: Int = activity.getStartTime

    override def endTime: Int = activity.getEndTime

    override def startPlace: Place = activity.getStartPlace

    override def endPlace: Place = activity.getEndPlace

    override def flatten: List[Block] = List(SingleBlock(activity))

    override def Zero: Block = SingleBlock(null)

    override def op(t1:Block,t2:Block): Block = BlockSequence(List(t1,t2))
  }

  case class BlockSequence(blocks: List[Block]) extends Block {
    override def startTime: Int = blocks.map(b => b.startTime).sortBy(a => a).head

    override def endTime: Int = blocks.map(b => b.endTime).sortBy(a => a).reverse.head

    override def startPlace: Place = blocks.sortBy(b => b.startTime).map(b => b.startPlace).head

    override def endPlace: Place =  blocks.sortBy(b => b.endTime).map(b => b.endPlace).reverse.head

    override def flatten: List[Block] = blocks.flatMap(b => b.flatten)

    override def Zero: Block = BlockSequence(List())

    override def op(t1:Block,t2:Block): Block = (t1,t2) match {
      case (SingleBlock(activity1),SingleBlock(activity2)) => new BlockSequence(List(SingleBlock(activity1),SingleBlock(activity2)))
      case (BlockSequence(activities),SingleBlock(activity2)) => new BlockSequence(List(BlockSequence(activities),SingleBlock(activity2)))
      case (BlockSequence(activities),BlockSequence(activity2)) => new BlockSequence(List(BlockSequence(activities),BlockSequence(activities)))
    }
  }

}