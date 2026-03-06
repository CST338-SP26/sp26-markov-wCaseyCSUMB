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
        String currentString = randomWord(BEGINS_SENTENCE);

        StringBuilder sb = new StringBuilder();
        while (true) {
            sb.append(currentString);

            if (endsWithPunctuation(currentString)) {
                break;
            }
            sb.append(" ");
            currentString = randomWord(currentString);

            if (currentString == null) {
                break;
            }
        }

        return sb.toString();
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
        return words.toString();
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

    public static boolean endsWithPunctuation(String word) {
        try {
            if (word == null || word.isEmpty()) {
                return false;
            }
            char lastLetter = word.charAt(word.length() - 1);
                if (PUNCTUATION_MARKS.indexOf(lastLetter) >= 0) {
                    return true;
                } else {
                    return false;
                }
        } catch (Exception e) {
            System.out.println("endsWithPunctuation catch exception");
            return false;
        }
    }

}
