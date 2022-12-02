import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;

public class Day1 {

    public static void execute() {
        File file = new File("resources/day1.txt");

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            String st;
            ArrayList<Integer> deer = new ArrayList<Integer>();
            Integer total = 0;
            while ((st = br.readLine()) != null) {
                if (st.isBlank()) {
                    deer.add(total);
                    total = 0;
                } else {
                    total += Integer.parseInt(st);
                }
            }
            deer.sort(Collections.reverseOrder());
            System.out.println("Day 1 - Question 1: "+ deer.get(0).toString());
            System.out.println("Day 1 - Question 2: "+ (deer.get(0)+deer.get(1)+deer.get(2)));

        }
        catch (Exception e) {
            System.err.println(e);
        }
    }
}
