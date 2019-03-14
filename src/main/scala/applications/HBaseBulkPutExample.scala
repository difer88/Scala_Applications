package applications

import org.apache.hadoop.hbase.spark.HBaseContext
import org.apache.spark.SparkContext
import org.apache.hadoop.hbase.{TableName, HBaseConfiguration}
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.client.Put
import org.apache.spark.SparkConf

/**
  * This is a simple example of putting records in HBase
  * with the bulkPut function.
  */
object HBaseBulkPutExample {

  def main(args: Array[String]) {

    val tableName = "ns:test_data"
    val columnFamily = "content"

    val sparkConf = new SparkConf().setAppName("HBaseBulkPutExample " +
      tableName + " " + columnFamily)
    sparkConf.setMaster("local")
    val sc = new SparkContext(sparkConf)

    try {
      //[(Array[Byte], Array[(Array[Byte], Array[Byte], Array[Byte])])]
      val rdd = sc.parallelize(Array(
        (Bytes.toBytes("1"),
          Array((Bytes.toBytes(columnFamily), Bytes.toBytes("1"), Bytes.toBytes("1")))),
        (Bytes.toBytes("2"),
          Array((Bytes.toBytes(columnFamily), Bytes.toBytes("1"), Bytes.toBytes("2")))),
        (Bytes.toBytes("3"),
          Array((Bytes.toBytes(columnFamily), Bytes.toBytes("1"), Bytes.toBytes("3")))),
        (Bytes.toBytes("4"),
          Array((Bytes.toBytes(columnFamily), Bytes.toBytes("1"), Bytes.toBytes("4")))),
        (Bytes.toBytes("5"),
          Array((Bytes.toBytes(columnFamily), Bytes.toBytes("1"), Bytes.toBytes("5"))))
      ))

      val conf = HBaseConfiguration.create()

      val hbaseContext = new HBaseContext(sc, conf)
      hbaseContext.bulkPut[(Array[Byte], Array[(Array[Byte], Array[Byte], Array[Byte])])](rdd,
        TableName.valueOf(tableName),
        (putRecord) => {
          val put = new Put(putRecord._1)
          putRecord._2.foreach((putValue) =>
            put.addColumn(putValue._1, putValue._2, putValue._3))
          put
        });
    } finally {
      sc.stop()
    }
  }
}