import java.io.IOException;
import java.io.StringReader;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import org.apache.lucene.analysis.en.EnglishAnalyzer;

import java.util.*;

public class StemTest {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        while (true) {
            String str = sc.nextLine();
            System.out.println(lucy(str));
        }
    }

    public static String lucy(String str) {
        try {
            StringReader reader = new StringReader(str);
            WhitespaceTokenizer token = new WhitespaceTokenizer();
            token.setReader(reader);

            TokenStream tokenStream = new StopFilter(token, StopAnalyzer.ENGLISH_STOP_WORDS_SET);
            if(Miner.PORTER)
            {
                tokenStream = new PorterStemFilter(tokenStream);
            }            
            // tokenStream = new KStemFilter(tokenStream);

            final CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
            tokenStream.reset();

            StringBuilder sb = new StringBuilder();

            while (tokenStream.incrementToken()) {
                sb.append(charTermAttribute.toString() + " ");
            }

            tokenStream.end();
            tokenStream.close();
            return sb.toString();
        } catch (Exception e) {

        }
        return str;
    }

}

/*
String theSentence = "I Belived this to be really 45434^&^& stupid #TEH# but the ketchup is good 232 4434".toLowerCase();
        StringReader reader = new StringReader(theSentence);

        WhitespaceTokenizer wst = new WhitespaceTokenizer();
        wst.setReader(reader);



        // Tokenizer whitespaceTokenizer = new Tokenizer();
        // whitewhitespaceTokenizer.setReader(reader);
        TokenStream tokenStream = new StopFilter(wst, StopAnalyzer.ENGLISH_STOP_WORDS_SET);
        // tokenStream = new PorterStemFilter(tokenStream);

        final CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
        tokenStream.reset();

        while (tokenStream.incrementToken()) {
            System.out.println(charTermAttribute.toString());
        }

        tokenStream.end();
        tokenStream.close();

        */