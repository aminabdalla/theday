import Activity.{PlaceTimePath, PlaceTimeStation}
import Plan.{ActivitySequence, SingleActivity}
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class Block$Test extends FunSuite {

  val earthLoc: Location = Location("Earth", (0, 0))
  val europeLoc: Location = Location("Europe", (0, 1))
  val germanyLoc: Location = Location("Germany", (0, 2))
  val ukLoc: Location = Location("UK", (0, 3))
  val londonLoc: Location = Location("London", (0, 4))
  val cinemaLoc: Location = Location("Cinema", (4, 5))
  val uniLoc: Location = Location("Uni", (0, 5))
  val homeLoc: Location = Location("Home", (0, 2))
  val supermarketLoc: Location = Location("Supermarket", (1, 1))
  val workLoc: Location = Location("Work", (0, 5))
  val zooLoc: Location = Location("Zoo", (2, 3))


  val workPlace: Place = SubPlace(workLoc,List())
  val uniPlace: Place = SubPlace(uniLoc,List())
  val homePlace: Place = SubPlace(homeLoc,List())
  val cinemaPlace: Place = SubPlace(cinemaLoc,List())
  val supermarketPlace: Place = SubPlace(supermarketLoc,List())
  val zooPlace: Place = SubPlace(zooLoc,List())

  val london: Place = SubPlace(londonLoc,List(workPlace,uniPlace,homePlace,cinemaPlace,supermarketPlace,zooPlace))
  val earth = TopPlace(loc = earthLoc,List(europe))
  val germany = SubPlace(loc = germanyLoc,List())
  val europe = SubPlace(loc = europeLoc,List(germany))

  val singleActivityStart4End5 = SingleActivity(PlaceTimeStation(cinemaPlace,(4,5),""))
  val coveringActivityStart0End5 = SingleActivity(PlaceTimeStation(workPlace,(0,5),""))
  val staticActivityStartsAt0 = PlaceTimeStation( homePlace,(0,2),"")
  val stayingAtHomeFrom2 = PlaceTimeStation( homePlace,(2,3),"")
  val movingActivityStartsAt2 = PlaceTimePath( supermarketPlace, zooPlace,(2,3),"")
  val overlappingActivity = PlaceTimeStation( uniPlace,(0,2),"")
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
  test("single static activity starts at Home")(assert(SingleActivity(staticActivityStartsAt0).startPlace.getLocation.name == "Home"))

  test("single moving activity starts at Home")(assert(SingleActivity(movingActivityStartsAt2).startPlace.getLocation.name == "Supermarket"))

  test("sequence of static&moving activities start at Home")(assert(sequenceOfActivities.startPlace.getLocation.name == "Home"))

  test("sequence of unsorted static&moving activities start at Home")(assert(unsortedSequenceOfActivities.startPlace.getLocation.name == "Home"))

  test("single static activity ends at Home")(assert(SingleActivity(staticActivityStartsAt0).endPlace.getLocation.name == "Home"))

  test("single moving activity ends at Zoo")(assert(SingleActivity(movingActivityStartsAt2).endPlace.getLocation.name == "Zoo"))

  test("sequence of static&moving activities end at Zoo")(assert(sequenceOfActivities.endPlace.getLocation.name == "Zoo"))

  test("sequence of unsorted static&moving activities end at Zoo")(assert(unsortedSequenceOfActivities.endPlace.getLocation.name == "Zoo"))

  //tests isPossible
  test("single block1 is possible before single2")(assert(SingleActivityat0.before(SingleActivityat2)))
  test("single block2 is not possible before single1")(assert(SingleActivityat2.before(SingleActivityat0) == false))
  test("single block0 is not possible before overlapping block")(assert(SingleActivityat0.before(overlappingSingleActivity) == false))
  test("cannot start one activity if within the time span of another")(assert(SingleActivityat0.before(coveringActivityStart0End5) == false))
  test("cannot start one activity before another that falls within it")(assert(coveringActivityStart0End5.before(SingleActivityat0) == false))


  //tests parallel blocks
  test("single block0 is in parallel to overlapping block")(assert(SingleActivityat0.parallel(overlappingSingleActivity) == true))
  test("single overlapping is in parallel to block0 block")(assert(overlappingSingleActivity.parallel(SingleActivityat0) == true))

  //tests chainable blocks
  test("single block is chainable before or after another block")(assert(SingleActivityat0.isChainable(SingleActivityat2)==true))
  test("sequence block is chainable before or after another block")(assert(sequenceOfActivities.isChainable(singleActivityStart4End5)==true))

  //tests temporalProjection
  test("a plan with two sequenced activities starting at starts at 0 and ends at 3")(assert(unsortedSequenceOfActivities.temporalProjection == (0,3)))

  //tests place projection
  test("a plan from home to the supermarket to the zoo, has a temporal projection of home, supermarket zoo")(assert(unsortedSequenceOfActivities.placeProjection.exists(a => List("Zoo","Supermarket","Home").contains(a.getLocation.name))))
  test("a plan of 2 activities at the same place has a temporal projection of only one place")(assert(stayingAtHomePlan.placeProjection.map(p => p.getLocation.name).contains("Home")),assert(stayingAtHomePlan.placeProjection.size == 1))


  //tests temporal containment
  val goingToUniFrom0_5 = SingleActivity(PlaceTimeStation( uniPlace,(0,5),""))
  val attendingLecturesFrom1_4 = ActivitySequence(List(SingleActivity(PlaceTimeStation( uniPlace,(1,2),"")),SingleActivity(PlaceTimeStation(uniPlace,(3,4),""))))
  test("plan to stay in uni is contained by the plan to attend lectures")(assert(attendingLecturesFrom1_4.during(goingToUniFrom0_5)))
  test("plan to attend lectures os not contained by going to cinema")(assert(!attendingLecturesFrom1_4.during(singleActivityStart4End5)))
}
