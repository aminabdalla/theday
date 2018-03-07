package com.sila.theday.abstracts.construct

import com.sila.theday.abstracts.primitive.{Geometry, Granularity}

case class Place(var name: String, var location: Geometry, var granularity: Granularity)


