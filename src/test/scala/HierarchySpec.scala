

import org.scalatest.{FlatSpec, Matchers}

class HierarchySpec extends BaseTest {

  behavior of "TreeSpec"

  it should "return the sub hierarchy from a position" in {
    world.subHierarchy(ukPlace) shouldBe Some(uk)
    world.subHierarchy(londonPlace) shouldBe Some(london)
    world.subHierarchy(hounslowPlace) shouldBe Some(hounslow)
    world.subHierarchy(viennaPlace) shouldBe Some(vienna)
    world.subHierarchy(donaustadtPlace) shouldBe Some(donaustadt)
    world.subHierarchy(edinburghPlace) shouldBe Some(edinburgh)
    world.subHierarchy(worldPlace) shouldBe Some(world)
  }

  it should "get a subhierarchy for a specific position" in {


  }

  it should "value" in {

  }

  it should "subTree" in {

  }

}