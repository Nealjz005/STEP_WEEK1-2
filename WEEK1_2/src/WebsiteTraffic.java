import java.util.*;

class AnalyticsSystem {

    // Page → total views
    private Map<String, Integer> pageViews = new HashMap<>();

    // Page → unique users
    private Map<String, Set<String>> uniqueUsers = new HashMap<>();

    // Source → clicks
    private Map<String, Integer> sourceClicks = new HashMap<>();

    // Process event
    public void processEvent(String page, String user, String source) {

        // Count page views
        pageViews.put(page, pageViews.getOrDefault(page, 0) + 1);

        // Track unique users
        uniqueUsers.putIfAbsent(page, new HashSet<>());
        uniqueUsers.get(page).add(user);

        // Count source clicks
        sourceClicks.put(source, sourceClicks.getOrDefault(source, 0) + 1);
    }

    // Display analytics
    public void displayStats() {
        System.out.println("\n--- Page Views ---");
        for (String page : pageViews.keySet()) {
            System.out.println(page + " → " + pageViews.get(page));
        }

        System.out.println("\n--- Unique Users ---");
        for (String page : uniqueUsers.keySet()) {
            System.out.println(page + " → " + uniqueUsers.get(page).size());
        }

        System.out.println("\n--- Source Clicks ---");
        for (String src : sourceClicks.keySet()) {
            System.out.println(src + " → " + sourceClicks.get(src));
        }
    }

    // Top N pages
    public void topPages(int n) {
        System.out.println("\n--- Top " + n + " Pages ---");

        pageViews.entrySet().stream()
                .sorted((a, b) -> b.getValue() - a.getValue())
                .limit(n)
                .forEach(e -> System.out.println(e.getKey() + " → " + e.getValue()));
    }

    public static void main(String[] args) {
        AnalyticsSystem system = new AnalyticsSystem();

        // Simulated events
        system.processEvent("/article/breaking-news", "user1", "google");
        system.processEvent("/article/breaking-news", "user2", "facebook");
        system.processEvent("/sports/cricket", "user1", "google");
        system.processEvent("/sports/cricket", "user3", "direct");
        system.processEvent("/article/breaking-news", "user1", "google");

        system.displayStats();
        system.topPages(2);
    }
}