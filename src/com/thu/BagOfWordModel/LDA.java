package com.thu.BagOfWordModel;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineModel;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.ml.feature.CountVectorizer;
import org.apache.spark.ml.feature.RegexTokenizer;
import org.apache.spark.ml.feature.StopWordsRemover;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jugs on 3/6/17.
 */
public class LDA implements Serializable
{
    public static void main(String[] args)
    {
        LDA lda = new LDA();
        lda.getDataSet();
    }

    private void getDataSet()
    {
        RawDataPreparation createTFIDF = new RawDataPreparation();
        List<MovieObject> movieObjectList = createTFIDF.runProgram();
        computeLDA(movieObjectList);

    }

    private void computeLDA(List<MovieObject> movieObjectList)
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

}
