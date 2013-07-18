package org.iamsteveholmes.gradle

import org.gradle.api.tasks.{InputFiles, OutputDirectory, TaskAction}
import java.io.File
import com.twitter.scrooge.{Main, Compiler}
import scala.collection.JavaConverters._
import org.gradle.api.DefaultTask

class ScroogeCompileTask extends DefaultTask {

    private var _dest: File = _
    private var _files: java.lang.Iterable[File] = _
    def getDest = _dest
    def getThriftFiles = _files

    @OutputDirectory
    def setDest(destinationDirectory: File) {
        _dest = destinationDirectory
    }

    @InputFiles
    def setThriftFiles(files: java.lang.Iterable[File]) {
        _files = files
    }

    @TaskAction
    def compile() {
        val destination = getDest.getAbsolutePath
        val thriftFiles = _files.asScala.map{_.getAbsolutePath}
        thriftFiles.foreach(println)
        val compiler = new Compiler()
        compiler.destFolder = destination

        Main.parseOptions( compiler, thriftFiles.toSeq)
        compiler.run()
    }
}
