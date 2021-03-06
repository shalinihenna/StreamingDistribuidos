name := "TwitterStreaming"

version := "0.1"

scalaVersion := "2.11.8"

libraryDependencies += "org.apache.spark" %% "spark-core" % "1.5.2"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "1.5.2"
libraryDependencies += "org.apache.spark" %% "spark-streaming" % "1.5.2"
libraryDependencies += "org.apache.spark" % "spark-streaming-twitter_2.11" % "1.5.2"

libraryDependencies += "org.twitter4j" % "twitter4j-core" % "3.0.6"
libraryDependencies += "org.twitter4j" % "twitter4j-stream" % "3.0.6"

libraryDependencies += "org.mongodb.scala" %% "mongo-scala-driver" % "2.6.0"
libraryDependencies += "org.mongodb.spark" %% "mongo-spark-connector" % "2.4.0"
libraryDependencies += "org.mongodb" %% "casbah" % "3.0.0"
