package example

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.client.Put
import org.apache.hadoop.hbase.spark.HBaseContext
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.{HBaseConfiguration, TableName}
import org.apache.hadoop.security.UserGroupInformation
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.KafkaUtils
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.{Duration, StreamingContext}

import scala.language.postfixOps


object TransactionStreaming {

  // Spark Config
  val appName = "PFM-Transaction-Streaming"
  val master = "local"
  val spark = SparkSession
    .builder
    .appName(appName)
    .master(master)
    .getOrCreate()

  spark.sparkContext.setLogLevel("ERROR")

  // Kafka Config
  val kafkaTopics = Array("test")
  val kafkaParams = Map[String, Object](
    "bootstrap.servers" -> "localhost:9092",
    "key.deserializer" -> classOf[StringDeserializer],
    "value.deserializer" -> classOf[StringDeserializer],
    "group.id" -> "test",
    "auto.offset.reset" -> "latest",
    "enable.auto.commit" -> (false: java.lang.Boolean)
  )

  // HBase Config
  val zookeeperQuorum = "localhost"
  val tableName = "ns:test_data"

  val hbaseConf: Configuration = HBaseConfiguration.create()

  hbaseConf.set("hbase.master", "local")
  hbaseConf.set("hbase.zookeeper.quorum", zookeeperQuorum)

  UserGroupInformation.setConfiguration(hbaseConf)

  val hbaseContext = new HBaseContext(spark.sparkContext, hbaseConf)

  def main(args: Array[String]) {

    val ssc = new StreamingContext(spark.sparkContext, new Duration(1000))

    val stream = KafkaUtils.createDirectStream[String, String](
      ssc,
      PreferConsistent,
      Subscribe[String, String](kafkaTopics, kafkaParams)
    )

    val transactions = stream.map(record => record.value)


    hbaseContext.streamBulkPut[String](transactions,
      TableName.valueOf(tableName),
      (putRecord) => {
        if (putRecord.length() > 0) {
          val values = putRecord.split(",")

          print(values)

          new Put(Bytes.toBytes(values(0)))
            .addColumn(Bytes.toBytes("Office"), Bytes.toBytes("teste1"), Bytes.toBytes(values(1)))
            .addColumn(Bytes.toBytes("Office"), Bytes.toBytes("teste2"), Bytes.toBytes(values(2)))
            .addColumn(Bytes.toBytes("Personal"), Bytes.toBytes("teste3"), Bytes.toBytes(values(3)))
        } else {
          null
        }
      })


    ssc.start
    try
      ssc.awaitTermination
    catch {
      case e: InterruptedException => e.printStackTrace()
    }


  }

}

