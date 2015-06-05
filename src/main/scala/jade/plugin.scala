package jade

import sbt._
import Keys._
import Path.relativeTo

import com.typesafe.sbt.web.{incremental, SbtWeb}
import incremental._

import de.neuland.jade4j._

object Import {
  val jade = TaskKey[Seq[File]]("jade", "Generate html files from jade")
}

object SbtJade extends AutoPlugin {
  override def requires = SbtWeb

  override def trigger = AllRequirements

  val autoImport = Import

  import SbtWeb.autoImport._
  import WebKeys._
  import autoImport._

  val name = "jade"

  lazy val jadeTask = Def.task {
    val sourceDir = (sourceDirectory in Assets).value
    val targetDir = (resourceManaged in jade in Assets).value
    val sources = (sourceDir ** ((includeFilter in jade in Assets).value --
      (excludeFilter in jade in Assets).value)).get
    val config = new JadeConfiguration
    val model = new java.util.HashMap[String, Object]
    config.setPrettyPrint(true)
    val results = incremental.syncIncremental(
      (streams in Assets).value.cacheDirectory / "run", sources) { srcs ⇒
      if (!srcs.isEmpty)
        streams.value.log.info(s"Processing ${srcs.size} $name source(s)")
      val targets = srcs map { src ⇒
        src.relativeTo(sourceDir) map { f ⇒
          targetDir / f.toString.replaceFirst("\\.jade$", ".html")
        } map { f ⇒
          (f, config.renderTemplate(config.getTemplate(src.toString), model))
        } map { case (f, html) ⇒
          IO.write(f, html)
          f
        }
      }
      val ops = srcs zip targets map { case (src, tgt) ⇒
        val op = tgt map { t ⇒
          OpSuccess(Set(src), Set(t))
        } getOrElse OpFailure
        src → op
      }
      (Map(ops: _*), targets.flatten)
    }
    val targets = results._2
    if(targets.length > 0)
      streams.value.log.info(
        s"$name compilation results: ${targets.mkString(", ")}")
    results._1.toSeq
  }

  val baseSbtJadeSettings = Seq(
    excludeFilter in jade := HiddenFileFilter,
    includeFilter in jade := s"*.$name",
    managedResourceDirectories += (resourceManaged in jade in Assets).value,
    resourceManaged in jade in Assets := webTarget.value / name / "main",
    resourceGenerators in Assets <+= jade in Assets,
    jade in Assets :=
      jadeTask.dependsOn(WebKeys.webModules in Assets).value
  )

  override def projectSettings =
    Seq(jade := (jade in Assets).value) ++
    inConfig(Assets)(baseSbtJadeSettings)
}
