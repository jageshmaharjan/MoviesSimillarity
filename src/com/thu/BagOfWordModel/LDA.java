package com.thu.BagOfWordModel;

import com.thu.simillarity.IMDBJavaObjectCode.ImdbCom;
import com.thu.simillarity.IMDBPackage.ReadIMDBXMLFile;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineModel;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.ml.feature.CountVectorizer;
import org.apache.spark.ml.feature.RegexTokenizer;
import org.apache.spark.ml.feature.StopWordsRemover;
import org.apache.spark.ml.linalg.Vectors;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jugs on 3/6/17.
 */
public class LDA implements Serializable
{
    public static void main(String[] args)
    {
        LDA lda = new LDA();
        lda.runProgram();
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

        RegexTokenizer tokenizer = new RegexTokenizer()
                .setInputCol("review")
                .setOutputCol("words");

        StopWordsRemover remover = new StopWordsRemover()
                .setInputCol("words")
                .setOutputCol("filtered");

        CountVectorizer cv = new CountVectorizer()
                .setVocabSize(1000)
                .setInputCol("filtered")
                .setOutputCol("features");

        Pipeline pipeline = new Pipeline()
                .setStages(new PipelineStage[]{tokenizer, remover, cv});

        PipelineModel model = pipeline.fit(reviewDf);

        JavaRDD<Vector> countVectors = model.transform(reviewDf)
                .select("features").toJavaRDD().map(new Function<Row, Vector>()
                {
                    @Override
                    public Vector call(Row row) throws Exception
                    {
                        return (Vector) (row.get(0))  ;
                    }
                });

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
