package com.thu.simillarity;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jugs on 3/1/17.
 */
public class ReviewsIntegrationSentenceWise
{
    private int index = 0;
    public static void main(String[] args) throws Exception
    {
        ReviewsIntegrationSentenceWise revPerSentence = new ReviewsIntegrationSentenceWise();
        revPerSentence.mergeImdbRottenTomatoes();
    }

    private void mergeImdbRottenTomatoes() throws Exception
    {
        Set<String> sentenceSet = new HashSet<>();
        String path = "ReviewSentences";
        File dir = new File(path);
        for (String file : dir.list())
        {
            File f = new File(path+"/"+file);
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            while ((line = br.readLine()) != null)
            {
                sentenceSet.add(line);
            }
        }
        saveFullReview(sentenceSet);
    }

    private void saveFullReview(Set<String> sentenceSet) throws Exception
    {
        String path = "ReviewSentences";
        for (String sentence : sentenceSet)
        {
            FileWriter fileWriter = new FileWriter(path + "/" + "fullReviews.txt",true);
            fileWriter.write(index + "\t" +  sentence + "\n\n");
            index++;
            fileWriter.close();
        }
    }
}
