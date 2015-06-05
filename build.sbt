sbtPlugin := true

organization := "tryp.sbt"

name := "sbt-jade"

version := "0.0.1"

resolvers += Resolver.sonatypeRepo("releases")

libraryDependencies += "de.neuland-bfi" % "jade4j" % "0.4.3"

addSbtPlugin("com.typesafe.sbt" % "sbt-web" % "1.2.0")
