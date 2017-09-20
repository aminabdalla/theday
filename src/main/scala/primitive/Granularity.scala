package primitive

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
    case Building => District
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

sealed abstract trait TemporalGranularity extends Granularity{
  def top : Granularity = Year
  def bottom : Granularity = Second
  override def coarsen : Granularity  = this match {
    case Second => Minute
    case Minute => Hour
    case Hour => Day
    case Day => Month
    case Month => Year
  }
}

final case object Second extends TemporalGranularity{
  override def compare(that: Granularity): Int = that match {
    case Second => 0
    case Minute => 1
    case Hour => 1
    case Day => 1
    case Month => 1
    case Year => 1
  }
}

final case object Minute extends TemporalGranularity{
  override def compare(that: Granularity): Int = that match {
    case Second => -1
    case Minute => 0
    case Hour => 1
    case Day => 1
    case Month => 1
    case Year => 1
  }
}

final case object Hour extends TemporalGranularity{
  override def compare(that: Granularity): Int = that match {
    case Second => -1
    case Minute => -1
    case Hour => 0
    case Day => 1
    case Month => 1
    case Year => 1
  }
}

final case object Day extends TemporalGranularity{
  override def compare(that: Granularity): Int = that match {
    case Second => -1
    case Minute => -1
    case Hour => -1
    case Day => 0
    case Month => 1
    case Year => 1
  }
}

final case object Month extends TemporalGranularity{
  override def compare(that: Granularity): Int = that match {
    case Second => -1
    case Minute => -1
    case Hour => -1
    case Day => -1
    case Month => 0
    case Year => 1
  }
}

final case object Year extends TemporalGranularity{
  override def compare(that: Granularity): Int = that match {
    case Second => -1
    case Minute => -1
    case Hour => -1
    case Day => -1
    case Month => -1
    case Year => 0
  }
}