package com.thu.BagOfWordModel.TFIDF;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jugs on 3/17/17.
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

        writeFileForMLPClassifier(tfidfList,labelList);
    }

    private static void writeFileForMLPClassifier(List<String> tfidfList, List<String> labelList) throws Exception
    {
        for (int i=0; i<tfidfList.size(); i++)
        {
            String labelCode = labelList.get(i).split(",")[0];
            String labelname = labelList.get(i).split(",")[1];
            if ((!labelname.equals("Film-Noir")) && (!labelname.equals("War")) && (!labelname.equals("History")) && (!labelname.equals("Musical"))
                    && (!labelname.equals("Romance")) && (!labelname.equals("Western")) && (!labelname.equals("Sci-Fi")) && (!labelname.equals("Family")) )
            {
                FileWriter fw = new FileWriter("tfIDFDataForMLP.csv",true);
                fw.write(tfidfList.get(i) + "," + labelCode +"\n");
                fw.close();
            }
            System.out.println();
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

//    private List<String> readLabelFile(String tfidfFilePath) throws Exception
//    {

//        File file = new File(lableFilepath);
//        BufferedReader br = new BufferedReader(new FileReader(file));
//        String line;
//        List<String> labelList = new ArrayList<>();
//        while ((line = br.readLine()) != null)
//        {
//            labelList.add(line);
//        }
//        return labelList;
//    }
}
