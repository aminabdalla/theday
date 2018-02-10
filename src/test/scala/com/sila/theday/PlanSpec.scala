package com.sila.theday

import com.sila.theday.abstracts.construct.Activity.{PlaceTimePath, PlaceTimeStation}
import com.sila.theday.abstracts.construct.{ActivityAlternatives, ActivitySequence, SingleActivity}

class PlanSpec extends BaseTest {

  "A plan" should "return the starttime and endtime of single static activities" in {
    SingleActivity(staticActivityStartsAt0).potentialStartTime shouldEqual List(0)
    SingleActivity(staticActivityStartsAt0).potentialEndTime shouldEqual List(2)
  }

  it should "return startime and endtime of a single moving activity" in {
    SingleActivity(movingActivityStartsAt2).potentialStartTime shouldEqual List(2)
    SingleActivity(movingActivityStartsAt2).potentialEndTime shouldEqual List(3)
  }

  it should "return the start and end time of sorted and unsorted sequences of blocks" in {
    sequenceOfActivities.potentialStartTime shouldBe List(0)
    unsortedSequenceOfActivities.potentialStartTime shouldBe List(0)
    sequenceOfActivities.potentialEndTime shouldBe List(3)
    unsortedSequenceOfActivities.potentialEndTime shouldBe List(3)
  }

  it should "return start and end place of single static activity" in {
    unsortedSequenceOfActivities.potentialStartTime shouldBe List(0)
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
    SingleActivityAt0.before(SingleActivityAt2) shouldBe true
    SingleActivityAt2.before(SingleActivityAt0) shouldBe false
    SingleActivityAt0.before(overlappingSingleActivity) shouldBe false
    SingleActivityAt0.before(coveringActivityStart0End5) shouldBe false
    coveringActivityStart0End5.before(SingleActivityAt0) shouldBe false
  }

  it should "check blocks of activities are parallel" in {
    SingleActivityAt0.parallel(overlappingSingleActivity) shouldBe true
    overlappingSingleActivity.parallel(SingleActivityAt0) shouldBe true
  }

  it should "check if blocks of activities are chainable" in {
    SingleActivityAt0.isChainable(SingleActivityAt2) shouldBe true
    sequenceOfActivities.isChainable(singleActivityStart4End5) shouldBe true
  }

  it should "temporally project activities" in {
    unsortedSequenceOfActivities.temporalProjection shouldBe(List(0), List(3))
  }

  it should "spatially project activities" in {
    unsortedSequenceOfActivities.placeProjection.exists(a => List("Zoo", "Supermarket", "Home").contains(a.name)) shouldBe true
    stayingAtHomePlan.placeProjection.map(p => p.name).contains("Home") shouldBe true
    stayingAtHomePlan.placeProjection.size shouldBe 1
  }

//  it should "check if activities are temporally contained" in {
//    attendingLecturesFrom1_4.during(goingToUniFrom0_5) shouldBe true
//    attendingLecturesFrom1_4.during(singleActivityStart4End5) shouldBe false
//  }

  it should "returns a coarsened plan that covers the initial one" in {
    implicit val geo = geog
    val expectedResultActivity = SingleActivity(PlaceTimeStation(londonPlace, (0, 3), "supermarket to zoo,home"))
    unsortedSequenceOfActivities.coarsen shouldBe expectedResultActivity
  }

  it should "combine to sequential single activities in the right order" in {
    val singleActivityFirst = SingleActivity(PlaceTimeStation(homePlace, (0, 2), "home"))
    val singleActivitySecond = SingleActivity(PlaceTimeStation(cinemaPlace, (3, 4), "cinema"))

    val combinedResult = new ActivitySequence(List(singleActivityFirst, singleActivitySecond))

    singleActivityFirst.combine(singleActivityFirst) shouldBe singleActivityFirst
    singleActivitySecond.combine(singleActivityFirst) shouldBe combinedResult
    singleActivityFirst.combine(singleActivitySecond) shouldBe singleActivitySecond.combine(singleActivityFirst)
  }

  it should "combine single and sequence activities in the right order" in {
    val singleActivityFirst = SingleActivity(PlaceTimeStation(homePlace, (0, 2), "home"))
    val singleActivityInTheMiddle = SingleActivity(PlaceTimePath(homePlace, cinemaPlace, (2, 3), "travel"))
    val singleActivitySecond = SingleActivity(PlaceTimeStation(cinemaPlace, (3, 4), "cinema"))

    val activitySequence = singleActivityFirst.combine(singleActivitySecond)

    val combinedResult = ActivitySequence(List(singleActivityFirst, singleActivityInTheMiddle, singleActivitySecond))

    activitySequence.combine(activitySequence) shouldBe activitySequence
    activitySequence.combine(singleActivityInTheMiddle) shouldBe combinedResult
    singleActivityInTheMiddle.combine(activitySequence) shouldBe combinedResult
    singleActivityInTheMiddle.combine(activitySequence) shouldBe activitySequence.combine(singleActivityInTheMiddle)

  }

  it should "combine two mutually exclusive single activities into an Alt type" in {
    val singleActivity1 = SingleActivity(PlaceTimeStation(cinemaPlace, (3, 4), "cinema"))
    val singleActivity2 = SingleActivity(PlaceTimeStation(homePlace, (3, 4), "home"))
    val combinedResult = ActivityAlternatives(Set(singleActivity1, singleActivity2))
    singleActivity1.combine(singleActivity2) shouldBe combinedResult
    singleActivity2.combine(singleActivity1) shouldBe combinedResult
  }

  it should "combine two mutually exclusive activities, one single and a sequence into a Seq with a nested Alt type" in {
    val singleActivity1 = SingleActivity(PlaceTimeStation(cinemaPlace, (3, 4), "cinema"))
    val singleActivity2 = SingleActivity(PlaceTimeStation(homePlace, (3, 4), "home"))
    val lastActivity = SingleActivity(PlaceTimeStation(uniPlace, (5, 6), "uni"))
    val sequencedActivity = ActivitySequence(List(singleActivity1,lastActivity))
    val combinedResult = ActivitySequence(List(ActivityAlternatives(Set(singleActivity1, singleActivity2)),lastActivity))
//      sequencedActivity.combine(singleActivity2) shouldBe combinedResult
    singleActivity2.combine(sequencedActivity) shouldBe combinedResult
  }
}
