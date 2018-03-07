package com.sila.theday.abstracts

import scala.annotation.tailrec


sealed abstract class Hierarchy[A] {
  def isEmpty: Boolean

  def value: A

  def subLevel: List[Hierarchy[A]]

  def subHierarchy(position: A): Option[Hierarchy[A]]

  def parent: A

}


case class Node[A](value: A, subItems: List[Hierarchy[A]], parent: A) extends Hierarchy[A] {
  override def isEmpty = subItems.isEmpty

  override def subLevel = subItems

  override def subHierarchy(position: A): Option[Hierarchy[A]] = this match {

    case n@Node(v, Nil, _) => if (v == position) Some(this) else None
    case n@Node(v, l, _) =>
      if(v == position)
        Some(n)
      else if (l.map(_.value).contains(position))
        l.find(_.value == position)
      else
        l.flatMap(s => s.subHierarchy(position)).find(_.value == position)

  }


}