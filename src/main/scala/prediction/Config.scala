package prediction

import org.apache.spark.{SparkConf, SparkContext}

object Config {
  def getContext() = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("ml")
    new SparkContext(conf)
  }
}