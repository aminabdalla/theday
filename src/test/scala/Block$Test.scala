import Activity.{PlaceTimePath, PlaceTimeStation}
import Block.{BlockSequence, SingleBlock}
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

/**
  * Created by amin on 19/03/2017.
  */
@RunWith(classOf[JUnitRunner])
class Block$Test extends FunSuite {

  val staticActivityStartsAt0 = PlaceTimeStation("Home",(0,2),"")
  val movingActivityStartsAt1 = PlaceTimePath("Home","Zoo",(2,3),"")
  val sequenceOfActivities = BlockSequence(List(staticActivityStartsAt0,movingActivityStartsAt1))
  val unsortedSequenceOfActivities = BlockSequence(List(movingActivityStartsAt1,staticActivityStartsAt0))

  test("single static activity starts at 0")(assert(SingleBlock(staticActivityStartsAt0).startTime == 0))

  test("single moving activity starts at 0")(assert(SingleBlock(movingActivityStartsAt1).startTime == 2))

  test("sequence of static&moving activities start at 0")(assert(sequenceOfActivities.startTime == 0))

  test("sequence of unsorted static&moving activities start at 0")(assert(unsortedSequenceOfActivities.startTime == 0))

  test("single static activity ends at 2")(assert(SingleBlock(staticActivityStartsAt0).endTime == 2))

  test("single moving activity ends at 2")(assert(SingleBlock(movingActivityStartsAt1).endTime == 3))

  test("sequence of static&moving activities end at 3")(assert(sequenceOfActivities.endTime == 3))

  test("sequence of unsorted static&moving activities end at 3")(assert(unsortedSequenceOfActivities.endTime == 3))







}
