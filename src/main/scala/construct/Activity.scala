package construct

import construct.TemporalEvent._

abstract class Activity(var name: String, var timeSpan: Interval) extends RelationalTemporalEvent {
  def getPlaces: List[Place]

  def getStartPlace: Place

  def getEndPlace: Place

  def getDescription: String = name

  def getStartTime: Int = timeSpan._1

  def getEndTime: Int = timeSpan._2
}

object Activity {

  final case class PlaceTimeStation(var place: Place,
                                    var span: Interval,
                                    var descr: String) extends Activity(descr, span) {

    def apply(p: Place, span: Interval, descr: String) = new PlaceTimeStation(p, span, descr)

    override def getPlaces: List[Place] = List(this.place)

    override def getStartPlace: Place = place

    override def getEndPlace: Place = place
  }

  final case class PlaceTimePath(var startPlace: Place,
                                 var endPlace: Place,
                                 var span: Interval,
                                 var descr: String) extends Activity(descr, span) {

    def apply(sp: Place, ep: Place, s: Interval, d: String) = new PlaceTimePath(sp, ep, s, d)

    override def getPlaces: List[Place] = List(this.startPlace, this.endPlace)

    override def getStartPlace: Place = startPlace

    override def getEndPlace: Place = endPlace
  }

}