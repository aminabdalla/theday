package construct

import primitive.{Geometry, Granularity}

case class Place(var name: String, var location: Geometry, var granularity: Granularity)


