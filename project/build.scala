import sbt._
import Keys._

import bintray._
import BintrayKeys._

object SbtJadeBuild
extends Build
{
  lazy val root = (project in file("."))
    .settings(BintrayPlugin.bintrayPublishSettings: _*)
    .settings(
      sbtPlugin := true,
      organization in ThisBuild := "tryp.sbt",
      name := "sbt-jade",
      description := "Simple syntax-only jade asset plugin for sbt-web",
      bintrayRepository in bintray := "sbt-plugins",
      bintrayOrganization in bintray := None,
      licenses += ("MIT", url("http://opensource.org/licenses/MIT")),
      resolvers += Resolver.sonatypeRepo("releases"),
      libraryDependencies += "de.neuland-bfi" % "jade4j" % "1.2.3",
      addSbtPlugin("com.typesafe.sbt" % "sbt-web" % "1.4.0")
    )
}
