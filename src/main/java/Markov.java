import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


/**
 * Abstract: The Markov class provides a framework by which words or phrases can be passed
 * in through files and picked apart so they can be stitched back together at random,
 * forming new sentences
 * Title: Markov
 * @author William Casey
 */
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

    /**
     * Creates a sentence based on the contents of the hashMap
     * Picks a random starting word branching off of BEGINS_SENTENCE, and
     * then picks follower words until it is stopped by a punctuation mark included
     * in PUNCTUATION_MARKS
     *
     * @return the sentence generated
     */
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

    /**
     * Reads through the given file line by line and adds its contents to the
     * Markov model
     * @param filename the name of the path/file to read
     */
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

    /**
     * Passes a given word through some checks before adding it into the hashMap words
     * then gives prevWord a new value
     * @param word the word to be added
     */
    public void addWord(String word) {
        if (word == null || word.isEmpty()) {
            return;
        }
//        if(!words.containsKey(word)) {
//            words.put(word, new ArrayList<>());
//        }
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

    /**
     * Chooses the next word in the sequence randomly based on the word that is passed in
     * @param word words following this word are chosen at random
     * @return the random word that is chosen by the method
     */
    public String randomWord(String word) {
        Random rand = new Random();
        ArrayList<String> tempList = words.get(word);
        if (tempList == null || tempList.isEmpty()) {
            return null;
        }
        int index = rand.nextInt(tempList.size());
        return tempList.get(index);
    }

    /**
     * Applies the toString method on the hashMap words and returns it promptly
     * @return the hashMap words converted into a string
     */
    public String toString() {
        return words.toString();
    }

    public HashMap<String, ArrayList<String>> getWords() {
        return words;
    }

    /**
     * Adds a single line of text into the Markov model, splits it up into
     * single words for the program to function properly
     * @param lineIn line of text to be added
     */
    public void addLine(String lineIn) {
        if (lineIn == null || lineIn.isEmpty()) {
//            System.out.println("Cant add line, empty/nonexistent");
            return;
        }
        //I can split the line wherever there is a space, good tool to remember
        String[] separatedWords = lineIn.split("\\s+");
        for (int i = 0; i < separatedWords.length; i++) {
            String currentWord = separatedWords[i];

            if (!currentWord.isEmpty()) {
                addWord(currentWord);
            }
        }

    }

    /**
     * Checks if a given word ends in a punctuation, and returns whether or not
     * that is the case
     * @param word word passed in to check if it ends with a punctuation
     * @return true if the word ends with any of the symbols in PUNCTUATION_MARKS
     */
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
