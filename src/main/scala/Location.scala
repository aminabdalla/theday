

class Location(var name: String, var location: (Int, Int)) {
  def isSameAs(location: Location): Boolean = location.name == this.name
}

object Location {
  def apply(name: String, location: (Int, Int)) = new Location(name,location)
}