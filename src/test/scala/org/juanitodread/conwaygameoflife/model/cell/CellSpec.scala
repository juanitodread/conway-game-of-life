package org.juanitodread.conwaygameoflife.model.cell

import org.juanitodread.conwaygameoflife.UnitSpec
import org.juanitodread.conwaygameoflife.model.cell.{
  Cell,
  State
}

class CellSpec extends UnitSpec {

  "A Cell" should "be created with a default dead state" in {
    val cell = Cell()
    assert(cell.isDead)
  }

  "A Dead Cell" should "have toString method equals to \"o\"" in {
    val cell = Cell(State.Dead)
    assert(cell.toString === "o")
  }

  "A Dead Cell" should "return true for isDead() method" in {
    val cell = Cell(State.Dead)
    assert(cell.isDead)
  }

  "An Alive Cell" should "have toString method equals to \"x\"" in {
    val cell = Cell(State.Alive)
    assert(cell.toString === "x")
  }

  "An Alive Cell" should "return true for isAlive() method" in {
    val cell = Cell(State.Alive)
    assert(cell.isAlive)
  }

}