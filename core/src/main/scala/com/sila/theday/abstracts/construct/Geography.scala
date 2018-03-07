package com.sila.theday.abstracts.construct

import com.sila.theday.abstracts.Hierarchy
import com.sila.theday.abstracts.primitive.Geometry

class Geography(geog: Hierarchy[Place]) {

  def contains(p1: Place, p2: Place): Boolean = geog.subHierarchy(p1).flatMap(sH => sH.subHierarchy(p2)).isDefined

  def transportDistance(p1: Place, p2: Place): Double = euclideanDistance(p1, p2) * 1.5

  def euclideanDistance(p1: Place, p2: Place): Double = p1.euclideanDistance(p2)

  def travelTime(p1: Place, p2: Place): Double = euclideanDistance(p1, p2) * 2

  def isImmediateParent(p1: Place, p2: Place) = geog.subHierarchy(p2).map(h => h.parent == p1).getOrElse(false)


  def getParent(p: Place): Option[Place] = geog.subHierarchy(p).flatMap(h => Option.apply(h.parent))

  implicit def toGeom(p: Place): Geometry = p.location

  def containingPlace(involvedPlaces : List[Place]): Place = {

    val topGranularity = involvedPlaces.map(_.granularity).sorted.head
    val involvedPlacesOfTopGranularity = involvedPlaces.filter(p => p.granularity == topGranularity)

    if (involvedPlacesOfTopGranularity.length == 1)
      findCoveringPlace(involvedPlacesOfTopGranularity.head,involvedPlaces)
    else
      involvedPlacesOfTopGranularity.map(p => findCoveringPlace(p,involvedPlaces)).sortBy(_.granularity).reverse.head

  }

  private def findCoveringPlace(topPlace : Place, places : List[Place]) : Place = {

    def containsAllPlaces(topGranularityPlace: Place) = places.map(ip => this.contains(topGranularityPlace, ip)).reduce((x, y) => x && y)

    if (containsAllPlaces(topPlace))
      topPlace
    else
      findCoveringPlace(this.getParent(topPlace).get,places)
  }

}
