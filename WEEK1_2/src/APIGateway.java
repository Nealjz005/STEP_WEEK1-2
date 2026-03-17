import java.util.*;
import java.util.concurrent.*;

class TokenBucket {
    private int capacity;
    private int tokens;
    private long lastRefillTime;
    private int refillRate; // tokens per second

    public TokenBucket(int capacity, int refillRate) {
        this.capacity = capacity;
        this.tokens = capacity;
        this.refillRate = refillRate;
        this.lastRefillTime = System.currentTimeMillis();
    }

    // Refill tokens based on time passed
    private synchronized void refill() {
        long now = System.currentTimeMillis();
        long seconds = (now - lastRefillTime) / 1000;

        if (seconds > 0) {
            int newTokens = (int) (seconds * refillRate);
            tokens = Math.min(capacity, tokens + newTokens);
            lastRefillTime = now;
        }
    }

    // Try to consume token
    public synchronized boolean allowRequest() {
        refill();

        if (tokens > 0) {
            tokens--;
            return true;
        }
        return false;
    }

    public int getTokens() {
        refill();
        return tokens;
    }
}

class RateLimiter {
    private Map<String, TokenBucket> userBuckets = new ConcurrentHashMap<>();

    public boolean checkRateLimit(String userId) {
        userBuckets.putIfAbsent(userId, new TokenBucket(1000, 10));
        return userBuckets.get(userId).allowRequest();
    }

    public int getRemainingTokens(String userId) {
        return userBuckets.get(userId).getTokens();
    }
}

public class RateLimiterDemo {
    public static void main(String[] args) {
        RateLimiter limiter = new RateLimiter();
        String user = "abc123";

        for (int i = 1; i <= 5; i++) {
            boolean allowed = limiter.checkRateLimit(user);
            System.out.println("Request " + i + ": " + (allowed ? "ALLOWED" : "BLOCKED"));
        }

        System.out.println("Remaining tokens: " + limiter.getRemainingTokens(user));
    }
}
