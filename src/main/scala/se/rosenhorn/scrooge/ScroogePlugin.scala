package se.rosenhorn.scrooge

import org.gradle.api.{Plugin, Project}

class ScroogePlugin extends Plugin[Project] {
  override def apply(project: Project): Unit = {
    project.getExtensions.create("scroogeConfig", classOf[ScroogePluginExtention])
    project.getTasks.create("compileScrooge", classOf[ScroogeCompileTask])
  }
}
