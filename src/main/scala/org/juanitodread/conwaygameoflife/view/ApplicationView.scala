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

/**
 * This Frame represents a Conway's game of life interface.
 *
 * @author juanitodread
 * @version 1.0.1
 * @since 1.0.0
 *
 * Mar 23, 2015
 */
class ApplicationView extends SimpleSwingApplication {
  
  UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName )
  
  val titleApp = "Conway's Game of Life :: juanitodread :: 2015"
  
  val xAxis = 50
  
  var generations = false

  val seconds = 100 * 1

  val gridCell = initialGrid

  var mouseIsPressed = false
  
  def initialGrid( ) = ParArray.tabulate[ToggleButton]( xAxis, xAxis ){
    (x, y) => new ToggleButton {
      name = s"$x:$y"
      listenTo( mouse.clicks, mouse.moves )
      // Add reactions when mouse events occurs
      reactions += {
        case e: MousePressed  => 
          if( SwingUtilities.isLeftMouseButton( e.peer ) ) { mouseIsPressed = true }
        case e: MouseReleased => 
          if( SwingUtilities.isLeftMouseButton(e.peer ) )  { mouseIsPressed = false }
        case e: MouseEntered  => 
          if( mouseIsPressed ) { selectButton( this ) }
        case e: MouseExited   => 
          if( mouseIsPressed ) { selectButton( this ) }
      }
    }
  }

  def selectButton( toggle: ToggleButton ) = toggle.selected = true

  def gridCellRows( ) = gridCell.iterator.map( _.iterator )

  val gridPanel = new GridPanel( xAxis, xAxis ) {
    println( "Start grid layout" )
    for ( x <- 0 until xAxis; 
          y <- 0 until xAxis ) {
      contents += gridCell( x )( y )
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
  
  val leftPanel = new BoxPanel( Orientation.Vertical ) {
    contents += start
    contents += stop
    contents += clear

    listenTo( start )
  }

  val mainLayout = new BorderPanel {
    layout( gridPanel ) = Center
    layout( leftPanel ) = West
  }

  def mod( x: Int, m: Int ): Int = {
    val absoluteM = m.abs
    ( x % absoluteM + absoluteM ) % absoluteM
  }

  def getNeighborCount( x: Int, y: Int ): Int = {
    var neighborCount = 0
    val xSize = xAxis
    val ySize = xAxis

    if ( gridCell( mod( x + 1, xSize ) )( y ).selected ) { 
      neighborCount = neighborCount + 1 
    }

    if ( gridCell( mod( x + 1, xSize ) )( mod( y + 1, ySize ) ).selected ) { 
      neighborCount = neighborCount + 1 
    }
    
    if ( gridCell( x )( mod( y + 1, ySize ) ).selected ) { 
      neighborCount = neighborCount + 1 
    }
    
    if ( gridCell( x )( mod( y - 1, ySize ) ).selected ) { 
      neighborCount = neighborCount + 1 
    }
    
    if ( gridCell( mod( x + 1, xSize ) )( mod( y - 1, ySize ) ).selected ) { 
      neighborCount = neighborCount + 1 
    }
    
    if ( gridCell( mod( x - 1, xSize ) )( y ).selected ) { 
      neighborCount = neighborCount + 1 
    }
    
    if ( gridCell( mod( x - 1, xSize ) )( mod( y - 1, ySize ) ).selected ) { 
      neighborCount = neighborCount + 1 
    }
    
    if ( gridCell( mod( x - 1, xSize ) )( mod( y + 1, ySize ) ).selected ) { 
      neighborCount = neighborCount + 1 
    }

    neighborCount
  }

  def getState( x: Int, y: Int ): Boolean = {
    gridCell( x )( y ).selected && getNeighborCount( x, y ) == 2 || getNeighborCount( x, y ) == 3
  }
  
  val nextGridGeneration = initialGrid

  def top = new MainFrame {
    println( UIManager.getLookAndFeel )
    title = titleApp

    contents = mainLayout

    listenTo( start )
    listenTo( stop )
    listenTo( clear )
    
    reactions += {
      case ButtonClicked( component ) if component == start => {
        println( "Start clicked" )
        println( Thread.currentThread )
        generations = true
        start.enabled = false
        clear.enabled = false

        val startFuture: Future[Unit] = future {
          println( s"Future started: ${Thread.currentThread}" )
          while ( generations ) {
            for ( x <- 0 until xAxis; 
                  y <- 0 until xAxis ) {
              nextGridGeneration( x )( y ).selected = getState( x, y )
            }
            for ( x <- 0 until xAxis; 
                  y <- 0 until xAxis ) {
              gridCell( x )( y ).selected = nextGridGeneration( x )( y ).selected
            }
            Thread.sleep( seconds )
          }          
        }
        startFuture onSuccess{
          case u => println( "Future.onSuccess => Start stoped" )
        }
      }
      case ButtonClicked( component ) if component == stop => {
        println( "Stop clicked" )
        generations = false
        start.enabled = true
        clear.enabled = true
      }
      case ButtonClicked( component ) if component == clear => {
        println( "Clear clicked" )
        gridCell.foreach { 
          x => x.foreach { 
            y => y.selected = false 
          } 
        }
      }
    }
    size = new Dimension( 800, 600 )
    preferredSize = new Dimension( 800, 600 )
    pack
  }
}
