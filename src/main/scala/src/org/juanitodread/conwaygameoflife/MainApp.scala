package org.juanitodread.conwaygameoflife

import javax.swing.SwingUtilities
import org.juanitodread.conwaygameoflife.view.ApplicationView

object MainApp {

  def main( args: Array[ String ] ): Unit = {
    SwingUtilities.invokeLater( new Runnable {
      def run {
        val app = new ApplicationView
        app.startup( args )
      }
    } )
  }
}
