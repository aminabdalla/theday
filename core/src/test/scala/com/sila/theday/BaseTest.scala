package com.sila.theday

import com.sila.theday.abstracts.{Node, POINT}
import com.sila.theday.abstracts.construct.{PlaceTimePath, PlaceTimeStation, _}
import com.sila.theday.abstracts.primitive._
import org.scalatest.{FlatSpec, Matchers}


trait BaseTest extends FlatSpec with Matchers {

  val worldPlace = Place("World", POINT(1, 1), World)

  val europePlace = Place("Europe", POINT(0, 1), Continent)

  val germanyPlace = Place("Germany", POINT(0, 2), Country)
  val francePlace = Place("France", POINT(0, 2), Country)
  val ukPlace = Place("UK", POINT(0, 3), Country)
  val austriaPlace = Place("Austria", POINT(1, 1), Country)

  val londonPlace = Place("London", POINT(0, 4), City)
  val viennaPlace = Place("Vienna", POINT(1, 1), City)
  val edinburghPlace = Place("Edinburgh", POINT(1, 1), Country)
  val parisPlace = Place("Paris", POINT(1, 1), City)

  val hounslowPlace = Place("Hounslow", POINT(1, 1), District)
  val cinemaPlace = Place("Cinema", POINT(4, 5), Building)
  val donaustadtPlace = Place("Donaustadt", POINT(1, 1), District)

  val uniPlace = Place("Uni", POINT(0, 5), Building)
  val homePlace = Place("Home", POINT(0, 0), Building)
  val supermarketPlace = Place("Supermarket", POINT(1, 1), Building)
  val workPlace = Place("Work", POINT(0, 5), Building)
  val zooPlace = Place("Zoo", POINT(2, 3), Building)

  val workNode = Node(workPlace, List(), londonPlace)
  val uniNode = Node(uniPlace, List(), londonPlace)
  val homeNode = Node(homePlace, List(), londonPlace)
  val cinemaNode = Node(cinemaPlace, List(), londonPlace)
  val supermarketNode = Node(supermarketPlace, List(), londonPlace)
  val zooNode = Node(zooPlace, List(), londonPlace)

  val hounslow = Node(hounslowPlace, List.empty, londonPlace)
  val donaustadt = Node(donaustadtPlace, List.empty, viennaPlace)

  val vienna = Node(viennaPlace, List(donaustadt), austriaPlace)
  val edinburgh = Node(edinburghPlace, List.empty, ukPlace)
  val london = Node(londonPlace, List(hounslow, workNode, uniNode, homeNode, cinemaNode, supermarketNode, zooNode), ukPlace)
  val paris = Node(parisPlace, List.empty, francePlace)

  val austria = Node(austriaPlace, List(vienna), europePlace)
  val uk = Node(ukPlace, List(london, edinburgh), europePlace)
  val germany = Node(germanyPlace, List(), europePlace)
  val france = Node(francePlace, List(paris), europePlace)

  val europe = Node(europePlace, List(germany, france, uk, austria), worldPlace)
  val world = Node(worldPlace, List(europe), worldPlace)

  val geog = new Geography(world)

  val singleActivityStart4End5 = SingleActivity(PlaceTimeStation(cinemaPlace, (4, 5), ""))
  val coveringActivityStart0End5 = SingleActivity(PlaceTimeStation(workPlace, (0, 5), ""))
  val staticActivityStartsAt0 = PlaceTimeStation(homePlace, (0, 2), "home")
  val stayingAtHomeFrom2 = PlaceTimeStation(homePlace, (2, 3), "")
  val movingActivityStartsAt2 = PlaceTimePath(supermarketPlace, zooPlace, (2, 3), "supermarket to zoo")
  val movingActivityWithTravel = PlaceTimePath(homePlace, francePlace, (5, 8), "")
  val overlappingActivity = PlaceTimeStation(uniPlace, (0, 2), "")
  val BeingAtUniFrom3To4PTS = PlaceTimeStation(uniPlace, (3, 4), "")
  val BeingAtUniFrom8To9PTS = PlaceTimeStation(uniPlace, (8, 9), "")
  val overlappingSingleActivity = SingleActivity(overlappingActivity)
  val BeingAtHomeFrom0To2 = SingleActivity(staticActivityStartsAt0)
  val SingleActivityAt4 = SingleActivity(movingActivityStartsAt2)
  val MovingFromSupermarketToZooFrom2To4 = SingleActivity(movingActivityStartsAt2)
  val sequenceWithTravel = ActivitySequence(List(SingleActivity(staticActivityStartsAt0), SingleActivity(movingActivityStartsAt2), SingleActivity(movingActivityWithTravel)))
  val sequenceOfActivities = ActivitySequence(List(SingleActivity(staticActivityStartsAt0), SingleActivity(movingActivityStartsAt2)))
  val stayingAtHomePlan = ActivitySequence(List(SingleActivity(staticActivityStartsAt0), SingleActivity(stayingAtHomeFrom2)))
  val unsortedSequenceOfActivities = ActivitySequence(List(SingleActivity(movingActivityStartsAt2), SingleActivity(staticActivityStartsAt0)))

  val goingToUniFrom0_5 = SingleActivity(PlaceTimeStation(uniPlace, (0, 5), ""))
  val attendingLecturesFrom1_4 = ActivitySequence(List(SingleActivity(PlaceTimeStation(uniPlace, (1, 2), "")), SingleActivity(PlaceTimeStation(uniPlace, (3, 4), ""))))
}
