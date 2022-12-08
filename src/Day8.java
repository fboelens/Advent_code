import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;


public class Day8 {

    public static void execute() {
        File file = new File("resources/day8.txt");
        int currentTree;
        int visibleTrees = 0;
        boolean visible;
        boolean northVisible, eastVisible, southVisible, westVisible;

        ArrayList<String> treemap = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;

            while ((st = br.readLine()) != null) {
                treemap.add(st);
            }

            for (int i = 0; i <= treemap.size()-1; i++) {
                String treeRow = treemap.get(i);
                for (int j = 0 ; j<=treeRow.length()-1;j++) {
                    northVisible = true;
                    southVisible = true;
                    westVisible = true;
                    eastVisible = true;

                    currentTree = Integer.parseInt(""+treeRow.charAt(j));
                    // look up
                    for (int up = i-1;up>=0;up--) {
                        if (Integer.parseInt(""+treemap.get(up).charAt(j)) >= currentTree) {
                            northVisible = false;
                        }
                    }

                    for (int right = j+1; right<treeRow.length() ;right++) {
                        if (Integer.parseInt(""+treeRow.charAt(right)) >= currentTree) {
                            eastVisible = false;
                        }
                    }

                    for (int down = i+1;down<treemap.size();down++) {
                        if (Integer.parseInt("" + treemap.get(down).charAt(j)) >= currentTree) {
                            southVisible = false;
                        }
                    }

                    for (int left = j-1; left>=0;left--) {
                        if (Integer.parseInt("" + treeRow.charAt(left)) >= currentTree) {
                            westVisible = false;
                        }
                    }

                    if (northVisible || eastVisible || westVisible || southVisible) {
                        visibleTrees++;
                    }

                }
            }

            int northScore;
            int eastScore;
            int southScore;
            int westScore;
            int topScore = 0;

            for (int i = 0; i <= treemap.size()-1; i++) {
                String treeRow = treemap.get(i);

                for (int j = 0 ; j<=treeRow.length()-1;j++) {
                    northScore = 0;
                    eastScore = 0;
                    southScore = 0;
                    westScore = 0;


                    currentTree = Integer.parseInt(""+treeRow.charAt(j));
                    // look up
                    for (int up = i-1;up>=0;up--) {
                        northScore++;
                        if (Integer.parseInt(""+treemap.get(up).charAt(j)) >= currentTree) {
                            break;
                        }
                    }

                    for (int right = j+1; right<treeRow.length() ;right++) {
                        eastScore++;
                        if (Integer.parseInt(""+treeRow.charAt(right)) >= currentTree) {
                            break;
                        }
                    }

                    for (int down = i+1;down<treemap.size();down++) {
                        southScore++;
                        if (Integer.parseInt("" + treemap.get(down).charAt(j)) >= currentTree) {
                            break;
                        }
                    }

                    for (int left = j-1; left>=0;left--) {
                        westScore++;
                        if (Integer.parseInt("" + treeRow.charAt(left)) >= currentTree) {
                            break;
                        }
                    }

                    int score = northScore*eastScore*southScore*westScore;
                    if (score>topScore) {
                        topScore = score;
                    }

                }
            }

            System.out.println("Day 8 - Question 1: " + visibleTrees);
            System.out.println("Day 8 - Question 2: " + topScore);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
