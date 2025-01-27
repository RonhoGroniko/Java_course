import java.util.*;


public class LogisticMaxAndMin {
    public static void main(String[] args) {

        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Insert roads number: ");
        int max = 0;
        int roadNumber = 0;
        int roads = myObj.nextInt();
        for(int i = 0; i<roads; i++){
            int min = -1;
            int tunnels = myObj.nextInt();
            for(int j = 0; j<tunnels; j++){
                int tunnelHeight = myObj.nextInt();
                if(tunnelHeight < min || min == -1) {
                    min = tunnelHeight;
                }
            }
            if(min > max){
                roadNumber = i+1;
                max = min;
            }
        }
        System.out.println("Result: " + roadNumber + " " + max);
    }
}
