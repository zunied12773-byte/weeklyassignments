import java.util.*;

public class EcommerceFlash {

    public static void main(String[] args) {

        HashMap<String, Integer> stock = new HashMap<>();
        HashMap<String, Queue<Integer>> waitingList = new HashMap<>();

        stock.put("IPHONE15_256GB", 3);

        String product = "IPHONE15_256GB";

        System.out.println(stock.get(product));

        for (int userId = 1; userId <= 5; userId++) {

            if (stock.get(product) > 0) {
                stock.put(product, stock.get(product) - 1);
                System.out.println("Success " + userId + " remaining " + stock.get(product));
            } else {
                waitingList.putIfAbsent(product, new LinkedList<>());
                waitingList.get(product).add(userId);
                System.out.println("Waiting " + userId + " position " + waitingList.get(product).size());
            }
        }

        System.out.println(stock.get(product));
        System.out.println(waitingList.get(product));
    }
}