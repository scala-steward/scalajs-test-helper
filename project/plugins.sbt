val scalaJSVersion =
  Option(System.getenv("SCALAJS_VERSION")).getOrElse("1.1.1")

addSbtPlugin("com.dwijnand" % "sbt-travisci" % "1.2.0")
addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.4.0")
//addSbtPlugin("io.github.davidgregory084" % "sbt-tpolecat" % "0.1.10")
addSbtPlugin("org.scala-js" % "sbt-scalajs" % scalaJSVersion)
addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "3.9.4")
addSbtPlugin("com.github.gseitz" % "sbt-release" % "1.0.13")
addSbtPlugin("com.jsuereth" % "sbt-pgp" % "2.0.1")
