package com.thu.Tools_PrafulLabel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Created by jugs on 3/21/17.
 */
public class MovieNameToClasslabel
{
    public static void main(String[] args) throws Exception
    {
        MovieNameToClasslabel movietoclass = new MovieNameToClasslabel();
        movietoclass.program();
    }

    private void program() throws Exception
    {
        String path = "ForWord2Vec/plotW2vData.txt";
        File file = new File(path);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = "";
        while ((line = br.readLine()) != null)
        {
            String[] split = line.split(":::\t");
            if (split.length == 2 )
            {
                String labelName = getLabelGenre(split[0]);
                if ((!labelName.equals("")) || (labelName.equals("None")))
                writeLabelWithLable(labelName, split[1]);
            }
        }
    }

    private void writeLabelWithLable(String labelName, String plot) throws Exception
    {
        FileWriter fw = new FileWriter("MoviePlotAndlabel/plotlabel.txt",true);
        fw.write(labelName + ":::\t" + plot + "\n");
        fw.close();
    }

    private String getLabelGenre(String title) throws Exception
    {
        String lablename = "";
        String prafullabelCsvpath = "/home/jugs/Downloads/movieTitles labeled.csv";
        File file = new File(prafullabelCsvpath);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null)
        {
            String[] split = line.split(",");
            if (split[0].trim().equals(title))
            {
                lablename = split[1];
                break;
            }
        }
        return lablename;
    }
}
