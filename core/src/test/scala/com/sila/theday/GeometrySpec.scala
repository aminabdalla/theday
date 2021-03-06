package com.sila.theday

import com.sila.theday.abstracts.{BBOX, POINT}
import org.scalatest.{FlatSpec, Matchers}

class GeometrySpec extends FlatSpec with Matchers {

  val point1 = POINT(0, 0)
  val point2 = POINT(1, 1)

  val pointLoc = POINT(1, 2);
  val bboxLoc = BBOX((0, 0), (1, 1));

  "The Geometry" should "be convertable" in {
    pointLoc.toPointRepresentation._1 shouldBe 1
    pointLoc.toPointRepresentation._2 shouldBe 2
    bboxLoc.toPointRepresentation._1 shouldBe 0.5
    bboxLoc.toPointRepresentation._2 shouldBe 0.5
  }

  it should "compute the euclidean distance for every type of geomentry" in {
    point1.euclideanDistance(point2) shouldBe 1.4142135623730951
    point2.euclideanDistance(point1) shouldBe 1.4142135623730951
    point1.euclideanDistance(bboxLoc) shouldBe 0.7071067811865476
    bboxLoc.euclideanDistance(point1) shouldBe 0.7071067811865476
  }

}
