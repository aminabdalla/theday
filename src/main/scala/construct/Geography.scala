package construct

import abstracts.Hierarchy
import primitive.Geometry

class Geography(implicit geog : Hierarchy[Place]) {

  def contains(p1: Place, p2: Place): Boolean = geog.subHierarchy(p1).flatMap(sH => sH.subHierarchy(p2)).isDefined

  def transportDistance(p1: Place, p2: Place): Double = euclideanDistance(p1, p2) * 1.5

  def euclideanDistance(p1: Place, p2: Place): Double = p1.euclideanDistance(p2)

  def travelTime(p1: Place, p2: Place): Double = euclideanDistance(p1, p2) * 2

  implicit def toGeom(p: Place): Geometry = p.location.geometry

}
