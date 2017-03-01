package com.thu.simillarity.IMDBPackage;


import com.thu.simillarity.IMDBJavaObjectCode.ImdbCom;
import com.thu.simillarity.RT_Package.ReadRT_XMLFile;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * Created by jugs on 10/10/16.
 */
public class ReadIMDBXMLFile
{
    private StanfordCoreNLP pipeline;
    public ReadIMDBXMLFile()
    {
        Properties prop = new Properties();
        prop.put("annotators", "tokenize,ssplit");
        pipeline = new StanfordCoreNLP(prop);
    }
    public static void main(String[] args)
    {
        ReadIMDBXMLFile readIMDBXMLFile = new ReadIMDBXMLFile();
        ImdbCom imdb = readIMDBXMLFile.getIMDBData();
        System.out.println();
    }

    public ImdbCom getIMDBData()
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
//                String storyLine = imdbCom.getIMDB().get(i).getStoryLine();
                String review = null;
                if (!imdbCom.getIMDB().get(i).getReviews().isEmpty()) //.split("– See all my reviews")[1].equals(null)))
                {
                    if (imdbCom.getIMDB().get(i).getReviews().split("– See all my reviews").length > 1)
                    {
                        review = imdbCom.getIMDB().get(i).getReviews().split("– See all my reviews")[1].split("[0-9]+\\s\\w+\\s[0-9]+\\s[p\\e+]")[0];
                        //System.out.println(imdbCom.getIMDB().get(i).getReviews().split("– See all my reviews")[1] + "\n");
                        getReviews(review);
                    }
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return imdbCom;
    }

    private void getReviews(String review) throws Exception
    {
        Annotation document = new Annotation(review);
        pipeline.annotate(document);
        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
        Set<String> sentenceSet = new HashSet<>();

        for (CoreMap sentence : sentences)
        {
            sentenceSet.add(sentence.toString());
        }
        saveToFile(sentenceSet);
    }

    private void saveToFile(Set<String> sentenceSet) throws Exception
    {
        String path = "ReviewSentences";
        for (String sentence : sentenceSet)
        {
            FileWriter fw = new FileWriter(path+"/"+"IMDB_Reviews.txt", true);
            fw.write( sentence + "\n");
            fw.close();
            System.out.println(sentence);
        }
    }
}
