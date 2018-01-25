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





