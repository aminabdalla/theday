import abstracts.Node
import cats.data.NonEmptyList
import construct.Activity.{PlaceTimePath, PlaceTimeStation}
import construct.Plan.{ActivitySequence, SingleActivity}
import construct._
import primitive.Geometry.POINT
import primitive._


class Fixture {

  val earthPlace = Place("Earth", POINT(0, 0), World)
  val europePlace = Place("Europe", POINT(0, 1), Continent)
  val germanyPlace = Place("Germany", POINT(0, 2), Country)
  val francePlace = Place("France", POINT(0, 2), Country)
  val ukPlace = Place("UK", POINT(0, 3), Country)
  val londonPlace = Place("London", POINT(0, 4), City)
  val cinemaPlace = Place("Cinema", POINT(4, 5), Building)
  val uniPlace = Place("Uni", POINT(0, 5), Building)
  val homePlace = Place("Home", POINT(0, 2), Building)
  val supermarketPlace = Place("Supermarket", POINT(1, 1), Building)
  val workPlace = Place("Work", POINT(0, 5), Building)
  val zooPlace = Place("Zoo", POINT(2, 3), Building)

  val workNode = Node(workPlace, List(),londonPlace)
  val uniNode = Node(uniPlace, List(),londonPlace)
  val homeNode = Node(homePlace, List(),londonPlace)
  val cinemaNode = Node(cinemaPlace, List(),londonPlace)
  val supermarketNode = Node(supermarketPlace, List(),londonPlace)
  val zooNode = Node(zooPlace, List(),londonPlace)

  val london = Node(londonPlace, List(workNode, uniNode, homeNode, cinemaNode, supermarketNode, zooNode),ukPlace)
  val germany = Node(germanyPlace, List(),europePlace)
  val france = Node(francePlace, List(),europePlace)
  val europe = Node(europePlace, List(germany, france),europePlace)
  implicit val earth = Node(earthPlace, List(europe),workPlace)

  val geog = new Geography

  val singleActivityStart4End5 = SingleActivity(PlaceTimeStation(cinemaPlace, (4, 5), ""))
  val coveringActivityStart0End5 = SingleActivity(PlaceTimeStation(workPlace, (0, 5), ""))
  val staticActivityStartsAt0 = PlaceTimeStation(homePlace, (0, 2), "")
  val stayingAtHomeFrom2 = PlaceTimeStation(homePlace, (2, 3), "")
  val movingActivityStartsAt2 = PlaceTimePath(supermarketPlace, zooPlace, (2, 3), "")
  val movingActivityWithTravel = PlaceTimePath(homePlace, francePlace, (5, 8), "")
  val overlappingActivity = PlaceTimeStation(uniPlace, (0, 2), "")
  val overlappingSingleActivity = SingleActivity(overlappingActivity)
  val SingleActivityat0 = SingleActivity(staticActivityStartsAt0)
  val SingleActivityat4 = SingleActivity(movingActivityStartsAt2)
  val SingleActivityat2 = SingleActivity(movingActivityStartsAt2)
  val sequenceWithTravel = ActivitySequence(List(SingleActivity(staticActivityStartsAt0), SingleActivity(movingActivityStartsAt2), SingleActivity(movingActivityWithTravel)))
  val sequenceOfActivities = ActivitySequence(List(SingleActivity(staticActivityStartsAt0), SingleActivity(movingActivityStartsAt2)))
  val stayingAtHomePlan = ActivitySequence(List(SingleActivity(staticActivityStartsAt0), SingleActivity(stayingAtHomeFrom2)))
  val unsortedSequenceOfActivities = ActivitySequence(List(SingleActivity(movingActivityStartsAt2), SingleActivity(staticActivityStartsAt0)))

  val goingToUniFrom0_5 = SingleActivity(PlaceTimeStation(uniPlace, (0, 5), ""))
  val attendingLecturesFrom1_4 = ActivitySequence(List(SingleActivity(PlaceTimeStation(uniPlace, (1, 2), "")), SingleActivity(PlaceTimeStation(uniPlace, (3, 4), ""))))
}
