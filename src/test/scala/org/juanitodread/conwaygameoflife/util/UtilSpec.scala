package org.juanitodread.conwaygameoflife.util

import java.io.File

import org.juanitodread.conwaygameoflife.UnitSpec
import org.juanitodread.conwaygameoflife.model.Board

class UtilSpec extends UnitSpec {

  def createNewFilePath(fileName: String): String = {
    val currentPath = new File(".").getCanonicalPath()
    s"$currentPath${File.separator}$fileName"
  }

  def fileExists(fileName: String): Boolean = {
    val file = new File(fileName)
    file.exists() && file.isFile
  }

  "A Util object" should "write a string to a valid path" in {
    val strObject = "test"
    val filePath = createNewFilePath("serialize-string.cgl")

    Util.binarySerialization(strObject, filePath)
    assert(fileExists(filePath) === true)
  }

  "A Util object" should "write a Board to a valid path" in {
    val board = Board(30)
    val filePath = createNewFilePath("serialize-board.cgl")

    Util.binarySerialization(board, filePath)
    assert(fileExists(filePath) === true)
  }

  "A Util object" should "read a string from a valid path" in {
    val filePath = createNewFilePath("serialize-string.cgl")

    val str = Util.binaryDeserialization(filePath).asInstanceOf[String]
    assert(str === "test")
  }

  "A Util object" should "read a Board from a valid path" in {
    val filePath = createNewFilePath("serialize-board.cgl")

    val board = Util.binaryDeserialization(filePath).asInstanceOf[Board]
    assert(board.size === 30)
  }

}
