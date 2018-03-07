//name := "core"

//version := "0.0.1-SNAPSHOT"

val akkaHttpVersion = "10.0.10"
val jackson4sVersion = "3.5.3"
val catsVersion = "0.9.0"
val log4jVersion = "2.8.2"
val log4jApiVersion = "11.0"
val scalaMockVersion = "3.6.0"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % catsVersion,
  "org.scalatest" %% "scalatest" % "3.0.4" % Test,
  "org.scalamock" %% "scalamock-scalatest-support" % scalaMockVersion % Test
)