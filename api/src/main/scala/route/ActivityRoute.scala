package route

import akka.http.scaladsl.server.Directives._
import com.sila.theday.abstracts.construct.Activity
import route.uk.revolut.api.route.JsonMarshalling

trait ActivityRoute extends JsonMarshalling {

  lazy val activityPostRoute =
    post {
      path("activity") {
        entity(as[Activity]) { activity =>
          complete("thx")
        }
      }
    }

}
