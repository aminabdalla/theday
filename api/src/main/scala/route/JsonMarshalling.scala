package route
package uk.revolut.api.route

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.sila.theday.abstracts.construct._
import com.sila.theday.abstracts.primitive.{City, Granularity, Room}
import com.sila.theday.abstracts.{BBOX, Geometry, POINT}
import spray.json.{DefaultJsonProtocol, JsObject, JsString, JsValue, RootJsonFormat}


trait JsonMarshalling extends SprayJsonSupport {
  import DefaultJsonProtocol._
//  implicit val granularityJsonFormat = jsonFormat(MeetingPoint)

  implicit val pointJsonFormat = jsonFormat1(POINT)
  implicit val bboxJsonFormat = jsonFormat2(BBOX)
  implicit val placeJsonFormat = jsonFormat3(Place)
  implicit val placeTimeStationJsonFormat = jsonFormat3(PlaceTimeStation)
  implicit val placeTimePathJsonFormat = jsonFormat4(PlaceTimePath)
  implicit val singleActivityJsonFormat = jsonFormat1(SingleActivity)
  implicit val activitySequenceJsonFormat = jsonFormat1(ActivitySequence)
  implicit val activityAlternativesJsonFormat = jsonFormat1(ActivityAlternatives)


  implicit object GeometryJsonFormat extends RootJsonFormat[Geometry] {
    def write(geo: Geometry) = geo match {
      case bbox: BBOX => bboxJsonFormat.write(bbox)
      case point: POINT => pointJsonFormat.write(point)
    }
    def read(value: JsValue) : Geometry = value.asJsObject.fields.head._1 match {
      case "point" => pointJsonFormat.read(value)
      case _ => bboxJsonFormat.read(value)
    }
  }

  implicit object GranularityJsonFormat extends RootJsonFormat[Granularity] {
    override def read(json: JsValue): Granularity = json.asInstanceOf[JsString].value match {
      case "Room" => Room
      case "City" => City
    }

    override def write(obj: Granularity): JsValue = obj match {
      case g => JsString(g.toString)
    }
  }

  implicit object ActivityJsonFormat extends RootJsonFormat[Activity] {
    override def write(obj: Activity): JsValue = obj match {
      case pts: PlaceTimeStation => {
        JsObject.apply(placeTimeStationJsonFormat.write(pts).asJsObject.fields + ("activityType" -> JsString("PTS")))
      }
      case ptp: PlaceTimePath => {
        JsObject.apply(placeTimePathJsonFormat.write(ptp).asJsObject.fields + ("activityType" -> JsString("PTP")))
      }
    }

    override def read(json: JsValue) = {
      json.asInstanceOf[JsObject].getFields("activityType").head.convertTo[String] match {
        case "PTS" => placeTimeStationJsonFormat.read(json)
        case "PTP" => placeTimePathJsonFormat.read(json)
      }
    }
  }

  implicit object PlanJsonFormat extends RootJsonFormat[Plan] {
    override def write(obj: Plan): JsValue = ???

    override def read(json: JsValue): Plan = ???
  }
}