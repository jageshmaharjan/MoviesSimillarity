package com.thu.BagOfWordModel.SparkExamples;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.ml.feature.*;
import org.apache.spark.mllib.clustering.KMeans;
import org.apache.spark.mllib.clustering.KMeansModel;
import org.apache.spark.ml.linalg.SparseVector;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.List;

/**
 * Created by jugs on 3/6/17.
 */
public class KMeansClustering
{
    public static void main(String[] args)
    {
        KMeansClustering kMeans = new KMeansClustering();
        KMeansClustering.getData();

    }

    public static void getData()
    {
        RawDataPreparation createTFIDF = new RawDataPreparation();
        List<MovieObject> movieObjectList = createTFIDF.runProgram();
        processData(movieObjectList);
    }

    private static void processData(List<MovieObject> movieObjectList)
    {
        SparkSession spark = SparkSession
                .builder()
                .appName("Read ListObject Data of movie review")
                .master("local")
                .getOrCreate();

        JavaRDD<MovieObject> movieRDD = new JavaSparkContext(spark.sparkContext()).parallelize(movieObjectList);
        Dataset<Row> movieDf = spark.createDataFrame(movieRDD,MovieObject.class);

        movieDf.createOrReplaceTempView("movie");
        Dataset<Row> reviewDf = movieDf.select("review");

        Tokenizer tokenizer = new Tokenizer().setInputCol("review").setOutputCol("alltokens");
        Dataset<Row> tokenizedDF = tokenizer.transform(reviewDf);

        StopWordsRemover remover = new StopWordsRemover()
                .setInputCol("alltokens")
                .setOutputCol("tokens");
        Dataset<Row> filteredData = remover.transform(tokenizedDF);

        int numberOfFeatures = 500;
        HashingTF hashingTF = new HashingTF()
                .setNumFeatures(numberOfFeatures)
                .setInputCol("tokens")
                .setOutputCol("rawFeatures");

        Dataset<Row> featuredVector = hashingTF.transform(filteredData);
        IDF idf = new IDF().setInputCol("rawFeatures").setOutputCol("features");
        IDFModel idfModel = idf.fit(featuredVector);

//        ----In Dataset<Row> format---
//        Dataset<Row> rescaledDF = idfModel.transform(featuredVector);
//        rescaledDF.select("features").show();

        JavaRDD<Row> featuresRDD = idfModel.transform(featuredVector)
                .select("features").toJavaRDD().cache();

        computeKmeans(featuresRDD);
    }

    private static Vector parseData(Row row){
        return Vectors.dense(((SparseVector)row.get(0)).toArray());
    }

    private static void computeKmeans(JavaRDD<Row> featuresRDD)
    {
        JavaRDD<Vector> parsedData = featuresRDD.map(row -> parseData(row)).cache();

        System.out.println();
        int numberOfCluster = 10;
        int numberofIterations = 100;
        KMeansModel clusters = KMeans.train(parsedData.rdd(), numberOfCluster, numberofIterations);

        for (Vector center: clusters.clusterCenters())
        {
            System.out.println("Centroid: " + center);
        }



        System.out.println();

    }

}
