import org.scalatest.FunSuite

class PlaceTest extends FunSuite {

  test("testIsParentOf") {

    val earthLoc: Location = new Location("Earth", (0, 0))
    val europeLoc: Location = new Location("Europe", (0, 1))
    val germanyLoc: Location = new Location("Germany", (0, 2))
    val germany = new SubPlace(loc = germanyLoc,List(null))
    val europe = new SubPlace(loc = europeLoc,List(germany))
    val earth = new TopPlace(loc = earthLoc,List(europe))

    assert(earth.isParentOf(germany))
    assert(earth.isParentOf(europe))
    assert(europe.isParentOf(germany))

  }

  test("testChildPlaces") {
    val earthLoc: Location = new Location("Earth", (0, 0))
    val europeLoc: Location = new Location("Europe", (0, 1))
    val germanyLoc: Location = new Location("Germany", (0, 2))
    val franceLoc: Location = new Location("France", (0, 2))
    val france = new SubPlace(loc = franceLoc,List())
    val germany = new SubPlace(loc = germanyLoc,List())
    val europe = new SubPlace(loc = europeLoc,List(germany))
    val earth = new TopPlace(loc = earthLoc,List(europe))
    assert(germany.isChild(europe))
    assert(germany.isChild(earth))
    assert(!germany.isChild(france))
  }

  test("testIsSameAs") {

  }

  test("testParent") {

  }

}
