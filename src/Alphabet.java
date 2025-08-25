public class Alphabet {

    public static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    public static final String DIGITS    = "0123456789";
    public static final String SYMBOLS   = "!@#$%^&*()-_=+\\/~?";

    private final StringBuilder pool;

    public Alphabet(boolean useUpper, boolean useLower, boolean useDigits, boolean useSymbols) {
        pool = new StringBuilder();

        if (useUpper) pool.append(UPPERCASE);
        if (useLower) pool.append(LOWERCASE);
        if (useDigits) pool.append(DIGITS);
        if (useSymbols) pool.append(SYMBOLS);

        if (pool.length() == 0) {
            throw new IllegalArgumentException("⚠️ At least one character set must be selected.");
        }
    }

    public String getCharacters() {
        return pool.toString();
    }
}
