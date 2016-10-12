package com.thu.simillarity.RottenTomatoesPackage;

import com.thu.simillarity.RottenTomatoesJavaObjectCode.RottentomatoesCom;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Created by jugs on 10/10/16.
 */
public class ReadRottenTomatoesXMLFile
{
//    public static void main(String[] args)
//    {
//        ReadRottenTomatoesXMLFile rottentomatoesCom = new ReadRottenTomatoesXMLFile();
//        RottentomatoesCom rtdata = rottentomatoesCom.getRotttenTomatoesData();
//
//        System.out.println();
//    }

    public static RottentomatoesCom getRotttenTomatoesData()
    {

        RottentomatoesCom rottentomatoes = null;
                File file = new File("input/RottenTomatoes/RottenTomatoesMovies_2.xml");
        try
        {
            JAXBContext jaxbContext = JAXBContext.newInstance(RottentomatoesCom.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            rottentomatoes = (RottentomatoesCom) unmarshaller.unmarshal(file);

        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return rottentomatoes;
    }
}
