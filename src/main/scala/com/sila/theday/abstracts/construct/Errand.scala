package com.sila.theday.abstracts.construct

case class Errand(var description: String, due: Int, duration: Int, places: List[Place])
