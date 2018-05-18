package route

import com.sila.theday.abstracts.construct.{Place, PlaceTimePath, PlaceTimeStation}
import com.sila.theday.abstracts.primitive.{City, Room}
import com.sila.theday.abstracts.{BBOX, POINT}
import org.scalatest.{FlatSpec, Matchers}
import route.uk.revolut.api.route.JsonMarshalling

class JsonMarshallingSpec extends FlatSpec with JsonMarshalling with Matchers {

  "GeometryJsonFormat" should "write and read a Geometry object" in {
    val point = POINT(0l,1l)
    val bbox = BBOX((0l,0l),(1l,1l))

    val jsonPoint = GeometryJsonFormat.write(point)
    val jsonBbox = GeometryJsonFormat.write(bbox)

    GeometryJsonFormat.read(jsonPoint) shouldBe point
    GeometryJsonFormat.read(jsonBbox) shouldBe bbox
  }

  "ActivityJsonFormat" should "write and read an Activity object" in {
    val vienna = Place("vienna", POINT(0l, 0l), City)
    val london = Place("london", POINT(1l, 1l), Room)
    val pts = PlaceTimeStation(vienna,(0,1),"example")
    val ptp = PlaceTimePath(vienna,london,(0,1),"example")

    val jsonPTS = ActivityJsonFormat.write(pts)
    val jsonPTP = ActivityJsonFormat.write(ptp)

    ActivityJsonFormat.read(jsonPTS) shouldBe pts
    ActivityJsonFormat.read(jsonPTP) shouldBe ptp
  }

}
