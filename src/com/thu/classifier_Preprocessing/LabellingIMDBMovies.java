package com.thu.classifier_Preprocessing;

import com.thu.XMLDataManipulation.IMDBJavaObjectCode.ImdbCom;
import com.thu.XMLDataManipulation.IMDBPackage.ReadIMDBXMLFile;

import java.io.FileWriter;
import java.util.List;

/**
 * Created by jugs on 3/9/17.
 */
public class LabellingIMDBMovies
{
    public static void main(String[] args) throws Exception
    {
        LabellingIMDBMovies labellingIMDBMovies = new LabellingIMDBMovies();
        labellingIMDBMovies.getData();
    }

    private void getData() throws Exception
    {
        ReadIMDBXMLFile readIMDBXMLFile = new ReadIMDBXMLFile();
        ImdbCom imdbCom =  readIMDBXMLFile.getIMDBData();
        run(imdbCom);
    }

    private void run(ImdbCom imdbCom) throws Exception
    {
        for (Object imbd : imdbCom.getIMDB())
        {
            String title = ((ImdbCom.IMDB) imbd).getTitle();
            List<String> genreList = ((ImdbCom.IMDB) imbd).getGenres().getGenre();
            String plot = ((ImdbCom.IMDB) imbd).getStoryLine();
            String reviews = getReview(((ImdbCom.IMDB) imbd).getReviews());
            writeToFile(title, genreList, reviews, plot);
//            getAllPlotOnly(plot);   //Experimental pupose for word2Vec
        }
    }

    private void getAllPlotOnly(String plot) throws Exception
    {
        if (!plot.equals("Plot is unknown.") && (!plot.equals("Add a Plot")))
        {
            String path = "/home/jugs/IdeaProjects/MoviesSimillarity/ForWord2Vec/plotW2vData.txt";
            FileWriter fw = new FileWriter(path, true);
            fw.write(plot.replaceAll("See full summary", "").trim() +"\n");
            fw.close();

        }

    }

    private void writeToFile(String title, List<String> genreList, String reviews, String plot) throws Exception
    {
//        String path = "/home/jugs/Desktop/Test";    //Temporary Directory
//        if ((!genreList.isEmpty()) && (!reviews.equals("")) && (!plot.equals("")))
//        {
//            for (String genre : genreList)
//            {
//                String genreName = genre.trim();
//                File directory = new File(path + "/" + genreName);
//                if (!directory.exists())
//                {
//                    directory.mkdir();
//                }
//
//                String new_title = checkTitleNamingFormat(title);
//                FileWriter fw = new FileWriter(path + "/" + genreName + "/" + new_title +".txt" , false);
//                fw.write(plot.trim() + "\n" + reviews.trim());
//                fw.close();
//            }
//        }

        asASingleFile(title,genreList,reviews,plot);    //For Spark TFIDF Generation Purpose
    }

    private void asASingleFile(String title, List<String> genreList, String reviews, String plot) throws Exception
    {
        if ((!genreList.get(0).isEmpty()) && (!reviews.equals("")) && (!plot.equals("")))
        {
            FileWriter fw = new FileWriter("ReviewInOneFile.txt", true);
            fw.write(genreList.get(0) + "::\t" + plot.trim() + " " + reviews.trim() + "\n");
            fw.close();
        }
    }

    private String getReview(String reviewString)
    {
        String review = reviewString;
        if (!reviewString.isEmpty()) //.split("– See all my reviews")[1].equals(null)))
        {
            if (reviewString.split("– See all my reviews").length > 1)
            {
                review = reviewString.split("– See all my reviews")[1].split("[0-9]+\\s\\w+\\s[0-9]+\\s[p\\e+]")[0];
            }
        }
        return review;
    }

    private String checkTitleNamingFormat(String title)
    {
        String new_str = title;
        if (title.contains("/"))
        {
            new_str = title.replaceAll("/","&");
        }
        return new_str;
    }
}
