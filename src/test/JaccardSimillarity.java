package test;

import com.thu.simillarity.IMDBJavaObjectCode.ImdbCom;
import com.thu.simillarity.IMDBPackage.ReadIMDBXMLFile;
import com.thu.simillarity.RottenTomatoesJavaObjectCode.RottentomatoesCom;
import com.thu.simillarity.RottenTomatoesPackage.ReadRottenTomatoesXMLFile;
import edu.stanford.nlp.util.StringUtils;

import java.util.*;


/**
 * Created by jugs on 10/10/16.
 */
public class JaccardSimillarity
{
    public static void main(String[] args)
    {
        JaccardSimillarity jacsim = new JaccardSimillarity();
//        jacsim.JSimTest();
        jacsim.getData();

    }

    public void getData()
    {
        ReadIMDBXMLFile readIMDBXMLFile = new ReadIMDBXMLFile();
        ImdbCom imdbData = readIMDBXMLFile.getIMDBData();
        RottentomatoesCom rtData = ReadRottenTomatoesXMLFile.getRotttenTomatoesData();


        for (ImdbCom.IMDB imdbMovie : imdbData.getIMDB())
        {
            for (RottentomatoesCom.RottenTomatoes rtMovie : rtData.getRottenTomatoes())
            {
                try
                {
                    double jSimTitle = compareStringtitle(imdbMovie.getTitle() , rtMovie.getTitle());
                    double jSimActors = compareStringLst(imdbMovie.getActors().getActor() , rtMovie.getActors().getActor());
//                    double jSimDirectors = compareStringLst(imdbMovie.getDrectors().getDirector(), rtMovie.getDirectors().getDirector());
//                    double jSimDuration = compareStringDuration(imdbMovie.getDuration(),rtMovie.getDuration());
//                    double jSimGenres = compareStringGenre(imdbMovie.getGenres().getGenre(), rtMovie.getGenres().getGenre());
//                    double jSimDate = compareStringDate(imdbMovie.getReleaseDate(), rtMovie.getReleaseDate());

                    if (jSimTitle >= 0.8 && jSimActors >= 0.8 )//  && jSimGenres >= 0.8 && jSimDate == 1) //jSimActors >= 0.8 && jSimDirectors >= 0.8 && jSimDuration >= 0.0
                    {
                        System.out.println(imdbMovie.getTitle() + " = " + rtMovie.getTitle());
//                        System.out.println(imdbMovie.getReleaseDate() + " = " + rtMovie.getReleaseDate());
//                        System.out.println(imdbMovie.getDuration() + " = " + rtMovie.getDuration());
//                        System.out.println(imdbMovie.getActors().getActor() + " = " + rtMovie.getActors().getActor());
//                        System.out.println(imdbMovie.getDrectors().getDirector() + " = " + rtMovie.getDirectors().getDirector());
//                        System.out.println(imdbMovie.getGenres().getGenre() + " = " + rtMovie.getGenres().getGenre());
                    }
                }
                catch (Exception e)
                {
//                    System.out.println(e);
                }
            }
        }
    }

    private double compareStringDuration(String imdbString, String rtString)
    {
        imdbString = imdbString.replace(" ","");
        rtString = rtString.replace(" ","");

        if (Integer.parseInt(imdbString) == Integer.parseInt(rtString))
            return 1;
        else if (Integer.parseInt(imdbString) + 5 >= Integer.parseInt(rtString) && Integer.parseInt(rtString)>= Integer.parseInt(imdbString) -5)
            return 0.95;
        else if (Integer.parseInt(imdbString) + 10 >= Integer.parseInt(rtString) && Integer.parseInt(rtString)>= Integer.parseInt(imdbString) -10)
            return 0.90;
        else if (Integer.parseInt(imdbString) + 15 >= Integer.parseInt(rtString) && Integer.parseInt(rtString)>= Integer.parseInt(imdbString) -15)
            return 0.85;
        else if (Integer.parseInt(imdbString) + 25 >= Integer.parseInt(rtString) && Integer.parseInt(rtString)>= Integer.parseInt(imdbString) -25)
            return 0.80;
        else
            return 0;
    }

    private double compareStringDate(String imdbString, String rtString)
    {
        if (imdbString.equals(rtString.replace(" ","")))
            return 1;
        else
            return 0;
    }

    private double compareStringGenre(List<String> imdbGenres, List<String> rtGenres)
    {
        String[] stringArrayRT = null;
        int count = 0;
        for (String imdbGenre : imdbGenres)
        {
            imdbGenre = imdbGenre.replace(" ","");
            for (String rtGenre : rtGenres)
            {
                stringArrayRT = rtGenre.split("&");
                for (String rtGen : stringArrayRT )
                {
                    rtGen = rtGen.replace(" ","");
                    if (rtGen.equals(imdbGenre))
                        count++;
                }
            }
        }

        if (count >= rtGenres.size())
            return 1;
        else if (count > 2 && count >= rtGenres.size()-1)
            return 0.9;
        else if (count > 1 && count >= rtGenres.size()- 1)
            return 0.8;
        else
            return 0;
    }

    private double compareStringLst(List<String> imdbReclst, List<String> rtRecLst)
    {
        int count = 0;
        for (String imdbAct : imdbReclst)
        {
            imdbAct = imdbAct.replace(" ","");
            for (String rtAct : rtRecLst)
            {
                rtAct = rtAct.replace(" ","");
                if (imdbAct.equals(rtAct))
                  count += 1;
            }
        }

        if (count >= rtRecLst.size())
            return 1;
        else if (rtRecLst.size() >= 3 && count >= rtRecLst.size() - 1)
            return 0.9;
        else if (rtRecLst.size() >= 3 && count >= rtRecLst.size() - 2)
            return 0.8;
        else
            return 0;
    }

    private double compareStringActor(String imdbActors, String rtActors)
    {
        int intersection = 0;
        int union = 0;
        double Jsimillarity ;

        List<String> imdb_1gramLst = (List<String>) StringUtils.getNgramsString(imdbActors,1,1);
        List<String> rt_1gramLst = (List<String>) StringUtils.getNgramsString(rtActors,1,1);

        Set<String> imdb_SetStr = new HashSet<>();
        Set<String> rt_SetStr = new HashSet<>();
        imdb_SetStr.addAll(imdb_1gramLst);
        rt_SetStr.addAll(rt_1gramLst);

        Set<String> union_str = new HashSet<>();
        union_str.addAll(imdb_SetStr);
        union_str.addAll(rt_SetStr);

        union = union_str.size();
        intersection = rt_SetStr.size() + imdb_SetStr.size() - union;

//        System.out.println("union : " + union + " intersection : " + intersection);

        Jsimillarity = (double) intersection/union;

        return Jsimillarity;
    }


    private double compareStringtitle(String imdbString , String rtString)
    {
        int intersection = 0;
        int union = 0;
        double Jsimillarity ;

        List<String> imdb_2gramLst = (List<String>) StringUtils.getNgramsString(imdbString,2,2);
        List<String> rt2_gramLst = (List<String>) StringUtils.getNgramsString(rtString,2,2);

        Set<String> imdb_SetStr = new HashSet<>();
        Set<String> rt_SetStr = new HashSet<>();
        imdb_SetStr.addAll(imdb_2gramLst);
        rt_SetStr.addAll(rt2_gramLst);

        Set<String> union_str = new HashSet<>();
        union_str.addAll(imdb_SetStr);
        union_str.addAll(rt_SetStr);

        union = union_str.size();
        intersection = rt_SetStr.size() + imdb_SetStr.size() - union;

        Jsimillarity = (double) intersection/union;

        return Jsimillarity;
    }

    public void JSimTest()
    {
        String str1 = "i have a pen i have an apple, pen";
        String str2 = "i have a pen i have an apple and pen";
        List<String> str1gram = (List<String>) StringUtils.getNgramsString(str1,2,2);
        List<String> str2gram = (List<String>) StringUtils.getNgramsString(str2,2,2);

        int intersections = 0;
        int union =0;

        Set<String> temp_str1 = new HashSet<>();
        Set<String> temp_str2 = new HashSet<>();

        temp_str1.addAll(str1gram);
        temp_str2.addAll(str2gram);

        Set<String> finalStr = new HashSet<>();
        finalStr.addAll(temp_str1);
        finalStr.addAll(temp_str2);

        union = finalStr.size();
        intersections =  temp_str1.size() + temp_str2.size() - finalStr.size();

        System.out.println(union + " : " + intersections );
        System.out.println((double) intersections/union);
    }

}
