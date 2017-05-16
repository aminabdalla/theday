import java.sql.Time

import TemporalEvent.Interval

abstract class Activity(var name: String,var timeSpan : Interval) extends RelationalTemporalEvent{
  def getPlaces : List[Location]
  def getStartPlace: Location
  def getEndPlace : Location
  def getDescription : String = name
  def getStartTime : Int = timeSpan._1
  def getEndTime : Int = timeSpan._2
}


object Activity {

  final case class PlaceTimeStation(
                                     var place: Location,
                                     var span: Interval,
                                     var descr: String) extends Activity(descr,span) {

    def apply(p : Location,span:Interval,descr:String) = new PlaceTimeStation(p,span,descr)

    override def getPlaces: List[Location] = List(this.place)

    override def getStartPlace: Location = place

    override def getEndPlace: Location = place
  }

  final case class PlaceTimePath(
                                  var startPlace: Location,
                                  var endPlace: Location,
                                  var span: Interval,
                                  var descr: String) extends Activity(descr,span) {

    def apply(sp : Location,ep:Location,s:Interval,d:String) = new PlaceTimePath(sp,ep,s,d)

    override def getPlaces: List[Location] = List(this.startPlace,this.endPlace)

    override def getStartPlace: Location = startPlace

    override def getEndPlace: Location = endPlace
  }

}
