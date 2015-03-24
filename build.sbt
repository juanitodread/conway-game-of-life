import Dependencies._

lazy val root = (project in file(".")).
		settings(
			name := "Conway's Game of Life",
			version := "1.0",
			organization := "org.juanitodread",
			scalaVersion := "2.11.6",
			scalaHome := Some(file("/opt/scala-sdk/scala-2.11.6/"))
		).
		settings(
			libraryDependencies ++= backendDeps
		)
