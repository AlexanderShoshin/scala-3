package prediction

import prediction.data.DataLocation
import prediction.utils.{MLUtils, TrainingDataProvider}

object ClientsDecisionJob {
  def execute(dataLocation: DataLocation) = {
    val context = Config.getContext()

    // Split input data into two parts:
    // first (the biggest one) - for model training
    // and second - for grading recieved model (AUC ROC)
    val Array(trainingData, testData) =  TrainingDataProvider.prepareInputData(
      context.textFile(path = dataLocation.paramsFile),
      context.textFile(path = dataLocation.resultsFile),
      trainingRate = 0.7)

    val model = MLUtils.getModel(trainingData)
    val metrics = MLUtils.getMetrics(model, testData)

    // To build ROC curve we don't need all points -
    // small sample will be enough
    MLUtils.printMetrics(
      metrics,
      dataLocation.outputPath,
      sampleRate = 0.01)
  }
}