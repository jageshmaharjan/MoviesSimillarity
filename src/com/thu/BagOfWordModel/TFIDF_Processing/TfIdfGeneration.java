package com.thu.BagOfWordModel.TFIDF_Processing;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.ml.feature.*;
import org.apache.spark.ml.linalg.SparseVector;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

/**
 * Created by jugs on 3/16/17.
 * This class generates TFIDF from the text file where each line is review of one movie.
 * Reads one line as a document and generates a tfidf. This is a direct Strategy for generating TFIDF, (i.e without filtering any POS).
 * This may contains huge noise, and error rate might be very high too.
 */
public class TfIdfGeneration
{
    public static void main(String[] args)
    {
        TfIdfGeneration tfIdfGeneration = new TfIdfGeneration();
        tfIdfGeneration.program();
    }

    private void program()
    {
        String filePath = "/home/jugs/IdeaProjects/MoviesSimillarity/ReviewInOneFile.txt";

        SparkSession spark = SparkSession
                .builder()
                .appName("TFIDF Generation")
                .master("local")
                .getOrCreate();

        JavaRDD<MovieObject> movieRDD = spark.read().textFile(filePath)
                .javaRDD().map((Function<String, MovieObject>) s ->
                {
                    String[] parts = s.split("::\t");
                    MovieObject movieObject = new MovieObject();
                    movieObject.setLabel(parts[0]);
                    movieObject.setReviews(parts[1]);
                    return movieObject;
                });

        Dataset<Row> movieDF = spark.createDataFrame(movieRDD, MovieObject.class);

        movieDF.createOrReplaceTempView("movie");

        //------------Save All the labels according to the review ---------
        movieDF.select("label").toJavaRDD().saveAsTextFile("labels.txt");
        Dataset<Row> reviewsDF = movieDF.select("reviews"); //spark.sql("select review from movie");

        Tokenizer tokenizer = new Tokenizer().setInputCol("reviews").setOutputCol("words");
        Dataset<Row> wordData = tokenizer.transform(reviewsDF);

        //---------StopWord Removal-------------------
        StopWordsRemover remover = new StopWordsRemover()
                .setInputCol("words")
                .setOutputCol("filtered");
        Dataset<Row> filteredData = remover.transform(wordData);

        NGram nGramTransform = new NGram().setInputCol("filtered").setN(1).setOutputCol("ngrams");
        Dataset<Row> nGramDF =  nGramTransform.transform(filteredData);

        int numberOfFeatures = 1000;
        HashingTF hashingTF = new HashingTF()
                .setInputCol("ngrams")
                .setOutputCol("rawFeatures")
                .setNumFeatures(numberOfFeatures);

        Dataset<Row> featuredData = hashingTF.transform(nGramDF);
        IDF idf = new IDF().setInputCol("rawFeatures").setOutputCol("features");
        IDFModel idfModel = idf.fit(featuredData);

        JavaRDD<Row> featuresRDD = idfModel.transform(featuredData)
                .select("features").toJavaRDD().cache();

        JavaRDD<Vector> parsedData = featuresRDD.map(row -> parseData(row)).cache();

        parsedData.saveAsTextFile("tfidfdata.csv");

        System.out.println();


    }

    private static Vector parseData(Row row){
        return Vectors.dense(((SparseVector)row.get(0)).toArray());
    }
}
