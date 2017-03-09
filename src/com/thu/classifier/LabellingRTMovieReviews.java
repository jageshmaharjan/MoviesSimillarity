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
            List<String> reviewList = ((RT_Movies.Item) movie).getReviews().getValue();
            System.out.println(title);
            writeToFile(title, reviewList);
        }
    }

    private void writeToFile(String title, List<String> reviewList) throws Exception
    {
        String path = "/home/jugs/IdeaProjects/MoviesSimillarity/Review_Document";
        String new_title = checkTitleNamingFormat(title);
        FileWriter fw = new FileWriter(path +"/" + new_title + ".txt" , false);
        for (String review : reviewList)
        {
            fw.write(review + "\n");
        }
        fw.close();
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
