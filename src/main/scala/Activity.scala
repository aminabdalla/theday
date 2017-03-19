import Activity.{Interval, Place}

abstract class Activity(var name: String){
  def getPlaces : List[Place]
  def getTimeSpan : Interval
}


object Activity {
  type Place = String
  type Interval = (Int, Int)

  final case class PlaceTimeStation(
                                     var place: Place,
                                     var interval: Interval,
                                     var descr: String) extends Activity(descr) {

    override def getPlaces: List[Place] = List(this.place)

    override def getTimeSpan: (Int, Int) = this.interval
  }

  final case class PlaceTimePath(
                                  var startPlace: Place,
                                  var endPlace: Place,
                                  var interval: Interval,
                                  var descr: String) extends Activity(descr) {

    override def getPlaces: List[Place] = List(this.startPlace,this.endPlace)

    override def getTimeSpan: (Int, Int) = this.interval
  }

}
