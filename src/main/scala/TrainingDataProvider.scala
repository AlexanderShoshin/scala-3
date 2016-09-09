import org.apache.spark.mllib.feature.Normalizer
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.rdd.RDD

object TrainingDataProvider {
  def prepareTrainingData(objects: RDD[String], target: RDD[String]) = {
    val clientsParams = prepareClientsParams(objects)
    val clientsIssues = prepareClientsIssues(target)

    val trainingData = clientsParams
        .join(clientsIssues)
        .map{case (id, data) => data}
        .map{
          case (clientInfo, clientIssue) =>
            LabeledPoint(
              clientIssue.toDouble,
              Vectors.dense(
                clientInfo
              )
            )
        }
    normalizeTrainingData(trainingData)
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
        //.mapValues(Client(_))
  }
  private def prepareClientsIssues(target: RDD[String]) = {
    target
        .zipWithIndex()
        .map(_.swap)
  }
  private def normalizeTrainingData(trainingData: RDD[LabeledPoint]) = {
    trainingData.map(
      labeledPoint => LabeledPoint(labeledPoint.label, new Normalizer().transform(labeledPoint.features))
    )
  }
}