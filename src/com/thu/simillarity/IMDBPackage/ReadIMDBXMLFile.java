package com.thu.simillarity.IMDBPackage;


import com.thu.simillarity.IMDBJavaObjectCode.ImdbCom;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;
import java.util.Properties;

/**
 * Created by jugs on 10/10/16.
 */
public class ReadIMDBXMLFile
{
    public static void main(String[] args)
    {
        ReadIMDBXMLFile readIMDBXMLFile = new ReadIMDBXMLFile();
        ImdbCom imdb = readIMDBXMLFile.getIMDBData();
        System.out.println();
    }

    public static ImdbCom getIMDBData()
    {
        ImdbCom imdbCom = null;

        File file = new File("input/IMDB/IMDBMovies.xml");
        try
        {
            JAXBContext jaxbContext = JAXBContext.newInstance(ImdbCom.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            imdbCom = (ImdbCom) unmarshaller.unmarshal(file);
            System.out.println();
            for (int i =0; i < imdbCom.getIMDB().size(); i++)
            {
                String storyLine = imdbCom.getIMDB().get(i).getStoryLine();
                System.out.println(storyLine);

            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return imdbCom;
    }
}
