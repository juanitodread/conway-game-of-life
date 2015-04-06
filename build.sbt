import Dependencies._
import com.github.retronym.SbtOneJar._

oneJarSettings

lazy val root = (project in file(".")).
		settings(
			name := "Conway's Game of Life",
			version := "1.0.1",
			organization := "org.juanitodread",
			scalaVersion := "2.11.6",
			scalaHome := Some(file("/opt/scala-sdk/scala-2.11.6/"))
		).
		settings(
			libraryDependencies ++= backendDeps
		).settings(
			scalacOptions += "-feature"	
		)
