package com.thu.BagOfWordModel.TFIDF;

import com.esotericsoftware.reflectasm.shaded.org.objectweb.asm.Label;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.classification.SVMModel;
import org.apache.spark.mllib.classification.SVMWithSGD;
import org.apache.spark.mllib.evaluation.BinaryClassificationMetrics;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.util.MLUtils;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;

import java.io.Serializable;
import java.util.function.Function;

/**
 * Created by jugs on 3/16/17.
 */
public class ApplySVMClassifier implements Serializable
{
    public static void main(String[] args)
    {
        ApplySVMClassifier applySVMClassifier = new ApplySVMClassifier();
        applySVMClassifier.program();
        //arrangeString();
    }

    private void program()
    {
        SparkConf conf = new SparkConf().setAppName("JavaWithSVM").setMaster("local");
        SparkContext sc = new SparkContext(conf);


        String pathFormatted = "/home/jugs/IdeaProjects/MoviesSimillarity/formattedFile.txt/part-00000";
        JavaRDD<LabeledPoint> data = MLUtils.loadLibSVMFile(sc, pathFormatted,20).toJavaRDD();

        JavaRDD<LabeledPoint> training = data.sample(false, 0.6);
        training.cache();
        JavaRDD<LabeledPoint> test = data.subtract(training);

        int numInteration = 100;
        SVMModel model = SVMWithSGD.train(training.rdd(), numInteration);

        model.clearThreshold();

        JavaRDD<Tuple2<Object, Object>> scoreAndLabels = test.map(p ->
                new Tuple2<Object, Object>(model.predict(p.features()), p.label()));

        BinaryClassificationMetrics metrics =
                new BinaryClassificationMetrics(JavaRDD.toRDD(scoreAndLabels));
        double auROC = metrics.areaUnderROC();

        System.out.println("Area under ROC= " + auROC);

        //model.save(sc, "JugsModel");


    }



}
