package org.juanitodread.conwaygameoflife.view

import org.juanitodread.conwaygameoflife.UnitSpec
import org.juanitodread.conwaygameoflife.model.Board
import org.juanitodread.conwaygameoflife.model.cell.Cell

import scala.swing.{ GridPanel, ToggleButton }

class ApplicationViewSpec extends UnitSpec {

  def createDiagonalAliveState() = {
    val state = Board(30)

    for {
      row <- 0 until state.size
      col <- 0 until state.size
    } {
      if (row == col) state.aliveCell(row, col)
    }
    state
  }

  "An ApplicationView of default size" should "be created with board size of 50" in {
    val applicationView = ApplicationView()
    assert(applicationView.boardSize === 50)
  }

  "An ApplicationView of size 30" should "be created with board size of 30" in {
    val applicationView = ApplicationView(30)
    assert(applicationView.boardSize === 30)
  }

  "An ApplicationView of invalid state size" should "raise IllegalArgumentException" in {
    assertThrows[IllegalArgumentException] {
      ApplicationView(29)
    }

    assertThrows[IllegalArgumentException] {
      ApplicationView(101)
    }
  }

  "An ApplicationView of size 30" should "have a graphic board of type GridPanel" in {
    val applicationView = ApplicationView(30)
    val graphicBoard = applicationView.graphicBoard

    assert(graphicBoard.isInstanceOf[GridPanel] === true)
  }

  "An ApplicationView of size 30" should "have a graphic board of 30X30 cells" in {
    val applicationView = ApplicationView(30)
    val graphicBoard = applicationView.graphicBoard

    assert(graphicBoard.contents.size === 900)
  }

  "An ApplicationView of size 30" should "have a graphic board of 30X30 cells of type ToggleButton" in {
    val applicationView = ApplicationView(30)
    val graphicBoard = applicationView.graphicBoard

    assert(graphicBoard.contents.forall(_.isInstanceOf[ToggleButton]) === true)
  }

  "An ApplicationView" should "refresh their graphic board based on a default state" in {
    val applicationView = ApplicationView(30)

    applicationView.graphicBoard.contents.foreach { cell =>
      val graphicCell = cell.asInstanceOf[ToggleButton]
      assert(graphicCell.selected === false)
    }
  }

  "An ApplicationView" should "refresh their graphic board based on a diagonal state" in {
    val state = createDiagonalAliveState()
    val applicationView = ApplicationView(state.size)

    applicationView.refreshGraphicBoard(state)

    applicationView.graphicBoard.contents.foreach { cell =>
      val graphicCell = cell.asInstanceOf[ToggleButton]
      val (row, col) = Cell.parseId(graphicCell.name)
      if (row == col) assert(graphicCell.selected === true)
      else assert(graphicCell.selected === false)
    }
  }

}
