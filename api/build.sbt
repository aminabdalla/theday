name := "api"

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
  "org.scalamock" %% "scalamock-scalatest-support" % scalaMockVersion % Test,
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % Test,
  "org.scalamock" %% "scalamock-scalatest-support" % "3.6.0" % Test,
  "com.typesafe.akka" %% "akka-http-jackson" % akkaHttpVersion
)