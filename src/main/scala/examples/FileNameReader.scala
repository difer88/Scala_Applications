package examples

import java.nio.file.{Paths, Files}

class FileNameReader {

  val ScalaSource = "*.sc"
  val Path = "C:\\Desenvolvimento\\Projetos\\Udemy\\Scala_Tests\\src\\main\\scala\\section_01"

  val stream = Files.newDirectoryStream(Paths.get(Path), ScalaSource)

}

object FileNameReader {

  def main(args: Array[String]): Unit = {

    val fileNameReader = new FileNameReader

    val paths = fileNameReader.stream.iterator

    while (paths.hasNext) {
      println(paths.next.getFileName)
    }

  }
}
