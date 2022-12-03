import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Day3 {

    public static void execute() {
        File file = new File("resources/day3.txt");

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            ArrayList arr = new ArrayList();
            String st;
            int value = 0;
            while ((st = br.readLine()) != null) {
                Integer len = st.length() / 2;
                String st1 = st.substring(0, len);
                String st2 = st.substring(len, st.length());
                value += (int) getMatch(st1,st2).get(0);

                arr.add(st);
            }

            System.out.println("Day 3 - Question 1: "+value);
            int chunk = 3; // chunk size to divide
            String st1 = "";
            String st2 = "";
            String st3 = "";
            String st4 = "";
            String st5 = "";
            value = 0;
            for(int i=0;i<arr.size();i+=chunk){
                st1 = (String) arr.get(i);
                st2 = (String) arr.get(i+1);
                st3 = (String) arr.get(i+2);

                st4 = (String) getMatch(st1,st2).get(1);
                st5 = (String) getMatch (st3,st4).get(1);
                value+= (int) getMatch(st3,st4).get(0);
            }
            System.out.println("Day 3 - Question 2: "+value);
        }
        catch (Exception e) {
            System.err.println(e);
        }
    }

    // Function to count the matching characters
    static ArrayList getMatch(String str1, String str2)
    {
        ArrayList arr = new ArrayList();
        int c = 0, j = 0;
        int value = 0;
        // Traverse the string 1 char by char
        String skip = "";
        for (int i = 0; i < str1.length(); i++)
        {
         if (!skip.contains(""+str1.charAt(i)) && str2.contains(""+str1.charAt(i)))
            {
                c += 1;
                int a = ((int) (str1.charAt(i)));
                if (a<97) {
                    a=a-64+26;
                }
                else {
                    a=a-96;
                }
                skip = skip + str1.charAt(i);
                value+=a;
            }
        }
        arr.add(value);
        arr.add(skip);
        arr.add(c);
        return arr;
    }
}
