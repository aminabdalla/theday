import construct.Plan.SingleActivity
import org.scalatest.{FlatSpec, Matchers}

class PlanSpec extends FlatSpec with Matchers {

  "A plan" should "return the starttime and endtime of single static activities" in new Fixture {
    SingleActivity(staticActivityStartsAt0).getStartTime shouldEqual 0
    SingleActivity(staticActivityStartsAt0).getEndTime shouldEqual 2
  }

  it should "return startime and endtime of a single moving activity" in new Fixture {
    SingleActivity(movingActivityStartsAt2).getStartTime shouldEqual 2
    SingleActivity(movingActivityStartsAt2).getEndTime shouldEqual 3
  }

  it should "return the start and end time of sorted and unsorted sequences of blocks" in new Fixture {
    sequenceOfActivities.getStartTime shouldBe 0
    unsortedSequenceOfActivities.getStartTime shouldBe 0
    sequenceOfActivities.getEndTime shouldBe 3
    unsortedSequenceOfActivities.getEndTime shouldBe 3
  }

  it should "return start and end place of single static activity" in new Fixture {
    unsortedSequenceOfActivities.getStartTime shouldBe 0
    SingleActivity(staticActivityStartsAt0).endPlace.name shouldBe "Home"
  }

  it should "return start and end place of single moving activity" in new Fixture {
    SingleActivity(movingActivityStartsAt2).startPlace.name shouldBe "Supermarket"
    SingleActivity(movingActivityStartsAt2).endPlace.name shouldBe "Zoo"
  }

  it should "return start and end place of sorted and unsorted sequence of activities" in new Fixture {
    SingleActivity(movingActivityStartsAt2).endPlace.name shouldBe "Zoo"
    unsortedSequenceOfActivities.startPlace.name shouldBe "Home"
    sequenceOfActivities.endPlace.name shouldBe "Zoo"
    unsortedSequenceOfActivities.endPlace.name shouldBe "Zoo"
  }

  it should "check if blocks of activities are before others" in new Fixture {
    SingleActivityat0.before(SingleActivityat2) shouldBe true
    SingleActivityat2.before(SingleActivityat0) shouldBe false
    SingleActivityat0.before(overlappingSingleActivity) shouldBe false
    SingleActivityat0.before(coveringActivityStart0End5) shouldBe false
    coveringActivityStart0End5.before(SingleActivityat0) shouldBe false
  }

  it should "check blocks of activities are parallel" in new Fixture {
    SingleActivityat0.parallel(overlappingSingleActivity) shouldBe true
    overlappingSingleActivity.parallel(SingleActivityat0) shouldBe true
  }

  it should "check if blocks of activities are chainable" in new Fixture {
    SingleActivityat0.isChainable(SingleActivityat2) shouldBe true
    sequenceOfActivities.isChainable(singleActivityStart4End5) shouldBe true
  }

  it should "temporally project activities" in new Fixture {
    unsortedSequenceOfActivities.temporalProjection shouldBe(0, 3)
  }

  it should "spatially project activities" in new Fixture {
    unsortedSequenceOfActivities.placeProjection.exists(a => List("Zoo", "Supermarket", "Home").contains(a.name)) shouldBe true
    stayingAtHomePlan.placeProjection.map(p => p.name).contains("Home") shouldBe true
    stayingAtHomePlan.placeProjection.size shouldBe 1
  }

  it should "check if activities are temporally contained" in new Fixture {
    attendingLecturesFrom1_4.during(goingToUniFrom0_5) shouldBe true
    attendingLecturesFrom1_4.during(singleActivityStart4End5) shouldBe false
  }

  it should "return the covering spatial granularity of multiple activities" in new Fixture {
//    sequenceOfActivities.containingPlace shouldBe london
//    sequenceWithTravel.containingPlace shouldBe europe
    fail
  }

  it should "return the covering temporal granularity" in new Fixture {
    fail
  }

}
