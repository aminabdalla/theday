import Activity.{Interval, Place}

abstract class Activity(var name: String,var interval :(Int,Int)){
  def getPlaces : List[Place]
  def getTimeSpan : Interval = interval
  def getStartTime : Int = interval._1
  def getEndTime : Int = interval._2
  def getStartPlace: Place
  def getEndPlace : Place
  def getDescription : String = name
}


object Activity {
  type Place = String
  type Interval = (Int, Int)

  final case class PlaceTimeStation(
                                     var place: Place,
                                     var span: Interval,
                                     var descr: String) extends Activity(descr,span) {

    override def getPlaces: List[Place] = List(this.place)

    override def getStartPlace: Place = place

    override def getEndPlace: Place = place
  }

  final case class PlaceTimePath(
                                  var startPlace: Place,
                                  var endPlace: Place,
                                  var span: Interval,
                                  var descr: String) extends Activity(descr,span) {

    override def getPlaces: List[Place] = List(this.startPlace,this.endPlace)

    override def getStartPlace: Place = startPlace

    override def getEndPlace: Place = endPlace
  }

}
