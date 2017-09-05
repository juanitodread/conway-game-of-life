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

  override def toString(): String = {
    board.map(row => row.mkString(",")).mkString("\n")
  }

  private def isValidCellPosition(row: Int, col: Int): Boolean = {
    row >= 0 && row < this.size && col >= 0 && col < this.size
  }

}

object Board {
  private final val MinSize = 30
  private final val MaxSize = 100

  def apply() = new Board()
  def apply(size: Int) = new Board(size)
}