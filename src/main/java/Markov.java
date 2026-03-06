import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Markov {
    private static final String BEGINS_SENTENCE = "__$";
    private String prevWord;
    private HashMap<String, ArrayList<String>> words; //instructions say not final, don't change for warning
    private static final String PUNCTUATION_MARKS = ".!?$";

    public Markov() {
        words = new HashMap<>();
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
        if (word == null || word.isEmpty()) {
            return;
        }
        if(!words.containsKey(word)) {
            words.put(word, new ArrayList<>());
        }
        //not sure if this is fully necessary, check back
        if (endsWithPunctuation(prevWord)) {
            words.get(BEGINS_SENTENCE).add(word);
            prevWord = word;
            return;
        }
        if(!words.containsKey(prevWord)) {
            words.put(prevWord, new ArrayList<>());
        }
        words.get(prevWord).add(word);
        prevWord = word;
    }

    public String randomWord(String word) {
        Random rand = new Random();
        ArrayList<String> tempList = words.get(word);
        if (tempList == null || tempList.isEmpty()) {
            return null;
        }
        int index = rand.nextInt(tempList.size());
        return tempList.get(index);
    }

    public String toString() {
        return words.toString();
    }

    public HashMap<String, ArrayList<String>> getWords() {
        return words;
    }

    public void addLine(String lineIn) {
        if (lineIn == null || lineIn.isEmpty()) {
//            System.out.println("Cant add line, empty/nonexistent");
            return;
        }
        //I can split the line wherever there is a space, good tool to remember
        String[] separatedWords = lineIn.split(" ");
        for (int i = 0; i < separatedWords.length; i++) {
            String currentWord = separatedWords[i];

            if (!currentWord.isEmpty()) {
                addWord(currentWord);
            }
        }

    }

    public static boolean endsWithPunctuation(String word) {
        try {
            if (word == null || word.isEmpty()) {
                return false;
            }
            char lastLetter = word.charAt(word.length() - 1);
            return PUNCTUATION_MARKS.indexOf(lastLetter) >= 0;
        } catch (Exception e) {
            System.out.println("endsWithPunctuation catch exception");
            return false;
        }
    }

}
