package com.thu.simillarity.IMDBPackage;


import com.thu.simillarity.IMDBJavaObjectCode.ImdbCom;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Created by jugs on 10/10/16.
 */
public class ReadIMDBXMLFile
{
//    public static void main(String[] args)
//    {
//        ReadIMDBXMLFile readIMDBXMLFile = new ReadIMDBXMLFile();
//        ImdbCom imdb = readIMDBXMLFile.getIMDBData();
//        System.out.println();
//    }

    public static ImdbCom getIMDBData()
    {
        ImdbCom imdbCom = null;

        File file = new File("input/IMDB/IMDBMovies.xml");
        try
        {
            JAXBContext jaxbContext = JAXBContext.newInstance(ImdbCom.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            imdbCom = (ImdbCom) unmarshaller.unmarshal(file);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return imdbCom;
    }
}
