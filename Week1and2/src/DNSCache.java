import java.util.*;

public class DNSCache {

    static class Entry {
        String ip;
        long expiry;

        Entry(String ip, long ttl) {
            this.ip = ip;
            this.expiry = System.currentTimeMillis() + ttl;
        }
    }

    public static void main(String[] args) throws InterruptedException {

        HashMap<String, Entry> cache = new HashMap<>();

        int hits = 0, misses = 0;

        String domain = "google.com";

        if (cache.containsKey(domain) && cache.get(domain).expiry > System.currentTimeMillis()) {
            System.out.println("Cache HIT: " + cache.get(domain).ip);
            hits++;
        } else {
            System.out.println("Cache MISS: querying...");
            String ip = "172.217.14.206";
            cache.put(domain, new Entry(ip, 3000));
            System.out.println("Stored: " + ip);
            misses++;
        }

        if (cache.containsKey(domain) && cache.get(domain).expiry > System.currentTimeMillis()) {
            System.out.println("Cache HIT: " + cache.get(domain).ip);
            hits++;
        }

        Thread.sleep(3100);

        if (cache.containsKey(domain) && cache.get(domain).expiry > System.currentTimeMillis()) {
            System.out.println("Cache HIT: " + cache.get(domain).ip);
            hits++;
        } else {
            System.out.println("Cache EXPIRED: querying...");
            String ip = "172.217.14.207";
            cache.put(domain, new Entry(ip, 3000));
            System.out.println("Updated: " + ip);
            misses++;
        }

        int total = hits + misses;
        double hitRate = (total == 0) ? 0 : (hits * 100.0 / total);

        System.out.println("Hit Rate: " + hitRate + "%");
    }
}