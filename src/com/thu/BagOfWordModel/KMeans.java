package com.thu.BagOfWordModel;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.ml.feature.*;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.List;

/**
 * Created by jugs on 3/6/17.
 */
public class KMeans
{
    public static void main(String[] args)
    {
        KMeans kMeans = new KMeans();
        KMeans.getData();

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

        JavaRDD<Row> featuresRDD = idfModel.transform(featuredVector)
                .select("features").toJavaRDD();

    }
}
