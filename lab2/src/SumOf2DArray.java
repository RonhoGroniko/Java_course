public class SumOf2DArray {
    public static int sumOf2DArray(int[][] arr){

        int rows = arr.length;
        int cols = arr[0].length;
        int sum = 0;

        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                sum += arr[i][j];
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        int[][] arr = {{1, 2 , 3},
                {4, 5, 6},
                {7, 8, 9}};
        System.out.println(sumOf2DArray(arr));
    }
}
