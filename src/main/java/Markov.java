import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Markov {
    private static final String BEGINS_SENTENCE = "__$";
    private String prevWord;
    private HashMap<String, ArrayList<String>> words;
    private static final String PUNCTUATION_MARKS = ".!?$";

    public Markov() {
        words = new HashMap<>;
        words.put(BEGINS_SENTENCE, new ArrayList<>());
        prevWord = BEGINS_SENTENCE;
    }

    public String getSentence() {

    }

    public void addFromFile(String filename) {
            //need bufferedreader for readline?

        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            while ((line = br.readLine()) != null) {
                addLine(line);
            }
        } catch (IOException ioe) {
            System.out.println("Can't access " + filename + ". Error");
        }
    }

    public void addWord(String word) {

    }

    public String randomWord(String word) {

    }

    public String toString() {

    }

    public HashMap<String, ArrayList<String>> getWords() {
        return words;
    }

    public void addLine(String lineIn) {
        if (lineIn == null || lineIn.length() == 0) {
            System.out.println("Cant add line, empty/nonexistent");
            return;
        }



    }

    public static boolean endsWithPunctuation(String input) {

    }

}
