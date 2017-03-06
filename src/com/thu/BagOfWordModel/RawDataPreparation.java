package com.thu.BagOfWordModel;

import com.thu.XMLDataManipulation.IMDBJavaObjectCode.ImdbCom;
import com.thu.XMLDataManipulation.IMDBPackage.ReadIMDBXMLFile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jugs on 3/3/17.
 */
public class RawDataPreparation implements Serializable
{
    public static void main(String[] args)
    {
        RawDataPreparation wc = new RawDataPreparation();
        wc.runProgram();
    }

    public List<MovieObject> runProgram()
    {
        List<MovieObject> movieObjectList = new ArrayList<>();
        ReadIMDBXMLFile readIMDBXMLFile = new ReadIMDBXMLFile();
        List<ImdbCom.IMDB> imdbMovieList = readIMDBXMLFile.getIMDBData().getIMDB();
        for (ImdbCom.IMDB movie : imdbMovieList)
        {
            String movieTitle = movie.getTitle();
            String movieReview = getReview(movie);
            if (!movieReview.equals(""))
            {
                MovieObject movieObject = new MovieObject();
                movieObject.setTitle(movieTitle);
                movieObject.setReview(movieReview);
                movieObjectList.add(movieObject);
                //System.out.println(movieTitle + "\t" + movieReview);
            }
        }
        System.out.println(movieObjectList);

        return movieObjectList;
    }

    private String getReview(ImdbCom.IMDB movie)
    {
        String review = "";
        if (!movie.getReviews().isEmpty()) //.split("– See all my reviews")[1].equals(null)))
        {
            if (movie.getReviews().split("– See all my reviews").length > 1)
            {
                review = movie.getReviews().split("– See all my reviews")[1].split("[0-9]+\\s\\w+\\s[0-9]+\\s[p\\e+]")[0];
                //System.out.println(imdbCom.getIMDB().get(i).getReviews().split("– See all my reviews")[1] + "\n");
            }
        }
        return review;
    }
}
