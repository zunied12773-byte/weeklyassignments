import java.util.*;

public class RiskThreshold {

    static int linearSearch(int[] arr, int target) {
        int comparisons = 0;
        for (int i = 0; i < arr.length; i++) {
            comparisons++;
            if (arr[i] == target) {
                System.out.println("Found at index " + i + " Comparisons: " + comparisons);
                return i;
            }
        }
        System.out.println("Not found Comparisons: " + comparisons);
        return -1;
    }

    static int insertionPoint(int[] arr, int target) {
        int low = 0, high = arr.length - 1;
        int comparisons = 0;
        int ans = arr.length;

        while (low <= high) {
            int mid = (low + high) / 2;
            comparisons++;
            if (arr[mid] >= target) {
                ans = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        System.out.println("Insertion Index: " + ans + " Comparisons: " + comparisons);
        return ans;
    }

    static int floor(int[] arr, int target) {
        int low = 0, high = arr.length - 1;
        int res = -1;
        int comparisons = 0;

        while (low <= high) {
            int mid = (low + high) / 2;
            comparisons++;
            if (arr[mid] <= target) {
                res = arr[mid];
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        System.out.println("Floor: " + res + " Comparisons: " + comparisons);
        return res;
    }

    static int ceiling(int[] arr, int target) {
        int low = 0, high = arr.length - 1;
        int res = -1;
        int comparisons = 0;

        while (low <= high) {
            int mid = (low + high) / 2;
            comparisons++;
            if (arr[mid] >= target) {
                res = arr[mid];
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        System.out.println("Ceiling: " + res + " Comparisons: " + comparisons);
        return res;
    }

    public static void main(String[] args) {
        int[] risks = {10, 25, 50, 100};

        linearSearch(risks, 30);

        insertionPoint(risks, 30);
        floor(risks, 30);
        ceiling(risks, 30);
    }
}