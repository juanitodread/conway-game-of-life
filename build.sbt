name := "Conway's Game of Life"
version := conwaysVersion
organization := "org.juanitodread"

scalaVersion := "2.13.6"

lazy val root = project in file(".")

lazy val conwaysVersion = "1.2.0"
lazy val scalaSwingVersion = "3.0.0"
lazy val scalaLoggingVersion = "3.9.4"
lazy val logbackVersion = "1.2.6"
lazy val scalaTestVersion = "3.2.9"

libraryDependencies ++= Seq(
	"org.scala-lang.modules" %% "scala-swing" % scalaSwingVersion,
	"com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
	"ch.qos.logback" % "logback-classic" % logbackVersion,
	"org.scalatest" %% "scalatest" % scalaTestVersion % "test",
)

scalacOptions ++= List("-feature", "-deprecation")

fork := true // Enabling fork JVM to send the Java parameter that uses another sort algorithm
javaOptions ++= Seq("-Djava.util.Arrays.useLegacyMergeSort=true")

mainClass := Some("org.juanitodread.conwaygameoflife.MainApp")
assemblyJarName := s"conways-game-of-life_${conwaysVersion}.jar"
