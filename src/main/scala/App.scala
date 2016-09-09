import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}

object App extends App {
  //Logger.getLogger("org").setLevel(Level.OFF)
  //Logger.getLogger("akka").setLevel(Level.OFF)
  val conf = new SparkConf().setMaster("local[*]").setAppName("ml")
  val sc = new SparkContext(conf)
  var input = TrainingDataProvider.prepareTrainingData(
    sc.textFile("data/Objects.csv"),
    sc.textFile("data/Target.csv")
  )
  val Array(training, test) =  input.randomSplit(Array(0.7, 0.3))
  val model = MLUtils.getModel(training)
  val metrics = MLUtils.getMetrics(model, test)
  println(metrics.areaUnderROC())
}