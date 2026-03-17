import java.util.*;

class DNSRecord {
    String ip;
    long expiryTime;

    DNSRecord(String ip, long ttlMillis) {
        this.ip = ip;
        this.expiryTime = System.currentTimeMillis() + ttlMillis;
    }

    boolean isExpired() {
        return System.currentTimeMillis() > expiryTime;
    }
}

class DNSCache {
    private final Map<String, DNSRecord> cache = new HashMap<>();
    private int cacheHits = 0;
    private int cacheMisses = 0;

    // Simulated DNS lookup
    private String fetchFromDNS(String domain) {
        return "172.217.14." + new Random().nextInt(255);
    }

    public String resolve(String domain, long ttlMillis) {
        DNSRecord record = cache.get(domain);

        if (record != null && !record.isExpired()) {
            cacheHits++;
            System.out.println(domain + " → Cache HIT: " + record.ip);
            return record.ip;
        }

        cacheMisses++;
        String newIP = fetchFromDNS(domain);
        cache.put(domain, new DNSRecord(newIP, ttlMillis));

        System.out.println(domain + " → Cache MISS. New IP: " + newIP);
        return newIP;
    }

    public void printStats() {
        System.out.println("Cache Hits: " + cacheHits);
        System.out.println("Cache Misses: " + cacheMisses);
    }
}

public class DNSCacheDemo {
    public static void main(String[] args) throws InterruptedException {
        DNSCache dnsCache = new DNSCache();

        String domain = "google.com";

        // First request → MISS
        dnsCache.resolve(domain, 3000);

        Thread.sleep(1000);

        // Second request → HIT
        dnsCache.resolve(domain, 3000);

        Thread.sleep(3000);

        // Third request → EXPIRED → MISS
        dnsCache.resolve(domain, 3000);

        dnsCache.printStats();
    }
}