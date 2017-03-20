package com.thu.Tools_PrafulLabel;

import org.spark_project.guava.base.Charsets;

import java.io.*;

/**
 * Created by jugs on 3/20/17.
 */
public class ReviewFileWithLabelGenerate
{
    public static void main(String[] args) throws Exception
    {
        ReviewFileWithLabelGenerate reviewGen = new ReviewFileWithLabelGenerate();
        reviewGen.program();
    }

    private void program() throws Exception
    {
        String path ="/home/jugs/IdeaProjects/MoviesSimillarity/Review_Document/RottenTomatoes";
        File parentDir = new File(path);
        File[] dirs = parentDir.listFiles();
        for (File dir : dirs)
        {
            File[] files = dir.listFiles();
            for (File file : files)
            {
                String txt = org.spark_project.guava.io.Files.toString(file, Charsets.UTF_8).trim();
                writeRTReviewsWithlabel(dir.getName(), txt.replaceAll("\n"," "));
            }
        }
    }

    private void writeRTReviewsWithlabel(String lableName, String line) throws Exception
    {
        String path = "PrafulLabelling";
        FileWriter fw = new FileWriter(path+"/"+"reviewRTWithLabelInOneFile.txt", true);
        fw.write(lableName + "::\t" + line.trim() + "\n");
        fw.close();
    }
}
