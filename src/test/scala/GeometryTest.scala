import org.scalatest.FunSuite

class GeometryTest extends FunSuite {

  val pointLoc = Geometry.POINT(1,2);
  val bboxLoc = Geometry.BBOX((0,0),(1,1));
  test("point stays point")(assert(pointLoc.toPointRepresentation._1 == 1 &&  pointLoc.toPointRepresentation._2 == 2))
  test("bbox is converted to point, x-ccordinate")(assert(bboxLoc.toPointRepresentation._1 == 0.5))
  test("bbox is converted to point, y-ccordinate")(assert(bboxLoc.toPointRepresentation._2 == 0.5))

  test("testEuclideanDistance") {

  }

}
