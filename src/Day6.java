import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day6 {
    public static void execute() {
        File file = new File("resources/day6.txt");
        int position=0, position2=0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;

            while ((st = br.readLine()) != null) {
                for (int i=0; i<st.length()-3;i++) {
                    if (uniqueCharacters(st.substring(i,i+4))) {
                        position=i+4; //4 erbij omdat dit de laatste positie is in de substring.
                        break;
                    }
                }
                for (int i=0; i<st.length()-13;i++) {
                    if (uniqueCharacters(st.substring(i,i+14))) {
                        position2=i+14; //14 erbij omdat dit de laatste positie is in de substring.
                        break;
                    }
                }
            }
            System.out.println("Day 6 - Question 1: "+position);
            System.out.println("Day 6 - Question 2: "+position2);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    static boolean uniqueCharacters(String str)
    {
        for (int i = 0; i < str.length(); i++)
            for (int j = i + 1; j < str.length(); j++)
                if (str.charAt(i) == str.charAt(j))
                    return false;
        return true;
    }

}
