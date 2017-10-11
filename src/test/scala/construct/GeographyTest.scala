package construct

import abstracts.Fixture
import org.scalatest.{FlatSpec, Matchers}

class GeographyTest extends FlatSpec with Matchers {


  "testContains" should "" in new Fixture {

    implicit val hierarchy = world
    val geography = new Geography()

    geography.contains(londonPlace,hounslowPlace) shouldBe true
    geography.contains(hounslowPlace,londonPlace) shouldBe false
    geography.contains(hounslowPlace,londonPlace) shouldBe false

  }

}
