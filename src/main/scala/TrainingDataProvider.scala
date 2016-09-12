import org.apache.spark.mllib.feature.Normalizer
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.rdd.RDD

object TrainingDataProvider {

  def prepareInputData(params: RDD[String], decisions: RDD[String], trainingRate: Double) = {
    val clientsParams = prepareClientsParams(params)
    val clientsDecisions = prepareClientsDecisions(decisions)
    val inputData = createLabelPoints(clientsParams, clientsDecisions)
    val normalizedData = normalizeData(inputData)
    normalizedData.randomSplit(Array(trainingRate, 1 - trainingRate))
  }

  private def prepareClientsParams(objects: RDD[String]) = {
    objects
        .zipWithIndex()
        .map(_.swap)
        .mapValues(";NaN$".r.replaceFirstIn(_, ""))
        .mapValues(_.replaceAll(",", "."))
        .filter{case (id, data) => data.count(_ == ';') == 48}
        .filter{case (id, data) => !data.contains("NaN")}
        .mapValues(_.split(";").map(_.toDouble))
  }

  private def prepareClientsDecisions(target: RDD[String]) = {
    target
        .zipWithIndex()
        .map(_.swap)
  }

  private def createLabelPoints(points: RDD[(Long, Array[Double])], labels: RDD[(Long, String)]) = {
    points
        .join(labels)
        .map{case (id, data) => data}
        .map{
          case (points, label) =>
            LabeledPoint(
              label.toDouble,
              Vectors.dense(
                points
              )
            )
        }
  }

  private def normalizeData(trainingData: RDD[LabeledPoint]) = {
    trainingData.map(
      labeledPoint => LabeledPoint(labeledPoint.label, new Normalizer().transform(labeledPoint.features))
    )
  }
}