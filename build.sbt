organization in ThisBuild := "net.exoego"
val projectName = "scalajs-test-helper"

val scalatestVersion = "3.2.2"

lazy val root = project
  .in(file("."))
  .settings(
    name := projectName,
    MySettings.commonSettings,
    MySettings.publishingSettings,
    MySettings.nonPublishingSettings
  )
  .aggregate(core, forScalaTest, forMunit)

lazy val core = project
  .enablePlugins(ScalaJSPlugin)
  .in(file("module/core"))
  .settings(
    name := s"${projectName}-core",
    MySettings.commonSettings,
    MySettings.publishingSettings,
    libraryDependencies ++= Seq(
      "org.scalatest" %%% "scalatest" % scalatestVersion % Test
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
      "org.scalatest" %%% "scalatest" % scalatestVersion % Compile
    )
  )
  .dependsOn(core)

lazy val forMunit = project
  .enablePlugins(ScalaJSPlugin)
  .in(file("module/munit"))
  .settings(
    name := s"${projectName}-munit",
    MySettings.commonSettings,
    MySettings.publishingSettings,
    libraryDependencies ++= Seq(
      "org.scalameta" %%% "munit" % "0.7.17" % Compile
    ),
    scalaJSLinkerConfig ~= (_.withModuleKind(ModuleKind.CommonJSModule)),
    testFrameworks += new TestFramework("munit.Framework")
  )
  .dependsOn(core)
