public class RotateClockwise {
    public static int[][] RotateMatrix(int[][] arr){
        int rows = arr.length;
        int cols = arr[0].length;

        int[][] new_arr = new int[cols][rows];

        for(int row = 0; row < rows; row++){
            for(int col = 0; col < cols; col++){
                new_arr[col][rows - row - 1] = arr[row][col];
            }
        }
        return new_arr;
    }

    public static void main(String[] args) {

        int[][] arr = {{1, 2 , 3},
                        {4, 5, 6}};
        int[][] new_arr = RotateMatrix(arr);

        for (int i = 0; i < new_arr.length; i++) {
            for (int j = 0; j < new_arr[0].length; j++)
                System.out.print(new_arr[i][j] + " ");
            System.out.println();
        }
    }
}
