package edu.penzgtu;

public class TimSortApplication {
    public static void main(String[] args) {
        int[] arr = { -2, 7,  15,  -14, 0, 15,  0, 7,
            -7, -4, -13, 5,   8, -14, 12 };
        int n = arr.length;

        System.out.println("Given Array is");
        printArray(arr, n);

        TimSort.timSort(arr, n);

        System.out.println("After Sorting Array is");
        printArray(arr, n);
    }

    // Utility function to print the Array
    private static void printArray(int[] arr, int n)
    {
        for (int i = 0; i < n; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.print("\n");
    }
}
