package com.thu.BagOfWordModel.TFIDF;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.SparkSession;

/**
 * Created by jugs on 3/17/17.
 */
public class FileFormatting
{
    public static void main(String[] args)
    {
        FileFormatting fileFormatting = new FileFormatting();
        //Takes the input as tfidf with format (label, value1, value2....valuen) and output a tfidf of format (val1, vak2....valn, label)
        SparkSVMInputFormat();
    }

    private static void SparkSVMInputFormat()
    {
        SparkSession spark = SparkSession.builder()
                .appName("Format File accepted by SVM")
                .master("local[*]")
                .getOrCreate();

        String path = "/home/jugs/IdeaProjects/MoviesSimillarity/tfidfReview.csv";
        JavaRDD<String> formattedForSVM = spark.read().textFile(path).toJavaRDD().map(row -> arrangeString(row));
        formattedForSVM.saveAsTextFile("formattedFile.txt");

    }
    private static String arrangeString(String row)
    {
        String[] splitRow = row.split(",");
        String finalRow = "";
//        if (splitRow.length == 1000)
//        {
            String temp = splitRow[splitRow.length - 1];
            splitRow[splitRow.length - 1] = splitRow[0];
            splitRow[0] = temp;

            for (int i=0; i<splitRow.length; i++)
            {
                finalRow += splitRow[i] + " ";

            }
//        }
        return finalRow;
    }
}
