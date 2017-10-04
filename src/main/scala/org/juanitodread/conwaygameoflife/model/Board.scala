package org.juanitodread.conwaygameoflife.model

import scala.collection.mutable.ArrayBuffer
import org.juanitodread.conwaygameoflife.model.cell.{
  Cell,
  State
}

class Board(val size: Int = Board.MinSize) {
  require(size >= Board.MinSize && size <= Board.MaxSize)

  private val board = ArrayBuffer.fill(size, size)(Cell())

  def cellAt(row: Int, col: Int): Cell = {
    require(isValidCellPosition(row, col))
    val state = board(row)(col).isDead match {
      case true => State.Dead
      case false => State.Alive
    }
    Cell(state)
  }

  def aliveCell(row: Int, col: Int): Unit = {
    require(isValidCellPosition(row, col))
    board(row)(col) = Cell(State.Alive)
  }

  def deadCell(row: Int, col: Int): Unit = {
    require(isValidCellPosition(row, col))
    board(row)(col) = Cell(State.Dead)
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
    (currentCell.isAlive, this.cellShouldAlive(row, col)) match {
      case (true, true) => State.Alive
      case _ => State.Dead
    }
  }

  def cellShouldAlive(row: Int, col: Int): Boolean = {
    Board.ValidNeighborsCount.contains(this.countAliveNeighborsForCell(row, col))
  }

  override def toString(): String = {
    board.map(row => row.mkString(",")).mkString("\n")
  }

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
  private final val ValidNeighborsCount = List(2, 3)
  // $COVERAGE-ON$

  def apply() = new Board()
  def apply(size: Int) = new Board(size)

  def calculateCircularPosition(neighborPosition: Int, boardSize: Int): Int = {
    (neighborPosition % boardSize + boardSize) % boardSize
  }
}