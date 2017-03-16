package com.thu.BagOfWordModel.TFIDF;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jugs on 3/16/17.
 */
public class CleanTfIDFDocuments
{
    public static void main(String[] args) throws Exception
    {
        CleanTfIDFDocuments cleanTfIDFDocuments = new CleanTfIDFDocuments();
        cleanTfIDFDocuments.processLabels();
        cleanTfIDFDocuments.processTFIDFDoc();
    }

    private void processTFIDFDoc() throws Exception
    {
        String path = "/home/jugs/IdeaProjects/MoviesSimillarity/ifidfdata.csv/part-00000";
        File file = new File(path);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = null;
        while ((line = br.readLine()) != null)
        {
            String tfidf = line.replace("[", "").replace("]","");
//            System.out.println(tfidf);
            writeTFIDF(tfidf);
        }
    }

    private void writeTFIDF(String tfidf) throws Exception
    {
        FileWriter fw = new FileWriter("tfidfReview.csv", true);
        fw.write(tfidf + "\n");
        fw.close();
    }

    private void processLabels() throws Exception
    {
        String filePath = "/home/jugs/IdeaProjects/MoviesSimillarity/labels.txt/part-00000";
        File file = new File(filePath);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        //Set<String> entity = new HashSet<>();
        while ((line = br.readLine()) != null)
        {
            String labels = line.replace("[","").replace("]","");
            int lableCode = assignNumber(labels);
            writeToLabels(labels, lableCode);
            //entity.add(labels);
        }
        //System.out.println(entity.toString());
    }

    private void writeToLabels(String labels, int lableCode) throws Exception
    {
        FileWriter fw = new FileWriter("labelAndCode.csv", true);
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
