package applications

import org.apache.spark.sql.SparkSession.builder

class ReadCSV {

  //Configuring Spark
  val spark = builder
    .master("local")
    .appName("Spark CSV Reader")
    .getOrCreate;

  // Setting the Spark log level
  val sc = spark.sparkContext
  sc.setLogLevel("WARN")

  // Creating DataFrame
  val df = spark.read
    .format("csv")
    .option("header", "true") //first line in file has headers
    .option("mode", "DROPMALFORMED")
    .load("hdfs://localhost:9000/diego/data.csv")

}

object ReadCSV {

  def main(args: Array[String]): Unit = {

    val csvReader = new ReadCSV

    csvReader.df.show()

  }

}
