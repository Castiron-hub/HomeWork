import sbt.Keys._

lazy val commonSettings = Seq(
  name := "HomeWork",
  version := "0.1",
  scalaVersion := "2.12.10",
  libraryDependencies ++= Seq(
    "org.apache.spark" %%  "spark-core" % "2.4.6" % Provided,
    "org.apache.spark" %%  "spark-sql" % "2.4.6" % Provided
  )
)

lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  enablePlugins(AssemblyPlugin)


assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

assemblyJarName in assembly := "home_2.12-1.0.jar"
