import java.util.*;

public class RealTimeAnalytics {

    public static void main(String[] args) {

        HashMap<String, Integer> pageViews = new HashMap<>();
        HashMap<String, Set<String>> uniqueVisitors = new HashMap<>();
        HashMap<String, Integer> sourceCount = new HashMap<>();

        String[][] events = {
                {"/article/breaking-news", "user1", "google"},
                {"/article/breaking-news", "user2", "facebook"},
                {"/sports/championship", "user3", "google"},
                {"/article/breaking-news", "user1", "direct"},
                {"/sports/championship", "user4", "google"},
                {"/sports/championship", "user5", "facebook"}
        };

        for (String[] event : events) {
            String url = event[0];
            String user = event[1];
            String source = event[2];

            pageViews.put(url, pageViews.getOrDefault(url, 0) + 1);

            uniqueVisitors.putIfAbsent(url, new HashSet<>());
            uniqueVisitors.get(url).add(user);

            sourceCount.put(source, sourceCount.getOrDefault(source, 0) + 1);
        }

        List<Map.Entry<String, Integer>> list = new ArrayList<>(pageViews.entrySet());
        list.sort((a, b) -> b.getValue() - a.getValue());

        System.out.println("Top Pages:");
        int count = 0;
        for (Map.Entry<String, Integer> entry : list) {
            String url = entry.getKey();
            int views = entry.getValue();
            int unique = uniqueVisitors.get(url).size();

            System.out.println(url + " - " + views + " views (" + unique + " unique)");

            count++;
            if (count == 2) break;
        }

        System.out.println("Traffic Sources:");
        for (String src : sourceCount.keySet()) {
            System.out.println(src + " : " + sourceCount.get(src));
        }
    }
}