import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Password {
    private final String value;

    private static final Set<String> WEAK_WORDS = new HashSet<>(Arrays.asList(
            "password", "12345", "123456", "qwerty", "secret", "admin", "welcome", "letmein"
    ));

    public Password(String value) {
        this.value = value;
    }

    private boolean hasUpper() { return value.chars().anyMatch(Character::isUpperCase); }
    private boolean hasLower() { return value.chars().anyMatch(Character::isLowerCase); }
    private boolean hasDigit() { return value.chars().anyMatch(Character::isDigit); }
    private boolean hasSymbol() { return value.chars().anyMatch(c -> !Character.isLetterOrDigit(c)); }

    private boolean containsWeakWord() {
        String lower = value.toLowerCase();
        return WEAK_WORDS.stream().anyMatch(lower::contains);
    }

    public int getStrengthScore() {
        int score = 0;
        if (hasUpper()) score += 2;
        if (hasLower()) score += 2;
        if (hasDigit()) score += 2;
        if (hasSymbol()) score += 2;
        int length = value.length();
        if (length >= 8) score += 1;
        if (length >= 12) score += 1;
        if (containsWeakWord()) score = Math.min(score, 3);
        return Math.min(score, 10);
    }

    private String strengthBar(int score) {
        int max = 10;
        return "[" + "#".repeat(score) + ".".repeat(max - score) + "] " + score + "/" + max;
    }

    public String getFeedback() {
        int score = getStrengthScore();
        StringBuilder feedback = new StringBuilder();

        feedback.append("Password: ").append(value).append("\n");
        feedback.append("Strength: ").append(strengthBar(score)).append("\n");

        if (containsWeakWord()) {
            feedback.append("⚠️ Contains weak word like 'password' or '12345'. Avoid dictionary words.\n");
        }
        if (!hasUpper()) feedback.append("🔼 Add at least one uppercase letter.\n");
        if (!hasLower()) feedback.append("🔽 Add at least one lowercase letter.\n");
        if (!hasDigit()) feedback.append("🔢 Add at least one digit.\n");
        if (!hasSymbol()) feedback.append("💥 Add at least one special symbol (!@#$...).\n");

        if (value.length() < 8) {
            feedback.append("📏 Too short! Use at least 8 characters.\n");
        } else if (value.length() < 12) {
            feedback.append("📏 Decent, but 12+ characters is better.\n");
        }

        if (score <= 3) feedback.append("💩 This will get hacked in seconds.\n");
        else if (score <= 5) feedback.append("😐 Better than '12345', but still risky.\n");
        else if (score <= 8) feedback.append("🔥 Solid password. Would take years to crack.\n");
        else feedback.append("🦾 Even Thanos can’t crack this.\n");

        return feedback.toString();
    }

    @Override
    public String toString() {
        return value;
    }
}
