import TemporalEvent.Interval

trait RelationalTemporalEvent extends TemporalEvent {
  def starts(i: RelationalTemporalEvent): Boolean = this.getStartTime == i.getStartTime && this.getEndTime < i.getEndTime
  def startedBy(i: RelationalTemporalEvent): Boolean = i.starts(this)

  def before(i: RelationalTemporalEvent): Boolean = this.getEndTime < i.getStartTime
  def after(i: RelationalTemporalEvent): Boolean = i.before(this)

  def during(i: RelationalTemporalEvent): Boolean = this.getStartTime > i.getStartTime && this.getEndTime < i.getEndTime
  def contains(i: RelationalTemporalEvent): Boolean = i.during(this)

  def equal(i: RelationalTemporalEvent): Boolean = this.getStartTime == i.getStartTime && this.getEndTime == i.getEndTime

  def meets(i: RelationalTemporalEvent): Boolean = this.getEndTime == i.getStartTime
  def metBy(i: RelationalTemporalEvent): Boolean = i.meets(this)

  def overlaps(i: RelationalTemporalEvent): Boolean = this.getStartTime < i.getStartTime && this.getEndTime < i.getEndTime && !this.meets(i)
  def overlapped(i: RelationalTemporalEvent): Boolean = i.overlaps(this)

  def finishes(i: RelationalTemporalEvent): Boolean = this.getStartTime > i.getStartTime && this.getEndTime == i.getEndTime
  def finishedBy(i: RelationalTemporalEvent): Boolean = i.finishes(this)
}

