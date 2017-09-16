import Activity.{PlaceTimePath, PlaceTimeStation}
import Geometry.POINT
import Plan.{ActivitySequence, SingleActivity}
import org.junit.runner.RunWith
import org.scalatest.{FlatSpec, FunSuite}
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class Block$Test extends FlatSpec {


  it should "return the starttime and endtime of single static activities" in new Fixture {
    assert(SingleActivity(staticActivityStartsAt0).getStartTime == 0)
    assert(SingleActivity(staticActivityStartsAt0).getEndTime == 2)
  }

  it should "return startime and endtime of a single moving activities" in new Fixture {
    assert(SingleActivity(movingActivityStartsAt2).getStartTime == 2)
    assert(SingleActivity(movingActivityStartsAt2).getEndTime == 3)
  }

  it should "return the start and end time of sorted and unsorted sequences of blocks" in new Fixture {
    assert(sequenceOfActivities.getStartTime == 0)
    assert(unsortedSequenceOfActivities.getStartTime == 0)
    assert(sequenceOfActivities.getEndTime == 3)
    assert(unsortedSequenceOfActivities.getEndTime == 3)
  }


  it should "return start and end place of single static activity" in new Fixture {
    assert(unsortedSequenceOfActivities.getStartTime == 0)
    assert(SingleActivity(staticActivityStartsAt0).endPlace.getLocation.name == "Home")
  }

  it should "return start and end place of single moving activity" in new Fixture {
    assert(SingleActivity(movingActivityStartsAt2).startPlace.getLocation.name == "Supermarket")
    assert(SingleActivity(movingActivityStartsAt2).endPlace.getLocation.name == "Zoo")
  }

  it should "return start and end place of sorted and unsorted sequence of activities" in new Fixture {
    assert(SingleActivity(movingActivityStartsAt2).endPlace.getLocation.name == "Zoo")
    assert(unsortedSequenceOfActivities.startPlace.getLocation.name == "Home")
    assert(sequenceOfActivities.endPlace.getLocation.name == "Zoo")
    assert(unsortedSequenceOfActivities.endPlace.getLocation.name == "Zoo")
  }

  //tests isPossible

  it should "check if blocks of activities are before others" in new Fixture {
    assert(SingleActivityat0.before(SingleActivityat2))
    assert(!SingleActivityat2.before(SingleActivityat0))
    assert(!SingleActivityat0.before(overlappingSingleActivity))
    assert(!SingleActivityat0.before(coveringActivityStart0End5))
    assert(!coveringActivityStart0End5.before(SingleActivityat0))
  }


  //tests parallel blocks

  it should "check blocks of activities are parallel" in new Fixture {
    assert(SingleActivityat0.parallel(overlappingSingleActivity) == true)
    assert(overlappingSingleActivity.parallel(SingleActivityat0) == true)
  }

  //tests chainable blocks
  it should "check if blocks of activities are chainable" in new Fixture {
    assert(SingleActivityat0.isChainable(SingleActivityat2) == true)
    assert(sequenceOfActivities.isChainable(singleActivityStart4End5) == true)
  }


  it should "temporally project activities" in new Fixture {
    assert(unsortedSequenceOfActivities.temporalProjection ==(0, 3))
  }

  //tests place projection
  it should "spatially project activities" in new Fixture {
    assert(unsortedSequenceOfActivities.placeProjection.exists(a => List("Zoo", "Supermarket", "Home").contains(a.getLocation.name)))
    assert(stayingAtHomePlan.placeProjection.map(p => p.getLocation.name).contains("Home"))
    assert(stayingAtHomePlan.placeProjection.size == 1)
  }

  //tests temporal containment
  it should "check if activities are temporally contained" in new Fixture {
    assert(attendingLecturesFrom1_4.during(goingToUniFrom0_5))
    assert(!attendingLecturesFrom1_4.during(singleActivityStart4End5))
  }

}
