

abstract class Place(var name: String, var location: (Int, Int), var parent: Place,var childPlaces : List[Place]) {

  def isSameAs(place: Place): Boolean
  def isContainedIn: List[Place]
}

object Place {

  final case class Earth(var children: List[Place]) extends Place(null, null, null, children) {
    override def isSameAs(place: Place): Boolean = place.parent == null

    override def isContainedIn: List[Place] = List.empty
  }

  final case class SubPlace(n: String, loc: (Int, Int), par: Place, children: List[Place]) extends Place(n, loc, par, children) {
    override def isSameAs(place: Place): Boolean = name.equals(place.name)

    override def isContainedIn: List[Place] = par match {
      case Earth(c) => List(parent)
      case _ => List(parent) ::: parent.isContainedIn
    }
  }

}