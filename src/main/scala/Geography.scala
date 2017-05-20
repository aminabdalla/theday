
trait Geography {

  def spatiallyContains(p1 : Place, p2:Place) : Boolean
  def transportDistance(p1 : Place, p2:Place) : Double
  def euclideanDistance(p1 : Place, p2:Place): Double
  def travelTime(p1 : Place, p2:Place) : Double
  def pointRepresentation(p : Place): (Int,Int)

}


object Geography extends Geography{
  override def spatiallyContains(p1: Place, p2: Place): Boolean = ???

  override def pointRepresentation(p: Place): (Int, Int) = ???

  override def euclideanDistance(p1: Place, p2: Place): Double = ???

  override def travelTime(p1: Place, p2: Place): Double = ???

  override def transportDistance(p1: Place, p2: Place): Double = ???
}
