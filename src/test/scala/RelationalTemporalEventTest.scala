import Activity.PlaceTimeStation
import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

/**
  * Created by amin on 19/03/2017.
  */
@RunWith(classOf[JUnitRunner])
class RelationalTemporalEventTest extends FunSuite {

  val eventFrom0_1 = new PlaceTimeStation(null,(0,1),null)
  val eventFrom0_2 = new PlaceTimeStation(null,(0,2),null)
  val eventFrom0_3 = new PlaceTimeStation(null,(0,3),null)
  val eventFrom1_2 = new PlaceTimeStation(null,(1,2),null)
  val eventFrom1_3 = new PlaceTimeStation(null,(1,3),null)
  val eventFrom2_3 = new PlaceTimeStation(null,(2,3),null)
  val eventFrom2_4 = new PlaceTimeStation(null,(2,4),null)
  val eventFrom3_4 = new PlaceTimeStation(null,(3,4),null)
  val eventFrom0_4 = new PlaceTimeStation(null,(0,4),null)

  test("\"(0,2) starts (0,3)\"")(assert(eventFrom0_2.starts(eventFrom0_3)))
  test("\"(0,2) does not start (0,1)\"")(assert(!eventFrom0_2.starts(eventFrom0_1)))

  test("\"(0,3) is started by (0,2)\"")(assert(eventFrom0_3.startedBy(eventFrom0_2)))
  test("\"(0,1) is not started by (0,2)\"")(assert(!eventFrom0_1.startedBy(eventFrom0_2)))

  test("\"(0,2) is before (3,4)\"")(assert(eventFrom0_2.before(eventFrom3_4)))
  test("\"(3,4) is not before (0,1)\"")(assert(!eventFrom3_4.before(eventFrom0_1)))

  test("\"(3,4) is after (0,1)\"")(assert(eventFrom3_4.after(eventFrom0_1)))
  test("\"(0,2) is not after (3,4)\"")(assert(!eventFrom0_2.after(eventFrom3_4)))

  test("\"(1,2) is during (0,4)\"")(assert(eventFrom1_2.during(eventFrom0_4)))
  test("\"(0,4) is not during (1,2)\"")(assert(!eventFrom0_4.during(eventFrom1_2)))

  test("\"(1,2) is equal (1,2)\"")(assert(eventFrom1_2.equal(eventFrom1_2)))
  test("\"(0,4) is not equal (1,2)\"")(assert(!eventFrom0_4.during(eventFrom1_2)))

  test("\"(1,2) meets (2,3)\"")(assert(eventFrom1_2.meets(eventFrom2_3) ))
  test("\"(1,2) does not meet (3,4)\"")(assert(!eventFrom1_2.meets(eventFrom3_4)))

  test("\"(3,4) is met by (2,3)\"")(assert(eventFrom3_4.metBy(eventFrom2_3)))
  test("\"(1,2) is not met by (2,3)\"")(assert(!eventFrom1_2.metBy(eventFrom2_3)))

  test("\"(0,4) contains (2,3)\"")(assert(eventFrom0_4.contains(eventFrom2_3)))
  test("\"(1,2) does not contain (2,3)\"")(assert(!eventFrom1_2.contains(eventFrom2_3)))

  test("\"(1,3) overlaps (2,4)\"")(assert(eventFrom1_3.overlaps(eventFrom2_4)))
  test("\"(1,2) does not overlap (2,3)\"")(assert(!eventFrom1_2.overlaps(eventFrom2_3)))

  test("\"(2,4) is overlapped (1,3)\"")(assert(eventFrom2_4.overlapped(eventFrom1_3)))
  test("\"(2,3) is not overlapped by (1,2)\"")(assert(!eventFrom2_3.overlaps(eventFrom1_2)))


  test("\"(2,4) finishes (0,4)\"")(assert(eventFrom2_4.finishes(eventFrom0_4)))
  test("\"(2,3) does not finish (0,4)\"")(assert(!eventFrom2_3.finishes(eventFrom0_4)))

  test("\"(0,4) is finished by (2,4)\"")(assert(eventFrom0_4.finishedBy(eventFrom2_4)))
  test("\"(2,3) is not finished by (0,4)\"")(assert(!eventFrom2_3.finishedBy(eventFrom0_4)))
}
