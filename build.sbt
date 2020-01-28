name := "scalajs-test-helper"

version := "0.1.0-SNAPSHOT"
val groupId = "net.exoego"
val projectName = "scalajs-test-helper"

lazy val root = project
  .in(file("."))
  .settings(
    name := "scalajs-test-helper",
    MySettings.commonSettings,
    MySettings.nonPublishingSettings
  )
  .aggregate(core, forScalaTest)

lazy val core = project
  .enablePlugins(ScalaJSPlugin)
  .in(file("module/core"))
  .settings(
    name := s"${projectName}-core",
    MySettings.commonSettings,
    MySettings.publishingSettings,
    libraryDependencies ++= Seq(
      "org.scalatest" %%% "scalatest" % "3.1.0" % Test
    )
  )

lazy val forScalaTest = project
  .enablePlugins(ScalaJSPlugin)
  .in(file("module/scalatest"))
  .settings(
    name := s"${projectName}-scalatest",
    MySettings.commonSettings,
    MySettings.publishingSettings,
    libraryDependencies ++= Seq(
      "org.scalatest" %%% "scalatest" % "3.1.0" % Compile
    )
  )
  .dependsOn(core)
