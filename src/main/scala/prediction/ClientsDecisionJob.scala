package prediction

import prediction.data.DataLocation
import prediction.utils.{MLUtils, TrainingDataProvider}

object ClientsDecisionJob {
  def execute(dataLocation: DataLocation) = {
    val context = Config.getContext()

    val Array(trainingData, testData) =  TrainingDataProvider.prepareInputData(
      context.textFile(path = dataLocation.paramsFile),
      context.textFile(path = dataLocation.resultsFile),
      trainingRate = 0.7)

    val model = MLUtils.getModel(trainingData)
    val metrics = MLUtils.getMetrics(model, testData)

    MLUtils.printMetrics(
      metrics,
      dataLocation.outputPath,
      sampleRate = 0.01)
  }
}