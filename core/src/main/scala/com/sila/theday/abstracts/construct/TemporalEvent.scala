package com.sila.theday.abstracts.construct

import com.sila.theday.abstracts.construct.TemporalEvent.Interval

trait TemporalEvent {
  def getTimeSpan: Interval = (this.getStartTime, this.getEndTime)

  def getStartTime: Int

  def getEndTime: Int
}

object TemporalEvent {
  type Interval = (Int, Int)
}


