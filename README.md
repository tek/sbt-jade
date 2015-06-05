## Description

Simple syntax-only jade asset plugin for sbt-web that uses [jade4j][1].

## Usage

```scala
resolvers += Resolver.url("bintray-tek-sbt-plugins",
  url("http://dl.bintray.com/tek/sbt-plugins"))(Resolver.ivyStylePatterns)

addSbtPlugin("tryp.sbt" % "sbt-jade" % "0.0.1")
```

jade files in the asset dir will be processed to html in staging.

[1]: https://github.com/neuland/jade4j 'jade4j'
