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
  
  val titleApp = "Conway's Game of Life"
  
  val xAxis = 50
  
  val gridCell = Array.tabulate[ToggleButton](xAxis, xAxis){
    (x, y) => new ToggleButton{
      name = s"$x:$y"
    }
  }
  
  def gridCellRows() = gridCell.iterator.map(_.iterator)
  
  println(s"gridCell: $gridCell")
  
  val gridPanel = new GridPanel(xAxis, xAxis) {
    println("Start grid layout")
    for(row <- gridCellRows; elem <- row) {
      contents += elem
    }
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

  def top = new MainFrame {
    title = titleApp
    
    menuBar = menu
    
    contents = mainLayout
    
    listenTo(leftPanel.start)
    listenTo(leftPanel.clear)
    
    reactions += {
      case ButtonClicked(component) if component == leftPanel.start => {
        println("Start clicked")
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