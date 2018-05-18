lazy val commonSettings = Seq(
  organization :="com.sila",
  scalaVersion := "2.12.3",
  name := "theday",
  version := "0.0.1-SNAPSHOT"
)

lazy val root = Project("theday", file("."))
lazy val core = project.settings(commonSettings)
lazy val api = project.dependsOn(core)