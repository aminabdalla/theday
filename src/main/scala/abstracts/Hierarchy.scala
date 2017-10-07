package abstracts


sealed abstract class Hierarchy[A] {
  def isEmpty: Boolean

  def value: A

  def subLevel: List[Hierarchy[A]]

  def subHierarchy(position: A): Option[Hierarchy[A]]

}


case class Node[A](value: A, subItems: List[Hierarchy[A]]) extends Hierarchy[A] {
  override def isEmpty = subItems.isEmpty

  override def subLevel = subItems

  override def subHierarchy(position: A): Option[Hierarchy[A]] = this match {

    case n@Node(v, Nil) => if (v == position) Some(this) else None
    case n@Node(v, l) =>
      if (l.find(_.value == position).isDefined)
        l.find(_.value == position)
      else
        l.flatMap(s => s.subHierarchy(position)).find(_.value == position)

  }


}