package com.thu.BagOfWordModel.SVMLib_Example;

import org.apache.spark.ml.classification.LogisticRegression;
import org.apache.spark.ml.classification.OneVsRest;
import org.apache.spark.ml.classification.OneVsRestModel;
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

/**
 * Created by jugs on 3/19/17.
 */
public class ApplyOnveVsRestClassifier
{
    public static void main(String[] args)
    {
        ApplyOnveVsRestClassifier onveVsRestClassifier = new ApplyOnveVsRestClassifier();
        onveVsRestClassifier.program();
    }

    private void program()
    {
        SparkSession spark = SparkSession.builder()
                .master("local[*]")
                .appName("One Vs Rest Classifier")
                .getOrCreate();

        String path = "/home/jugs/IdeaProjects/MoviesSimillarity/PrafulLabelling/tfIDFDataForSVM.txt";

        Dataset<Row> inputData = spark.read().format("libsvm").load(path);

        Dataset<Row>[] tmp = inputData.randomSplit(new double[]{0.6, 0.4});
        Dataset<Row> train = tmp[0];
        Dataset<Row> test = tmp[1];

        LogisticRegression classifier = new LogisticRegression()
                .setMaxIter(10)
                .setTol(1E-6)
                .setFitIntercept(true);

        OneVsRest ovr = new OneVsRest().setClassifier(classifier);

        OneVsRestModel oneVsRestModel = ovr.fit(train);

        Dataset<Row> prediction = oneVsRestModel.transform(test).select("prediction", "label");

        MulticlassClassificationEvaluator evaluator = new MulticlassClassificationEvaluator().setMetricName("accuracy");

        double accuracy = evaluator.evaluate(prediction);

        System.out.println("Test Error: " + (1-accuracy));

    }
}
