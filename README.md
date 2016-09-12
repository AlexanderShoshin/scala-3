## Description

scala-3 is a spark machine learning application. It builds a prediction model that can evaluate is customer will be interested in new bank service.
As input it takes two training datasets with customers characteristics and customers decisions about a new service.
As output it save to the text file pints of a ROC curve and print to the console the AUC ROC.

## Testing environment

Program was tested on HDP 2.4 sandbox.

## How to deploy

1. Make spark-ml-assembly-1.0.jar by running sbt command from project root:
```
sbt clean assembly
```
2. Copy spark-ml-assembly-1.0.jar from target/scala-2.10/ to machine with Spark installed.
3. Put data/Target.csv and data/Objects.csv files to hdfs if you going to run a job on a cluster.
4. Run spark job, defining cluster manager, paths to the dataset files and path to the folder where output will be produced:
```
spark-submit \
--class prediction.App \
--master <cluster_manager> \
<path_to_jar>/spark-ml-assembly-1.0.jar \
--params <path_to_clients_characteristics_dataset>/Objects.csv \
--results <path_to_clients_decisions_dataset>/Target.csv \
--output <path_to_output_folder>
```
4.1 Local mode task submitting may look like this:
```
spark-submit \
--class prediction.App \
--master local[*] \
spark-ml-assembly-1.0.jar \
--params /training/dataset/ml/Objects.csv \
--results /training/dataset/ml/Target.csv \
--output /training/spark/roc
```
4.2 Submitting to YARN example:
```
spark-submit \
--class prediction.App \
--master yarn \
--executor-memory 512m \
--driver-memory 512m \
spark-ml-assembly-1.0.jar \
--params /training/dataset/ml/Objects.csv \
--results /training/dataset/ml/Target.csv \
--output /training/spark/roc
```
**It is recommended to define --executor-memory and --driver-memory flags when running on YARN. Otherwise your job may wait forever for resources allocation**

## Results

You can find of used algorithm ROC curve in results/ folder

## Process

To build prediction model regression was used as we need to recieve continuous variable (probability that a customer will respond an offer about a new bank service).
During an experiments several regression algorithms were tried: linear regression, logistic regression and naive bayes. Logistic regression was selected as it has the biggest AUC ROC (0.67) on the target dataset.
Input data normalization also helped to improve the result.