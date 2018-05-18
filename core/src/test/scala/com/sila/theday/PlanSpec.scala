package com.sila.theday

import com.sila.theday.abstracts.construct.{PlaceTimePath, PlaceTimeStation}
import com.sila.theday.abstracts.construct.Plan._
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
    SingleActivity(staticActivityStartsAt0).potentialEndPlaces.head.name shouldBe "Home"
  }

  it should "return start and end place of single moving activity" in {
    SingleActivity(movingActivityStartsAt2).potentialStartPlaces.head.name shouldBe "Supermarket"
    SingleActivity(movingActivityStartsAt2).potentialEndPlaces.head.name shouldBe "Zoo"
  }

  it should "return start and end place of sorted and unsorted sequence of activities" in {
    SingleActivity(movingActivityStartsAt2).potentialEndPlaces.head.name shouldBe "Zoo"
    unsortedSequenceOfActivities.potentialStartPlaces.head.name shouldBe "Home"
    sequenceOfActivities.potentialEndPlaces.head.name shouldBe "Zoo"
    unsortedSequenceOfActivities.potentialEndPlaces.head.name shouldBe "Zoo"
  }

  it should "check if blocks of activities are before others from a temporal perspective" in {
    BeingAtHomeFrom0To2.temporallyBefore(MovingFromSupermarketToZooFrom2To4) shouldBe true
    MovingFromSupermarketToZooFrom2To4.temporallyBefore(BeingAtHomeFrom0To2) shouldBe false
    BeingAtHomeFrom0To2.temporallyBefore(overlappingSingleActivity) shouldBe false
    BeingAtHomeFrom0To2.temporallyBefore(coveringActivityStart0End5) shouldBe false
    coveringActivityStart0End5.temporallyBefore(BeingAtHomeFrom0To2) shouldBe false
  }

  it should "check blocks of activities are parallel" in {
    BeingAtHomeFrom0To2.parallel(overlappingSingleActivity) shouldBe true
    overlappingSingleActivity.parallel(BeingAtHomeFrom0To2) shouldBe true
  }

  it should "check if blocks of activities are chainable" in {
    BeingAtHomeFrom0To2.isChainable(SingleActivity(PlaceTimePath(supermarketPlace, zooPlace, (2, 3), "supermarket to zoo"))) shouldBe false
    sequenceOfActivities.isChainable(SingleActivity(PlaceTimeStation(cinemaPlace, (10, 11), ""))) shouldBe true
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
    val singleActivitySecond = SingleActivity(PlaceTimeStation(cinemaPlace, (10, 11), "cinema"))

    val combinedResult = new ActivitySequence(List(singleActivityFirst, singleActivitySecond))

    singleActivityFirst.combine(singleActivityFirst) shouldBe singleActivityFirst
    singleActivitySecond.combine(singleActivityFirst) shouldBe combinedResult
    singleActivityFirst.combine(singleActivitySecond) shouldBe singleActivitySecond.combine(singleActivityFirst)
  }

  it should "combine single and sequence activities in the right order" in {
    val singleActivityFirst = SingleActivity(PlaceTimeStation(homePlace, (0, 2), "home"))
    val singleActivityInTheMiddle = SingleActivity(PlaceTimePath(homePlace, cinemaPlace, (2, 11), "travel"))
    val singleActivitySecond = SingleActivity(PlaceTimeStation(cinemaPlace, (12, 13), "cinema"))

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
    val lastActivity = SingleActivity(PlaceTimeStation(uniPlace, (20, 21), "uni"))
    val sequencedActivity = ActivitySequence(List(singleActivity1, lastActivity))
    val combinedResult = ActivitySequence(List(ActivityAlternatives(Set(singleActivity1, singleActivity2)), lastActivity))
    sequencedActivity.combine(singleActivity2) shouldBe combinedResult
    singleActivity2.combine(sequencedActivity) shouldBe combinedResult
  }

  it should "combine activities into a sequence" in {
    val singleActivity1 = SingleActivity(PlaceTimeStation(homePlace, (1, 2), "home"))
    val singleActivity2 = SingleActivity(PlaceTimeStation(cinemaPlace, (10, 11), "cinema"))
    val singleActivity3 = SingleActivity(PlaceTimeStation(uniPlace, (20, 21), "uni"))
    val singleActivity4 = SingleActivity(PlaceTimeStation(viennaPlace, (30, 31), "vienna"))
    val sequence1 = singleActivity1.combine(singleActivity2)
    val sequence2 = singleActivity3.combine(singleActivity4)

    val expectedSequence = ActivitySequence(List(singleActivity1, singleActivity2, singleActivity3, singleActivity4))

    sequence1 combine sequence2 shouldBe expectedSequence
    sequence2 combine sequence1 shouldBe expectedSequence
    singleActivity1 combine singleActivity2 combine sequence2 shouldBe expectedSequence
    singleActivity1 combine singleActivity2 combine singleActivity3 combine singleActivity4 shouldBe expectedSequence
    singleActivity1 combine singleActivity3 combine singleActivity4 combine singleActivity2 shouldBe expectedSequence
  }

  it should "combine alternatives with a sequence" in {
    val atHome = SingleActivity(PlaceTimeStation(homePlace, (9, 10), "home"))
    val atTheCinema = SingleActivity(PlaceTimeStation(cinemaPlace, (9, 10), "cinema"))
    val somethingAtTheStart = SingleActivity(PlaceTimeStation(viennaPlace,(0,2),"somethingAtTheStart"))
    val somethingAtTheEnd = SingleActivity(PlaceTimeStation(viennaPlace,(15,20),"somethingAtTheEnd"))
    val altActivities = ActivityAlternatives(Set(atHome,atTheCinema))
    val expectedResult = ActivitySequence(List(somethingAtTheStart,ActivityAlternatives(Set(atHome,atTheCinema)),somethingAtTheEnd))
    somethingAtTheStart combine somethingAtTheEnd combine altActivities shouldBe expectedResult
    somethingAtTheStart combine altActivities combine somethingAtTheEnd shouldBe expectedResult
    altActivities combine somethingAtTheStart combine somethingAtTheEnd shouldBe expectedResult
  }

  it should "combine alternatives with alternatives into alternatives of sequences" in {
    val atUni = SingleActivity(PlaceTimeStation(uniPlace, (1, 3), "uni"))
    val atHome = SingleActivity(PlaceTimeStation(homePlace, (1, 9), "home"))
    val homeVsUni = combine(atUni,atHome)
    val atCinema = SingleActivity(PlaceTimeStation(cinemaPlace, (8, 11), "cinema"))
    combine(atUni,atCinema) shouldBe ActivitySequence(List(atUni,atCinema))
    combine(homeVsUni,atCinema) shouldBe ActivityAlternatives(Set(ActivitySequence(List(atUni,atCinema)),atHome))
    combine(atCinema,homeVsUni) shouldBe ActivityAlternatives(Set(ActivitySequence(List(atUni,atCinema)),atHome))
    atHome combine (atCinema combine atUni) shouldBe ActivityAlternatives(Set(ActivitySequence(List(atUni,atCinema)),atHome))
    (atHome combine atCinema) combine atUni shouldBe ActivityAlternatives(Set(ActivitySequence(List(atUni,atCinema)),atHome))
  }


  it should "return true/false if an activity is possible/impossible to achieve before another due to spatial restrictions" in {
    BeingAtHomeFrom0To2.possibleBefore(SingleActivity(BeingAtUniFrom3To4PTS)) shouldBe false
    BeingAtHomeFrom0To2.possibleBefore(SingleActivity(BeingAtUniFrom8To9PTS)) shouldBe true
  }

}
