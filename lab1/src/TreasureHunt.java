import java.util.*;

public class TreasureHunt {
    public static void main(String[] args) {

        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Insert treasure coordinates: ");
        int xTreasureCoord = myObj.nextInt(); // Read user input
        int yTreasureCoord = myObj.nextInt();
        int steps = 0;
        List<int[]> tips = new ArrayList<>();
        while (true) {
            String direction = myObj.next();
            if (direction.equals("стоп")){
                break;
            }
            else{
                int distance = myObj.nextInt();

                int[] tip = new int[2];
                switch (direction){
                    case "север":
                        tip[1] = distance;
                        break;
                    case "юг":
                        tip[1] = -distance;
                        break;
                    case "запад":
                        tip[0] = -distance;
                        break;
                    case "восток":
                        tip[0] = distance;
                        break;
                }
                tips.add(tip);
            }
        }

        int currentX = 0;
        int currentY = 0;

        for (int i = 0; i < tips.size(); i++) {
            int[] move = tips.get(i);
            currentX += move[0];
            currentY += move[1];
            steps++;

            if (currentX == xTreasureCoord && currentY == yTreasureCoord) {
                System.out.println("Minimum steps to treasure: " + steps);
                return;
            }

        }
    }
}

