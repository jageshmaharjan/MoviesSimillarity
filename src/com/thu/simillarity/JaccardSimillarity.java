package com.thu.simillarity;

import com.thu.simillarity.IMDBJavaObjectCode.ImdbCom;
import com.thu.simillarity.IMDBPackage.ReadIMDBXMLFile;
import com.thu.simillarity.RottenTomatoesJavaObjectCode.RottentomatoesCom;
import com.thu.simillarity.RottenTomatoesPackage.ReadRottenTomatoesXMLFile;
import edu.stanford.nlp.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by jugs on 10/10/16.
 */
public class JaccardSimillarity
{
    public static void main(String[] args)
    {
        JaccardSimillarity jacsim = new JaccardSimillarity();
//        jacsim.getData();

    }

    public void getData()
    {
        ImdbCom imdbData = ReadIMDBXMLFile.getIMDBData();
        RottentomatoesCom rtData = ReadRottenTomatoesXMLFile.getRotttenTomatoesData();


        for (ImdbCom.IMDB imdbMovie : imdbData.getIMDB())
        {
            for (RottentomatoesCom.RottenTomatoes rtMovie : rtData.getRottenTomatoes())
            {
                try
                {
                    double jaccardSimTitle = compareString(imdbMovie.getTitle() , rtMovie.getTitle());
                    if (jaccardSimTitle > 0.8)
                        System.out.println(jaccardSimTitle);
//                    compareString(imdbMovie.getActors().getActor() , rtMovie.getActors().getActor());
//                    compareString(imdbMovie.getDrectors().getDirector(), rtMovie.getDirectors().getDirector());
//                    compareString(imdbMovie.getDuration(),rtMovie.getDuration());
//                    compareString(imdbMovie.getGenres().getGenre(), rtMovie.getGenres().getGenre());
//                    compareString(imdbMovie.getReleaseDate(), rtMovie.getReleaseDate());

                }
                catch (Exception e)
                {
                    System.out.println(e);
                }
            }
        }
    }

    private double compareString(List<String> imdbRecords, List<String> rtRecords)
    {
        String stringIMDB = "";
        String stringRT = "";
        for (String actor : imdbRecords)
        {
            stringIMDB += actor;
        }

        for (String actor : rtRecords)
        {
            stringRT += actor;
        }
        double jaccardSim = compareString(stringIMDB,stringRT);

        return jaccardSim;
    }


    private double compareString(String imdbString , String rtString)
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

        System.out.println("union : " + union + " intersection : " + intersection);

        Jsimillarity = (double) intersection/union;

        return Jsimillarity;
    }

    public void JSimTest()
    {
        String str1 = "i have a pen i have an apple";
        String str2 = "i have a pen i have an apple pen";
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
