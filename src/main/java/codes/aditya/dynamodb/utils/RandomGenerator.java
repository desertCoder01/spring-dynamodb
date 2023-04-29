package codes.aditya.dynamodb.utils;

import java.util.Random;

public class RandomGenerator {

    private static final String BASE_EMAIL = "example";

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final String[] DOMAINS = { "gmail.com", "yahoo.com", "hotmail.com", "outlook.com" };

    private static final Random RANDOM = new Random();

    public static String randomEmailAddress() {
        int randomInt = RANDOM.nextInt(100000);
        String domain = DOMAINS[RANDOM.nextInt(DOMAINS.length)];
        return BASE_EMAIL + randomInt + "@" + domain;
    }

    public static String generateRandomString() {
        int length = 8;
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(index);
            sb.append(randomChar);
        }
        return sb.toString();
    }
}
