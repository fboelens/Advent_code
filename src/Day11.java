import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day11 {
    static ArrayList<Monkey> monkeys = new ArrayList<>();
    static ArrayList<Monkey> monkeys2 = new ArrayList<>();
    public static void execute() {

        File file = new File("resources/day11.txt");
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            ArrayList<Integer> number =  new ArrayList<>();
            Monkey monkey = new Monkey();
            Monkey monkey2 = new Monkey();
            while ((st = br.readLine()) != null) {
                number.clear();
                Pattern p = Pattern.compile("\\d+");
                Matcher m = p.matcher(st);
                while (m.find()) {
                    number.add(Integer.parseInt(m.group()));
                }
                if (st.startsWith("Monkey")) {
                    monkey = new Monkey();
                    monkey.number = number.get(0);
                    monkey2 = new Monkey();
                    monkey2.number = number.get(0);

                }
                else {
                    if (st.contains("Starting items")) {
                        monkey.items =  (ArrayList<Integer>) number.clone();
                        monkey2.items = (ArrayList<Integer>) monkey.items.clone();
                    }
                    else {
                        if (st.contains("Operation")) {
                            String substring = st.substring(st.indexOf("old") + 4, st.indexOf("old") + 5);
                            monkey.operation = substring;
                            monkey2.operation = substring;
                            if (number.size()>0) {
                                monkey.operationNumber = number.get(0);
                                monkey2.operationNumber = number.get(0);
                            }
                            else {
                                monkey.operationNumber = 0;
                                monkey2.operationNumber = 0;
                            }
                        }
                        else {
                            if (st.contains("Test")) {
                                monkey.test = number.get(0);
                                monkey2.test = number.get(0);
                            }
                            else {
                                if (st.contains("true")) {
                                    monkey.trueMonkey = number.get(0);
                                    monkey2.trueMonkey = number.get(0);
                                }
                                else {
                                    if (st.contains("false")) {
                                        monkey.falseMonkey = number.get(0);
                                        monkey2.falseMonkey = number.get(0);

                                        monkeys.add(monkey);
                                        monkeys2.add(monkey2);
                                    }
                                }
                            }
                        }
                    }
                }
            }


            Long worryLevel;
            ArrayList<Integer> inspected = new ArrayList<>();

            for (int i=0;i<20;i++) {
                for (Monkey m:monkeys) {
                    for (Integer item:m.items) {
                        m.inspected++;
                        worryLevel = m.executeOperation(Long.valueOf(item));
                        worryLevel = worryLevel / 3;
                        if (worryLevel % m.test == 0) {
                            monkeys.get(m.trueMonkey).items.add(worryLevel.intValue());
                        }
                        else {
                            monkeys.get(m.falseMonkey).items.add(worryLevel.intValue());
                        }

                    }
                    m.items.clear();
                }
            }

            int test =1;
            for (Monkey m:monkeys) {
                inspected.add(m.inspected);
                test = test * m.test;
            }
            inspected.sort(Collections.reverseOrder());
            System.out.println("Day 11 - Question 1: " + inspected.get(0)*inspected.get(1));

            for (int i=0;i<10000;i++) {
                for (Monkey m:monkeys2) {
                    //m.printMonkey();
                    for (Integer item:m.items) {

                        worryLevel = m.executeOperation(Long.valueOf(item));
                        worryLevel %= test;
                        if (worryLevel % m.test == 0) {
                            monkeys2.get(m.trueMonkey).items.add(worryLevel.intValue());
                        }
                        else {
                            monkeys2.get(m.falseMonkey).items.add(worryLevel.intValue());
                        }
                    }
                    m.inspected += m.items.size();
                    m.items.clear();
                }
            }
            ArrayList<Integer> inspected2 = new ArrayList<>();
            for (Monkey m:monkeys2) {
                inspected2.add(m.inspected);
            }

            inspected2.sort(Collections.reverseOrder());
            Long big = Long.valueOf(inspected2.get(0)) * Long.valueOf(inspected2.get(1));
            System.out.println("Day 11 - Question 2: " + big);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


class Monkey {
    ArrayList<Integer> items;
    int number;
    String operation;
    int operationNumber;
    int test;
    int trueMonkey;
    int falseMonkey;

    int inspected=0;

    public Long executeOperation(Long old) {
        Long result = 0L;
        Long nr;
        //System.out.println(old);
        if (operationNumber ==0) {
            nr = old;
        }
        else {
            nr = (long) operationNumber;
        }

        switch (operation) {
            case "*" -> result = old * nr;
            case "+" -> result = old + nr;
        }
        return result;
    }
/*
    public void printMonkey () {
        System.out.println("nr: " + number);
        System.out.println("items: " + items);
        System.out.println("operation: " + operation + operationNumber);
        System.out.println("test: " + test);
        System.out.println("true: " + trueMonkey);
        System.out.println("false: " + falseMonkey);
        System.out.println("inspected: " + inspected);
    }*/
}