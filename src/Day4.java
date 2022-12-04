import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Day4 {

    public static void execute() {
        File file = new File("resources/day4.txt");
        int[] pairs;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            int i=0;
            int j=0;
            String st;
            while ((st = br.readLine()) != null) {
                pairs = Arrays.stream(st.split("[,-]+"))
                        .mapToInt(Integer::parseInt)
                        .toArray();

                // zit pair 1 in pair 2?
                if ((pairs[0] <= pairs[2] && pairs[1]>=pairs[3]) || (pairs[2] <= pairs[0] && pairs[3]>=pairs[1])) {
                    i++;
                }

                if (!(pairs[1] < pairs[2] || pairs[3]<pairs[0])) {
                    j++;
                }

            }
            System.out.println("Day 4 - Question 1: "+i);
            System.out.println("Day 4 - Question 2: "+j);
        }
        catch (Exception e) {
            System.err.println(e);
        }
    }

}
