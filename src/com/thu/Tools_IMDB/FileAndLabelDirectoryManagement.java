package com.thu.Tools_IMDB;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


/**
 * Created by jugs on 3/19/17.
 * Reads The csv File with Movie name and its Label(Class,Genre) and process the real files with data and creates directory to copy files
 */
public class FileAndLabelDirectoryManagement
{
    public static void main(String[] args) throws Exception
    {
        FileAndLabelDirectoryManagement fileClassMgnt  = new FileAndLabelDirectoryManagement();
        fileClassMgnt.program();
    }

    private void program() throws Exception
    {
        String csvPath ="/home/jugs/Downloads/movieTitles labeled.csv";
        File file = new File(csvPath);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null)
        {
            String[] movieClass = line.split(",");
            copyFileToDirectory(movieClass[0], movieClass[1]);
            //System.out.println(movieClass[0] + " : " + movieClass[1]);
        }
    }

    private void copyFileToDirectory(String movieTitle, String movieClass) throws Exception
    {
        String fileAndDirPath = "/home/jugs/IdeaProjects/MoviesSimillarity/Review_Document/unlabelled";
        File dir = new File(fileAndDirPath+"/"+movieClass);
        if (!dir.exists())
        {
            dir.mkdir();
        }
        File file = new File(fileAndDirPath);
        File[] listFiles = file.listFiles();
        for (int i=0;i < listFiles.length; i++)
        {
            if ((listFiles[i].getName().equals(movieTitle+".txt")))
            {
                FileUtils.copyFileToDirectory(listFiles[i],dir);
                System.out.println(listFiles[i].getName());
            }
        }
    }
}
