package org.juanitodread.conwaygameoflife.model

import org.juanitodread.conwaygameoflife.UnitSpec
import org.juanitodread.conwaygameoflife.model.cell.{
  Cell,
  State
}

class BoardSpec extends UnitSpec with BoardSpecConstants {

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
      Board(29)
    }
  }

  "A Board" should "throw IllegalArgumentException if the provided size is greather than 100" in {
    assertThrows[IllegalArgumentException] {
      Board(101)
    }
  }

  "A Board" should "return a Dead Cell according the position [0,0]" in {
    val board = Board()
    val cell = board.cellAt(0, 0)
    assert(cell.isInstanceOf[Cell])
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

  "A Board" should "create a new Dead Cell at the position [0,0]" in {
    val board = Board()
    val oldCell = board.cellAt(0, 0)
    board.deadCell(0, 0)
    val newCell = board.cellAt(0, 0)
    assert(oldCell !== newCell)
    assert(newCell.isDead)
  }

  "A Board" should "create a new Alive Cell at the position [0,0]" in {
    val board = Board()
    val oldCell = board.cellAt(0, 0)
    board.aliveCell(0, 0)
    val newCell = board.cellAt(0, 0)
    assert(oldCell !== newCell)
    assert(newCell.isAlive)
  }

  "A Default Board" should "return a String grid of size 30 with o's" in {
    val board = Board()
    assert(board.toString === DefaultBoardToString)
  }

  "A Board" should "calculate a valid position for edges in the board" in {
    val board = Board()
    val minEdgeCase = -1
    val maxEdgeCase = board.size

    assert(Board.calculateCircularPosition(minEdgeCase, board.size) === board.size - 1)
    assert(Board.calculateCircularPosition(maxEdgeCase, board.size) === 0)

    for (i <- 0 until board.size) {
      assert(Board.calculateCircularPosition(i, board.size) === i)
    }
  }

  "A Board" should "calculate how many alive neighbors a cell has" in {
    val board = Board()

    var neighbors = board.countAliveNeighborsForCell(0, 0)
    assert(neighbors === 0)

    board.aliveCell(29, 29)
    neighbors = board.countAliveNeighborsForCell(0, 0)
    assert(neighbors === 1)

    board.aliveCell(29, 0)
    neighbors = board.countAliveNeighborsForCell(0, 0)
    assert(neighbors === 2)

    board.aliveCell(29, 1)
    neighbors = board.countAliveNeighborsForCell(0, 0)
    assert(neighbors === 3)

    board.aliveCell(0, 29)
    neighbors = board.countAliveNeighborsForCell(0, 0)
    assert(neighbors === 4)

    board.aliveCell(0, 1)
    neighbors = board.countAliveNeighborsForCell(0, 0)
    assert(neighbors === 5)

    board.aliveCell(1, 29)
    neighbors = board.countAliveNeighborsForCell(0, 0)
    assert(neighbors === 6)

    board.aliveCell(1, 0)
    neighbors = board.countAliveNeighborsForCell(0, 0)
    assert(neighbors === 7)

    board.aliveCell(1, 1)
    neighbors = board.countAliveNeighborsForCell(0, 0)
    assert(neighbors === 8)

    board.aliveCell(2, 2)
    neighbors = board.countAliveNeighborsForCell(0, 0)
    assert(neighbors === 8)
  }

  "A Board" should "calculate how many dead neighbors a cell has" in {
    val board = Board()

    var neighbors = board.countDeadNeighborsForCell(0, 0)
    assert(neighbors === 8)

    board.aliveCell(29, 29)
    neighbors = board.countDeadNeighborsForCell(0, 0)
    assert(neighbors === 7)

    board.aliveCell(29, 0)
    neighbors = board.countDeadNeighborsForCell(0, 0)
    assert(neighbors === 6)

    board.aliveCell(29, 1)
    neighbors = board.countDeadNeighborsForCell(0, 0)
    assert(neighbors === 5)

    board.aliveCell(0, 29)
    neighbors = board.countDeadNeighborsForCell(0, 0)
    assert(neighbors === 4)

    board.aliveCell(0, 1)
    neighbors = board.countDeadNeighborsForCell(0, 0)
    assert(neighbors === 3)

    board.aliveCell(1, 29)
    neighbors = board.countDeadNeighborsForCell(0, 0)
    assert(neighbors === 2)

    board.aliveCell(1, 0)
    neighbors = board.countDeadNeighborsForCell(0, 0)
    assert(neighbors === 1)

    board.aliveCell(1, 1)
    neighbors = board.countDeadNeighborsForCell(0, 0)
    assert(neighbors === 0)

    board.aliveCell(2, 2)
    neighbors = board.countDeadNeighborsForCell(0, 0)
    assert(neighbors === 0)
  }

  "A Board" should "calculate the new state of a Cell" in {
    val board = Board()
    board.aliveCell(0, 0)

    assert(board.calculateCellState(0, 0) === State.Dead)
    board.aliveCell(0, 1)
    board.aliveCell(1, 0)
    assert(board.calculateCellState(0, 0) === State.Alive)

    board.aliveCell(1, 1)
    assert(board.calculateCellState(0, 0) === State.Alive)

    board.aliveCell(29, 29)
    assert(board.calculateCellState(0, 0) === State.Dead)
  }

}