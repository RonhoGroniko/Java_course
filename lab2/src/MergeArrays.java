public class MergeArrays {
    public static int[] MergeArray(int[] arr1, int[] arr2){

        int[] result = new int[arr1.length + arr2.length];
        int i = 0, j = 0, k = 0;

        while (i < arr1.length && j < arr2.length) {
            if (arr1[i] < arr2[j]) {
                result[k++] = arr1[i++];
            } else {
                result[k++] = arr2[j++];
            }
        }
        while (i < arr1.length) {
            result[k++] = arr1[i++];
        }
        while (j < arr2.length) {
            result[k++] = arr2[j++];
        }
        return result;

    }
    public static int[] BubbleSort(int[] arr){

        int n = arr.length;

        for (int i = 0; i < n - 1; i++)
            for (int j = 0; j < n - i - 1; j++)
                if (arr[j] > arr[j + 1]) {

                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
        return arr;
    }
    public static void main(String[] args) {

        int[] arr1 = {4, 5, 1, 2, 7};
        int[] arr2 = {47, 25, 14, 2, 77};

        int[] sort1 = BubbleSort(arr1);
        int[] sort2 = BubbleSort(arr2);
        int[] result = MergeArray(sort1, sort2);
        for (int j : result) {
            System.out.print(j + " ");
        }

    }
}
