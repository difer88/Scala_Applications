package applications

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

class CharSearchCSV {

  val logFile = "hdfs://localhost:9000/diego/data.csv" // Should be some file on your system

  val conf = new SparkConf().setAppName("Simple Application").setMaster("local")

  val sc = new SparkContext(conf)

  // Setting the log level
  sc.setLogLevel("WARN")

  val logData = sc.textFile(logFile, 2).cache()

}

object CharSearchCSV {

  def main(args: Array[String]) {

    val charSearchCSV = new CharSearchCSV

    val numAs = charSearchCSV.logData.filter(line => line.contains("a")).count()
    val numBs = charSearchCSV.logData.filter(line => line.contains("b")).count()

    println("Lines with a: %s, Lines with b: %s".format(numAs, numBs))

  }

}
