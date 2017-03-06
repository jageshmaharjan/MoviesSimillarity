package com.thu.XMLDataManipulation.RT_Package;

import com.thu.XMLDataManipulation.RT_JavaObjectCode.RT_Movies;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileWriter;
import java.util.*;

/**
 * Created by jugs on 2/27/17.
 */
public class ReadRT_XMLFile
{
    public static void main(String[] args)
    {
        ReadRT_XMLFile readRTXmlFile = new ReadRT_XMLFile();
        RT_Movies rt_movies = readRTXmlFile.getRT_Data();
    }

    private RT_Movies getRT_Data()
    {
        RT_Movies rt_movies = null;
        File file = new File("input/RottenTomatoesData/rt_movies.xml");
        HashSet reviewSet = new HashSet();
        try
        {
            JAXBContext jaxbContext = JAXBContext.newInstance(RT_Movies.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            rt_movies = (RT_Movies) unmarshaller.unmarshal(file);
            for (int i=0; i< rt_movies.getItem().size(); i++)
            {
                ArrayList<String> reviews = (ArrayList) rt_movies.getItem().get(i).getReviews().getValue();
                for (String sentence : reviews)
                {
                    reviewSet.add(sentence);
                }
                //getAllReviews(reviews);
            }
            //getAllReviews(reviewSet);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return rt_movies;
    }

    private void getAllReviews(HashSet<String> reviewSet) throws Exception
    {
        String path = "ReviewSentences";
        FileWriter fw = new FileWriter(path +  "/" + "RT_reviews.txt",true);
        for (String review : reviewSet)
        {
            fw.write( review + "\n");
        }
        fw.close();
    }

    private void getAllReviews(ArrayList<String> reviews) throws Exception
    {
        FileWriter fw = new FileWriter("RT_reviews_old.txt",true);
        for (String review : reviews)
        {
                fw.write(review + "\n");

        }
        fw.close();
    }
}
