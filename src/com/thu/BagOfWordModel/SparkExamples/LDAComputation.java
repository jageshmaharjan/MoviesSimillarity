package com.thu.BagOfWordModel.SparkExamples;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineModel;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.ml.feature.CountVectorizer;
import org.apache.spark.ml.feature.RegexTokenizer;
import org.apache.spark.ml.feature.StopWordsRemover;
import org.apache.spark.ml.linalg.SparseVector;
import org.apache.spark.mllib.clustering.LDA;
import org.apache.spark.mllib.clustering.LDAModel;
import org.apache.spark.mllib.linalg.Matrix;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jugs on 3/6/17.
 */
public class LDAComputation implements Serializable
{
    public static void main(String[] args)
    {
        LDAComputation lda = new LDAComputation();
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
        Dataset<Row> movieDf = spark.createDataFrame(movieRDD, MovieObject.class);

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
                .select("features").toJavaRDD()
                .map(row -> Vectors.dense(((SparseVector)row.get(0)).toArray()));

        JavaPairRDD<Long, Vector> corpus = JavaPairRDD.fromJavaRDD(countVectors.zipWithIndex()
                .map(doc_id -> doc_id.swap()));

        corpus.cache();

        LDAModel ldaModel =new LDA().setK(3).run(corpus);

        System.out.println("Learned topics as distributed over dataset of " + ldaModel.vocabSize() + " words ");

        Matrix topics = ldaModel.topicsMatrix();
        for (int topic = 0 ; topic < 3 ; topic ++)
        {
            System.out.println("Topics: " + topic + " : ");
            for (int word = 0 ; word < ldaModel.vocabSize(); word ++)
            {
                System.out.println(" " + topics.apply(word, topic));
            }
            System.out.println();
        }
        System.out.println();
    }
}
