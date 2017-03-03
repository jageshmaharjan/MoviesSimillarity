package com.thu.BagOfWordModel;

import com.thu.simillarity.IMDBJavaObjectCode.ImdbCom;
import com.thu.simillarity.IMDBPackage.ReadIMDBXMLFile;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.ml.feature.*;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jugs on 3/3/17.
 */
public class CreateTFIDF
{
    public static void main(String[] args)
    {
        CreateTFIDF wc = new CreateTFIDF();
        wc.runProgram();
    }

    private void runProgram()
    {
        List<MovieObject> movieObjectList = new ArrayList<>();
        ReadIMDBXMLFile readIMDBXMLFile = new ReadIMDBXMLFile();
        List<ImdbCom.IMDB> imdbMovieList = readIMDBXMLFile.getIMDBData().getIMDB();
        for (ImdbCom.IMDB movie : imdbMovieList)
        {
            String movieTitle = movie.getTitle();
            String movieReview = getReview(movie);
            if (!movieReview.equals(""))
            {
                MovieObject movieObject = new MovieObject();
                movieObject.setTitle(movieTitle);
                movieObject.setReview(movieReview);
                movieObjectList.add(movieObject);
                //System.out.println(movieTitle + "\t" + movieReview);
            }
        }
        createJavaRDD(movieObjectList);
        System.out.println(movieObjectList);
    }

    private void createJavaRDD(List<MovieObject> movieObjectList)
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

        int numberOfFeatures = 1000;
        HashingTF hashingTF = new HashingTF()
                .setInputCol("tokens")
                .setOutputCol("rawFeatures");

        Dataset<Row> featuredVector = hashingTF.transform(filteredData);
        IDF idf = new IDF().setInputCol("rawFeatures").setOutputCol("features");
        IDFModel idfModel = idf.fit(featuredVector);
        Dataset<Row> tfIdfDF = idfModel.transform(featuredVector);

        System.out.println();

    }

    private String getReview(ImdbCom.IMDB movie)
    {
        String review = "";
        if (!movie.getReviews().isEmpty()) //.split("– See all my reviews")[1].equals(null)))
        {
            if (movie.getReviews().split("– See all my reviews").length > 1)
            {
                review = movie.getReviews().split("– See all my reviews")[1].split("[0-9]+\\s\\w+\\s[0-9]+\\s[p\\e+]")[0];
                //System.out.println(imdbCom.getIMDB().get(i).getReviews().split("– See all my reviews")[1] + "\n");
            }
        }
        return review;
    }
}
