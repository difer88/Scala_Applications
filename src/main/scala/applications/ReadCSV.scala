package applications

import org.apache.spark.sql.SparkSession.builder

class ReadCSV {

  val spark = builder
    .master("local")
    .appName("Spark CSV Reader")
    .getOrCreate;

  val df = spark.read
    .format("csv")
    .option("header", "true") //first line in file has headers
    .option("mode", "DROPMALFORMED")
    .load("hdfs:///csv/file/dir/file.csv")

}

object ReadCSV {

  def main(args: Array[String]): Unit = {

    val csvReader = new ReadCSV

    csvReader.df.show()

  }

}
