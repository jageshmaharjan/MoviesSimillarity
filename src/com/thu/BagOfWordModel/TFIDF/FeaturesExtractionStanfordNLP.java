package com.thu.BagOfWordModel.TFIDF;

import edu.stanford.nlp.ling.CoreAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by jugs on 3/18/17.
 */
public class FeaturesExtractionStanfordNLP
{
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
        String path = "/home/jugs/IdeaProjects/MoviesSimillarity/ReviewInOneFile.txt";
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
        FileWriter fw = new FileWriter("reviewOnPOSBased.txt",true);
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

        return document.get(CoreAnnotations.SentencesAnnotation.class)
                .stream()
                .flatMap(sentence -> sentence.get(CoreAnnotations.TokensAnnotation.class).stream())
                .filter(tokens -> ALLOWED.contains(tokens.get(CoreAnnotations.PartOfSpeechAnnotation.class)))
                .map(tokens -> tokens.get(CoreAnnotations.LemmaAnnotation.class))
                .collect(Collectors.toList());
    }
}
