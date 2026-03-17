import java.util.*;

// LRU Cache using LinkedHashMap
class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private int capacity;

    public LRUCache(int capacity) {
        super(capacity, 0.75f, true); // access-order = true
        this.capacity = capacity;
    }

    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > capacity;
    }
}

// Multi-Level Cache
class MultiLevelCache {
    private LRUCache<String, String> L1;
    private LRUCache<String, String> L2;
    private Map<String, String> L3;

    private int l1Hits = 0, l2Hits = 0, l3Hits = 0;

    public MultiLevelCache() {
        L1 = new LRUCache<>(2);  // small cache
        L2 = new LRUCache<>(5);  // medium cache
        L3 = new HashMap<>();   // large storage

        // Preload L3 (simulate DB)
        L3.put("video1", "Video Data 1");
        L3.put("video2", "Video Data 2");
        L3.put("video3", "Video Data 3");
    }

    public String get(String key) {

        // L1 check
        if (L1.containsKey(key)) {
            l1Hits++;
            System.out.println("L1 HIT");
            return L1.get(key);
        }

        // L2 check
        if (L2.containsKey(key)) {
            l2Hits++;
            System.out.println("L2 HIT → Promoted to L1");
            String value = L2.get(key);
            L1.put(key, value);
            return value;
        }

        // L3 check
        if (L3.containsKey(key)) {
            l3Hits++;
            System.out.println("L3 HIT → Promoted to L2 & L1");
            String value = L3.get(key);
            L2.put(key, value);
            L1.put(key, value);
            return value;
        }

        System.out.println("MISS");
        return null;
    }

    public void put(String key, String value) {
        L1.put(key, value);
        L2.put(key, value);
        L3.put(key, value);
    }

    public void getStats() {
        int total = l1Hits + l2Hits + l3Hits;

        System.out.println("\n--- Cache Stats ---");
        System.out.println("L1 Hits: " + l1Hits);
        System.out.println("L2 Hits: " + l2Hits);
        System.out.println("L3 Hits: " + l3Hits);

        if (total > 0) {
            System.out.println("Overall Hit Rate: " + (100.0 * total / (total + 1)) + "%");
        }
    }

    public static void main(String[] args) {
        MultiLevelCache cache = new MultiLevelCache();

        cache.get("video1"); // L3 → promote
        cache.get("video1"); // L1 hit
        cache.get("video2"); // L3 → promote
        cache.get("video2"); // L1 hit
        cache.get("video3"); // L3 → promote

        cache.getStats();
    }
}
