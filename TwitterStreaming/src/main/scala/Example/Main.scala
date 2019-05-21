package Example

import com.mongodb.util.JSON
import org.apache.spark.SparkContext._

import org.mongodb.scala.Document
import org.mongodb.scala.{MongoClient, MongoCollection, MongoDatabase}
//import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.StreamingContext._
import org.apache.spark.streaming._
import org.apache.spark.streaming.twitter._
import org.apache.log4j.Level
import Utils._


object Main{

  def main(args: Array[String]) {

    // Configure Twitter credentials using twitter.txt
    setupTwitter()
    val ssc = new StreamingContext("local[*]", "Main", Seconds(1))
    setupLogging()

    // Create a DStream from Twitter using our streaming context
    val tweets = TwitterUtils.createStream(ssc, None)

    //Filters all tweets in spanish
    val filtered = tweets.filter(_.getLang=="es")

    // Now extract the text of each status update into RDD's using map()
    val statuses = filtered.map(status => status.getText())
    statuses.print()

    //Connection and storage of tweets in mongoDB
    filtered.foreachRDD{ x=>
      x.foreach{ x =>
        val mongoClient: MongoClient = MongoClient("mongodb://localhost:27017")
        val database: MongoDatabase = mongoClient.getDatabase("streamingTwitter")
        val collection: MongoCollection[Document] = database.getCollection("tweets")
        val doc = Document("createdAt" -> x.getCreatedAt, "text" -> x.getText)
        println("PROBANDO DOC")
        println(doc)
        collection.insertOne(doc)
        println("Se insertó")
        println("")
      }
    }


    //Streaming starts
    ssc.start()

    //Streaming waits for an action to end
    ssc.awaitTermination()
  }
}
