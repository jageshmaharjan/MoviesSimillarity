package com.thu.classifier;

import com.thu.XMLDataManipulation.IMDBJavaObjectCode.ImdbCom;
import com.thu.XMLDataManipulation.IMDBPackage.ReadIMDBXMLFile;

import java.io.File;
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

        }
    }

    private void writeToFile(String title, List<String> genreList, String reviews, String plot) throws Exception
    {
        String path = "/home/jugs/IdeaProjects/MoviesSimillarity/Review_Document";
        if ((!genreList.isEmpty()) && (!reviews.equals("")))
        {
            for (String genre : genreList)
            {
                String genreName = genre.trim();
                File directory = new File(path + "/" + genreName);
                if (!directory.exists())
                {
                    directory.mkdir();
                }

                String new_title = checkTitleNamingFormat(title);
                FileWriter fw = new FileWriter(path + "/" + genreName + "/" + new_title +".txt" , false);
                fw.write(plot.trim() + "\n" + reviews.trim());
                fw.close();
            }
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
