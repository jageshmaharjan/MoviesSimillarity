package com.thu.BagOfWordModel.TFIDF_Processing;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by jugs on 3/18/17.
 * Reads the Review File, extracts the features(tokens/lemma) based on PartOfSpeech (POS), and outputs the review with only tokens in a file. The output Format is as:
 * label:: tok_1, tok_2, ..... tok_n
 * Note* -> Choose the POS based on the need Type
 */
public class FeaturesExtractionStanfordNLP
{
    /** Set of POS Annotation as annotated in StanfordCoreNLP Library
     * https://www.ling.upenn.edu/courses/Fall_2003/ling001/penn_treebank_pos.html
      */
    private static final Set<String> ALLOWED = new HashSet<>(Arrays.asList
            ("NN","NNS","NNP","NNPS")); //----All NOUN Form----
//            ("JJ", "JJS", "JJR", "RB", "RBR", "RBS", "UH","VB","WRB","VBD","VBG","VBN","VBP","VBZ"));

    private StanfordCoreNLP pipeline;

    public FeaturesExtractionStanfordNLP()
    {
        Properties prop = new Properties();
        prop.put("annotators", "tokenize, ssplit, pos, lemma");
        pipeline = new StanfordCoreNLP(prop);
    }

    public static void main(String[] args) throws Exception
    {
        FeaturesExtractionStanfordNLP featuresExtractionStanfordNLP = new FeaturesExtractionStanfordNLP();
        featuresExtractionStanfordNLP.program();
    }

    private void program() throws Exception
    {
        //String path = "/home/jugs/IdeaProjects/MoviesSimillarity/IMDBLabelling/ReviewInOneFile.txt";
        String path = "PrafulLabelling/reviewRTWithLabelInOneFile.txt";
        File file = new File(path);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null)
        {
            String[] split = line.split("::\t");
            String classType = split[0];
            List tokens = getTokens(split[1]);
            saveTokensPerDocument(classType, tokens);
        }

    }

    private void saveTokensPerDocument(String classType, List tokens) throws Exception
    {
        FileWriter fw = new FileWriter("PrafulLabelling/reviewOnPOSBasedPrafulLabel.txt",true);
        String tokenString = tokens.toString().replaceAll("\\[","").replaceAll("]","");
        fw.write(classType + "::\t" + tokenString + "\n");
        fw.close();
    }

    private List<String> getTokens(String line)
    {
        List<String> tokensList = new ArrayList<>();
        Annotation document = new Annotation(line);
        pipeline.annotate(document);

//        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
//        for (CoreMap sentence : sentences)
//        {
//            for (CoreLabel tokens : sentence.get(CoreAnnotations.TokensAnnotation.class))
//            {
//                String pos = tokens.get(CoreAnnotations.PartOfSpeechAnnotation.class);
//                String lemma = tokens.get(CoreAnnotations.LemmaAnnotation.class);
//                if (isAllowable(pos))
//                {
//                    tokensList.add(lemma);
//                }
//                System.out.println("POS: " + pos + ", Lemma: " + lemma);
//            }
//        }
        /**
         * Reads the Review per Movie, SentenceAnnotation, TokenAnnotation,
         * PartsOfSpeechAnnotaion, LemmaAnnotatopm are read in the stream, and returns list the applicable lemma.
         */
        return document.get(CoreAnnotations.SentencesAnnotation.class)
                .stream()
                .flatMap(sentence -> sentence.get(CoreAnnotations.TokensAnnotation.class).stream())
                .filter(tokens -> ALLOWED.contains(tokens.get(CoreAnnotations.PartOfSpeechAnnotation.class)))
                .map(tokens -> tokens.get(CoreAnnotations.LemmaAnnotation.class))
                .collect(Collectors.toList());
    }
}
