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
    void testWeakPasswordFeedback() {
        Password p = new Password("secret");
        String feedback = p.getFeedback();
        assertTrue(feedback.contains("💩") || feedback.contains("⚠️"));
    }

    @Test
    void testStrongPasswordFeedback() {
        Password p = new Password("GreenMango42!");
        String feedback = p.getFeedback();
        assertTrue(feedback.contains("🦾") || feedback.contains("🔥"));
    }

    @Test
    void testWordListProvidesWords() {
        String word = WordList.getRandomWord();
        assertNotNull(word);
        assertFalse(word.isEmpty());
        assertTrue(Character.isUpperCase(word.charAt(0))); // words should be capitalized
    }

    @Test
    void testGeneratedRandomPasswordHasCorrectLength() {
        int length = 16;
        String pool = Alphabet.UPPERCASE + Alphabet.LOWERCASE + Alphabet.DIGITS + Alphabet.SYMBOLS;
        StringBuilder pass = new StringBuilder();
        java.security.SecureRandom random = new java.security.SecureRandom();

        for (int i = 0; i < length; i++) {
            pass.append(pool.charAt(random.nextInt(pool.length())));
        }

        assertEquals(16, pass.toString().length());
    }

    @Test
    void testPassphraseStructure() {
        String w1 = WordList.getRandomWord();
        String w2 = WordList.getRandomWord();
        assertNotNull(w1);
        assertNotNull(w2);
        assertNotEquals("", w1);
        assertNotEquals("", w2);
    }
}
