

class Location(var name: String, var location: (Int, Int)) {

  def isSameAs(location: Location): Boolean = location.name == this.name

}