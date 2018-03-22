package se.rosenhorn.scrooge

import org.gradle.api.tasks._
import java.io.File

import com.twitter.scrooge.{Compiler, Main}
import com.typesafe.scalalogging.LazyLogging

import _root_.scala.collection.JavaConverters._
import org.gradle.api.{DefaultTask, GradleException}
import java.lang

import org.gradle.api.file.FileCollection
import org.gradle.api.internal.file.collections.SimpleFileCollection

@CacheableTask
class ScroogeCompileTask extends DefaultTask with LazyLogging {
    // needs to be lazy so that the correct options is grabbed at runtime
    private lazy val pluginExtensions: ScroogePluginExtention = getProject.getExtensions
      .findByType(classOf[ScroogePluginExtention])

    private lazy val opts = pluginExtensions.getOpts

    def getSourceDir: File = {
        new File(s"${getProject.getProjectDir.getAbsolutePath}/${pluginExtensions.getSourceDir}")
    }

    @OutputDirectory
    def getOutputDir: File = {
        new File(s"${getProject.getProjectDir.getAbsolutePath}/${pluginExtensions.getTargetDir}")
    }

    @PathSensitive(PathSensitivity.NAME_ONLY)
    @InputFiles
    def getSourceFiles: FileCollection = {
        val files = CompilerHelper.recursiveListFiles(getSourceDir)
        new SimpleFileCollection(files.toList.asJava)
    }

    @Input
    @Optional
    def getOpts: lang.Iterable[String] = opts

    @TaskAction
    def compile() {
        CompilerHelper.compile(getOutputDir, opts.asScala, getSourceDir)
    }
}

object CompilerHelper extends LazyLogging {
    def recursiveListFiles(f: File): Array[File] = {
        val these = f.listFiles
        these ++ these.filter(_.isDirectory).flatMap(recursiveListFiles)
    }

    def compile(targetDir: File, opts: Seq[String], sourceDir: File): Unit = {
        val sourceFiles = recursiveListFiles(sourceDir).map (entry => {
            val absPath = entry.getAbsolutePath
            logger.info(s"Compiling $absPath")
            absPath
        })

        try {
            val compiler = new Compiler()
            compiler.destFolder = targetDir.getAbsolutePath
            val args: Seq[String] = opts ++ sourceFiles
            Main.parseOptions(compiler, args)
            compiler.run()
        } catch {
            case e: Exception =>
                throw new GradleException(e.getMessage, e)
        }
    }
}