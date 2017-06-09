package com.thu.BagOfWordModel.TFIDF_Processing;

import java.io.*;

/**
 * Created by jugs on 3/16/17.
 * This class Reads the "tfidfdata.csv/part-00000" and "labels.txt/part-00000" file generated fom the "TfIdfGeneration.java" class,
 * which is a spark based output file.
 */
public class CleanTfIDFDocuments
{
    public static void main(String[] args) throws Exception
    {
        CleanTfIDFDocuments cleanTfIDFDocuments = new CleanTfIDFDocuments();
        //----This Method reads the "tfidfdata.csv/part-00000" file, does some cleaning
        cleanTfIDFDocuments.processLabels();
        cleanTfIDFDocuments.processTFIDFDoc();
    }

    private String processTFIDFDoc() throws Exception
    {
        String path = "IMDBLabelling/tfidfDataPOS.csv/part-00001";
//        String path = "PrafulLabelling/tfidfDataPOS.csv/part-00000";
//        String path = "MoviePlotAndlabel/tfidfDataPOS.csv/part-00000";
        File file = new File(path);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = null;
        String tfidf = null;
        while ((line = br.readLine()) != null)
        {
            tfidf = line.replace("[", "").replace("]","");
//            System.out.println(tfidf);
            writeTFIDF(tfidf);
        }
        return tfidf;
    }

    private void writeTFIDF(String tfidf) throws Exception
    {
        String path = "IMDBLabelling/tfidfReview.csv";
//        String path = "PrafulLabelling/tfidfReview.csv";
//        String path = "MoviePlotAndlabel/tfidfReview.csv";
        FileWriter fw = new FileWriter(path, true);
        fw.write(tfidf + "\n");
        fw.close();
    }

    private int processLabels() throws Exception
    {
        String filePath = "IMDBLabelling/labels.txt/part-00001";
//        String filePath = "PrafulLabelling/labels.txt/part-00000";
//        String filePath = "MoviePlotAndlabel/labels.txt/part-00000";
        File file = new File(filePath);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        int lableCode = 0;
        //Set<String> entity = new HashSet<>();
        while ((line = br.readLine()) != null)
        {
            String labels = line.replace("[","").replace("]","");
            lableCode = assignNumber(labels);
            writeToLabels(labels, lableCode);
            //entity.add(labels);
        }
        //System.out.println(entity.toString());
        return lableCode;
    }

    private void writeToLabels(String labels, int lableCode) throws Exception
    {
        String path = "IMDBLabelling/labelAndCode.csv";
//        String path = "PrafulLabelling/labelAndCode.csv";
//        String path = "MoviePlotAndlabel/labelAndCode.csv";
        FileWriter fw = new FileWriter(path, true);
        fw.write(lableCode + "," + labels + "\n");
        fw.close();
    }

    private int assignNumber(String labels)
    {
        int assignNo = 0;
        switch (labels)
        {
            case " ":
                assignNo = 0;
                break;
            case "Film-Noir":
                assignNo = 1;
                break;
            case "Action":
                assignNo = 2;
                break;
            case "Adventure":
                assignNo = 3;
                break;
            case "Horror":
                assignNo = 4;
                break;
            case "Romance":
                assignNo = 5;
                break;
            case "War":
                assignNo = 6;
                break;
            case "History":
                assignNo = 7;
                break;
            case "Western":
                assignNo = 8;
                break;
            case "Sci-Fi":
                assignNo = 9;
                break;
            case "Drama":
                assignNo = 10;
                break;
            case "Thriller":
                assignNo = 11;
                break;
            case "Crime":
                assignNo = 12;
                break;
            case "Biography":
                assignNo = 13;
                break;
            case "Fantasy":
                assignNo = 14;
                break;
            case "Animation":
                assignNo = 15;
                break;
            case "Family":
                assignNo = 16;
                break;
            case "Comedy":
                assignNo = 17;
                break;
            case "Mystery":
                assignNo = 18;
                break;
            case "Musical":
                assignNo = 19;
                break;
        }
        return assignNo;
    }

}
