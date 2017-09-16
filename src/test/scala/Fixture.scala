import Activity.{PlaceTimePath, PlaceTimeStation}
import Geometry.POINT
import Plan.{ActivitySequence, SingleActivity}


class Fixture {

  val earthLoc: Location = Location("Earth", POINT(0, 0))
  val europeLoc: Location = Location("Europe", POINT(0, 1))
  val germanyLoc: Location = Location("Germany", POINT(0, 2))
  val ukLoc: Location = Location("UK", POINT(0, 3))
  val londonLoc: Location = Location("London", POINT(0, 4))
  val cinemaLoc: Location = Location("Cinema", POINT(4, 5))
  val uniLoc: Location = Location("Uni", POINT(0, 5))
  val homeLoc: Location = Location("Home", POINT(0, 2))
  val supermarketLoc: Location = Location("Supermarket", POINT(1, 1))
  val workLoc: Location = Location("Work", POINT(0, 5))
  val zooLoc: Location = Location("Zoo", POINT(2, 3))

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

  val goingToUniFrom0_5 = SingleActivity(PlaceTimeStation(uniPlace, (0, 5), ""))
  val attendingLecturesFrom1_4 = ActivitySequence(List(SingleActivity(PlaceTimeStation(uniPlace, (1, 2), "")), SingleActivity(PlaceTimeStation(uniPlace, (3, 4), ""))))
}
