package com.thu.simillarity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

/**
 * Created by jugs on 3/2/17.
 */
//----This class reads all the review sentences of IMDb and RottenTomaotoes, filters the sentences of size less than 200, and randomly selects the 5500 sentences from both domain, and merge into single review file
public class SentenceDetection
{
    public static void main(String[] args) throws Exception
    {

        SentenceDetection sentenceDetection = new SentenceDetection();
        List<String> imdb_sentences = sentenceDetection.sentenceRandomSelection_imdb();
        List<String> rt_sentences = sentenceDetection.sentenceRandomSelection_rt();
        sentenceDetection.FinalizeSentence(imdb_sentences,rt_sentences);

    }

    private void FinalizeSentence(List<String> imdb_sentences, List<String> rt_sentences) throws Exception
    {
        List<String> allSentences = new ArrayList<>();
        for (String sentence : imdb_sentences)
        {
            allSentences.add(sentence);
        }
        for (String sentence : rt_sentences)
        {
            allSentences.add(sentence);
        }
        System.out.println();
        int index = 0;
        String path = "ReviewSentences";
        for (String sentence : allSentences)
        {
            FileWriter fw = new FileWriter(path+ "/" +"FilteredReview.txt",true);
            fw.write(index + "\t" + sentence + "\n\n");
            index++;
            fw.close();
        }
    }

    private List<String> sentenceRandomSelection_rt() throws Exception
    {
        File file = new File("sortedSentences_rt.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        List<String> sentenceList = new ArrayList<>();
        while ((line = br.readLine()) != null)
        {
            sentenceList.add(line);
        }

        Random random = new Random();
        List<String> rtPickedSentences = new ArrayList<>();
        int index = 0;
        while (index < 5500)
        {
            String randomSentence = sentenceList.get(random.nextInt(sentenceList.size()));
            index++;
            rtPickedSentences.add(randomSentence);
        }
        return rtPickedSentences;

    }

    public void sentenceOrdering() throws Exception
    {
        int longsentence = 0;
        File file = new File("ReviewSentences/RT_reviews.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        Set<String> sentences = new TreeSet<>();
        while ((line = br.readLine()) != null)
        {
            if (line.length() < 200)
            {
                sentences.add(line);
            }
        }

        for (String sentence : sentences)
        {
            FileWriter fw = new FileWriter("sortedSentences_rt.txt", true);
            fw.write(sentence + "\n");
            fw.close();
        }
        System.out.println(sentences.size());
    }

    public List<String> sentenceRandomSelection_imdb() throws Exception
    {
        File file = new File("sortedSentences_imbd.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        List<String> sentenceList  = new ArrayList();
        while ((line = br.readLine()) != null)
        {
            sentenceList.add(line);
        }
        Random random = new Random();
        List<String> imdbPickedSentences = new ArrayList<>();
        int index = 0;
        while (index < 5500)
        {
            String randomsent = sentenceList.get(random.nextInt(sentenceList.size()));
            index++;
            imdbPickedSentences.add(randomsent);
        }
        return imdbPickedSentences;
    }
}
