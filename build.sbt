name := "dot-challange"

version := "0.1"

scalaVersion := "2.13.4"

lazy val akkaVersion = "2.6.11"
lazy val scalaTestVersion = "3.2.2"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % scalaTestVersion,
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test,
)
