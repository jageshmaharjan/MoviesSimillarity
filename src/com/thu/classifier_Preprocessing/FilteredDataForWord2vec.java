package com.thu.classifier_Preprocessing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Created by jugs on 3/10/17.
 */
public class FilteredDataForWord2vec
{
    public static void main(String[] args) throws Exception
    {
        String path = "/home/jugs/IdeaProjects/MoviesSimillarity/ReviewSentences/FilteredReview.txt";
        File file = new File(path);
        String line;
        BufferedReader br = new BufferedReader(new FileReader(file));
        while ((line = br.readLine()) != null)
        {
            if (!line.equals(""))
            {
                if (line.split("\t").length == 2)
                {
                    saveFilteredDataOnly(line.split("\t")[1]);
                }
            }
        }
    }

    private static void saveFilteredDataOnly(String str) throws Exception
    {
        String path = "/home/jugs/IdeaProjects/MoviesSimillarity/ForWord2Vec/smallW2vData.txt";
        FileWriter fw = new FileWriter(path, true);
        fw.write(str +"\n");
        fw.close();
    }
}
