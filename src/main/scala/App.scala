object App extends App {
  val context = Config.getContext()
  val Array(trainingData, testData) =  TrainingDataProvider.prepareInputData(
    params = context.textFile("data/Objects.csv"),
    decisions = context.textFile("data/Target.csv"),
    trainingRate = 0.7
  )
  val model = MLUtils.getModel(trainingData)
  val metrics = MLUtils.getMetrics(model, testData)

  MLUtils.printMetrics(metrics = metrics, sampleRate = 0.01)
}