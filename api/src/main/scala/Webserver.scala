import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.sila.theday.abstracts.construct.Activity.PlaceTimeStation
import com.sila.theday.abstracts.construct.{Place, SingleActivity}
import com.sila.theday.abstracts.primitive.{City, Geometry}
import com.sila.theday.abstracts.primitive.Geometry.POINT

import scala.io.StdIn


object WebServer {

  val someActivity = SingleActivity(PlaceTimeStation(Place("home", POINT(0,0), City), (3, 4), "home"))

  def main(args: Array[String]) {

    implicit val system = ActorSystem("my-system")
    implicit val materializer = ActorMaterializer()
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext = system.dispatcher

    val route =
      path("hello") {
        get {
//          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, someActivity.toString))
        }
      }

    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }
}