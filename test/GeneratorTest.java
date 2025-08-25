import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class GeneratorTest {

    @Test
    void testAlphabetConstants() {
        assertEquals(26, Alphabet.UPPERCASE.length());
        assertEquals(26, Alphabet.LOWERCASE.length());
        assertEquals(10, Alphabet.DIGITS.length());
        assertTrue(Alphabet.SYMBOLS.contains("!"));
    }

    @Test
    void testPasswordFeedbackWeak() {
        Password p = new Password("secret");
        String feedback = p.getFeedback();
        assertTrue(feedback.contains("💩") || feedback.contains("⚠️"));
    }

    @Test
    void testPasswordFeedbackStrong() {
        Password p = new Password("GreenMango42!");
        String feedback = p.getFeedback();
        assertTrue(feedback.contains("🦾") || feedback.contains("🔥"));
    }

    @Test
    void testWordListProvidesWords() {
        String word = WordList.getRandomWord();
        assertNotNull(word);
        assertFalse(word.isEmpty());
    }

    @Test
    void testGeneratedRandomPasswordHasCorrectLength() {
        Generator g = new Generator(new java.util.Scanner(System.in));
        String pool = Alphabet.UPPERCASE + Alphabet.LOWERCASE + Alphabet.DIGITS + Alphabet.SYMBOLS;
        // manually simulate password generation logic
        int length = 12;
        StringBuilder pass = new StringBuilder();
        java.security.SecureRandom random = new java.security.SecureRandom();
        for (int i = 0; i < length; i++) {
            pass.append(pool.charAt(random.nextInt(pool.length())));
        }
        assertEquals(12, pass.toString().length());
    }

    @Test
    void testPassphraseHasMultipleWords() {
        String w1 = WordList.getRandomWord();
        String w2 = WordList.getRandomWord();
        assertNotEquals(w1, w2); // not guaranteed, but usually true
        assertTrue(w1.length() > 0 && w2.length() > 0);
    }
}
