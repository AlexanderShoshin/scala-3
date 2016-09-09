import org.apache.log4j.{Level, Logger}
import org.apache.spark.mllib.classification.LogisticRegressionWithLBFGS
import org.apache.spark.mllib.evaluation.BinaryClassificationMetrics
import org.apache.spark.mllib.regression.{LabeledPoint, LinearRegressionWithSGD}
import org.apache.spark.{SparkConf, SparkContext}

object App extends App {
  Logger.getLogger("org").setLevel(Level.OFF)
  Logger.getLogger("akka").setLevel(Level.OFF)
  val conf = new SparkConf().setMaster("local[*]").setAppName("ml")
  val sc = new SparkContext(conf)
  var input = TrainingDataProvider.prepareTrainingData(sc.textFile("data/Objects.csv"), sc.textFile("data/Target.csv"))
  input = TrainingDataProvider.scaleTrainingData(input)
  //input.sample(true, 0.001).collect().foreach(point => println(point.label + " - " + point.features))

  val Array(training, test) =  input.randomSplit(Array(0.7, 0.3))
  val lr = new LinearRegressionWithSGD()
  val model = lr.run(input)
  val predictionAndLabels = input.map { case LabeledPoint(label, features) =>
    val prediction = model.predict(features)
    (prediction, label)
  }
  val metrics = new BinaryClassificationMetrics(predictionAndLabels)
  println(metrics.areaUnderROC())
}