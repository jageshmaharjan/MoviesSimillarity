package com.thu.BagOfWordModel.TFIDF;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.io.Serializable;
import java.util.List;
import java.util.Properties;

/**
 * Created by jugs on 3/18/17.
 */
public class StanfordNLPProcessing
{
    public static void main(String[] args)
    {
//        new StanfordNLPProcessing().programPOSFiltering();
        new StanfordNLPProcessing().readPOSFilteredFile();
    }

    private void readPOSFilteredFile()
    {
        String path = "";


    }

    //-----Since StanfordCoreNLP Object is NotSerializable object, Used FeaturesExtractionStanfordNLP.java class only to extract tokens based on POS-------
    private void programPOSFiltering()
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

        JavaRDD<String> reviewRDD = movieDF.select("reviews").toJavaRDD().map(r ->
        {
            Properties prop = new Properties();
            prop.put("annotators","tokenize, ssplit, pos, lemma");
            StanfordCoreNLP pipeline = new StanfordCoreNLP(prop);
            Annotation document = new Annotation(r.toString());
            pipeline.annotate(document);
            List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
            String pos = "";
            String lemma = "";
            for (CoreMap sentence : sentences)
            {
                for (CoreLabel tokens : sentence.get(CoreAnnotations.TokensAnnotation.class))
                {
                    pos = tokens.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                    lemma = tokens.get(CoreAnnotations.LemmaAnnotation.class);
                }
            }
            System.out.println("POS: " + pos + ", Lemma: " + lemma);
            return r.toString();
        });

        System.out.println();

    }

//    private String processStanfordNLP(Row r)
//    {
//        Annotation document = new Annotation(r.toString());
//        pipeline.annotate(document);
//
//        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
//        for (CoreMap sentence : sentences)
//        {
//            for (CoreLabel tokens : sentence.get(CoreAnnotations.TokensAnnotation.class))
//            {
//                String pos = tokens.get(CoreAnnotations.PartOfSpeechAnnotation.class);
//                String lemma = tokens.get(CoreAnnotations.LemmaAnnotation.class);
//                System.out.println("POS: " + pos + ", Lemma: " + lemma);
//            }
//        }
//        return r.toString();
//    }
}
