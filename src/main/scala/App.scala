import org.apache.log4j.{Level, Logger}
import org.apache.spark.mllib.classification.LogisticRegressionWithLBFGS
import org.apache.spark.mllib.evaluation.BinaryClassificationMetrics
import org.apache.spark.{SparkConf, SparkContext}

object App extends App {
  Logger.getLogger("org").setLevel(Level.OFF)
  Logger.getLogger("akka").setLevel(Level.OFF)
  val conf = new SparkConf().setMaster("local[*]").setAppName("ml")
  val sc = new SparkContext(conf)
  var input = TrainingDataProvider.prepareTrainingData(sc.textFile("data/Objects.csv"), sc.textFile("data/Target.csv"))
  input = TrainingDataProvider.normalizeTrainingData(input)
  //input.sample(true, 0.001).collect().foreach(point => println(point.label + " - " + point.features))

  val Array(training, test) =  input.randomSplit(Array(0.7, 0.3))
  val lr = new LogisticRegressionWithLBFGS()
  val model = lr.run(training).clearThreshold()
  val predictionAndLabels = test.map ( lPoint => {
    val prediction = model.predict(lPoint.features)
    //println(prediction + " - " + lPoint.label)
    (prediction, lPoint.label)}
  )
  val metrics = new BinaryClassificationMetrics(predictionAndLabels)
  println(metrics.areaUnderROC())
}