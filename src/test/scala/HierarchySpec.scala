package abstracts

import org.scalatest.{FlatSpec, Matchers}

class HierarchySpec extends FlatSpec with Matchers {

  behavior of "TreeSpec"

  it should "return the sub hierarchy from a position" in new Fixture {
    world.subHierarchy("UK") shouldBe Some(uk)
    world.subHierarchy("London") shouldBe Some(london)
    world.subHierarchy("Hounslow") shouldBe Some(hounslow)
    world.subHierarchy("Hounslow") shouldBe Some(hounslow)
    world.subHierarchy("Vienna") shouldBe Some(vienna)
    world.subHierarchy("Donaustadt") shouldBe Some(donaustadt)
    world.subHierarchy("Edinburgh") shouldBe Some(edinburgh)
  }

  it should "get a subhierarchy for a specific position" in new Fixture {


  }

  it should "value" in {

  }

  it should "subTree" in {

  }

}

class Fixture {

  val hounslow = Node("Hounslow",List.empty)
  val donaustadt = Node("Donaustadt",List.empty)
  val vienna = Node("Vienna",List(donaustadt))
  val austria = Node("Austria",List(vienna))
  val edinburgh = Node("Edinburgh", List.empty)
  val london = Node("London", List(hounslow))
  val paris = Node("Paris", List.empty)
  val france = Node("France", List(paris))
  val uk = Node("UK", List(london,edinburgh))
  val world = Node("World",List(uk,france,austria))

}
