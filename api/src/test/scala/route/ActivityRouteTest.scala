package route

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.concurrent.ScalaFutures
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.http.scaladsl.model.StatusCodes.OK


class ActivityRouteTest extends FlatSpec with ActivityRoute with ScalatestRouteTest with ScalaFutures with Matchers {

  behavior of "ActivityRouteTest"

  it should "accept a plan in valid format" in {

    val singleActivity =
      """{"place" : {
        |"name" : "Vienna",
        |"location" : {"p":"(1,1)"}
        |"granularity" : "City"
        |}}""".stripMargin

    Post("activity", HttpEntity(ContentTypes.`application/json`, singleActivity)) ~> activityPostRoute ~> check {
      status shouldBe OK
    }

  }

}
