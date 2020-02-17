organization in ThisBuild := "net.exoego"
val projectName = "scalajs-test-helper"

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

lazy val forMunit = project
  .enablePlugins(ScalaJSPlugin)
  .in(file("module/munit"))
  .settings(
    name := s"${projectName}-munit",
    MySettings.commonSettings,
    MySettings.publishingSettings,
    libraryDependencies ++= Seq(
      "org.scalameta" %%% "munit" % "0.5.2" % Compile
    ),
    scalaJSLinkerConfig ~= (_.withModuleKind(ModuleKind.CommonJSModule)),
    testFrameworks += new TestFramework("munit.Framework")
  )
  .dependsOn(core)
