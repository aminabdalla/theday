package com.sila.theday

import com.sila.theday.abstracts.construct.Activity.{PlaceTimePath, PlaceTimeStation}
import com.sila.theday.abstracts.construct.Plan.{ActivitySequence, SingleActivity}

class PlanSpec extends BaseTest {

  "A plan" should "return the starttime and endtime of single static activities" in {
    SingleActivity(staticActivityStartsAt0).getStartTime shouldEqual 0
    SingleActivity(staticActivityStartsAt0).getEndTime shouldEqual 2
  }

  it should "return startime and endtime of a single moving activity" in {
    SingleActivity(movingActivityStartsAt2).getStartTime shouldEqual 2
    SingleActivity(movingActivityStartsAt2).getEndTime shouldEqual 3
  }

  it should "return the start and end time of sorted and unsorted sequences of blocks" in {
    sequenceOfActivities.getStartTime shouldBe 0
    unsortedSequenceOfActivities.getStartTime shouldBe 0
    sequenceOfActivities.getEndTime shouldBe 3
    unsortedSequenceOfActivities.getEndTime shouldBe 3
  }

  it should "return start and end place of single static activity" in {
    unsortedSequenceOfActivities.getStartTime shouldBe 0
    SingleActivity(staticActivityStartsAt0).endPlace.name shouldBe "Home"
  }

  it should "return start and end place of single moving activity" in {
    SingleActivity(movingActivityStartsAt2).startPlace.name shouldBe "Supermarket"
    SingleActivity(movingActivityStartsAt2).endPlace.name shouldBe "Zoo"
  }

  it should "return start and end place of sorted and unsorted sequence of activities" in {
    SingleActivity(movingActivityStartsAt2).endPlace.name shouldBe "Zoo"
    unsortedSequenceOfActivities.startPlace.name shouldBe "Home"
    sequenceOfActivities.endPlace.name shouldBe "Zoo"
    unsortedSequenceOfActivities.endPlace.name shouldBe "Zoo"
  }

  it should "check if blocks of activities are before others" in {
    SingleActivityat0.before(SingleActivityat2) shouldBe true
    SingleActivityat2.before(SingleActivityat0) shouldBe false
    SingleActivityat0.before(overlappingSingleActivity) shouldBe false
    SingleActivityat0.before(coveringActivityStart0End5) shouldBe false
    coveringActivityStart0End5.before(SingleActivityat0) shouldBe false
  }

  it should "check blocks of activities are parallel" in {
    SingleActivityat0.parallel(overlappingSingleActivity) shouldBe true
    overlappingSingleActivity.parallel(SingleActivityat0) shouldBe true
  }

  it should "check if blocks of activities are chainable" in {
    SingleActivityat0.isChainable(SingleActivityat2) shouldBe true
    sequenceOfActivities.isChainable(singleActivityStart4End5) shouldBe true
  }

  it should "temporally project activities" in {
    unsortedSequenceOfActivities.temporalProjection shouldBe(0, 3)
  }

  it should "spatially project activities" in {
    unsortedSequenceOfActivities.placeProjection.exists(a => List("Zoo", "Supermarket", "Home").contains(a.name)) shouldBe true
    stayingAtHomePlan.placeProjection.map(p => p.name).contains("Home") shouldBe true
    stayingAtHomePlan.placeProjection.size shouldBe 1
  }

  it should "check if activities are temporally contained" in {
    attendingLecturesFrom1_4.during(goingToUniFrom0_5) shouldBe true
    attendingLecturesFrom1_4.during(singleActivityStart4End5) shouldBe false
  }

  it should "returns a coarsened plan that covers the initial one" in {
    implicit val geo = geog
    val expectedResultActivity = SingleActivity(PlaceTimeStation(londonPlace,(0,3),"supermarket to zoo,home"))
    unsortedSequenceOfActivities.coarsen shouldBe expectedResultActivity
  }

  it should "combine to sequential single activities in the right order" in {
    val singleActivityFirst = SingleActivity(PlaceTimeStation(homePlace, (0, 2), "home"))
    val singleActivitySecond = SingleActivity(PlaceTimeStation(cinemaPlace, (3, 4), "cinema"))

    val combinedResult = new ActivitySequence(List(singleActivityFirst,singleActivitySecond))

    singleActivityFirst.combine(singleActivityFirst) shouldBe singleActivityFirst
    singleActivitySecond.combine(singleActivityFirst) shouldBe combinedResult
    singleActivityFirst.combine(singleActivitySecond) shouldBe singleActivitySecond.combine(singleActivityFirst)
  }

  it should "combine single and sequence activities in the right order" in {
    val singleActivityFirst = SingleActivity(PlaceTimeStation(homePlace, (0, 2), "home"))
    val singleActivityInTheMiddle = SingleActivity(PlaceTimePath(homePlace,cinemaPlace, (0, 2), "travel"))
    val singleActivitySecond = SingleActivity(PlaceTimeStation(cinemaPlace, (3, 4), "cinema"))

    val activitySequence = singleActivityFirst.combine(singleActivitySecond)

    val combinedResult = new ActivitySequence(List(singleActivityFirst,singleActivityInTheMiddle,singleActivitySecond))

    activitySequence.combine(activitySequence) shouldBe activitySequence
    activitySequence.combine(singleActivityInTheMiddle) shouldBe combinedResult
    singleActivityInTheMiddle.combine(activitySequence) shouldBe combinedResult
    singleActivityInTheMiddle.combine(activitySequence) shouldBe activitySequence.combine(singleActivityInTheMiddle)

  }

}
