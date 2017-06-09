package com.thu.BagOfWordModel.TFIDF_Processing;

import org.apache.spark.sql.catalyst.plans.logical.Except;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jugs on 3/17/17.
 * This class Prepare The Data to feed into the Machine Learning Algorithms.
 * Reads The TFIDF file and the corresponding label, that is cleaned by the CleanTfIDFDocuments.java class in the same package
 */
public class PrepareDataForClassifier
{
    public static void main(String[] args) throws Exception
    {
        PrepareDataForClassifier prepareDataForClassifier = new PrepareDataForClassifier();

        String lableFilepath = "IMDBLabelling/labelAndCode.csv";
        String tfidfFilePath = "IMDBLabelling/tfidfReview.csv";
//        String lableFilepath = "PrafulLabelling/labelAndCode.csv";
//        String tfidfFilePath = "PrafulLabelling/tfidfReview.csv";
//        String lableFilepath = "MoviePlotAndlabel/labelAndCode.csv";
//        String tfidfFilePath = "MoviePlotAndlabel/tfidfReview.csv";

        List<String> labelList = prepareDataForClassifier.readParameterFile(lableFilepath);
        List<String> tfidfList = prepareDataForClassifier.readParameterFile(tfidfFilePath);

        //-------Generates the Data for the input of MultiLayerperceptron Neural Network----
        //-------The output is in the Format of (val_1, val_2, val_3, ....... , val_n, label)
        writeFileForMLPClassifier(tfidfList,labelList);

        //-------Generates the Data for the input of Support Vector Machine(SVM) in Spark----
        //-------The output is in the Format of (label val_1 val_2 val_3 ....... val_n)
        writeFileForSVMSpark(tfidfList,labelList);
    }

    private static void writeFileForSVMSpark(List<String> tfidfList, List<String> labelList) throws Exception
    {
        for (int i= 0; i< tfidfList.size(); i++)
        {
            if (labelList.get(i).split(",").length == 2)
            {
                int label = getLabel(labelList.get(i).split(",")[1]);
                String sparsevec = getSparseFormat(tfidfList.get(i));
                String path ="IMDBLabelling/tfIDFDataForSVM.txt";
//                String path ="PrafulLabelling/tfIDFDataForSVM.txt";
//                String path ="MoviePlotAndlabel/tfIDFDataForSVM.txt";
                FileWriter fw  = new FileWriter(path,true);
                fw.write(label + " " + sparsevec + "\n" );
                fw.close();
            }
        }
    }

    private static String getSparseFormat(String dense)
    {
        String sparseFormat = "";
        String[] splitDense = dense.split(",");
        for (int i=0; i<splitDense.length; i++)
        {
            String sparesForm = "";
            if (!splitDense[i].equals("0.0"))
            {
                sparesForm = (i+1) +":"+splitDense[i];
                sparseFormat += sparesForm + " ";
            }
        }
        return sparseFormat;
    }

    private static int getLabel(String lblClass)
    {
        int lblValue = 0;
        switch (lblClass)
        {
            case "Action":
                lblValue = 0;
                break;
            case "War":
                lblValue = 0;
                break;
            case "Adventure":
                lblValue = 1;
                break;
            case "Horror":
                lblValue = 2;
                break;
            case "Thriller":
                lblValue = 2;
                break;
            case "Crime":
                lblValue = 3;
                break;
            case "Biography":
                lblValue = 4;
                break;
            case "History":
                lblValue = 4;
                break;
            case "Fantasy":
                lblValue = 5;
                break;
            case "Animation":
                lblValue = 5;
                break;
            case "Comedy":
                lblValue = 6;
                break;
            case "Mystery":
                lblValue = 7;
                break;
            case "Sci-Fi":
                lblValue = 7;
                break;
            case "Drama":
                lblValue = 8;
                break;
            case "Family":
                lblValue = 8;
                break;
            case "Romance":
                lblValue = 8;
                break;
            case "Film-Noir":
                lblValue = 8;
                break;
            case "Western":
                lblValue = 8;
                break;
            case "Musical":
                lblValue = 8;
                break;
        }
        return lblValue;
    }

    private static void writeFileForMLPClassifier(List<String> tfidfList, List<String> labelList) throws Exception
    {
        for (int i=0; i<tfidfList.size(); i++)
        {
            if (labelList.get(i).split(",").length == 2)
            {
                //String labelCode = labelList.get(i).split(",")[0];
                int label = getLabel(labelList.get(i).split(",")[1]);
                String path = "IMDBLabelling/tfIDFDataForMLP.csv";
//            String path = "PrafulLabelling/tfIDFDataForMLP.csv";
//                String path = "MoviePlotAndlabel/tfIDFDataForMLP.csv";
                FileWriter fw = new FileWriter(path,true);
                fw.write(tfidfList.get(i) + "," + label +"\n");
                fw.close();
            }
        }
    }

    private List<String> readParameterFile(String filePath) throws Exception
    {
        File file = new File(filePath);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        List<String> tfidfList = new ArrayList<>();
        while ((line = br.readLine()) != null)
        {
            tfidfList.add(line);
        }
        return tfidfList;
    }
}
