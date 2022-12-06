import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day5 {
    public static void execute() {
        File file = new File("resources/day5.txt");
        ArrayList<ArrayList<String>> stacks = new ArrayList<>();
        ArrayList<ArrayList<String>> stacks2 = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            int[] order;
            String st;
            List<List<String>> temp;
            String cratesString1 = "", cratesString2 = "";
            boolean readStacks = true;
            while ((st = br.readLine()) != null) {
                st = st.replaceAll("move ","").replaceAll(" from ",",").replaceAll(" to ",",");

                // lees stacks
                if (st.isBlank()) {
                    readStacks = false;
                    //System.out.println(stacks);
                    stacks2 = stacks.stream().map(ArrayList::new).collect(Collectors.toCollection(ArrayList::new)); // copy stack
                }
                else {
                    if (readStacks) {
                        int stackNumber =0;

                        // eerste keer stapels aanmaken
                        if (stacks.size()==0) {
                            for (int i = 0; i <= st.length(); i = i + 4) {
                                stacks.add(new ArrayList<>());
                            }
                        }
                        for (int i=0;i<st.length();i=i+4) {
                            if (Character.isAlphabetic(st.charAt(i + 1))) {
                                stacks.get(stackNumber).add(0,"" + st.charAt(i + 1));
                            }
                            stackNumber++;
                        }
                    }
                    else {
                        order = Arrays.stream(st.split(","))
                                .mapToInt(Integer::parseInt)
                                .toArray();

                        temp = moveStacks(stacks.get(order[1] - 1), stacks.get(order[2] - 1), order[0]);
                        stacks.set(order[1] - 1, (ArrayList<String>) temp.get(0));
                        stacks.set(order[2] - 1, (ArrayList<String>) temp.get(1));

                        temp = moveStacks9001(stacks2.get(order[1] - 1), stacks2.get(order[2] - 1), order[0]);
                        stacks2.set(order[1] - 1, (ArrayList<String>) temp.get(0));
                        stacks2.set(order[2] - 1, (ArrayList<String>) temp.get(1));
                    }
                }
            }
            for (ArrayList<String> stack : stacks) {
                cratesString1 = cratesString1.concat(stack.get(stack.size() - 1));
            }
            for (ArrayList<String> stack : stacks2) {
                cratesString2 = cratesString2.concat(stack.get(stack.size() - 1));
            }
            System.out.println("Day 5 - Question 1: " + cratesString1);
            System.out.println("Day 5 - Question 2: " + cratesString2);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    // source, target, nr
    static List<List<String>> moveStacks(ArrayList<String> arr1, ArrayList<String> arr2, int nr) {
        // move crate for crate from array 1 to array 2
        for (int i=0; i<nr;i++) {
            arr2.add(arr1.get(arr1.size()-1));
            arr1.remove(arr1.size()-1);
        }
        return Arrays.asList(arr1,arr2);
    }

    static List<List<String>> moveStacks9001(ArrayList<String> arr1, ArrayList<String> arr2, int nr) {
        // move crate for crate from array 1 to array 2
        arr2.addAll(arr2.size(),(arr1.subList((arr1.size()-nr), arr1.size()))); // voeg stapel toe van stack 1 naar 2
        arr1.subList(arr1.size()-nr, arr1.size()).clear(); // verwijder crates van eerste stapel
        return Arrays.asList(arr1,arr2);
    }
}
