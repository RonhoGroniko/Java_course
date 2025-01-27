import java.util.Scanner;

public class SyracuseSequence  {
    public static void main(String[] args) {

        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Start number: ");
        int n = myObj.nextInt(); // Read user input
        if (n < 0){
            System.out.println("Number must be positive, try again");
            System.exit(0);
        }
        int steps = 0;
        while(n != 1){
            if (n % 2 == 0){
                n = n/2;
                steps++;
            }
            else{
                n = 3*n+1;
                steps++;
            }
        }
        System.out.println("Steps: " + steps);  // Output
    }
}
