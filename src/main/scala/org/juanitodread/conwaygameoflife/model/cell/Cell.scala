package org.juanitodread.conwaygameoflife.model.cell

class Cell private (val id: String, val state: State.Value = State.Dead) {

  def isAlive() = this.state == State.Alive

  def isDead() = this.state == State.Dead

  override def toString() = this.state match {
    case State.Dead => "o"
    case State.Alive => "x"
  }
}

object Cell {
  def apply(row: Int, col: Int) = new Cell(makeId(row, col))
  def apply(row: Int, col: Int, state: State.Value) = new Cell(makeId(row, col), state)

  private def makeId(row: Int, col: Int) = s"$row:$col"
}