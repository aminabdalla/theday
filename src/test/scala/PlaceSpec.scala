import Geometry.POINT
import org.scalatest.FunSuite

class PlaceSpec extends FunSuite {

  test("testIsParentOf") {

    val earthLoc: Location = Location("Earth", POINT(0, 0))
    val europeLoc: Location = Location("Europe", POINT(0, 1))
    val germanyLoc: Location = Location("Germany", POINT(0, 2))
    val germany = SubPlace(loc = germanyLoc,List(null))
    val europe = SubPlace(loc = europeLoc,List(germany))
    val earth = TopPlace(loc = earthLoc,List(europe))

    assert(earth.isParentOf(germany))
    assert(earth.isParentOf(europe))
    assert(europe.isParentOf(germany))

  }

  test("testChildPlaces") {
    val earthLoc: Location = Location("Earth", POINT(0, 0))
    val europeLoc: Location = Location("Europe", POINT(0, 1))
    val germanyLoc: Location = Location("Germany", POINT(0, 2))
    val franceLoc: Location = Location("France", POINT(0, 2))
    val france = SubPlace(loc = franceLoc,List())
    val germany = SubPlace(loc = germanyLoc,List())
    val europe = SubPlace(loc = europeLoc,List(germany))
    val earth = TopPlace(loc = earthLoc,List(europe))
    assert(germany.isChild(europe))
    assert(germany.isChild(earth))
    assert(!germany.isChild(france))
  }


}
