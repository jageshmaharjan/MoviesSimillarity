package com.thu.simillarity.RT_Package;

import com.thu.simillarity.RT_JavaObjectCode.RT_Movies;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
        try
        {
            JAXBContext jaxbContext = JAXBContext.newInstance(RT_Movies.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            rt_movies = (RT_Movies) unmarshaller.unmarshal(file);
            for (int i=0; i< rt_movies.getItem().size(); i++)
            {
                ArrayList review = (ArrayList) rt_movies.getItem().get(i).getReviews().getValue();
                System.out.println();
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return rt_movies;
    }
}
