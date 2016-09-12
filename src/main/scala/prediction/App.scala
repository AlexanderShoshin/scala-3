package prediction

import prediction.data.DataLocation
import prediction.utils.CliParser

object App extends App {
  val cliParser = new CliParser
  cliParser.parse(args, DataLocation()) match {
    case Some(dataLocation) => ClientsDecisionJob.execute(dataLocation)
    case None => println("Restart the application with correct arguments")
  }
}