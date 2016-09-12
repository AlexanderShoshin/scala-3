## Description

scala-3 is a spark machine learning application. It builds a prediction model that can evaluate is customer will be interested in new bank service.
As input it takes two training datasets with customers characteristics and customers decisions about a new service.
As output it saves a ROC curve points to the text file and prints an AUC ROC to the console.

## Testing environment

Program was tested on HDP 2.4 sandbox.

## How to deploy

1. Make spark-ml-assembly-1.0.jar by running sbt command from project root:
```
sbt clean assembly
```
2. Copy spark-ml-assembly-1.0.jar from target/scala-2.10/ to machine with Spark installed.
3. Put data/Target.csv and data/Objects.csv files to hdfs if you going to run a job on a cluster.
4. Run spark job, defining a cluster manager, paths to the dataset files and the folder path where output will be produced:
```
spark-submit \
--class prediction.App \
--master <cluster_manager> \
<path_to_jar>/spark-ml-assembly-1.0.jar \
--params <path_to_clients_characteristics_dataset>/Objects.csv \
--results <path_to_clients_decisions_dataset>/Target.csv \
--output <path_to_output_folder>
```
Local mode task submitting may look like this:
```
spark-submit \
--class prediction.App \
--master local[*] \
spark-ml-assembly-1.0.jar \
--params /training/dataset/ml/Objects.csv \
--results /training/dataset/ml/Target.csv \
--output /training/spark/roc
```

**It is recommended to define --executor-memory and --driver-memory flags when running on YARN. Otherwise your job may wait forever for resources allocation**

## Results

You can find a ROC curve of used algorithm in results/ folder

## Process

To build a prediction model regression algorithms class was used as we need to recieve a continuous variable (probability that a customer will respond an offer about a new bank service).
During an experiments several regression algorithms were tried: linear regression, logistic regression and naive bayes. Logistic regression was selected as it has the biggest AUC ROC (0.67) on the target dataset.
Input data normalization also was used to improve the result.