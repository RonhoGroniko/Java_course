public class MaxRowElement {
    public static int[] maxRowElementsArray(int[][] arr){
        int rows = arr.length;
        int cols = arr[0].length;

        int[] max_arr = new int[rows];
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                max = Math.max(arr[i][j], max);
            }
            max_arr[i] = max;
        }
        return max_arr;
    }

    public static void main(String[] args) {
        int[][] matrix = {{1, 2, 3},
                    {4, 5, 6},
                    {7, 8, 9}};
        for (int i : maxRowElementsArray(matrix)) {
            System.out.print(i + " ");
        }
    }

}
