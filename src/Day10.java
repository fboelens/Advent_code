import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Day10 {
    static int cycle = 0;
    static int x = 1;
    static int totalValue = 0;

    static ArrayList<String> dotsArray = new ArrayList<>();
    static String crosses="";

    public static void execute() {

        File file = new File("resources/day10.txt");
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            String[] split;
            while ((st = br.readLine()) != null) {
                if (st.equals("noop")) {
                    addCycle();
                }
                else {
                    addCycle();
                    addCycle();
                    split = st.split("\\s+");
                    x = x + Integer.parseInt(split[1]);
                }
            }
            System.out.println("Day 10 - Question 1: " + x + " " + totalValue);
            System.out.println("Day 10 - Question 2: " );
            for (String s: dotsArray) {
                System.out.println(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addCycle() {
        int currentPos = crosses.length();

        if (currentPos == x || currentPos == x-1 || currentPos == x+1) {
            crosses = crosses.concat("#");

        }
        else {
            crosses = crosses.concat(".");
        }

        cycle++;

        if (cycle==20 || (cycle-20)%40==0) {
            totalValue += cycle*x;
        }
        if (cycle%40==0) {
            System.out.println(crosses);
            dotsArray.add(crosses);
            crosses = "";

        }
    }
}
