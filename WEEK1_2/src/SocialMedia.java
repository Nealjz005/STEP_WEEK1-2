import java.util.*;

class UsernameChecker {

    private HashMap<String, Integer> userMap;     // username -> userId
    private HashMap<String, Integer> attempts;    // username -> attempt count
    private int userIdCounter;

    public UsernameChecker() {
        userMap = new HashMap<>();
        attempts = new HashMap<>();
        userIdCounter = 1;
    }

    // Register a username
    public void register(String username) {
        userMap.put(username, userIdCounter++);
    }

    // Check availability in O(1)
    public boolean checkAvailability(String username) {
        attempts.put(username, attempts.getOrDefault(username, 0) + 1);
        return !userMap.containsKey(username);
    }

    // Suggest alternatives
    public List<String> suggestAlternatives(String username) {
        List<String> suggestions = new ArrayList<>();

        // append numbers
        for (int i = 1; i <= 3; i++) {
            String newName = username + i;
            if (!userMap.containsKey(newName)) {
                suggestions.add(newName);
            }
        }

        // replace underscore with dot
        if (username.contains("_")) {
            String alt = username.replace("_", ".");
            if (!userMap.containsKey(alt)) {
                suggestions.add(alt);
            }
        }

        return suggestions;
    }

    // Get most attempted username
    public String getMostAttempted() {
        String maxUser = null;
        int maxCount = 0;

        for (Map.Entry<String, Integer> entry : attempts.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                maxUser = entry.getKey();
            }
        }

        return maxUser + " (" + maxCount + " attempts)";
    }
}

public class Main {
    public static void main(String[] args) {
        UsernameChecker system = new UsernameChecker();

        // Pre-existing users
        system.register("john_doe");
        system.register("admin");

        // Check availability
        System.out.println("john_doe -> " + system.checkAvailability("john_doe"));
        System.out.println("jane_smith -> " + system.checkAvailability("jane_smith"));

        // Suggestions
        System.out.println("Suggestions for john_doe: " + system.suggestAlternatives("john_doe"));

        // Simulate attempts
        system.checkAvailability("admin");
        system.checkAvailability("admin");
        system.checkAvailability("admin");

        // Most attempted
        System.out.println("Most attempted: " + system.getMostAttempted());
    }
}