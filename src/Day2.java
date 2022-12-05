import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

public class Day2 {

    public static void execute() {
        File file = new File("resources/day2.txt");

        HashMap<String, Integer> strategy1 = new HashMap<>();
        strategy1.put("A X",1+3);
        strategy1.put("A Y",2+6);
        strategy1.put("A Z",3+0);
        strategy1.put("B X",1+0);
        strategy1.put("B Y",2+3);
        strategy1.put("B Z",3+6);
        strategy1.put("C X",1+6);
        strategy1.put("C Y",2+0);
        strategy1.put("C Z",3+3);

        HashMap<String, Integer> strategy2 = new HashMap<>();
        strategy2.put("A X",0+3);
        strategy2.put("A Y",3+1);
        strategy2.put("A Z",6+2);
        strategy2.put("B X",0+1);
        strategy2.put("B Y",3+2);
        strategy2.put("B Z",6+3);
        strategy2.put("C X",0+2);
        strategy2.put("C Y",3+3);
        strategy2.put("C Z",6+1);

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            String st;
            Integer scoreStrategy1 =0;
            Integer scoreStrategy2 = 0;
            while ((st = br.readLine()) != null) {
                scoreStrategy1 += strategy1.get(st);
                scoreStrategy2 += strategy2.get(st);

            }
            System.out.println("Day 2 - Question 1: "+ scoreStrategy1);
            System.out.println("Day 2 - Question 2: "+ scoreStrategy2);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
