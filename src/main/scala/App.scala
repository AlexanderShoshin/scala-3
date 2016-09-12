object App extends App {
  val cliParser = new CliParser
  cliParser.parse(args, DataLocation()) match {
    case Some(dataLocation) => DecisionPredictionJob.execute(dataLocation)
    case None => println("Restart the application with correct arguments")
  }
}