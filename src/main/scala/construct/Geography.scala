package construct

import primitive.Geometry

trait Geography {

  def spatiallyContains(p1 : Place, p2:Place) : Boolean
  def transportDistance(p1 : Place, p2:Place) : Double
  def euclideanDistance(p1 : Place, p2:Place): Double
  def travelTime(p1 : Place, p2:Place) : Double
  def geomRepresentation(p : Place): Geometry
  implicit def toGeom(p:Place) : Geometry = p.getLocation.geometry

}


object Geography extends Geography{
  override def spatiallyContains(p1: Place, p2: Place): Boolean = ???

  override def geomRepresentation(p: Place): Geometry = p

  override def euclideanDistance(p1: Place, p2: Place): Double = p1.euclideanDistance(p2)

  override def travelTime(p1: Place, p2: Place): Double = euclideanDistance(p1,p2) * 2

  override def transportDistance(p1: Place, p2: Place): Double =  euclideanDistance(p1,p2) * 1.5
}
