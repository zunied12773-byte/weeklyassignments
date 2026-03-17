import java.util.*;

public class AutocompleteSystem {

    private static class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        Map<String, Integer> freqMap = new HashMap<>();
    }

    private final TrieNode root = new TrieNode();
    private final Map<String, Integer> globalFreq = new HashMap<>();

    public AutocompleteSystem() {
    }

    public void addQuery(String query) {
        globalFreq.put(query, globalFreq.getOrDefault(query, 0) + 1);
        TrieNode node = root;
        for (char c : query.toCharArray()) {
            node.children.putIfAbsent(c, new TrieNode());
            node = node.children.get(c);
            node.freqMap.put(query, globalFreq.get(query));
        }
    }

    public List<String> search(String prefix) {
        TrieNode node = root;
        for (char c : prefix.toCharArray()) {
            if (!node.children.containsKey(c)) return new ArrayList<>();
            node = node.children.get(c);
        }

        PriorityQueue<Map.Entry<String, Integer>> pq =
                new PriorityQueue<>((a, b) -> a.getValue() - b.getValue());

        for (Map.Entry<String, Integer> entry : node.freqMap.entrySet()) {
            pq.offer(entry);
            if (pq.size() > 10) pq.poll();
        }

        List<String> result = new ArrayList<>();
        while (!pq.isEmpty()) result.add(pq.poll().getKey());
        Collections.reverse(result);
        return result;
    }

    public void updateFrequency(String query) {
        addQuery(query);
    }

    public static void main(String[] args) {
        AutocompleteSystem system = new AutocompleteSystem();

        system.addQuery("java tutorial");
        system.addQuery("java tutorial");
        system.addQuery("java stream");
        system.addQuery("java download");
        system.addQuery("javascript basics");

        System.out.println(system.search("jav"));
        system.updateFrequency("java tutorial");
        System.out.println(system.search("jav"));
    }
}