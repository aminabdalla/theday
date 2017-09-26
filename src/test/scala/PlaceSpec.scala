import construct.{SubPlace, TopPlace}
import org.scalatest.{FlatSpec, FunSuite, Matchers}
import primitive.Geometry.POINT
import primitive.Location

class PlaceSpec extends FlatSpec with Matchers {


  "A Place" should "return the parent" in new Fixture {
    earth.isParentOf(germany) shouldBe true
    earth.isParentOf(europe) shouldBe true
    europe.isParentOf(germany) shouldBe true
  }

  it should "return true if another place is a child" in new Fixture {
    germany.isChild(europe) shouldBe true
    germany.isChild(earth) shouldBe true
    germany.isChild(france) shouldBe false
    earth.isChild(germany) shouldBe false
  }


}
