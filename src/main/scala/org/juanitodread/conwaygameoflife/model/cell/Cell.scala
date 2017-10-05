package org.juanitodread.conwaygameoflife.model.cell

@SerialVersionUID(1L)
class Cell private (val id: String, val state: State.Value = State.Dead) extends Serializable {

  def isAlive() = this.state == State.Alive

  def isDead() = this.state == State.Dead

  override def toString() = this.state match {
    case State.Dead => "o"
    case State.Alive => "x"
  }
}

object Cell {
  private final lazy val IdDelimiter = ":"
  def apply(row: Int, col: Int) = new Cell(makeId(row, col))
  def apply(row: Int, col: Int, state: State.Value) = new Cell(makeId(row, col), state)

  def makeId(row: Int, col: Int) = s"$row$IdDelimiter$col"
  def parseId(id: String) = {
    val parsedId = id.split(IdDelimiter, 2)
    if (parsedId.size != 2) throw new IllegalArgumentException()

    (parsedId(0).toInt, parsedId(1).toInt)
  }
}