import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day13 {
    private static Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
    private static ArrayList<Integer> ordered = new ArrayList<>();

    public static void execute() {

        File file = new File("resources/day13.txt");
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st, st2;

            int index = 0;
            int k=1;
            while ((st = br.readLine()) != null) {
                if (!st.isBlank()) {
                    addToList(st);
                    st2 = br.readLine();
                    if (compare(st, st2)==1) {
                        index += k;
                    }
                    addToList(st2);

                    k++;
                }
            }
            Collections.sort(ordered);
            int firstMatch = ordered.indexOf(2)+1;
            ordered.add(firstMatch,2);
            int secondMatch = ordered.indexOf(6)+1;
            System.out.println("Day 13 - Question 1: " + index);
            System.out.println("Day 13 - Question 2: " + (firstMatch*secondMatch));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addToList(String firstNumber) {
        boolean cont=true;
        while (cont) {
            ArrayList<String> list1 = parseString(firstNumber.substring(1, firstNumber.length() - 1));
            if (list1.size()>0) {
                firstNumber = list1.get(0);
                if (isNumeric(firstNumber)) {
                    ordered.add(Integer.parseInt(firstNumber));
                    cont = false;
                }
                if (firstNumber.equals("[]")) {
                    ordered.add(0);
                    cont = false;
                }
            }
            else {
                ordered.add(0);
                cont = false;
            }
        }

    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }
    public static int compare (String st1, String st2) {
        if (st1.charAt(0)=='[' && st2.charAt(0)!='[') {
            st2 = "[" + st2 + "]";
        }

        if (st1.charAt(0) != '[' && st2.charAt(0) == '[') {
            st1 = "[" + st1 + "]";
        }
        if (st1.charAt(0) != '[' && st2.charAt(0) != '[') {
            if (Integer.parseInt(st1)<Integer.parseInt(st2)) {
                return 1;
            }
            if (Integer.parseInt(st1)==Integer.parseInt(st2)) {
                return 0;
            }
            return -1;
        }

        // 2 lijsten?
        if (st1.charAt(0) == '[' && st2.charAt(0) == '[') {
            int i = 0;
            ArrayList<String> list1 = parseString(st1.substring(1, st1.length() - 1));
            ArrayList<String> list2 = parseString(st2.substring(1, st2.length() - 1));

            while (i < list1.size() && i < list2.size()) {
                //System.out.println(list1 + " " + list2);
                int result = compare(list1.get(i), list2.get(i));

                if (result == 1 ) {
                    return 1;
                }
                if (result == -1) {
                    return -1;
                }
                i++;
            }
            if (i == list1.size()) {
                if (list1.size() == list2.size()) {
                    return 0;
                }
                return 1;
            }
        }
        return -1;
    }

    // string naar lijst
    public static ArrayList<String> parseString(String st) {
        ArrayList<String> list = new ArrayList<>();
        int i=0;
        int j=0;
        int brackets = 0;
        while (i<st.length()) {
            if (st.charAt(i)=='[') {
                if (brackets==0) {
                    j = i;
                }
                brackets++;
            }
            if (st.charAt(i)==']') {
                brackets--;
            }
            if (st.charAt(i)==',' && brackets==0) {
                list.add(st.substring(j,i));
                j = i+1;
            }
            if (i== st.length()-1) {
                list.add(st.substring(j,i+1));
            }
            i++;
        }
        return list;
    }
}
