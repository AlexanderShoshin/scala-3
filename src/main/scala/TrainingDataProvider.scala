import data.Client
import org.apache.spark.mllib.feature.StandardScaler
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.rdd.RDD

object TrainingDataProvider {
  def prepareTrainingData(objects: RDD[String], target: RDD[String]) = {
    val clientsParams = prepareClientsParams(objects)
    val clientsIssues = prepareClientsIssues(target)

    clientsParams
        .join(clientsIssues)
        .map{case (id, data) => data}
        .map{
          case (clientInfo, clientIssue) =>
            LabeledPoint(
              clientIssue.toDouble,
              Vectors.dense(
                clientInfo(0).toDouble,
                //clientInfo(1).toDouble,
                //clientInfo(2).toDouble,
                //clientInfo(3).toDouble,
                //clientInfo(4).toDouble,
                //clientInfo(5).toDouble,
                //clientInfo(6).toDouble,
                //clientInfo(7).toDouble,
                clientInfo(8).toDouble
                //clientInfo(9).toDouble
              )
            )
        }
  }
  def prepareClientsParams(objects: RDD[String]) = {
    objects
        .zipWithIndex()
        .map(_.swap)
        .mapValues(";NaN$".r.replaceFirstIn(_, ""))
        .mapValues(_.replaceAll(",", "."))
        .filter{case (id, data) => data.count(_ == ';') == 48}
        .filter{case (id, data) => !data.contains("NaN")}
        .mapValues(_.split(";"))
        //.mapValues(Client(_))
  }
  def prepareClientsIssues(target: RDD[String]) = {
    target
        .zipWithIndex()
        .map(_.swap)
  }
  def scaleTrainingData(trainingData: RDD[LabeledPoint]) = {
    val scaler = new StandardScaler().fit(trainingData.map(_.features))
    trainingData.map(
      labeledPoint => LabeledPoint(labeledPoint.label, scaler.transform(labeledPoint.features))
    )
  }
}