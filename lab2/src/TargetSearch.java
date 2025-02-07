public class TargetSearch {
    public static int[] pairSearch(int[] arr, int target){

        int n = arr.length;
        for(int i = 0; i<n; i++){
            for(int j = 0; j<n; j++){
                if(arr[i] + arr[j] == target){
                    int[] pair = {arr[i], arr[j]};
                    return pair;
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        int[] arr = {1, 4, 5, 12, 7};
        int target = 13;
        int[] result = pairSearch(arr, target);

        if (result != null) {
            for (int i : result) {
                System.out.print(i + " ");
            }
        }
        else{
            System.out.print("Couldn't find pair");
        }
    }

}
