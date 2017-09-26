import org.scalatest.FunSuite
import primitive.Geometry

class GeometrySpec extends FunSuite {

  val point1 = Geometry.POINT(0, 0)
  val point2 = Geometry.POINT(1, 1)

  val pointLoc = Geometry.POINT(1, 2);
  val bboxLoc = Geometry.BBOX((0, 0), (1, 1));

  test("point stays point")(assert(pointLoc.toPointRepresentation._1 == 1 && pointLoc.toPointRepresentation._2 == 2))
  test("bbox is converted to point, x-ccordinate")(assert(bboxLoc.toPointRepresentation._1 == 0.5))
  test("bbox is converted to point, y-ccordinate")(assert(bboxLoc.toPointRepresentation._2 == 0.5))

  test("testEuclideanDistanceForward")(assert(point1.euclideanDistance(point2) == 1.4142135623730951))
  test("testEuclideanDistanceBackward")(assert(point2.euclideanDistance(point1) == 1.4142135623730951))
  test("testEuclideanDistanceBetweenBoxAndPoint")(assert(point1.euclideanDistance(bboxLoc) == 0.7071067811865476))
  test("testEuclideanDistanceBetweenPointAndBox")(assert(bboxLoc.euclideanDistance(point1) == 0.7071067811865476))

}
