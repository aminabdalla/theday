

class Location(var name: String, var geometry: Geometry) {
  def isSameAs(location: Location): Boolean = location.name == this.name
}

object Location {
  def apply(name: String, location: Geometry) = new Location(name,location)
}