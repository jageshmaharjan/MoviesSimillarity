package com.thu.BagOfWordModel;

import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.ml.feature.StopWordsRemover;
import org.apache.spark.ml.feature.Tokenizer;
import org.apache.spark.ml.feature.Word2Vec;
import org.apache.spark.ml.feature.Word2VecModel;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jugs on 3/6/17.
 */
public class WordToVecEstimator implements Serializable
{
    public static void main(String[] args)
    {
        WordToVecEstimator word2vec = new WordToVecEstimator();
        word2vec.getData();
    }

    private void getData()
    {
        RawDataPreparation dataPreparation = new RawDataPreparation();
        List<MovieObject> movieObjectList = dataPreparation.runProgram();
        computeWord2Vector(movieObjectList);
    }

    private void computeWord2Vector(List<MovieObject> movieObjectList)
    {

        SparkSession spark = SparkSession
                .builder()
                .appName("Word To Vector")
                .master("local")
                .getOrCreate();

        JavaRDD<MovieObject> movieObjectJavaRDD = new JavaSparkContext(SparkContext.getOrCreate()).parallelize(movieObjectList);
        Dataset<Row> movieDF =  spark.createDataFrame(movieObjectJavaRDD, MovieObject.class);

        movieDF.createOrReplaceTempView("movie");
        Dataset<Row> reviewDf = movieDF.select("review");

        Tokenizer tokenizer = new Tokenizer().setInputCol("review").setOutputCol("alltokens");
        Dataset<Row> tokenizedDF = tokenizer.transform(reviewDf);

        StopWordsRemover remover = new StopWordsRemover()
                .setInputCol("alltokens")
                .setOutputCol("tokens");
        Dataset<Row> filteredData = remover.transform(tokenizedDF);

        List<Row> filteredRDD = getRowList(filteredData); //Arrays.asList(getRowList(filteredData)); //filteredData.select("tokens").toJavaRDD();

        System.out.println();

        Word2Vec word2Vec = new Word2Vec()
                .setInputCol("review")
                .setOutputCol("result")
                .setVectorSize(10)
                .setMinCount(0);

//        Word2VecModel model = word2Vec.fit(reviewDF);
//        Dataset<Row> result = model.transform(reviewDF);
//
//        for (Row row : result.collectAsList())
//        {
//            List<String> text = row.getList(0);
//            Vector vector = (Vector) row.get(1);
//            System.out.println("Text: " + text + "==> \n Vector: " + vector + "\n");
//
//        }

        System.out.println();
    }

    private List getRowList(Dataset<Row> filteredData)
    {
        JavaRDD<Row> filteredRDD = filteredData.select("tokens").toJavaRDD();
        List rowList = new ArrayList();
        for (int i=1; i<= filteredRDD.count() ; i++)
        {
            filteredRDD.take(i).get(0);
        }
        return Arrays.asList(RowFactory.create(Arrays.asList("a","b")),RowFactory.create(Arrays.asList("x","y")));

    }

}
