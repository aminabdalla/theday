import org.scalatest.FunSuite
import primitive._

class GranularitySpec extends FunSuite {

  test("meetingpoint granularity") {
    assert(MeetingPoint <= MeetingPoint)
    assert(MeetingPoint < Room)
    assert(MeetingPoint < Building)
    assert(MeetingPoint < District)
    assert(MeetingPoint < City)
    assert(MeetingPoint < State)
    assert(MeetingPoint < Country)
    assert(MeetingPoint < World)
  }

  test("city granularity") {
    assert(City > MeetingPoint)
    assert(City > Room)
    assert(City > Building)
    assert(City > District)
    assert(City <= City)
    assert(City < State)
    assert(City < Country)
    assert(City < World)
  }

  test("coarsen"){
    assert(Room.coarsen == Building)
    assert(City.coarsen == State)
    assert(City.coarsen.coarsen == Country)
  }

  test("common granule"){
    assert(Room.commonUpperGranule(Building) == Building)
    assert(City.commonUpperGranule(State) == State)
    assert(Room.commonUpperGranule(Building) == Building)
  }

}
