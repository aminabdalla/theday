package construct

import cats.data.NonEmptyList
import primitive.{Granularity, Location, SpatialGranularity}

sealed abstract trait Place {
  def isParentOf(p: Place): Boolean

  def isChild(p: Place): Boolean

  def getLocation: Location

  def granularity: Granularity

  def getChildren: List[Place]

}

case class TopPlace(var loc: Location, var children: NonEmptyList[Place], var granularity: Granularity) extends Place {

  def getLocation: Location = loc

  override def isParentOf(p: Place): Boolean = true

  override def isChild(p: Place): Boolean = false

  override def getChildren = children.toList
}

case class SubPlace(var loc: Location, var children: List[Place], var granularity: Granularity) extends Place {

  def getLocation: Location = loc

  override def isParentOf(p: Place): Boolean = children match {
    case Nil => false
    case list => if (list.contains(p)) true else children.exists(subPlace => subPlace.isParentOf(p))
  }

  override def isChild(p: Place): Boolean = p match {
    case TopPlace(_, _, _) => true
    case SubPlace(_, c, _) => if (c.contains(this)) true
    else if (c.isEmpty) false
    else c.exists(child => this.isChild(child))
  }

  override def getChildren = children
}
