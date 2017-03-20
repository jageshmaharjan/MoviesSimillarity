package thu.ScratchWork.jugs;


import java.util.Properties;

/**
 * Created by jugs on 11/25/16.
 */
public class Stanford
{
    public static void main(String[] args)
    {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");

    }
}
