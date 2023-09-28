name := "software-architecture-notes"

version := "1.0"

ThisBuild / scalaVersion := "3.2.1"

ThisBuild / logLevel := Level.Warn

val akkaVersion = "2.7.0"

libraryDependencies ++= Seq(
    // libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.15",
    "org.scalatest" %% "scalatest" % "3.2.15" % "test",
    "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
    "ch.qos.logback" % "logback-classic" % "1.2.11"
)
