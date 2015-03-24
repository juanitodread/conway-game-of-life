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

import scala.swing._
import scala.swing.BorderPanel.Position._
import javax.swing.BoxLayout
import scala.swing.event._

/**
 *
 *
 * @author juanitodread
 * @version $
 * @since 1.0
 *
 * Mar 23, 2015
 */
class ApplicationView extends SimpleSwingApplication {
  
  val titleApp = "Conway's Game of Life :: juanitodread :: 2015"
  
  val xAxis = 10
  
  val generations = 10

  val seconds = 1000 * 1

  val gridCell = Array.tabulate[ToggleButton](xAxis, xAxis){
    (x, y) => new ToggleButton{
      name = s"$x:$y"
    }
  }
  
  def gridCellRows() = gridCell.iterator.map(_.iterator)
  
  println(s"gridCell: $gridCell")
  
  val gridPanel = new GridPanel(xAxis, xAxis) {
    println("Start grid layout")
    for(x <- 0 until xAxis; y <- 0 until xAxis) {
      contents += gridCell(x)(y)
    }
    //for(row <- gridCellRows; elem <- row) {
    //  contents += elem
    //}
  }
  
  val leftPanel = new BoxPanel(Orientation.Vertical) {
    val start = new Button{
      text = "Start"
    }
    val clear = new Button{
      text = "Clear"
    }
    contents += start
    contents += clear
    
    listenTo(start)
  }
  
  val menu = new MenuBar {
    contents += new Menu( "A Menu" ) {
      contents += new MenuItem( "An item" )
      contents += new MenuItem( Action( "An action item" ) {
        println( "Action '" + titleApp + "' invoked" )
      } )
      contents += new Separator
      contents += new CheckMenuItem( "Check me" )
      contents += new CheckMenuItem( "Me too!" )
      contents += new Separator
      val a = new RadioMenuItem( "a" )
      val b = new RadioMenuItem( "b" )
      val c = new RadioMenuItem( "c" )
      val mutex = new ButtonGroup( a, b, c )
      contents ++= mutex.buttons
    }
    contents += new Menu( "Empty Menu" )
  }
  
  val mainLayout = new BorderPanel {
    layout(gridPanel) = Center
    layout(leftPanel) = West
  }

  def mod(x: Int, m: Int): Int = {
    val mod = (x %  m + m) % m
    mod
  }

  def getNeighborCount(x: Int, y: Int): Int = {
    var neighborCount = 0
    val xSize = xAxis
    val ySize = xAxis

    if(gridCell(mod(x + 1, xSize))(y).selected)                 {neighborCount = neighborCount + 1}
    if(gridCell(mod(x + 1, xSize))(mod(y + 1, ySize)).selected) {neighborCount = neighborCount + 1}
    if(gridCell(x)(mod(y + 1, ySize)).selected)                 {neighborCount = neighborCount + 1}
    if(gridCell(x)(mod(y - 1, ySize)).selected)                 {neighborCount = neighborCount + 1}
    if(gridCell(mod(x + 1, xSize))(mod(y - 1, ySize)).selected) {neighborCount = neighborCount + 1}
    if(gridCell(mod(x - 1, xSize))(y).selected)                 {neighborCount = neighborCount + 1}
    if(gridCell(mod(x - 1, xSize))(mod(y - 1, xSize)).selected) {neighborCount = neighborCount + 1}
    if(gridCell(mod(x - 1, xSize))(mod(y + 1, xSize)).selected) {neighborCount = neighborCount + 1}

    neighborCount
  }

  def getState(x: Int, y: Int): Boolean = {
    val isAlive = gridCell(x)(y).selected && getNeighborCount(x, y) == 2 || getNeighborCount(x,y) == 3
    println(s"Grid [${x},${y}]: ${isAlive}")
    isAlive
  }

  def top = new MainFrame {
    title = titleApp
    
    menuBar = menu
    
    contents = mainLayout
    
    listenTo(leftPanel.start)
    listenTo(leftPanel.clear)
    
    reactions += {
      case ButtonClicked(component) if component == leftPanel.start => {
        println("Start clicked")
        //for(i <- 1 to generations) {
            //println("Generation")
            for(x <- 0 until xAxis; y <- 0 until xAxis) {
              gridCell(x)(y).selected = getState(x, y)
            }
            //top.peer.repaint()
            //Thread.sleep(seconds);      
        //}
      }
      case ButtonClicked(component) if component == leftPanel.clear => {
        println("Clear clicked")
        for(row <- gridCellRows; elem <- row
            if(elem.selected)) {
          elem.selected = false
        }
      }
    }
    size = new Dimension(800, 600)
    preferredSize = new Dimension(800, 600)
    pack
  }
}