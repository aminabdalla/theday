package construct

import abstracts.Fixture
import org.scalatest.{FlatSpec, Matchers}

class GeographySpec extends FlatSpec with Matchers {


  "contains()" should "return true if place is contained within other" in new Fixture {

    implicit val hierarchy = world
    val geography = new Geography()

    geography.contains(londonPlace, hounslowPlace) shouldBe true
    geography.contains(hounslowPlace, londonPlace) shouldBe false
    geography.contains(worldPlace, londonPlace) shouldBe true

  }

  "isImmediateParent()" should "return true if place is parent of another other" in new Fixture {

    implicit val hierarchy = world
    val geography = new Geography()

    geography.isImmediateParent(londonPlace, hounslowPlace) shouldBe true
    geography.isImmediateParent(hounslowPlace, londonPlace) shouldBe false
    geography.isImmediateParent(ukPlace, londonPlace) shouldBe true
    geography.isImmediateParent(worldPlace, londonPlace) shouldBe false

  }

  "getParent()" should "returns parent place" in new Fixture {

    implicit val hierarchy = world
    val geography = new Geography()

    geography.getParent(londonPlace) shouldBe Some(ukPlace)
    geography.getParent(ukPlace) shouldBe Some(worldPlace)
    geography.getParent(viennaPlace) shouldBe Some(austriaPlace)
    geography.getParent(worldPlace) shouldBe Some(worldPlace)

  }

  "containingPlace()" should "return the smallest granular place containing all the other places" in new Fixture {
    implicit val hierarchy = world
    val geography = new Geography()

    geography.containingPlace(List(hounslowPlace,londonPlace,parisPlace,viennaPlace)) shouldBe worldPlace
    geography.containingPlace(List(hounslowPlace,donaustadtPlace)) shouldBe worldPlace
    geography.containingPlace(List(francePlace,donaustadtPlace,londonPlace)) shouldBe worldPlace

  }

}
