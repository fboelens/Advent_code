import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.util.List;

public class Day14b {
    public static void execute() {

        File file = new File("resources/day14.txt");
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            String[] xyFrom, xyTo;
            List<String> coordinate;

            int floor = 0;
            int left = 500;
            int right = 500;

            Set<Point> caveRocks = new HashSet<>();

            String coordinatePrevious;

            while ((st = br.readLine()) != null) {
                coordinate = Arrays.asList(st.split(" -> "));
                coordinatePrevious = "";
                for (String s: coordinate) {
                    if (!coordinatePrevious.equals("")) {
                        xyTo = s.split(","); // current
                        int xTo = Integer.parseInt(xyTo[0]);
                        int yTo = Integer.parseInt(xyTo[1]);

                        xyFrom = coordinatePrevious.split(",");
                        int xFrom = Integer.parseInt(xyFrom[0]);
                        int yFrom = Integer.parseInt(xyFrom[1]);
                        int finish = Math.max(Math.abs(xTo - xFrom),Math.abs(yTo - yFrom));
                        if (yFrom>floor) {
                            floor = yFrom;
                        }
                        if (xFrom<left) {
                            left = xFrom;
                        }
                        if (xFrom>right) {
                            right = xFrom;
                        }

                        for (int c = 0; c<=finish;c++) {
                            caveRocks.add(new Point(
                                    xFrom+c * (Integer.compare(xTo, xFrom)),
                                    yFrom+c * (Integer.compare(yTo, yFrom))
                            ));
                        }

                        coordinatePrevious = s;
                    }
                    else {
                        coordinatePrevious = coordinate.get(0);
                    }
                }
            }

            boolean caveFull = false;
            int sandPos = 500;
            int sandX, sandY;
            int answer1=0;
            int numberOfSand=0;

            // vloer aanmaken met een marge van 500 links en rechts
            for (int i=left-sandPos;i<=right+sandPos;i++) {
                caveRocks.add(new Point(i,floor+2));
            }

            while (!caveFull) {

                // nieuwe zandkorrel
                sandY = 0;
                sandX = sandPos;
                boolean blocked = false;
                while (!blocked) {
                    // kan ie naar onderen?
                    if (!caveRocks.contains(new Point(sandX, sandY+1))) {
                        sandY++;
                        if (sandY == floor && answer1==0) {
                            answer1 = numberOfSand;
                        }
                    }
                    else {
                        if (!caveRocks.contains(new Point(sandX-1, sandY+1))) {
                            sandX--;
                        }
                        else {
                            if (!caveRocks.contains(new Point(sandX+1, sandY+1))) {
                                sandX++;
                            }
                            else {
                                blocked = true;
                            }
                        }

                    }

                }
                caveRocks.add(new Point(sandX, sandY));
                if (sandX == 500 && sandY == 0) {
                    caveFull = true;
                }
                numberOfSand++;
            }
/*
            for (int i=0;i<=floor+2;i++) {
                for (int j=0;j<1000;j++) {
                    if (caveRocks.contains(new Point(j,i))) {
                        System.out.print('X');
                    }
                    else {
                        System.out.print('.');
                    }
                }
                System.out.println();
            }
*/
            System.out.println("Day 14 - Question 1: " + answer1); // laatste niet meetellen
            System.out.println("Day 14 - Question 2: " + numberOfSand);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

