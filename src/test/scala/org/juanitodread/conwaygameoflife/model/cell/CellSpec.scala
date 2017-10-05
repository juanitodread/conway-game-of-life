package org.juanitodread.conwaygameoflife.model.cell

import org.juanitodread.conwaygameoflife.UnitSpec

class CellSpec extends UnitSpec {

  "A Cell" should "be created with a default dead state" in {
    val cell = Cell(0, 0)
    assert(cell.isDead)
  }

  "A Dead Cell" should "have toString method equals to \"o\"" in {
    val cell = Cell(0, 0, State.Dead)
    assert(cell.toString === "o")
  }

  "A Dead Cell" should "return true for isDead() method" in {
    val cell = Cell(0, 0, State.Dead)
    assert(cell.isDead)
  }

  "An Alive Cell" should "have toString method equals to \"x\"" in {
    val cell = Cell(0, 0, State.Alive)
    assert(cell.toString === "x")
  }

  "An Alive Cell" should "return true for isAlive() method" in {
    val cell = Cell(0, 0, State.Alive)
    assert(cell.isAlive)
  }

  "A Cell with id (0,0)" should "return the string '0:0' as id" in {
    val cell = Cell(0, 0, State.Alive)
    assert(cell.id === "0:0")
  }

  "A Cell object" should "create an id according row and col parameters" in {
    var (row, col) = (3, 4)
    assert(Cell.makeId(row, col) === "3:4")

    row = -3
    col = -4
    assert(Cell.makeId(row, col) === "-3:-4")
  }

  "A Cell object" should "parse a valid id and return row and col values" in {
    val id = "3:4"
    val (row, col) = Cell.parseId(id)

    assert(row === 3)
    assert(col === 4)
  }

  "A Cell object" should "parse an id with multiple delimiters and throw IllegalArgumentException" in {
    assertThrows[IllegalArgumentException] {
      val id = "3:4:3"
      val (row, col) = Cell.parseId(id)
    }
  }

  "A Cell object" should "parse a non numeric id and throw IllegalArgumentException" in {
    assertThrows[IllegalArgumentException] {
      val id = "you:me"
      val (row, col) = Cell.parseId(id)
    }
  }

}