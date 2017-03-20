package thu.ScratchWork.jugs;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Created by jugs on 10/10/16.
 */
public class IMDBXMLRead
{
    public static void main(String[] args)
    {
        File file = new File("input/IMDB/IMDBMovies.xml");
        try
        {
            JAXBContext jaxbContext = JAXBContext.newInstance(ImdbCom.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            ImdbCom imdbCom = (ImdbCom) unmarshaller.unmarshal(file);

            System.out.println();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
}
