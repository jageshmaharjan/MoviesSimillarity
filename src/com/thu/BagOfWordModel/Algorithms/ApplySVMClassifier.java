package com.thu.BagOfWordModel.Algorithms;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.ml.classification.OneVsRest;
import org.apache.spark.ml.classification.LogisticRegression;
import org.apache.spark.ml.classification.OneVsRestModel;
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator;
import org.apache.spark.mllib.classification.SVMModel;
import org.apache.spark.mllib.classification.SVMWithSGD;
import org.apache.spark.mllib.evaluation.BinaryClassificationMetrics;
import org.apache.spark.mllib.optimization.SquaredL2Updater;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.util.MLUtils;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;

import java.io.Serializable;

/**
 * Created by jugs on 3/16/17.
 */
public class ApplySVMClassifier implements Serializable
{
    public static void main(String[] args)
    {
        ApplySVMClassifier applySVMClassifier = new ApplySVMClassifier();
        applySVMClassifier.programSVM();
        //applySVMClassifier.programLG();

    }

    private void programLG()
    {
        SparkSession spark = SparkSession.builder()
                .appName("logistic Regression")
                .master("local")
                .getOrCreate();

        String path = "/home/jugs/IdeaProjects/MoviesSimillarity/tfIDFDataForSVM.txt";
        Dataset<Row> inputData = spark.read().format("libsvm").load(path);

        Dataset<Row>[] tmp = inputData.randomSplit(new double[]{0.8,0.2});
        Dataset<Row> train = tmp[0];
        Dataset<Row> test = tmp[1];

        LogisticRegression classifier = new LogisticRegression()
                .setMaxIter(10)
                .setTol(1E-6)
                .setFitIntercept(true);

        OneVsRest ovr = new OneVsRest().setClassifier(classifier);

        OneVsRestModel oveModel = ovr.fit(train);

        Dataset<Row> prediction = oveModel.transform(test)
                .select("prediction", "label");

        MulticlassClassificationEvaluator evaluator = new MulticlassClassificationEvaluator()
                .setMetricName("accuracy");

        double accuracy = evaluator.evaluate(prediction);
        System.out.println("test error: " + (1-accuracy));


    }

    private void programSVM()
    {
        SparkConf conf = new SparkConf().setAppName("JavaWithSVM").setMaster("local");
        SparkContext sc = new SparkContext(conf);

        String pathFormatted = "/home/jugs/IdeaProjects/MoviesSimillarity/tfIDFDataForSVM.txt";
//        String pathFormatted = "/home/jugs/IdeaProjects/MoviesSimillarity/src/com/thu/BagOfWordModel/TFIDF/test_svm_data.txt";
        JavaRDD<LabeledPoint> data = MLUtils.loadLibSVMFile(sc, pathFormatted).toJavaRDD();

        JavaRDD<LabeledPoint> training = data.sample(false, 0.6);
        training.cache();
        JavaRDD<LabeledPoint> test = data.subtract(training);

        int numInteration = 100;
        SVMWithSGD svmWithSGD = new SVMWithSGD();
        svmWithSGD.optimizer().setRegParam(0.1)
                .setUpdater(new SquaredL2Updater());

        SVMModel model = svmWithSGD.train(training.rdd(),numInteration);

        model.clearThreshold();

        JavaRDD<Tuple2<Object, Object>> scoreAndLabels = test.map(p ->
                new Tuple2<Object, Object>(model.predict(p.features()), p.label()));

        BinaryClassificationMetrics metrics =
                new BinaryClassificationMetrics(JavaRDD.toRDD(scoreAndLabels));
        double auROC = metrics.areaUnderROC();

        System.out.println("Area under ROC=  " + auROC);

        //model.save(sc, "JModel");
    }
}
