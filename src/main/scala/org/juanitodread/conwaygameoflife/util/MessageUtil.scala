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
package org.juanitodread.conwaygameoflife.util

import java.util.Locale
import java.util.ResourceBundle
import org.juanitodread.conwaygameoflife.ApplicationConstants
import com.typesafe.scalalogging.LazyLogging

/**
 * Utility class to get translatable messages from properties file. This class
 * use the Locale of the OS. The default language is English.
 *
 * @author juanitodread
 * @version 1.2.0
 * @since 1.2.0
 *
 * Oct 20, 2015
 */
object MessageUtil extends ApplicationConstants with LazyLogging {
  lazy val locale = Locale.getDefault
  lazy val messages = ResourceBundle.getBundle(MessageBundle, locale)
  
  
  def getMessage(key: String): String = Option(key) match {
    case Some(x) => messages.getString(x)
    case None    => MessageNotFound
  }
  
}