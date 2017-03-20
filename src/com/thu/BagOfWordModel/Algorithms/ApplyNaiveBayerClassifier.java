package com.thu.BagOfWordModel.Algorithms;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.mllib.classification.NaiveBayes;
import org.apache.spark.mllib.classification.NaiveBayesModel;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.util.MLUtils;
import scala.Tuple2;

import java.io.Serializable;

/**
 * Created by jugs on 3/19/17.
 */
public class ApplyNaiveBayerClassifier implements Serializable
{
    public static void main(String[] args)
    {
        ApplyNaiveBayerClassifier nbClassifier = new ApplyNaiveBayerClassifier();
        nbClassifier.program();
    }

    private void program()
    {
        SparkConf conf = new SparkConf().setAppName("Naive bayers Classifier").setMaster("local[*]");
        SparkContext sc = new SparkContext(conf);

        String path = "/home/jugs/IdeaProjects/MoviesSimillarity/PrafulLabelling/tfIDFDataForSVM.txt";

        JavaRDD<LabeledPoint> inputData = MLUtils.loadLibSVMFile(sc, path).toJavaRDD();
        JavaRDD<LabeledPoint>[] tmp = inputData.randomSplit(new double[]{0.6, 0.4});
        JavaRDD<LabeledPoint> training = tmp[0];
        JavaRDD<LabeledPoint> test = tmp[1];

        final NaiveBayesModel model = NaiveBayes.train(training.rdd(), 1.0);

        JavaPairRDD<Double, Double> predictionAndLabel = test.mapToPair(new PairFunction<LabeledPoint, Double, Double>()
        {
            @Override
            public Tuple2<Double, Double> call(LabeledPoint point) throws Exception
            {
                return new Tuple2<>(model.predict(point.features()), point.label());
            }
        });

        double accuracy = predictionAndLabel.filter(new Function<Tuple2<Double, Double>, Boolean>()
        {
            @Override
            public Boolean call(Tuple2<Double, Double> ddT2) throws Exception
            {
                return ddT2._1().equals(ddT2._2());
            }
        }).count()/(double)test.count();

        System.out.println("Accuracy: " + accuracy);

        //---saving the classifier model---
//        String pathToSaveModel = "";
//        model.save(sc, pathToSaveModel);
        //---loading the classifier model---
//        NaiveBayesModel myModel = NaiveBayesModel.load(sc,pathToSaveModel);
    }
}
