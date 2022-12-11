import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Pattern;


public class Day9 {
    public static void execute() {
        File file = new File("resources/day9.txt");

        HashSet<Point> visited = new HashSet<>();
        HashSet<Point> visited2 = new HashSet<>();

        ArrayList<Point> rope = new ArrayList<>();
        for (int i=0;i<10;i++) {
            rope.add(new Point(0,0));
        }
        visited.add(rope.get(1));
        visited2.add(rope.get(9));

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            String[] split;
            int steps = 0;
            while ((st = br.readLine()) != null) {
                split = st.split("\\s+");
                System.out.println(split[0] + " " + split[1]);
                steps = Integer.parseInt(split[1]);
                switch (split[0]) {
                    case "U":
                        for (int i=0;i<steps;i++) {
                            rope.get(0).y++; // update head of rope
                            for (int j = 1; j<rope.size();j++) {
                                rope.set(j,moveRopePart(rope.get(j-1), rope.get(j)));
                            }
                            visited.add(new Point(rope.get(1)));
                            visited2.add(new Point(rope.get(9)));

                        }
                        break;
                    case "D":
                        for (int i=0;i<steps;i++) {
                            rope.get(0).y--; // update head of rope
                            for (int j = 1; j<rope.size();j++) {
                                rope.set(j,moveRopePart(rope.get(j-1), rope.get(j)));
                            }
                            visited.add(new Point(rope.get(1)));
                            visited2.add(new Point(rope.get(9)));

                        }
                        break;
                    case "L":
                        for (int i=0;i<steps;i++) {
                            rope.get(0).x--; // update head of rope
                            for (int j = 1; j<rope.size();j++) {
                                rope.set(j,moveRopePart(rope.get(j-1), rope.get(j)));
                            }
                            visited.add(new Point(rope.get(1)));
                            visited2.add(new Point(rope.get(9)));

                        }
                        break;
                    case "R":
                        for (int i=0;i<steps;i++) {
                            rope.get(0).x++; // update head of rope
                            for (int j = 1; j<rope.size();j++) {
                                rope.set(j,moveRopePart(rope.get(j-1), rope.get(j)));
                            }
                            visited.add(new Point(rope.get(1)));
                            visited2.add(new Point(rope.get(9)));

                        }
                        break;
                }


            }
            System.out.println("Day 9 - Question 1: "+visited.size());
            System.out.println("Day 9 - Question 2: "+visited2.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Point moveRopePart(Point head, Point tail) {
        int xDif = Math.abs(head.x - tail.x);
        int yDif = Math.abs(head.y - tail.y);

        // geen groot verschil?
        if (xDif<=1 && yDif<=1) {
            ;
        }
        else {

            if (xDif>=2 && yDif>=2) {
                //System.out.println("x en y");

                if (tail.y>=head.y) {
                    tail.y = head.y +1;

                }
                else {
                    tail.y = head.y-1;
                }

                if (tail.x>=head.x) {
                    tail.x = head.x+1;
                }
                else {
                    tail.x = head.x -1;
                }
            }
            else {
                if (yDif>=2) {
                    //System.out.println("y");
                    if (tail.y>=head.y) {
                        tail.y = head.y+1;

                    }
                    else {
                        tail.y = head.y -1;
                    }
                    tail.x = head.x;
                }
                else {
                    //System.out.println("x");
                    if (xDif>=2) {
                        tail.y = head.y;

                        if (tail.x>=head.x) {
                            tail.x = head.x+1;
                        }
                        else {
                            tail.x = head.x-1;
                        }

                    }
                }
            }
        }

        return tail;
    }

}
