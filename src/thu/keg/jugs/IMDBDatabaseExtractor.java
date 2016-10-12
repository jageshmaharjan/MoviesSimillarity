//package com.thu.simillarity;
//
//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.Unmarshaller;
//import java.io.File;
//
///**
// * Created by jugs on 10/10/16.
// */
//public class IMDBDatabaseExtractor
//{
//    public static void main(String[] args)
//    {
//        File imdbfile = new File("input/IMDB/IMDBMovies.xml");
//        try
//        {
//            JAXBContext jaxbContext = JAXBContext.newInstance(ImdbCom.class);
//            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
//            ImdbCom imdb = (ImdbCom) unmarshaller.unmarshal(imdbfile);
//
//            System.out.println();
//        }
//        catch (Exception e)
//        {
//            System.out.println(e);
//        }
//    }
//}
