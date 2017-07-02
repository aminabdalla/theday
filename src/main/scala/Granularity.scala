

sealed abstract trait Granularity extends Ordered[Granularity]{

  def coarsen : Granularity

  def commonUpperGranule(g : Granularity) : Granularity=
    if (this < g) g
    else if(this > g) this
    else this.coarsen
}

sealed abstract trait SpatialGranularity extends Granularity{
  def top : Granularity = World
  def bottom : Granularity = Room
  override def coarsen : Granularity  = this match {
    case MeetingPoint => Room
    case Room => Building
    case District => City
    case City => State
    case State => Country
    case Country => Continent
    case Continent => World
    case World => World
  }
}

final case object MeetingPoint extends SpatialGranularity{
  override def compare(that: Granularity): Int = that match {
    case MeetingPoint => 0
    case Room => -1
    case Building => -1
    case District => -1
    case City => -1
    case State => -1
    case Country => -1
    case Continent => -1
    case World => -1
  }
}
final case object Room extends SpatialGranularity {
  override def compare(that: Granularity): Int = that match {
    case MeetingPoint => 1
    case Room => 0
    case Building => -1
    case District => -1
    case City => -1
    case State => -1
    case Country => -1
    case Continent => -1
    case World => -1
  }
}
final case object Building extends SpatialGranularity {
  override def compare(that: Granularity): Int = that match {
    case MeetingPoint => 1
    case Room => 1
    case Building => 0
    case District => -1
    case City => -1
    case State => -1
    case Country => -1
    case Continent => -1
    case World => -1
  }
}

final case object District extends SpatialGranularity {
  override def compare(that: Granularity): Int = that match {
    case MeetingPoint => 1
    case Room => 1
    case Building => 1
    case District => 0
    case City => -1
    case State => -1
    case Country => -1
    case Continent => -1
    case World => -1
  }
}
final case object City extends SpatialGranularity {
  override def compare(that: Granularity): Int = that match {
    case MeetingPoint => 1
    case Room => 1
    case Building => 1
    case District => 1
    case City => 0
    case State => -1
    case Country => -1
    case Continent => -1
    case World => -1
  }
}
final case object State extends SpatialGranularity {
  override def compare(that: Granularity): Int = that match {
    case MeetingPoint => 1
    case Room => 1
    case Building => 1
    case District => 1
    case City => 1
    case State => 0
    case Country => -1
    case Continent => -1
    case World => -1
  }
}
final case object Country extends SpatialGranularity {
  override def compare(that: Granularity): Int = that match {
    case MeetingPoint => 1
    case Room => 1
    case Building => 1
    case District => 1
    case City => 1
    case State => 1
    case Country => 0
    case Continent => -1
    case World => -1
  }
}
final case object Continent extends SpatialGranularity {
  override def compare(that: Granularity): Int = that match {
    case MeetingPoint => 1
    case Room => 1
    case Building => 1
    case District => 1
    case City => 1
    case State => 1
    case Country => 1
    case Continent => 0
    case World => -1
  }
}
final case object World extends SpatialGranularity{
  override def compare(that: Granularity): Int = that match {
    case MeetingPoint => 1
    case Room => 1
    case Building => 1
    case District => 1
    case City => 1
    case State => 1
    case Country => 1
    case Continent => 1
    case World => 0
  }
}


