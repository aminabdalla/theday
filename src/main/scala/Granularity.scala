

sealed abstract trait Granularity extends Ordered[Granularity]{

  def coarsen : Granularity = this match {
    case MeetingPoint => Park
    case Room => Park
    case Building => Park
    case Park => Neighbourhood
    case Neighbourhood => District
    case District => City
    case City => State
    case State => Country
    case Country => Continent
    case Continent => World
    case World => World
  }

  def top : Granularity = World
  def bottom : Granularity = Room

  def commonUpperGranule(g : Granularity) : Granularity=
    if (this < g) g
    else if(this > g) this
    else this.coarsen
}

final case object MeetingPoint extends Granularity {
  override def compare(that: Granularity): Int = that match {
    case MeetingPoint => 0
    case Room => 0
    case Building => 0
    case Park => -1
    case Neighbourhood => -1
    case District => -1
    case City => -1
    case State => -1
    case Country => -1
    case Continent => -1
    case World => -1
  }
}
final case object Room extends Granularity {
  override def compare(that: Granularity): Int = that match {
    case MeetingPoint => 0
    case Room => 0
    case Building => 0
    case Park => -1
    case Neighbourhood => -1
    case District => -1
    case City => -1
    case State => -1
    case Country => -1
    case Continent => -1
    case World => -1
  }
}
final case object Building extends Granularity {
  override def compare(that: Granularity): Int = that match {
    case MeetingPoint => 0
    case Room => 0
    case Building => 0
    case Park => -1
    case Neighbourhood => -1
    case District => -1
    case City => -1
    case State => -1
    case Country => -1
    case Continent => -1
    case World => -1
  }
}
final case object Park extends Granularity {
  override def compare(that: Granularity): Int = that match {
    case MeetingPoint => 1
    case Room => 1
    case Building => 1
    case Park => 0
    case Neighbourhood => -1
    case District => -1
    case City => -1
    case State => -1
    case Country => -1
    case Continent => -1
    case World => -1
  }
}
final case object Neighbourhood extends Granularity {
  override def compare(that: Granularity): Int = that match {
    case MeetingPoint => 1
    case Room => 1
    case Building => 1
    case Park => 1
    case Neighbourhood => 0
    case District => -1
    case City => -1
    case State => -1
    case Country => -1
    case Continent => -1
    case World => -1
  }
}
final case object District extends Granularity {
  override def compare(that: Granularity): Int = that match {
    case MeetingPoint => 1
    case Room => 1
    case Building => 1
    case Park => 1
    case Neighbourhood => 1
    case District => 0
    case City => -1
    case State => -1
    case Country => -1
    case Continent => -1
    case World => -1
  }
}
final case object City extends Granularity {
  override def compare(that: Granularity): Int = that match {
    case MeetingPoint => 1
    case Room => 1
    case Building => 1
    case Park => 1
    case Neighbourhood => 1
    case District => 1
    case City => 0
    case State => -1
    case Country => -1
    case Continent => -1
    case World => -1
  }
}
final case object State extends Granularity {
  override def compare(that: Granularity): Int = that match {
    case MeetingPoint => 1
    case Room => 1
    case Building => 1
    case Park => 1
    case Neighbourhood => 1
    case District => 1
    case City => 1
    case State => 0
    case Country => -1
    case Continent => -1
    case World => -1
  }
}
final case object Country extends Granularity {
  override def compare(that: Granularity): Int = that match {
    case MeetingPoint => 1
    case Room => 1
    case Building => 1
    case Park => 1
    case Neighbourhood => 1
    case District => 1
    case City => 1
    case State => 1
    case Country => 0
    case Continent => -1
    case World => -1
  }
}
final case object Continent extends Granularity {
  override def compare(that: Granularity): Int = that match {
    case MeetingPoint => 1
    case Room => 1
    case Building => 1
    case Park => 1
    case Neighbourhood => 1
    case District => 1
    case City => 1
    case State => 1
    case Country => 1
    case Continent => 0
    case World => -1
  }
}
final case object World extends Granularity{
  override def compare(that: Granularity): Int = that match {
    case MeetingPoint => 1
    case Room => 1
    case Building => 1
    case Park => 1
    case Neighbourhood => 1
    case District => 1
    case City => 1
    case State => 1
    case Country => 1
    case Continent => 1
    case World => 0
  }
}


