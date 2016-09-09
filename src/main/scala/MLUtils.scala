import org.apache.spark.mllib.classification.{LogisticRegressionModel, LogisticRegressionWithLBFGS}
import org.apache.spark.mllib.evaluation.BinaryClassificationMetrics
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.rdd.RDD

object MLUtils {
  def getModel(data: RDD[LabeledPoint]) = {
    new LogisticRegressionWithLBFGS().run(data).clearThreshold()
  }

  def getMetrics(model: LogisticRegressionModel, data: RDD[LabeledPoint]) = {
    val predictionAndLabels = data.map(
      lPoint => {
        val prediction = model.predict(lPoint.features)
        //println(prediction + " - " + lPoint.label)
        (prediction, lPoint.label)
      }
    )
    new BinaryClassificationMetrics(predictionAndLabels)
  }
}