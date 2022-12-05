import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
/*
            [L] [M]         [M]
        [D] [R] [Z]         [C] [L]
        [C] [S] [T] [G]     [V] [M]
[R]     [L] [Q] [B] [B]     [D] [F]
[H] [B] [G] [D] [Q] [Z]     [T] [J]
[M] [J] [H] [M] [P] [S] [V] [L] [N]
[P] [C] [N] [T] [S] [F] [R] [G] [Q]
[Z] [P] [S] [F] [F] [T] [N] [P] [W]
 1   2   3   4   5   6   7   8   9

 */


public class Day5 {
    public static void execute() {
        File file = new File("resources/day5.txt");
        ArrayList<ArrayList<String>> stacks = new ArrayList<>();
        stacks.add(new ArrayList<>(Arrays.asList( "Z","P","M","H","R")));
        stacks.add(new ArrayList<>( Arrays.asList( "P","C","J","B")));
        stacks.add(new ArrayList<>( Arrays.asList( "S","N","H","G","L","C","D")));
        stacks.add(new ArrayList<>( Arrays.asList( "F","T","M","D","Q","S","R","L")));
        stacks.add(new ArrayList<>( Arrays.asList( "F","S","P","Q","B","T","Z","M")));
        stacks.add(new ArrayList<>( Arrays.asList( "T","F","S","Z","B","G")));
        stacks.add(new ArrayList<>( Arrays.asList( "N","R","V")));
        stacks.add(new ArrayList<>( Arrays.asList( "P","G","L","T","D","V","C","M")));
        stacks.add(new ArrayList<>( Arrays.asList( "W","Q","N","J","F","M","L")));

        ArrayList<ArrayList<String>> stacks2;
        stacks2 = stacks.stream().map(ArrayList::new).collect(Collectors.toCollection(ArrayList::new));

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            int[] order;
            String st;
            List<List<String>> temp;
            // move 3 from 7 to 3
            while ((st = br.readLine()) != null) {
                st = st.replaceAll("move ","").replaceAll(" from ",",").replaceAll(" to ",",");

                order = Arrays.stream(st.split(","))
                        .mapToInt(Integer::parseInt)
                        .toArray();

                temp = moveArrays(stacks.get(order[1]-1), stacks.get(order[2]-1),order[0]);
                stacks.set(order[1]-1,(ArrayList<String>) temp.get(0));
                stacks.set(order[2]-1,(ArrayList<String>) temp.get(1));

                temp = moveArrays9001(stacks2.get(order[1]-1), stacks2.get(order[2]-1),order[0]);
                stacks2.set(order[1]-1,(ArrayList<String>) temp.get(0));
                stacks2.set(order[2]-1,(ArrayList<String>) temp.get(1));
            }
            String cratesString1 = "";
            String cratesString2 = "";
            List<String> tempStack;
            for (ArrayList<String> stack : stacks) {
                tempStack = stack;
                cratesString1 = cratesString1.concat(tempStack.get(tempStack.size() - 1));
            }
            for (ArrayList<String> stack : stacks2) {
                tempStack = stack;
                cratesString2 = cratesString2.concat(tempStack.get(tempStack.size() - 1));
            }
            System.out.println("Day 5 - Question 1: " + cratesString1);
            System.out.println("Day 5 - Question 2: " + cratesString2);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    // source, target, nr
    static @NotNull List<List<String>> moveArrays(ArrayList<String> arr1, ArrayList<String> arr2, int nr) {
        List<List<String>> arr = new ArrayList<>();

        // move crate for crate from array 1 to array 2
        for (int i=0; i<nr;i++) {
            String a = arr1.get(arr1.size()-1);
            arr2.add(a);
            arr1.remove(arr1.size()-1);
        }
        arr.add(arr1);
        arr.add(arr2);
        return arr;
    }

    static @NotNull List<List<String>> moveArrays9001(ArrayList<String> arr1, ArrayList<String> arr2, int nr) {
        List<List<String>> arr = new ArrayList<>();

        // move crate for crate from array 1 to array 2
        ArrayList<String> a = new ArrayList<> (arr1.subList((arr1.size()-nr), arr1.size()));
        arr1.subList(arr1.size()-nr, arr1.size()).clear();
        arr2.addAll(arr2.size(),a);

        arr.add(arr1);
        arr.add(arr2);
        return arr;
    }
}
