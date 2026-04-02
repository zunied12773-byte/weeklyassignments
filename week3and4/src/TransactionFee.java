import java.util.*;

public class TransactionFee {

    static class Transaction {
        String id;
        double fee;
        String timestamp;

        Transaction(String id, double fee, String timestamp) {
            this.id = id;
            this.fee = fee;
            this.timestamp = timestamp;
        }
    }

    static void bubbleSort(ArrayList<Transaction> list) {
        int n = list.size();
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (list.get(j).fee > list.get(j + 1).fee) {
                    Transaction temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }

    static void insertionSort(ArrayList<Transaction> list) {
        int n = list.size();
        for (int i = 1; i < n; i++) {
            Transaction key = list.get(i);
            int j = i - 1;
            while (j >= 0 && (list.get(j).fee > key.fee ||
                    (list.get(j).fee == key.fee &&
                            list.get(j).timestamp.compareTo(key.timestamp) > 0))) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, key);
        }
    }

    static ArrayList<Transaction> highFee(ArrayList<Transaction> list) {
        ArrayList<Transaction> res = new ArrayList<>();
        for (Transaction t : list) {
            if (t.fee > 50) res.add(t);
        }
        return res;
    }

    public static void main(String[] args) {
        ArrayList<Transaction> list = new ArrayList<>();
        list.add(new Transaction("id1", 10.5, "10:00"));
        list.add(new Transaction("id2", 25.0, "09:30"));
        list.add(new Transaction("id3", 5.0, "10:15"));

        if (list.size() <= 100) {
            bubbleSort(list);
        } else if (list.size() <= 1000) {
            insertionSort(list);
        }

        for (Transaction t : list) {
            System.out.print(t.id + ":" + t.fee + " ");
        }

        System.out.println();

        insertionSort(list);
        for (Transaction t : list) {
            System.out.print(t.id + ":" + t.fee + "@" + t.timestamp + " ");
        }

        System.out.println();

        ArrayList<Transaction> outliers = highFee(list);
        if (outliers.isEmpty()) {
            System.out.println("none");
        } else {
            for (Transaction t : outliers) {
                System.out.print(t.id + " ");
            }
        }
    }
}