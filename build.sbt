import Dependencies._
import com.github.retronym.SbtOneJar._

oneJarSettings

lazy val root = (project in file(".")).
		settings(
			name := "Conway's Game of Life",
			version := "1.1.2",
			organization := "org.juanitodread",
			scalaVersion := "2.11.11"
		).
		settings(
			libraryDependencies ++= backendDeps
		).settings(
			scalacOptions += "-feature"	
		).settings(
			fork := true, // Enabling fork JVM to send the Java parameter that uses another sort algorithm
			javaOptions ++= Seq("-Djava.util.Arrays.useLegacyMergeSort=true")
		)
