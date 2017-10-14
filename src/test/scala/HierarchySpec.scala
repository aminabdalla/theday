package abstracts

import construct.Place
import org.scalatest.{FlatSpec, Matchers}
import primitive._

class HierarchySpec extends FlatSpec with Matchers {

  behavior of "TreeSpec"

  it should "return the sub hierarchy from a position" in new Fixture {
    world.subHierarchy(ukPlace) shouldBe Some(uk)
    world.subHierarchy(londonPlace) shouldBe Some(london)
    world.subHierarchy(hounslowPlace) shouldBe Some(hounslow)
    world.subHierarchy(viennaPlace) shouldBe Some(vienna)
    world.subHierarchy(donaustadtPlace) shouldBe Some(donaustadt)
    world.subHierarchy(edinburghPlace) shouldBe Some(edinburgh)
    world.subHierarchy(worldPlace) shouldBe Some(world)
  }

  it should "get a subhierarchy for a specific position" in new Fixture {


  }

  it should "value" in {

  }

  it should "subTree" in {

  }

}

class Fixture {

  val hounslowPlace = Place("Hounslow", Geometry.POINT(1, 1), District)
  val donaustadtPlace = Place("Donaustadt", Geometry.POINT(1, 1), District)
  val viennaPlace = Place("Vienna", Geometry.POINT(1, 1), City)
  val austriaPlace = Place("Austria", Geometry.POINT(1, 1), Country)
  val edinburghPlace = Place("Edinburgh", Geometry.POINT(1, 1), Country)
  val londonPlace = Place("London", Geometry.POINT(1, 1), City)
  val parisPlace = Place("Paris", Geometry.POINT(1, 1), City)
  val francePlace = Place("France", Geometry.POINT(1, 1), Country)
  val ukPlace = Place("UK", Geometry.POINT(1, 1), Country)
  val worldPlace = Place("World", Geometry.POINT(1, 1), World)

  val hounslow = Node(hounslowPlace, List.empty, londonPlace)
  val donaustadt = Node(donaustadtPlace, List.empty, viennaPlace)
  val vienna = Node(viennaPlace, List(donaustadt), austriaPlace)
  val austria = Node(austriaPlace, List(vienna), worldPlace)
  val edinburgh = Node(edinburghPlace, List.empty, ukPlace)
  val london = Node(londonPlace, List(hounslow), ukPlace)
  val paris = Node(parisPlace, List.empty, francePlace)
  val france = Node(francePlace, List(paris), worldPlace)
  val uk = Node(ukPlace, List(london, edinburgh), worldPlace)
  val world = Node(worldPlace, List(uk, france, austria), worldPlace)

}
