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
package org.juanitodread.conwaygameoflife

import javax.swing.SwingUtilities
import com.typesafe.scalalogging._

import org.juanitodread.conwaygameoflife.view.ApplicationView

/**
 * Main application. Instantiate the frame in a new Thread
 *
 * @author juanitodread
 * @version 1.1.2
 * @since 1.0.0
 *
 * Mar 23, 2015
 */
object MainApp extends LazyLogging {

  def main(args: Array[String]): Unit = {
    SwingUtilities.invokeLater(new Runnable {
      def run {
        logger.info("Starting application...")
        ApplicationView().startup(args)
        logger.info("Application started :D")
      }
    })
  }
}
