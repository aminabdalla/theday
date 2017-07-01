import org.scalatest.FunSuite

class GranularityTest extends FunSuite {

  test("meetingpoint granularity") {
    assert(MeetingPoint <= MeetingPoint)
    assert(MeetingPoint <= Room)
    assert(MeetingPoint <= Building)
    assert(MeetingPoint < Neighbourhood)
    assert(MeetingPoint < Park)
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
    assert(City > Park)
    assert(City > Neighbourhood)
    assert(City > District)
    assert(City <= City)
    assert(City < State)
    assert(City < Country)
    assert(City < World)
  }

  test("coarsen"){
    assert(Room.coarsen == Park)
    assert(City.coarsen == State)
    assert(City.coarsen.coarsen == Country)
  }

  test("common granule"){
    assert(Room.commonUpperGranule(Park) == Park)
    assert(City.commonUpperGranule(State) == State)
    assert(Room.commonUpperGranule(Building) == Park)
  }

}
