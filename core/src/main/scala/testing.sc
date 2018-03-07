val array = Array(1, 2, 3)

var total = 0

for (elem <- 0 to array.length - 1) {

  total += array(elem)

}

total

def recurs(total: Int, index: Int, array: Array[Int]): Int = {
  val n = array.length - 1
  if (index > n) total
  else
    recurs(total + array(index), index + 1, array)

}

recurs(0, 0, array)

case class Dog(name: String, x: String)

val d = Dog("hans", "hallo")
print(d.toString)

val largeNumber: PartialFunction[Int, String] = {
  case x => if (x >= 10) "Large Number" else "Small Number"
}


val resultMap: Map[String, Array[Int]] = Array(1, 2, 3, 10, 100) groupBy largeNumber
resultMap("Large Number")


val list = List(List(1, 2, 3), List(4, 5, 6), List(7, 8, 9))
list.transpose
val list2 = List(List(1), List(4))
list2.transpose

class Twice

object Twice extends Twice {
  def apply(x: Int): Int = x * 2

  def unapply(x: Int): Option[Int] = Some(1)
}

val result = Twice(2)

val matchedResult = result match {
  case Twice(value) => value
}

//call by name/value

def callSomething(x: Int, y: Int) = x
def callSomethingByName(x: => Int, y: => Int) = x


def returnTheInt = {
  println("I am called/n")
  1
}

callSomething(returnTheInt, returnTheInt)
callSomethingByName(returnTheInt, returnTheInt)


val input = List("a", "b", "c", "c", "d", "b", "f", "b","b","b")

val resultinger = input.map(value => (value, 1))
  .groupBy[String](_._1).mapValues[Int](kv => kv.map(_._2).sum).get("b")

def recurs(acc: List[(String, Int)], input: List[String]): List[(String, Int)] = input match {
  case Nil => acc
  case (x :: xs) => {
    if (!acc.isEmpty && x == acc.head._1)
      recurs((x, acc.head._2 + 1) :: acc.tail,xs)
    else recurs(((x, 1) :: acc), xs)
  }
}

recurs(List.empty, input)


val testList = List(1,2,3,4,5)
val resultingering = testList.toStream.map(doSome).take(3)

def doSome(n : Int) = {
  println(n)
}