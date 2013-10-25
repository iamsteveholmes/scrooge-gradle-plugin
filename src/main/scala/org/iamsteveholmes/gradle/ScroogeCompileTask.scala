package org.iamsteveholmes.gradle

import org.gradle.api.tasks._
import java.io.File
import com.twitter.scrooge.{Main, Compiler}
import _root_.scala.collection.JavaConverters._
import org.gradle.api.DefaultTask
import java.util

class ScroogeCompileTask extends DefaultTask {

    private var _dest: File = new File("src/gen/scala/")
    private var _files: java.lang.Iterable[File] = util.Arrays.asList(new File("src/main/thrift/"))
    private var _opts: java.lang.Iterable[String] = util.Arrays.asList("--finagle")

    @OutputDirectory
    def getDest = _dest

    def setDest(destinationDirectory: File) {
        _dest = destinationDirectory
    }

    @InputFiles
    def getThriftFiles = _files

    def setThriftFiles(files: java.lang.Iterable[File]) {
        _files = files
    }


    @Input
    @Optional
    def getOpts = _opts

    def setOpts(options: java.lang.Iterable[String]) {
        this._opts = options
    }

    @TaskAction
    def compile() {
        val destination = getDest.getAbsolutePath
        val thriftFiles = _files.asScala.map{_.getAbsolutePath}
        thriftFiles.foreach(println)
        val compiler = new Compiler()
        compiler.destFolder = destination

        val args: Seq[String] = _opts.asScala.toSeq ++ thriftFiles.toSeq
        Main.parseOptions(compiler, args)
        compiler.run()
    }
}
