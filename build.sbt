lazy val commonSettings = Seq(
  organization :="com.sila",
  scalaVersion := "2.12.3",
  name := "theday",
  version := "0.0.1-SNAPSHOT"
)

lazy val root = Project("theday", file("."))
lazy val api = project.settings(commonSettings)
lazy val core = project.settings(commonSettings)


//val akkaHttpVersion = "10.0.10"
//val jackson4sVersion = "3.5.3"
//val catsVersion = "0.9.0"
//val log4jVersion = "2.8.2"
//val log4jApiVersion = "11.0"
//val scalaMockVersion = "3.6.0"

//libraryDependencies ++= Seq(
//  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
//  "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
//  "com.typesafe.akka" %% "akka-http-jackson" % akkaHttpVersion,
//  "com.github.swagger-akka-http" % "swagger-akka-http_2.12" % "0.11.0",
//  "ch.megard" %% "akka-http-cors" % "0.2.1",
//  "org.typelevel" %% "cats-core" % catsVersion,
//  "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % Test,
//  "org.scalatest" %% "scalatest" % "3.0.4" % Test,
//  "org.scalamock" %% "scalamock-scalatest-support" % scalaMockVersion % Test
//)