import java.util.Scanner;

public class TwiceEven {
    public static void main(String[] args) {

        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Insert number: ");
        int sum = 0;
        int multip = 1;
        int number = myObj.nextInt();
        for(int i = 0; i < 3; i++){
            int digit = number%10;
            number /= 10;
            sum += digit;
            multip *= digit;
        }
        if ((sum % 2 == 0) && (multip % 2 == 0)){
            System.out.println("Number is twice even");
        }
        else{
            System.out.println("Number is not twice even");
        }
    }
}
