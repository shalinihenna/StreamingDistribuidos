package Example

import com.mongodb.util.JSON
import org.apache.spark.SparkContext._

import org.mongodb.scala._
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
        val doc = Document(
          "createdAt" -> x.getCreatedAt,
          "text" -> x.getText,
          "userName" -> x.getUser.getName,
          "userScreenName" -> x.getUser.getScreenName,
          "currentUserRetweetedId" -> x.getCurrentUserRetweetId,
          "favoriteCount" -> x.getFavoriteCount,
          "id" -> x.getId,
          "inReplyToScreenName" -> x.getInReplyToScreenName,
          "inReplyToStatusId" -> x.getInReplyToStatusId,
          "lang" -> x.getLang,
          "retweetCount" -> x.getRetweetCount,
          "isFavorited" -> x.isFavorited,
          "isPossiblySensitive" -> x.isPossiblySensitive,
          "isRetweet" -> x.isRetweet,
          "isRetweeted" -> x.isRetweeted,
          "isRetweetedByMe" -> x.isRetweetedByMe,
          "isTruncated" -> x.isTruncated,
          "source" -> x.getSource
          //"contributors" -> x.getContributors,
          //"geoLocation" -> x.getGeoLocation,
          //"place" -> x.getPlace,
          //"retweetedStatus" -> x.getRetweetedStatus,
          //"scopes" -> x.getScopes,

          )
        collection.insertOne(doc).subscribe(new Observer[Completed] {
            override def onNext(result: Completed): Unit = {
                println("[+] successfully inserted")
            }
            override def onError(e: Throwable) = {
                println("[!] error: " + e)
            }
            override def onComplete(): Unit = {
                println("[?] completed: ")
            }
        })
      }
    }


    //Streaming starts
    ssc.start()

    //Streaming waits for an action to end
    ssc.awaitTermination()
  }
}
