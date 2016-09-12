package prediction.utils

import org.apache.spark.mllib.classification.LogisticRegressionWithLBFGS
import org.apache.spark.mllib.evaluation.BinaryClassificationMetrics
import org.apache.spark.mllib.regression.{GeneralizedLinearModel, LabeledPoint}
import org.apache.spark.rdd.RDD

object MLUtils {
  def getModel(data: RDD[LabeledPoint]) = {
    new LogisticRegressionWithLBFGS().run(data).clearThreshold()
  }

  def getMetrics(model: GeneralizedLinearModel, data: RDD[LabeledPoint]) = {
    val predictionAndLabels = data.map(
      lPoint => {
        val prediction = model.predict(lPoint.features)
        (prediction, lPoint.label)
      }
    )
    new BinaryClassificationMetrics(predictionAndLabels)
  }

  def printMetrics(metrics: BinaryClassificationMetrics, outputPath: String, sampleRate: Double) = {
    metrics.roc().sample(withReplacement = true, fraction = sampleRate).saveAsTextFile(outputPath)
    println("AUC ROC = " + metrics.areaUnderROC())
  }
}