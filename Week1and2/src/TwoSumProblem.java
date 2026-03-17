import java.util.*;

public class TwoSumProblem {

    static class Transaction {
        int id;
        int amount;
        String merchant;
        String account;
        long time;

        Transaction(int id, int amount, String merchant, String account, long time) {
            this.id = id;
            this.amount = amount;
            this.merchant = merchant;
            this.account = account;
            this.time = time;
        }
    }

    public List<int[]> findTwoSum(List<Transaction> list, int target) {
        Map<Integer, Transaction> map = new HashMap<>();
        List<int[]> result = new ArrayList<>();

        for (Transaction t : list) {
            int complement = target - t.amount;
            if (map.containsKey(complement)) {
                result.add(new int[]{map.get(complement).id, t.id});
            }
            map.put(t.amount, t);
        }
        return result;
    }

    public List<int[]> findTwoSumWithWindow(List<Transaction> list, int target, long windowMillis) {
        List<int[]> result = new ArrayList<>();
        Map<Integer, List<Transaction>> map = new HashMap<>();

        for (Transaction t : list) {
            int complement = target - t.amount;
            if (map.containsKey(complement)) {
                for (Transaction prev : map.get(complement)) {
                    if (Math.abs(t.time - prev.time) <= windowMillis) {
                        result.add(new int[]{prev.id, t.id});
                    }
                }
            }
            map.computeIfAbsent(t.amount, k -> new ArrayList<>()).add(t);
        }
        return result;
    }

    public List<List<Integer>> findKSum(List<Transaction> list, int k, int target) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(list, k, target, 0, new ArrayList<>(), result);
        return result;
    }

    private void backtrack(List<Transaction> list, int k, int target, int start,
                           List<Integer> path, List<List<Integer>> result) {
        if (k == 0 && target == 0) {
            result.add(new ArrayList<>(path));
            return;
        }
        if (k == 0 || target < 0) return;

        for (int i = start; i < list.size(); i++) {
            path.add(list.get(i).id);
            backtrack(list, k - 1, target - list.get(i).amount, i + 1, path, result);
            path.remove(path.size() - 1);
        }
    }

    public List<String> detectDuplicates(List<Transaction> list) {
        Map<String, List<Transaction>> map = new HashMap<>();
        List<String> result = new ArrayList<>();

        for (Transaction t : list) {
            String key = t.amount + "-" + t.merchant;
            map.computeIfAbsent(key, k -> new ArrayList<>()).add(t);
        }

        for (Map.Entry<String, List<Transaction>> entry : map.entrySet()) {
            List<Transaction> group = entry.getValue();
            if (group.size() > 1) {
                Set<String> accounts = new HashSet<>();
                for (Transaction t : group) accounts.add(t.account);
                if (accounts.size() > 1) {
                    result.add(entry.getKey() + " -> " + accounts);
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        TwoSumProblem obj = new TwoSumProblem();

        List<Transaction> list = new ArrayList<>();
        list.add(new Transaction(1, 500, "StoreA", "acc1", System.currentTimeMillis()));
        list.add(new Transaction(2, 300, "StoreB", "acc2", System.currentTimeMillis()));
        list.add(new Transaction(3, 200, "StoreC", "acc3", System.currentTimeMillis()));

        System.out.println(obj.findTwoSum(list, 500));
        System.out.println(obj.findTwoSumWithWindow(list, 500, 3600000));
        System.out.println(obj.findKSum(list, 3, 1000));
        System.out.println(obj.detectDuplicates(list));
    }
}