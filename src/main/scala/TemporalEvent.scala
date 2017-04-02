import TemporalEvent.Interval

/**
  * Created by amin on 02/04/2017.
  */
trait TemporalEvent {
  def getTimeSpan : Interval = (this.getStartTime,this.getEndTime)
  def getStartTime : Int
  def getEndTime : Int
}

object TemporalEvent{
  type Interval = (Int,Int)
}


