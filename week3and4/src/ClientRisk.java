import java.util.*;

public class ClientRisk {

    static class Client {
        String id;
        int riskScore;
        double accountBalance;

        Client(String id, int riskScore, double accountBalance) {
            this.id = id;
            this.riskScore = riskScore;
            this.accountBalance = accountBalance;
        }
    }

    static void bubbleSort(Client[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j].riskScore > arr[j + 1].riskScore) {
                    Client temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    static void insertionSort(Client[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            Client key = arr[i];
            int j = i - 1;
            while (j >= 0 && (arr[j].riskScore < key.riskScore ||
                    (arr[j].riskScore == key.riskScore &&
                            arr[j].accountBalance < key.accountBalance))) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    static void topRisk(Client[] arr, int k) {
        for (int i = 0; i < k && i < arr.length; i++) {
            System.out.print(arr[i].id + "(" + arr[i].riskScore + ") ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Client[] arr = {
                new Client("clientC", 80, 5000),
                new Client("clientA", 20, 2000),
                new Client("clientB", 50, 3000)
        };

        bubbleSort(arr);
        for (Client c : arr) {
            System.out.print(c.id + ":" + c.riskScore + " ");
        }

        System.out.println();

        insertionSort(arr);
        for (Client c : arr) {
            System.out.print(c.id + ":" + c.riskScore + " ");
        }

        System.out.println();

        topRisk(arr, 10);
    }
}