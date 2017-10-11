package abstracts

import construct.Place
import org.scalatest.{FlatSpec, Matchers}
import primitive.{District, Geometry, Location}

class HierarchySpec extends FlatSpec with Matchers {

  behavior of "TreeSpec"

  it should "return the sub hierarchy from a position" in new Fixture {
    world.subHierarchy(ukPlace) shouldBe Some(uk)
    world.subHierarchy(londonPlace) shouldBe Some(london)
    world.subHierarchy(hounslowPlace) shouldBe Some(hounslow)
    world.subHierarchy(viennaPlace) shouldBe Some(vienna)
    world.subHierarchy(donaustadtPlace) shouldBe Some(donaustadt)
    world.subHierarchy(edinburghPlace) shouldBe Some(edinburgh)
  }

  it should "get a subhierarchy for a specific position" in new Fixture {


  }

  it should "value" in {

  }

  it should "subTree" in {

  }

}

class Fixture {

  val hounslowPlace = Place("Hounslow",Location(Geometry.POINT(1,1)),District)
  val donaustadtPlace = Place("Donaustadt",Location(Geometry.POINT(1,1)),District)
  val viennaPlace = Place("Vienna",Location(Geometry.POINT(1,1)),District)
  val austriaPlace = Place("Austria",Location(Geometry.POINT(1,1)),District)
  val edinburghPlace =Place("Edinburgh",Location(Geometry.POINT(1,1)),District)
  val londonPlace = Place("London",Location(Geometry.POINT(1,1)),District)
  val parisPlace =Place("Paris",Location(Geometry.POINT(1,1)),District)
  val francePlace = Place("France",Location(Geometry.POINT(1,1)),District)
  val ukPlace = Place("UK",Location(Geometry.POINT(1,1)),District)
  val worldPlace = Place("World",Location(Geometry.POINT(1,1)),District)

  val hounslow = Node(hounslowPlace,List.empty)
  val donaustadt = Node(donaustadtPlace,List.empty)
  val vienna = Node(viennaPlace,List(donaustadt))
  val austria = Node(austriaPlace,List(vienna))
  val edinburgh = Node(edinburghPlace, List.empty)
  val london = Node(londonPlace, List(hounslow))
  val paris = Node(parisPlace, List.empty)
  val france = Node(francePlace, List(paris))
  val uk = Node(ukPlace, List(london,edinburgh))
  val world = Node(worldPlace,List(uk,france,austria))

}
