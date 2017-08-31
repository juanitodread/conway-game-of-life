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

import java.io.FileOutputStream
import java.io.ObjectOutputStream
import java.io.ObjectInputStream
import java.io.FileInputStream

/**
 * Utility methods
 *
 * @author juanitodread
 * @version 1.2.0
 * @since   1.2.0
 *
 * 	        Oct 20, 2015
 */
object Util {

  /**
   * Simple binary serialization of objects
   *
   * @param obj  The object to be stored.
   * @param path The path of the file where the object will be stored.
   */
  def binarySerialization(obj: Object, path: String) = {
    val oos = new ObjectOutputStream(new FileOutputStream(path))
    oos.writeObject(obj)
    oos.close
  }

  /**
   * Simple binary deserialization of objects
   *
   * @param path The path of the file where the object is stored.
   * @return A new object instance.
   */
  def binaryDeserialization(path: String) = {
    val ois = new ObjectInputStream(new FileInputStream(path))
    ois.readObject
  }

}