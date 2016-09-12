class CliParser extends scopt.OptionParser[DataLocation]("scala-1") {
  opt[String]('p', "params")
      .required()
      .valueName("<file>")
      .action( (value, conf) => conf.copy(paramsFile = value) )
      .text("path to file with training data parameters")

  opt[String]('r', "results")
      .required()
      .valueName("<file>")
      .action( (value, conf) => conf.copy(resultsFile = value) )
      .text("path to file with training data results")

  opt[String]('o', "output")
      .required()
      .valueName("<folder>")
      .action( (value, conf) => conf.copy(outputPath = value) )
      .text("path to output folder to write ROC points")
}