package se.rosenhorn.scrooge

import java.io.File

import org.scalatest.{BeforeAndAfterEach, Matchers, WordSpec}

class ScroogePluginSpec extends WordSpec with Matchers with BeforeAndAfterEach {

  def recursiveListFiles(f: File): Array[File] = {
    val these = f.listFiles
    these ++ these.filter(_.isDirectory).flatMap(recursiveListFiles)
  }

  "ScroogePlugin" should {

    "compile builds 2 scala files with finagle options set" in {

      val sourceDir = new File(getClass.getResource("/thrift").getPath)

      CompilerHelper.compile(new File("target/generated"), Seq("-v", "--finagle"), sourceDir)

      val searchDir = new File(sourceDir, "../../../../target/generated")
      val files = recursiveListFiles(searchDir).filter(_.getName.endsWith(".scala"))
      files.length shouldBe 2
    }
  }
}
