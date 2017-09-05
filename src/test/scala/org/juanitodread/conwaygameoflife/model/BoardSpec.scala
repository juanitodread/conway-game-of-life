package org.juanitodread.conwaygameoflife.model

import org.juanitodread.conwaygameoflife.UnitSpec
import org.juanitodread.conwaygameoflife.model.Board

class BoardSpec extends UnitSpec {

  "A Board" should "be created with a default size of 30" in {
    val board = Board()
    assert(board.size === 30)
  }

  "A Board" should "be created with a size of 100" in {
    val board = Board(100)
    assert(board.size === 100)
  }

  "A Board" should "throw IllegalArgumentException if the provided size is less than 30" in {
    assertThrows[IllegalArgumentException] {
      val board = Board(29)
    }
  }

  "A Board" should "throw IllegalArgumentException if the provided size is greather than 100" in {
    assertThrows[IllegalArgumentException] {
      val board = Board(101)
    }
  }

  "A Board" should "be initialized with Dead Cells" in {
    val size = 50
    val board = Board(size)

    for (
      row <- 0 until size;
      col <- 0 until size
    ) {
      assert(board.cellAt(row, col).isDead)
    }
  }

}