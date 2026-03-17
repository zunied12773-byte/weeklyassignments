import java.util.concurrent.*;

public class DistributedRate {

    private static class TokenBucket {
        private final int maxTokens;
        private final double refillRate;
        private double tokens;
        private long lastRefillTime;

        public TokenBucket(int maxTokens, double refillRate) {
            this.maxTokens = maxTokens;
            this.refillRate = refillRate;
            this.tokens = maxTokens;
            this.lastRefillTime = System.nanoTime();
        }

        private synchronized void refill() {
            long now = System.nanoTime();
            double elapsed = (now - lastRefillTime) / 1e9;
            tokens = Math.min(maxTokens, tokens + elapsed * refillRate);
            lastRefillTime = now;
        }

        public synchronized boolean allowRequest() {
            refill();
            if (tokens >= 1) {
                tokens -= 1;
                return true;
            }
            return false;
        }

        public synchronized int getRemainingTokens() {
            refill();
            return (int) tokens;
        }

        public int getMaxTokens() {
            return maxTokens;
        }
    }

    private final ConcurrentHashMap<String, TokenBucket> buckets = new ConcurrentHashMap<>();
    private final int MAX_TOKENS = 1000;
    private final double REFILL_RATE = 1000.0 / 3600.0;

    private TokenBucket getBucket(String clientId) {
        return buckets.computeIfAbsent(clientId, id -> new TokenBucket(MAX_TOKENS, REFILL_RATE));
    }

    public String checkRateLimit(String clientId) {
        TokenBucket bucket = getBucket(clientId);
        if (bucket.allowRequest()) {
            return "Allowed (" + bucket.getRemainingTokens() + " requests remaining)";
        } else {
            return "Denied (0 requests remaining, retry later)";
        }
    }

    public String getRateLimitStatus(String clientId) {
        TokenBucket bucket = getBucket(clientId);
        return "{used: " + (bucket.getMaxTokens() - bucket.getRemainingTokens()) +
                ", limit: " + bucket.getMaxTokens() +
                ", remaining: " + bucket.getRemainingTokens() + "}";
    }

    public static void main(String[] args) {
        DistributedRate rateLimiter = new DistributedRate();
        String client = "abc123";
        System.out.println(rateLimiter.checkRateLimit(client));
        System.out.println(rateLimiter.checkRateLimit(client));
        System.out.println(rateLimiter.checkRateLimit(client));
        System.out.println(rateLimiter.getRateLimitStatus(client));
    }
}