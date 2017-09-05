package org.juanitodread.conwaygameoflife.model.cell

class Cell(state: State.Value = State.Dead) {

  def isAlive() = this.state == State.Alive

  def isDead() = this.state == State.Dead

  override def toString() = this.state match {
    case State.Dead => "o"
    case State.Alive => "x"
  }
}

object Cell {
  def apply() = new Cell()
  def apply(state: State.Value) = new Cell(state)
}