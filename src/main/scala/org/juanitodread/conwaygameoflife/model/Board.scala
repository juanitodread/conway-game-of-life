package org.juanitodread.conwaygameoflife.model

import scala.collection.mutable.ArrayBuffer

import org.juanitodread.conwaygameoflife.model.cell.{
  Cell,
  State
}

class Board(val size: Int = Board.MinSize) {
  require(size >= Board.MinSize && size <= Board.MaxSize)

  private var board = createBoard()

  def reset(): Unit = {
    board = createBoard()
  }

  def cellAt(row: Int, col: Int): Cell = {
    require(isValidCellPosition(row, col))
    Cell(row, col, board(row)(col).state)
  }

  def aliveCell(row: Int, col: Int): Unit = {
    require(isValidCellPosition(row, col))
    board(row)(col) = Cell(row, col, State.Alive)
  }

  def deadCell(row: Int, col: Int): Unit = {
    require(isValidCellPosition(row, col))
    board(row)(col) = Cell(row, col, State.Dead)
  }

  def countAliveNeighborsForCell(row: Int, col: Int): Int = {
    require(isValidCellPosition(row, col))
    countNeighborsFromCell(row, col, State.Alive)
  }

  def countDeadNeighborsForCell(row: Int, col: Int): Int = {
    require(isValidCellPosition(row, col))
    countNeighborsFromCell(row, col, State.Dead)
  }

  def calculateCellState(row: Int, col: Int): State.Value = {
    require(isValidCellPosition(row, col))
    val currentCell = this.cellAt(row, col)
    (currentCell.isAlive(), this.countAliveNeighborsForCell(row, col)) match {
      case (true, 2) => State.Alive
      case (_, 3) => State.Alive
      case _ => State.Dead
    }
  }

  override def toString(): String = {
    board.map(row => row.mkString(",")).mkString("\n")
  }

  private def createBoard() = ArrayBuffer.tabulate(size, size)(Cell(_, _))

  private def isValidCellPosition(row: Int, col: Int): Boolean = {
    row >= 0 && row < this.size && col >= 0 && col < this.size
  }

  private[this] def countNeighborsFromCell(row: Int, col: Int, state: State.Value): Int = {
    var neighborsCount = 0
    for (
      x <- row - 1 to row + 1;
      y <- col - 1 to col + 1
    ) {
      if (x != row || y != col) {
        val realX = Board.calculateCircularPosition(x, this.size)
        val realY = Board.calculateCircularPosition(y, this.size)

        val neighbor = this.cellAt(realX, realY)
        if (neighbor.state == state) {
          neighborsCount += 1
        }
      }
    }
    neighborsCount
  }

}

object Board {
  // $COVERAGE-OFF$
  private final val MinSize = 30
  private final val MaxSize = 100
  // $COVERAGE-ON$

  def apply() = new Board()
  def apply(size: Int) = new Board(size)

  def calculateCircularPosition(neighborPosition: Int, boardSize: Int): Int = {
    (neighborPosition % boardSize + boardSize) % boardSize
  }
}