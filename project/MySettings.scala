import com.jsuereth.sbtpgp.SbtPgp.autoImport._
import sbt.{ url, _ }
import sbt.Keys._
import sbtrelease.ReleasePlugin.autoImport._
import sbtrelease.ReleaseStateTransformations._
import xerial.sbt.Sonatype.SonatypeKeys

object MySettings {
  private val lintSettings = Def.setting({
    val isScala212 = scalaVersion.value.startsWith("2.12")
    val lints = (Seq(
      "adapted-args",
      "nullary-unit",
      "inaccessible",
      "nullary-override",
      "infer-any",
      "missing-interpolator",
      "doc-detached",
      "private-shadow",
      "type-parameter-shadow",
      "poly-implicit-overload",
      "option-implicit",
      "delayedinit-select",
      "package-object-classes",
      "stars-align",
      "constant"
    ) ++ Seq(
      "nonlocal-return",
      "implicit-not-found",
      "serial",
      "valpattern",
      "eta-zero",
      "eta-sam",
      "deprecation"
    ).filter(_ => !isScala212)).map(s => s"-Xlint:${s}")
    // no privates to allow private constructor
    val unused = Seq("imports", "implicits", "locals", "patvars")
      .filter(_ => !isScala212)
      .map(s => s"-Wunused:${s}")
    lints ++ unused
  })

  lazy val commonSettings = Seq(
    autoCompilerPlugins := true,
    scalacOptions ++= Seq(
      "-P:scalajs:sjsDefinedByDefault",
      "-deprecation",
      "-unchecked",
      "-feature",
      "-language:implicitConversions"
    ) ++ lintSettings.value,
    scalacOptions in Compile in compile ++= Seq(
      "-Xfatal-warnings"
    ),
    scalacOptions in Compile in doc ++= Seq(
      "-Xfatal-warnings",
      "-no-link-warnings"
    ),
    autoAPIMappings := true,
    publishTo := SonatypeKeys.sonatypePublishToBundle.value,
    licenses := Seq("APL2" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt")),
    scmInfo := Some(
      ScmInfo(
        url("https://github.com/exoego/aws-sdk-scalajs-facade"),
        "scm:git:git@github.com:exoego/aws-sdk-scalajs-facade.git"
      )
    ),
    homepage := scmInfo.value.map(_.browseUrl),
    developers := List(
      Developer(
        id = "exoego",
        name = "TATSUNO Yasuhiro",
        email = "ytatsuno.jp@gmail.com",
        url = url("https://www.exoego.net")
      )
    )
  )

  lazy val nonPublishingSettings = Seq(
    skip in publish := true,
    publishArtifact := false,
    publish := {},
    publishLocal := {}
  )

  lazy val publishingSettings = Seq(
    publishMavenStyle := true,
    publishArtifact in Test := false,
    publishArtifact in (Compile, packageDoc) := true,
    publishArtifact in (Compile, packageSrc) := true,
    pomIncludeRepository := { _ =>
      false
    },
    publishConfiguration := publishConfiguration.value.withOverwrite(true),
    publishLocalConfiguration := publishLocalConfiguration.value.withOverwrite(true),
    publishArtifact in packageDoc := false,
    sources in (Compile, doc) := Seq.empty,
    releasePublishArtifactsAction := PgpKeys.publishSigned.value,
    releaseProcess := Seq[ReleaseStep](
      checkSnapshotDependencies,
      inquireVersions,
      //  runTest,
      setReleaseVersion,
      commitReleaseVersion,
      runClean,
      releaseStepCommandAndRemaining("+publishSigned"),
      releaseStepCommand("sonatypeBundleRelease"),
      setNextVersion,
      commitNextVersion
    )
  )
}
