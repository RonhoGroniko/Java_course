public class MaxSumSubarray {

    public static int maxSumSubarray(int[] arr){

        int maxSum = Integer.MIN_VALUE;
        int currentSum = 0;

        for (int i : arr) {
            currentSum = Math.max(i, currentSum + i);
            maxSum = Math.max(maxSum, currentSum);
        }
        return maxSum;
    }

    public static void main(String[] args) {

        int[] array = {-8, 9, 1, -7, 8, 9};
        System.out.print(maxSumSubarray(array));
    }

}
