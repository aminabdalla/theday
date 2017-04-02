import Activity.{PlaceTimePath, PlaceTimeStation}
import Plan.{ActivitySequence, SingleActivity}
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

/**
  * Created by amin on 19/03/2017.
  */
@RunWith(classOf[JUnitRunner])
class Block$Test extends FunSuite {

  val singleActivityStart4End5 = SingleActivity(PlaceTimeStation("Cinema",(4,5),""))
  val coveringActivityStart0End5 = SingleActivity(PlaceTimeStation("Work",(0,5),""))
  val staticActivityStartsAt0 = PlaceTimeStation("Home",(0,2),"")
  val stayingAtHomeFrom2 = PlaceTimeStation("Home",(2,3),"")
  val movingActivityStartsAt2 = PlaceTimePath("Supermarket","Zoo",(2,3),"")
  val overlappingActivity = PlaceTimeStation("Uni",(0,2),"")
  val overlappingSingleActivity = SingleActivity(overlappingActivity)
  val SingleActivityat0 = SingleActivity(staticActivityStartsAt0)
  val SingleActivityat4 = SingleActivity(movingActivityStartsAt2)
  val SingleActivityat2 = SingleActivity(movingActivityStartsAt2)
  val sequenceOfActivities = ActivitySequence(List(SingleActivity(staticActivityStartsAt0),SingleActivity(movingActivityStartsAt2)))
  val stayingAtHomePlan = ActivitySequence(List(SingleActivity(staticActivityStartsAt0),SingleActivity(stayingAtHomeFrom2)))
  val unsortedSequenceOfActivities = ActivitySequence(List(SingleActivity(movingActivityStartsAt2),SingleActivity(staticActivityStartsAt0)))

  // testing times
  test("single static activity starts at 0")(assert(SingleActivity(staticActivityStartsAt0).getStartTime == 0))

  test("single moving activity starts at 0")(assert(SingleActivity(movingActivityStartsAt2).getStartTime == 2))

  test("sequence of being home&going to the zoo block starts at 0")(assert(sequenceOfActivities.getStartTime == 0))

  test("sequence of unsorted home&going to the zoo activities start at 0")(assert(unsortedSequenceOfActivities.getStartTime == 0))

  test("single static activity ends at 2")(assert(SingleActivity(staticActivityStartsAt0).getEndTime == 2))

  test("single moving activity ends at 2")(assert(SingleActivity(movingActivityStartsAt2).getEndTime == 3))

  test("sequence of static&moving activities end at 3")(assert(sequenceOfActivities.getEndTime == 3))

  test("sequence of unsorted static&moving activities end at 3")(assert(unsortedSequenceOfActivities.getEndTime == 3))

  // testing places
  test("single static activity starts at Home")(assert(SingleActivity(staticActivityStartsAt0).startPlace == "Home"))

  test("single moving activity starts at Home")(assert(SingleActivity(movingActivityStartsAt2).startPlace == "Supermarket"))

  test("sequence of static&moving activities start at Home")(assert(sequenceOfActivities.startPlace == "Home"))

  test("sequence of unsorted static&moving activities start at Home")(assert(unsortedSequenceOfActivities.startPlace == "Home"))

  test("single static activity ends at Home")(assert(SingleActivity(staticActivityStartsAt0).endPlace == "Home"))

  test("single moving activity ends at Zoo")(assert(SingleActivity(movingActivityStartsAt2).endPlace == "Zoo"))

  test("sequence of static&moving activities end at Zoo")(assert(sequenceOfActivities.endPlace == "Zoo"))

  test("sequence of unsorted static&moving activities end at Zoo")(assert(unsortedSequenceOfActivities.endPlace == "Zoo"))

  //tests isPossible
  test("single block1 is possible before single2")(assert(SingleActivityat0.isPossibleBefore(SingleActivityat2)))
  test("single block2 is not possible before single1")(assert(SingleActivityat2.isPossibleBefore(SingleActivityat0) == false))
  test("single block0 is not possible before overlapping block")(assert(SingleActivityat0.isPossibleBefore(overlappingSingleActivity) == false))
  test("cannot start one activity if within the time span of another")(assert(SingleActivityat0.isPossibleBefore(coveringActivityStart0End5) == false))
  test("cannot start one activity before another that falls within it")(assert(coveringActivityStart0End5.isPossibleBefore(SingleActivityat0) == false))


  //tests parallel blocks
  test("single block0 is in parallel to overlapping block")(assert(SingleActivityat0.parallel(overlappingSingleActivity) == true))
  test("single overlapping is in parallel to block0 block")(assert(overlappingSingleActivity.parallel(SingleActivityat0) == true))

  //tests chainable blocks
  test("single block is chainable before or after another block")(assert(SingleActivityat0.isChainable(SingleActivityat2)==true))
  test("sequence block is chainable before or after another block")(assert(sequenceOfActivities.isChainable(singleActivityStart4End5)==true))

  //tests temporalProjection
  test("a plan with two sequenced activities starting at starts at 0 and ends at 3")(assert(unsortedSequenceOfActivities.temporalProjection == (0,3)))

  //tests place projection
  test("a plan from home to the supermarket to the zoo, has a temporal projection of home, supermarket zoo")(assert(unsortedSequenceOfActivities.placeProjection.exists(a => List("Zoo","Supermarket","Home").contains(a))))
  test("a plan of 2 activities at the same place has a temporal projection of only one place")(assert(stayingAtHomePlan.placeProjection.contains("Home")),assert(stayingAtHomePlan.placeProjection.size == 1))

  //tests temporal containment
  val goingToUniFrom0_5 = SingleActivity(PlaceTimeStation("Uni",(0,5),""))
  val attendingLecturesFrom1_4 = ActivitySequence(List(SingleActivity(PlaceTimeStation("Maths",(1,2),"")),SingleActivity(PlaceTimeStation("Statistics",(3,4),""))))
  test("plan to stay in uni is contained by the plan to attend lectures")(assert(attendingLecturesFrom1_4.isTemporallyContainedBy(goingToUniFrom0_5)))
  test("plan to attend lectures os not contained by going to cinema")(assert(!attendingLecturesFrom1_4.isTemporallyContainedBy(singleActivityStart4End5)))
}
