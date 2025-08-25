import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.security.SecureRandom;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Scanner;

public class Generator {
    private final Scanner scanner;
    private final SecureRandom random = new SecureRandom();
    private final Deque<String> history = new ArrayDeque<>();

    public Generator(Scanner scanner) {
        this.scanner = scanner;
    }

    public void mainLoop() {
        System.out.println("🔐 Welcome to Smart Password Advisor!");

        String userOption = "";
        while (!userOption.equals("5")) {
            printMenu();
            userOption = scanner.next();

            switch (userOption) {
                case "1" -> generateRandomPassword();
                case "2" -> generatePassphrase();
                case "3" -> checkPassword();
                case "4" -> showHistory();
                case "5" -> printQuitMessage();
                default -> System.out.println("⚠️ Invalid option. Try again.");
            }
        }
    }

    // === Option 1: Random Strong Password ===
    private void generateRandomPassword() {
        System.out.print("Enter password length (default 12): ");
        int length = getIntInput(12);
        String pool = Alphabet.UPPERCASE + Alphabet.LOWERCASE + Alphabet.DIGITS + Alphabet.SYMBOLS;

        StringBuilder password = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            password.append(pool.charAt(random.nextInt(pool.length())));
        }

        String result = password.toString();
        System.out.println("🔑 Your generated password -> " + result);
        copyToClipboard(result); // NEW
        addToHistory(result);
    }

    // === Option 2: Memorable Passphrase ===
    private void generatePassphrase() {
        System.out.print("Enter number of words (default 4): ");
        int wordCount = getIntInput(4);

        List<String> chosen = new ArrayList<>();
        for (int i = 0; i < wordCount; i++) {
            chosen.add(WordList.getRandomWord());
        }

        int number = random.nextInt(100);
        char symbol = Alphabet.SYMBOLS.charAt(random.nextInt(Alphabet.SYMBOLS.length()));

        String passphrase = String.join("-", chosen) + "-" + number + symbol;
        System.out.println("🔑 Your generated passphrase -> " + passphrase);
        copyToClipboard(passphrase); // NEW
        addToHistory(passphrase);
    }

    // === Option 3: Check Password Strength ===
    private void checkPassword() {
        System.out.print("Enter your password: ");
        String input = scanner.next();
        Password p = new Password(input);
        System.out.println(p.getFeedback());
    }

    // === Option 4: Show History ===
    private void showHistory() {
        if (history.isEmpty()) {
            System.out.println("📭 No passwords generated yet.");
        } else {
            System.out.println("📜 Recently generated passwords:");
            history.forEach(p -> System.out.println(" - " + p));
        }
    }

    // === Clipboard Helper ===
    private void copyToClipboard(String text) {
        try {
            StringSelection selection = new StringSelection(text);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
            System.out.println("📋 (Password copied to clipboard)");
        } catch (HeadlessException e) {
            System.out.println("⚠️ Clipboard not available (headless mode).");
        } catch (Exception e) {
            System.out.println("⚠️ Could not copy to clipboard: " + e.getMessage());
        }
    }

    // === Helpers ===
    private void addToHistory(String password) {
        if (history.size() == 5) history.removeFirst();
        history.addLast(password);
    }

    private void printMenu() {
        System.out.println("""
                
                Menu:
                1 - Generate Random Strong Password
                2 - Generate Memorable Passphrase
                3 - Check Password Strength
                4 - Show Recently Generated Passwords
                5 - Quit
                """);
        System.out.print("Choice: ");
    }

    private void printQuitMessage() {
        System.out.println("👋 Closing the program. Stay safe online!");
    }

    private int getIntInput(int defaultValue) {
        String input = scanner.next();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
