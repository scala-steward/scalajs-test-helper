name := "scalajs-test-helper"

version := "0.1"

scalaVersion := "2.13.1"

val groupId = "org.scala-steward"
val projectName = "scala-steward"
val gitHubOwner = "fthomas"

lazy val `scalajs-test-helper` = project
  .in(file("."))
  .aggregate(core, forScalaTest)

lazy val core = project
  .enablePlugins(ScalaJSPlugin)
  .in(file("module/core"))
  .settings(
    scalacOptions += "-P:scalajs:sjsDefinedByDefault",
    libraryDependencies ++= Seq(
      "org.scalatest" %%% "scalatest" % "3.1.0" % Test
    )
  )

lazy val forScalaTest = project
  .enablePlugins(ScalaJSPlugin)
  .in(file("module/scalatest"))
  .settings(
    scalacOptions += "-P:scalajs:sjsDefinedByDefault",
    libraryDependencies ++= Seq(
      "org.scalatest" %%% "scalatest" % "3.1.0" % Compile
    )
  )
  .dependsOn(core)
