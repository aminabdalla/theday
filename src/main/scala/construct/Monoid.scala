package construct


trait Monoid[T] {
  def Zero: T
  def op(t1:T,t2:T): T;
}
