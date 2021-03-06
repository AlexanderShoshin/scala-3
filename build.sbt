name := "spark-ml"

version := "1.0"

scalaVersion := "2.10.5"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "1.6.0" % Provided,
  "org.apache.spark" %% "spark-mllib" % "1.6.0" % Provided,
  "com.github.scopt" %% "scopt" % "3.5.0"
)