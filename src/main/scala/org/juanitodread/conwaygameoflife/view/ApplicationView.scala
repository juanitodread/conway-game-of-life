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

import javax.swing.SwingUtilities
import javax.swing.UIManager

import scala.swing._
import scala.swing.BorderPanel.Position._
import scala.swing.event._
import scala.concurrent._
import ExecutionContext.Implicits.global

import scala.collection.parallel.mutable.ParArray

import com.typesafe.scalalogging._

/**
 * This Frame represents a Conway's game of life interface.
 *
 * @author juanitodread
 * @version 1.1.2
 * @since 1.0.0
 *
 * Mar 23, 2015
 */
class ApplicationView extends SimpleSwingApplication with LazyLogging {

  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName)

  val xAxis = ApplicationView.MatrixSize

  val gridCell = initialGrid

    val gridPanel = new GridPanel(xAxis, xAxis) {
      logger.info("Initializing grid layout...")
      for {
        x <- 0 until xAxis
        y <- 0 until xAxis
      } {
        contents += gridCell(x)(y)
      }
  }

  val start = new Button {
    text = "Start"
  }

  val stop = new Button {
    text = "Stop"
  }

  val clear = new Button {
    text = "Clear"
  }

  val buttonMinSize = new Dimension(70, 25)
  val buttonMaxSize = new Dimension(70, 25)
  val boxPanelSize = new Dimension(75, 600)

  val leftPanel = new BoxPanel(Orientation.Vertical) {
    preferredSize = boxPanelSize
    border = Swing.EmptyBorder(5, 5, 0, 0)
    contents += start
    contents += stop
    contents += clear

    contents.foreach {
      x =>
        x.maximumSize = buttonMaxSize
        x.minimumSize = buttonMinSize
    }

    listenTo(start)
  }
  
  val generationsLabel = new Label{
    text = "Generations: 0"
  }
  
  val southPanel = new FlowPanel(FlowPanel.Alignment.Right)() {
    contents += generationsLabel
  }
  
  val mainLayout = new BorderPanel {
    layout(gridPanel) = Center
    layout(leftPanel) = West
    layout(southPanel) = South
  }

  var generations = false

  var mouseIsPressed = false

  var generation = 0

  def initialGrid() = ParArray.tabulate[ToggleButton](xAxis, xAxis) {
    (x, y) =>
      new ToggleButton {
        name = s"$x:$y"
        listenTo(mouse.clicks, mouse.moves)
        // Add reactions when mouse events occurs
        reactions += {
          case e: MousePressed =>
            mouseIsPressed = SwingUtilities.isLeftMouseButton( e.peer )
          case e: MouseReleased =>
            mouseIsPressed = !SwingUtilities.isLeftMouseButton( e.peer )
          case e: MouseEntered =>
            if ( mouseIsPressed ) { this.selected = true }
          case e: MouseExited =>
            if ( mouseIsPressed ) { this.selected = true }
        }
      }
  }

  def gridCellRows() = gridCell.iterator.map(_.iterator)

  def mod(x: Int, m: Int): Int = {
    val absoluteM = m.abs
    (x % absoluteM + absoluteM) % absoluteM
  }

  def getNeighborCount(x: Int, y: Int): Int = {
    var neighborCount = 0
    val xSize = xAxis
    val ySize = xAxis

    if (gridCell(mod(x + 1, xSize))(y).selected) {
      neighborCount += 1
    }

    if (gridCell(mod(x + 1, xSize))(mod(y + 1, ySize)).selected) {
      neighborCount += 1
    }

    if (gridCell(x)(mod(y + 1, ySize)).selected) {
      neighborCount += 1
    }

    if (gridCell(x)(mod(y - 1, ySize)).selected) {
      neighborCount += 1
    }

    if (gridCell(mod(x + 1, xSize))(mod(y - 1, ySize)).selected) {
      neighborCount += 1
    }

    if (gridCell(mod(x - 1, xSize))(y).selected) {
      neighborCount += 1
    }

    if (gridCell(mod(x - 1, xSize))(mod(y - 1, ySize)).selected) {
      neighborCount += 1
    }

    if (gridCell(mod(x - 1, xSize))(mod(y + 1, ySize)).selected) {
      neighborCount += 1
    }

    neighborCount
  }

  def getState(x: Int, y: Int) = gridCell(x)(y).selected  && 
                                 getNeighborCount(x, y) == 2 || 
                                 getNeighborCount(x, y) == 3
  

  val nextGridGeneration = initialGrid

  def top = new MainFrame {
    logger.info(s"Look and feel used: ${UIManager.getLookAndFeel}")
    title = ApplicationView.TitleApp

    contents = mainLayout

    listenTo(start)
    listenTo(stop)
    listenTo(clear)

    reactions += {
      case ButtonClicked(component) if component == start => {
        logger.info("Start button clicked")
        generations = true
        start.enabled = false
        clear.enabled = false

        val startFuture: Future[Unit] = Future {
          logger.info("Starting Future call...")
          while (generations) {
            for {
              x <- 0 until xAxis
              y <- 0 until xAxis
            } {
              nextGridGeneration(x)(y).selected = getState(x, y)
            }
            for {
              x <- 0 until xAxis
              y <- 0 until xAxis
            } {
              gridCell(x)(y).selected = nextGridGeneration(x)(y).selected
            }
            generation += 1
            logger.info(s"New generation: $generation")
            generationsLabel.text = s"Generations: $generation"
            Thread.sleep(ApplicationView.TimeSleep)
          }
        }
        startFuture onSuccess {
          case u => logger.info("Future.onSuccess => Start action stopped") 
        }
      }
      case ButtonClicked(component) if component == stop => {
        logger.info("Stop button clicked")
        generations = false
        start.enabled = true
        clear.enabled = true
      }
      case ButtonClicked(component) if component == clear => {
        logger.info("Clear button clicked")
        generation = 0
        generationsLabel.text = s"Generations: $generation"
        gridCell.foreach {
          x =>
            x.foreach {
              y => y.selected = false
            }
        }
      }
    }
    size = new Dimension(800, 600)
    preferredSize = new Dimension(800, 600)
    minimumSize = new Dimension(400, 300)
    pack
  }
}


object ApplicationView {

  val TitleApp = "Conway's Game of Life - v1.1.2 :: juanitodread"

  val MatrixSize = 50

  val TimeSleep = 1 * 100

  def apply( ) = new ApplicationView( )
}