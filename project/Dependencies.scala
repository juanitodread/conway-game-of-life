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

import sbt._

object Dependencies {
	// versions
	lazy val scalaSwingVersion = "1.0.1"
  lazy val scalaLoggingVersion = "3.1.0"
  lazy val logbackVersion = "1.1.3"
	
	// libraries
	val scalaSwing = "org.scala-lang.modules" %% "scala-swing" % scalaSwingVersion
  val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion
  val logback    = "ch.qos.logback" % "logback-classic" % logbackVersion

	// projects 
	val backendDeps = Seq(scalaSwing, 
                        scalaLogging,
                        logback)
}
