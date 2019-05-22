package Example

import org.apache.log4j.Level
import java.util.regex.Pattern
import java.util.regex.Matcher
import org.mongodb.scala._
import scala.collection.mutable.ListBuffer
import Example.Helpers._

object Utils {
  /** Makes sure only ERROR messages get logged to avoid log spam. */
  def setupLogging() = {
    import org.apache.log4j.{Level, Logger}
    val rootLogger = Logger.getRootLogger()
    rootLogger.setLevel(Level.ERROR)
  }

  /** Configures Twitter service credentials using twiter.txt in the main workspace directory */
  def setupTwitter() = {
    import scala.io.Source

    for (line <- Source.fromFile("src/main/resources/twitter.txt").getLines) {
      val fields = line.split(" ")
      if (fields.length == 2) {
        System.setProperty("twitter4j.oauth." + fields(0), fields(1))
      }
    }
  }

  /** Retrieves a regex Pattern for parsing Apache access logs. */
  def apacheLogPattern():Pattern = {
    val ddd = "\\d{1,3}"
    val ip = s"($ddd\\.$ddd\\.$ddd\\.$ddd)?"
    val client = "(\\S+)"
    val user = "(\\S+)"
    val dateTime = "(\\[.+?\\])"
    val request = "\"(.*?)\""
    val status = "(\\d{3})"
    val bytes = "(\\S+)"
    val referer = "\"(.*?)\""
    val agent = "\"(.*?)\""
    val regex = s"$ip $client $user $dateTime $request $status $bytes $referer $agent"
    Pattern.compile(regex)
  }

  def getWords(): List[String] =  {
    val mongoClient: MongoClient = MongoClient()
    val database: MongoDatabase = mongoClient.getDatabase("distribuidos")

    val collection: MongoCollection[Document] = database.getCollection("words")
    val words = new ListBuffer[String]()

    collection.find().results().map(
      document => words += document.getString("value")
    )

    return words.toList
  }

  def containsAnyWord(text: String, words: List[String]): Boolean = {
    for(word <- words){
      if (text.contains(word)) {
        return true
      }
    }
    return false
  }
}
