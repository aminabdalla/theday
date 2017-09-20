package primitive




sealed trait Geometry {

type GeomPoint = (Double,Double)

  def euclideanDistance(g2: Geometry): Double = {
    val term1 = Math.pow((g2.toPointRepresentation._1 - this.toPointRepresentation._1),2)
    val term2 = Math.pow((g2.toPointRepresentation._2 - this.toPointRepresentation._2),2)
    Math.sqrt(term1 + term2)
  }
  def toPointRepresentation: GeomPoint = this match {
    case Geometry.BBOX(ll,ur) => ( (ur._1 - ll._1)/2, (ur._2 - ll._2)/2)
    case Geometry.POINT(p) => p
  }

}

object Geometry extends Geometry{
  case class BBOX(var lowerLeft: GeomPoint, var upperRight : GeomPoint) extends Geometry
  case class POINT(var p: GeomPoint) extends Geometry
}


