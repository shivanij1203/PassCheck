import java.io.*;
import java.util.*;

public class WordList {
    private static final List<String> WORDS = new ArrayList<>();
    private static final Random random = new Random();

    static {
        try (BufferedReader br = new BufferedReader(new FileReader("src/words.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String word = line.trim();
                if (!word.isEmpty()) {
                    WORDS.add(Character.toUpperCase(word.charAt(0)) + word.substring(1));
                }
            }
        } catch (IOException e) {
            System.err.println("⚠️ Could not load words.txt. Falling back to defaults.");
            WORDS.addAll(Arrays.asList("Sunset", "Banana", "Dragon", "Rocket", "Tiger"));
        }
    }

    public static String getRandomWord() {
        return WORDS.get(random.nextInt(WORDS.size()));
    }
}

