package construct

import primitive.Location

sealed abstract trait Place{
  def isParentOf(p : Place) : Boolean
  def isChild(p : Place) : Boolean
  def getLocation : Location
}

case class TopPlace(var loc : Location,var children:List[Place]) extends Place {
  def apply(loc:Location,childs:List[Place]) = new TopPlace(loc,childs)
  def getLocation = loc
  override def isParentOf(p: Place): Boolean = true
  override def isChild(p: Place): Boolean = false
}

case class SubPlace(var loc: Location, var children:List[Place]) extends Place {
  def apply(loc:Location,childs:List[Place]) = new SubPlace(loc,childs)
  def getLocation = loc
  override def isParentOf(p: Place): Boolean = children match {
    case Nil => false
    case list => if (list.contains(p)) true else children.find(subPlace => subPlace.isParentOf(p)).isDefined
  }

  override def isChild(p: Place): Boolean = p match {
    case TopPlace(loc,children) => true
    case SubPlace(loc,children) => if (children.contains(this)) true
    else if (children.isEmpty) false
    else children.find(c => this.isChild(c)).isDefined
  }
}
