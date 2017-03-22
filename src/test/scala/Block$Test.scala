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
  val movingActivityStartsAt1 = PlaceTimePath("Supermarket","Zoo",(2,3),"")
  val sequenceOfActivities = BlockSequence(List(SingleBlock(staticActivityStartsAt0),SingleBlock(movingActivityStartsAt1)))
  val unsortedSequenceOfActivities = BlockSequence(List(SingleBlock(movingActivityStartsAt1),SingleBlock(staticActivityStartsAt0)))

  // testing times
  test("single static activity starts at 0")(assert(SingleBlock(staticActivityStartsAt0).startTime == 0))

  test("single moving activity starts at 0")(assert(SingleBlock(movingActivityStartsAt1).startTime == 2))

  test("sequence of static&moving activities start at 0")(assert(sequenceOfActivities.startTime == 0))

  test("sequence of unsorted static&moving activities start at 0")(assert(unsortedSequenceOfActivities.startTime == 0))

  test("single static activity ends at 2")(assert(SingleBlock(staticActivityStartsAt0).endTime == 2))

  test("single moving activity ends at 2")(assert(SingleBlock(movingActivityStartsAt1).endTime == 3))

  test("sequence of static&moving activities end at 3")(assert(sequenceOfActivities.endTime == 3))

  test("sequence of unsorted static&moving activities end at 3")(assert(unsortedSequenceOfActivities.endTime == 3))

  // testing places
  test("single static activity starts at Home")(assert(SingleBlock(staticActivityStartsAt0).startPlace == "Home"))

  test("single moving activity starts at Home")(assert(SingleBlock(movingActivityStartsAt1).startPlace == "Supermarket"))

  test("sequence of static&moving activities start at Home")(assert(sequenceOfActivities.startPlace == "Home"))

  test("sequence of unsorted static&moving activities start at Home")(assert(unsortedSequenceOfActivities.startPlace == "Home"))

  test("single static activity ends at Home")(assert(SingleBlock(staticActivityStartsAt0).endPlace == "Home"))

  test("single moving activity ends at Zoo")(assert(SingleBlock(movingActivityStartsAt1).endPlace == "Zoo"))

  test("sequence of static&moving activities end at Zoo")(assert(sequenceOfActivities.endPlace == "Zoo"))

  test("sequence of unsorted static&moving activities end at Zoo")(assert(unsortedSequenceOfActivities.endPlace == "Zoo"))



}
