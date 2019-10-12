organization in ThisBuild := "tryp.sbt"

name := "sbt-jade"

description := "Simple syntax-only jade asset plugin for sbt-web"

enablePlugins(SbtPlugin, BintrayPlugin)

bintrayRepository in bintray := "sbt-plugins"

bintrayOrganization in bintray := None

licenses += ("MIT", url("http://opensource.org/licenses/MIT"))

resolvers += Resolver.sonatypeRepo("releases")

libraryDependencies += "de.neuland-bfi" % "jade4j" % "1.2.6"

addSbtPlugin("com.typesafe.sbt" % "sbt-web" % "1.4.4")
