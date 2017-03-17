package com.thu.classifier;

import com.thu.XMLDataManipulation.RT_JavaObjectCode.RT_Movies;
import com.thu.XMLDataManipulation.RT_Package.ReadRT_XMLFile;

import java.io.FileWriter;
import java.util.List;

/**
 * Created by jugs on 3/9/17.
 */
public class LabellingRTMovieReviews
{
    public static void main(String[] args) throws Exception
    {
        LabellingRTMovieReviews labellingMovieReviews = new LabellingRTMovieReviews();
        labellingMovieReviews.getData();
    }

    private void getData() throws Exception
    {
        ReadRT_XMLFile rt_xmlFile = new ReadRT_XMLFile();
        RT_Movies rt_movies =  rt_xmlFile.getRT_Data();
        run(rt_movies);
    }

    private void run(RT_Movies rt_movies) throws Exception
    {
        for (Object movie : rt_movies.getItem())
        {
            String title = ((RT_Movies.Item) movie).getTitle();
            String plot = ((RT_Movies.Item) movie).getStoryLine();
            List<String> reviewList = ((RT_Movies.Item) movie).getReviews().getValue();
            writeToFile(title, reviewList, plot);
            //getAllPlotOnly(plot);       //For Expiremental purpose for  word2vec
        }
    }

    private void getAllPlotOnly(String plot) throws Exception
    {
        String path = "/home/jugs/IdeaProjects/MoviesSimillarity/ForWord2Vec/plotW2vData.txt";
        FileWriter fw = new FileWriter(path, true);
        fw.write(plot.trim() +"\n");
        fw.close();
    }

    private void writeToFile(String title, List<String> reviewList, String plot) throws Exception
    {
//        String path = "/home/jugs/IdeaProjects/MoviesSimillarity/Review_Document";
        String path = "/home/jugs/Desktop/Test";    //Temporary Directory
        String new_title = checkTitleNamingFormat(title);
        FileWriter fw = new FileWriter(path +"/" + new_title + ".txt" , true);
        fw.write(plot.trim() + "\n");
        for (String review : reviewList)
        {
            fw.write(review.trim().replaceAll("[^\\x00-\\x7F]", "") + "\n");
        }
        fw.close();

        //asASingleFile(title, reviewList, plot);
    }

    private void asASingleFile(String title, List<String> reviewList, String plot) throws Exception
    {
        String concatReview = "";
        for (String review : reviewList)
        {
            concatReview += review.trim().replaceAll("[^\\x00-\\x7F]", " ").replace("[Full review in Spanish]", " ") + " ";
        }
        FileWriter fw = new FileWriter("ReviewInOneFile.txt", true);
        fw.write(plot.trim() + " " + concatReview + "\n");
        fw.close();
    }


    private String checkTitleNamingFormat(String title)
    {
        String new_str = title.trim();
        if (title.contains("/"))
        {
            new_str = title.replaceAll("/","&");
        }
        return new_str;
    }
}
