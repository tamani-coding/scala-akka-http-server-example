lazy val root = project
  .in(file("."))
  .settings(
    name := "scala3-akka-http-server",
    version := "0.1.0",
    scalaVersion := "3.0.0-RC1",
    libraryDependencies ++= Seq (
      "com.typesafe.akka" % "akka-actor-typed_2.13" % "2.6.14",
      "com.typesafe.akka" % "akka-stream-typed_2.13" % "2.6.14",
      "com.typesafe.akka" % "akka-http_2.13" % "10.2.4",
      "com.typesafe.akka" % "akka-http-spray-json_2.13" % "10.2.4",
      "ch.megard" % "akka-http-cors_2.13" % "1.1.1")
  )
