import java.util.*;

public class SocialMedia {

    public static void main(String[] args) {

        HashMap<String, Integer> users = new HashMap<>();
        HashMap<String, Integer> frequency = new HashMap<>();

        users.put("john_doe", 1);
        users.put("admin", 2);

        String username1 = "john_doe";
        frequency.put(username1, frequency.getOrDefault(username1, 0) + 1);
        System.out.println(!users.containsKey(username1));

        String username2 = "jane_smith";
        frequency.put(username2, frequency.getOrDefault(username2, 0) + 1);
        System.out.println(!users.containsKey(username2));

        String name = "john_doe";
        List<String> suggestions = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            suggestions.add(name + i);
        }
        suggestions.add(name.replace("_", "."));
        System.out.println(suggestions);

        frequency.put("admin", frequency.getOrDefault("admin", 0) + 1);
        frequency.put("admin", frequency.getOrDefault("admin", 0) + 1);
        frequency.put("john_doe", frequency.getOrDefault("john_doe", 0) + 1);

        String maxUser = "";
        int maxCount = 0;
        for (String user : frequency.keySet()) {
            if (frequency.get(user) > maxCount) {
                maxCount = frequency.get(user);
                maxUser = user;
            }
        }

        System.out.println(maxUser + " (" + maxCount + " attempts)");
    }
}