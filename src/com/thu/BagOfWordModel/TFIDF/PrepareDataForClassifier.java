package com.thu.BagOfWordModel.TFIDF;

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

        String lableFilepath = "/home/jugs/IdeaProjects/MoviesSimillarity/labelAndCode.csv";
        String tfidfFilePath = "/home/jugs/IdeaProjects/MoviesSimillarity/tfidfReview.csv";

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
            int label = getLabel(labelList.get(i).split(",")[1]);
            String sparsevec = getSparseFormat(tfidfList.get(i));

            FileWriter fw  = new FileWriter("tfIDFDataForSVM.txt",true);
            fw.write(label + " " + sparsevec + "\n" );
            fw.close();

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
                lblValue = 6;
                break;
            case "Comedy":
                lblValue = 7;
                break;
            case "Mystery":
                lblValue = 8;
                break;
            case "Sci-Fi":
                lblValue = 8;
                break;
            case "Drama":
                lblValue = 9;
                break;
            case "Family":
                lblValue = 9;
                break;
            case "Romance":
                lblValue = 9;
                break;
            case "Film-Noir":
                lblValue = 9;
                break;
            case "Western":
                lblValue = 9;
                break;
            case "Musical":
                lblValue = 9;
                break;
        }
        return lblValue;
    }

    private static void writeFileForMLPClassifier(List<String> tfidfList, List<String> labelList) throws Exception
    {
        for (int i=0; i<tfidfList.size(); i++)
        {
            //String labelCode = labelList.get(i).split(",")[0];
            int label = getLabel(labelList.get(i).split(",")[1]);
            FileWriter fw = new FileWriter("tfIDFDataForMLP.csv",true);
            fw.write(tfidfList.get(i) + "," + label +"\n");
            fw.close();
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
