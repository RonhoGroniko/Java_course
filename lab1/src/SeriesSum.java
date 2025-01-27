import java.util.Scanner;

public class SeriesSum {
    public static void main(String[] args) {

        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Length of series: ");
        int n = myObj.nextInt(); // Read user input
        int seriesSum = 0;
        if (n < 0){
            System.out.println("Length must be positive, try again");
            System.exit(0);
        }
        for(int i = 0; i <= n-1; i++){
            if (i % 2 == 0)
                seriesSum += myObj.nextInt();
            else{
                seriesSum -= myObj.nextInt();
            }
        }
        System.out.println("Series Sum: " + seriesSum);  // Output
    }
}
