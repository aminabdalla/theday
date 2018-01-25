object TestingStuff extends App {

  var counter = 0
  def retryMethodStub: () => Boolean = { () =>
    if(counter == 100) true
    else {
      counter += 1
      false
    }
  }

  import scala.concurrent.duration._

  def retry(duration : Duration, retrySteps : Duration, retryMethod: () => Boolean) = {
    var attempts = (0 to duration.toSeconds.toInt by retrySteps.toSeconds.toInt)
      .takeWhile{ i =>
        Thread.sleep(retrySteps.toMillis)
        println(s"taking based on retry resukt of: ${retryMethod()}")
        !retryMethod()
      }

        println(s"return the successResult of: ${retryMethod()}")
  }

  retry(10.second,2.second,retryMethodStub)



}
