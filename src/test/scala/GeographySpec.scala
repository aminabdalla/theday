import construct.Geography

class GeographySpec extends BaseTest {


  "contains()" should "return true if place is contained within other" in {

    val geography = new Geography(world)

    geography.contains(londonPlace, hounslowPlace) shouldBe true
    geography.contains(hounslowPlace, londonPlace) shouldBe false
    geography.contains(worldPlace, londonPlace) shouldBe true
    geography.contains(europePlace,uniPlace) shouldBe true

  }

  "isImmediateParent()" should "return true if place is parent of another other" in {

    val geography = new Geography(world)

    geography.isImmediateParent(londonPlace, hounslowPlace) shouldBe true
    geography.isImmediateParent(hounslowPlace, londonPlace) shouldBe false
    geography.isImmediateParent(ukPlace, londonPlace) shouldBe true
    geography.isImmediateParent(worldPlace, londonPlace) shouldBe false

  }

  "getParent()" should "returns parent place" in  {

    val geography = new Geography(world)

    geography.getParent(londonPlace) shouldBe Some(ukPlace)
    geography.getParent(ukPlace) shouldBe Some(europePlace)
    geography.getParent(viennaPlace) shouldBe Some(austriaPlace)
    geography.getParent(worldPlace) shouldBe Some(worldPlace)

  }

  "containingPlace()" should "return the smallest granular place containing all the other places" in {
    val geography = new Geography(world)

    geography.containingPlace(List(hounslowPlace,londonPlace,parisPlace,viennaPlace)) shouldBe europePlace
    geography.containingPlace(List(hounslowPlace,donaustadtPlace)) shouldBe europePlace
    geography.containingPlace(List(francePlace,donaustadtPlace,londonPlace,europePlace)) shouldBe europePlace
    geography.containingPlace(List(uniPlace,hounslowPlace)) shouldBe londonPlace

  }

}
