/**
 * Conway's Game of Life
 *
 * Copyright 2015 juanitodread
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.juanitodread.conwaygameoflife.view

import javax.swing.{
  SwingUtilities,
  UIManager
}

import scala.swing._
import scala.swing.BorderPanel.Position._
import scala.swing.event._
import scala.concurrent._

import com.typesafe.scalalogging._

import org.juanitodread.conwaygameoflife.model.Board
import org.juanitodread.conwaygameoflife.model.cell.{
  Cell,
  State
}

import scala.language.reflectiveCalls

import ExecutionContext.Implicits.global

/**
 * This Frame represents a Conway's game of life interface.
 *
 * @author juanitodread
 * @version 1.1.2
 * @since 1.0.0
 *
 * Mar 23, 2015
 */
class ApplicationView(val boardSize: Int) extends SimpleSwingApplication with LazyLogging {

  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName)

  private[this] val state = Board(boardSize)
  val graphicBoard = buildGraphicBoard(state)

  // State functions

  /**
   * Refresh the graphic board according to the given state. If the state has
   * alive cells then the graphic board paint them as alive cell.
   *
   * @param state The state of the board.
   */
  def refreshGraphicBoard(state: Board): Unit = {
    logger.debug("Refreshing graphic board according state")

    graphicBoard.contents.foreach { content =>
      val graphicCell = content.asInstanceOf[ToggleButton]
      val (row, col) = Cell.parseId(graphicCell.name)
      graphicCell.selected = state.cellAt(row, col).isAlive()
    }
  }

  /**
   * Refresh the state of the application according to the user input (state of the graphic board).
   *
   * @param graphicBoard The graphic board (the view)
   */
  def refreshState(graphicBoard: GridPanel): Unit = {
    logger.debug("Refreshing state based on graphic board")

    graphicBoard.contents.foreach { content =>
      val graphicCell = content.asInstanceOf[ToggleButton]
      val (row, col) = Cell.parseId(graphicCell.name)

      if (graphicCell.selected) {
        state.aliveCell(row, col)
      } else {
        state.deadCell(row, col)
      }
    }
  }

  private[this] def buildCellGraphicBoard(id: String): ToggleButton = {
    logger.debug(s"Building a graphic cell with id: $id")

    new ToggleButton {
      name = id
      listenTo(mouse.clicks, mouse.moves)

      reactions += {
        case e: MousePressed =>
          mouseIsPressed = SwingUtilities.isLeftMouseButton(e.peer)
        case e: MouseReleased =>
          mouseIsPressed = !SwingUtilities.isLeftMouseButton(e.peer)
        case _: MouseEntered | _: MouseExited =>
          if (mouseIsPressed) { this.selected = true }
      }
    }
  }

  private[this] def buildGraphicBoard(state: Board): GridPanel = {
    logger.debug(s"Building graphic board of size: ${state.size}")

    new GridPanel(state.size, state.size) {
      for {
        row <- 0 until state.size
        col <- 0 until state.size
      } {
        contents += buildCellGraphicBoard(Cell.makeId(row, col))
      }
    }
  }

  // Graphics
  var mouseIsPressed = false

  //   Panels
  val leftPanel = new BoxPanel(Orientation.Vertical) {
    preferredSize = new Dimension(75, 600)
    border = Swing.EmptyBorder(5, 5, 0, 0)

    val startBtn = ApplicationView.buildButton("start", "Start")
    val stopBtn = ApplicationView.buildButton("stop", "Stop")
    val clearBtn = ApplicationView.buildButton("clear", "Clear")
    val aboutBtn = ApplicationView.buildButton("about", "About")

    contents ++= List(
      startBtn,
      stopBtn,
      clearBtn,
      aboutBtn)
  }

  val southPanel = new FlowPanel(FlowPanel.Alignment.Right)() {
    val generations = new Label {
      text = "Generations: 0"
    }
    contents += generations
  }

  val mainPanel = new BorderPanel {
    layout(graphicBoard) = Center
    layout(leftPanel) = West
    layout(southPanel) = South
  }

  //    Frame
  def top = new MainFrame {
    logger.info(s"Look and feel used: ${UIManager.getLookAndFeel}")
    var running = false
    var generation = 0

    title = ApplicationView.TitleApp

    contents = mainPanel

    listenTo(leftPanel.startBtn)
    listenTo(leftPanel.stopBtn)
    listenTo(leftPanel.clearBtn)
    listenTo(leftPanel.aboutBtn)

    reactions += {
      case ButtonClicked(component) if component == leftPanel.startBtn => {
        logger.info("Start button clicked")

        running = true
        leftPanel.startBtn.enabled = false
        leftPanel.clearBtn.enabled = false
        leftPanel.aboutBtn.enabled = false

        val startFuture: Future[Unit] = Future {
          logger.info("Starting Future call...")

          while (running) {
            val nextState = Board(state.size)
            refreshState(graphicBoard)

            for {
              row <- 0 until state.size
              col <- 0 until state.size
            } {
              if (state.calculateCellState(row, col) == State.Alive) {
                nextState.aliveCell(row, col)
              } else {
                nextState.deadCell(row, col)
              }
            }

            refreshGraphicBoard(nextState)
            generation += 1
            southPanel.generations.text = s"Generations: $generation"

            logger.info(southPanel.generations.text)
            Thread.sleep(ApplicationView.TimeSleep)
          }
        }
        startFuture onComplete {
          case _ => logger.info("Future.onComplete => Start action stopped")
        }
      }
      case ButtonClicked(component) if component == leftPanel.stopBtn => {
        logger.info("Stop button clicked")

        running = false
        leftPanel.startBtn.enabled = true
        leftPanel.clearBtn.enabled = true
        leftPanel.aboutBtn.enabled = true
      }
      case ButtonClicked(component) if component == leftPanel.clearBtn => {
        logger.info("Clear button clicked")

        generation = 0
        southPanel.generations.text = s"Generations: $generation"
        state.reset()
        refreshGraphicBoard(state)
      }
      case ButtonClicked(component) if component == leftPanel.aboutBtn => {
        logger.info("About button clicked")

        Dialog.showMessage(this, "Conway's game of life in Scala\n\n@juanitodread - 2015 - v1.2.0")
      }
    }
    size = new Dimension(800, 600)
    preferredSize = new Dimension(800, 600)
    minimumSize = new Dimension(400, 300)
    pack()
  }
}

object ApplicationView {

  final val TitleApp = "Conway's Game of Life"
  final val DefaultBoardSize = 50
  final val TimeSleep = 1 * 100

  def apply(size: Int = DefaultBoardSize) = new ApplicationView(size)

  private def buildButton(id: String, label: String): Button = {
    new Button {
      name = id
      text = label
      maximumSize = new Dimension(70, 25)
      minimumSize = maximumSize
    }
  }
}