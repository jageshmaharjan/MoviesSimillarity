//-------------OMAR WROTE ----------------


//package thu.keg.jugs;
//
//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.JAXBException;
//import javax.xml.bind.Marshaller;
//import javax.xml.bind.Unmarshaller;
//import java.io.File;
//
///**
// * Created by jugs on 10/10/16.
// */
//public class XMLReaderWriter {
//    private String filename;
//
//    public static void main(String args[]){
//        XMLReaderWriter xmlReaderWriter = new XMLReaderWriter("input/IMDB/IMDBMovies.xml");
//        ImdbCom data = xmlReaderWriter.readData();
//
//        for (ImdbCom.IMDB movie : data.imdb){
//            System.out.println(movie.title);
//            System.out.println(movie.duration);
//            System.out.println(movie.id);
//            System.out.println(movie.releaseDate);
//            System.out.println(movie.reviews);
//        }
//
////        xmlReaderWriter.setFilename("input/IMDB/new.xml");
////        xmlReaderWriter.saveData(data);
//
//    }
//
//    public XMLReaderWriter(String file) {
//        filename = file;
//    }
//
//    public ImdbCom readData()
//    {
//        ImdbCom data = null;
//        File file = new File(filename);
//        try {
//            JAXBContext jaxbContext = JAXBContext.newInstance(ImdbCom.class);
//            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
//            data = (ImdbCom)unmarshaller.unmarshal(file);
//        }catch (JAXBException e){
//            e.printStackTrace();
//        }
//        return data;
//    }
//
//    public void setFilename(String str){
//        filename = str;
//    }
//
//    public void saveData(ImdbCom data){
//        File newFile = new File(filename);
//        try {
//            JAXBContext jaxbContext = JAXBContext.newInstance(ImdbCom.class);
//            Marshaller marshaller = jaxbContext.createMarshaller();
//            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//            marshaller.marshal(data, newFile);
//        }catch (JAXBException e){
//            e.printStackTrace();
//        }
//    }
//}
