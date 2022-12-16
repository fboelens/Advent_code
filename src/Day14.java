import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day14 {
    public static void execute() {

        File file = new File("resources/day14.txt");
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            String[] xy;
            List<String> coordinate;
            ArrayList<Coordinate> cs = new ArrayList<>();
            Cave cave = new Cave();
            int minX = 500;
            int maxX = 500;
            int maxY = 0;
            int sandPosition = 500;
            while ((st = br.readLine()) != null) {
                coordinate = Arrays.asList(st.split(" -> "));
                for (String s: coordinate) {
                    xy = s.split(",");
                    int x = Integer.parseInt(xy[0]);
                    int y = Integer.parseInt(xy[1]);
                    if (x < minX) {
                        minX = x;
                    }
                    if (x > maxX) {
                        maxX = x;
                    }
                    if (y > maxY) {
                        maxY = y;
                    }
                    cs.add(new Coordinate(x,y));
                }
                cave.addCoordinate(new ArrayList<>(cs));
                cs.clear();

            }

            cave.setUpCave(maxX-minX, maxY, minX, sandPosition);
            boolean caveFull = false;
            int granules = 0;
            int firstDown = 0;
            while (!caveFull) {
                caveFull = cave.dropSand();
                if (cave.firstDown && firstDown==0) {
                    firstDown = granules;
                }
                granules++;
            }
            cave.printCave();

            System.out.println("Day 14 - Question 1: " +firstDown); // laatste niet meetellen
            System.out.println("Day 14 - Question 2: " +granules);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Cave {
    int[][] mp;
    int sandPosition;

    boolean firstDown = false;

    ArrayList<ArrayList<Coordinate>> coordinates;

    public Cave() {
        this.coordinates = new ArrayList<>();
    }

    public void setUpCave(int width, int height, int minX, int sandPosition) {
        this.sandPosition = sandPosition - minX+150; // ugly
        mp = new int[height+1+2][300+width+1]; // ugly
        Arrays.stream(mp).forEach(a -> Arrays.fill(a, 0));
        Arrays.fill(mp[height+2],1);

        int xFrom, xTo, yFrom, yTo;


        for (ArrayList<Coordinate> cs:coordinates) {
            for (int i=0;i<cs.size()-1;i++) {
                xFrom = cs.get(i).x - minX + 150;
                yFrom = cs.get(i).y;
                xTo =  cs.get(i+1).x - minX + 150;
                yTo = cs.get(i+1).y;

                if (xFrom > xTo) {
                    for (int j = xTo; j<= xFrom ; j++) {

                        mp[yFrom][j] = 1;
                    }
                }
                else {
                    if (xFrom < xTo) {
                        for (int j = xFrom; j<= xTo ; j++) {

                            mp[yFrom][j] = 1;
                        }
                    }
                    else {
                        if (yFrom > yTo) {
                            for (int j = yTo; j<= yFrom ; j++) {

                                mp[j][xFrom] = 1;
                            }
                        }
                        else {
                            if (yFrom < yTo) {
                                for (int j = yFrom; j <= yTo; j++) {

                                    mp[j][xFrom] = 1;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void printCave() {
        for (int[] row: mp) {
            for (int col : row) {
                System.out.print(col);
            }
            System.out.println();

        }
    }

    public void addCoordinate(ArrayList<Coordinate> coordinate) {
        this.coordinates.add(coordinate);
    }

    public boolean dropSand() {
        boolean caveFull = false;

        int sandY = 0;
        int sandX = this.sandPosition;
        boolean blocked = false;
        while (sandY < mp.length-1 && !blocked) {

            // kan korrel vallen?
            if (mp[sandY+1][sandX]!=0) {

                // kan niet verder naar beneden vallen, dus probeer naar links
                if (sandX==0) {
                    blocked = true;
                }
                else {
                    if (mp[sandY+1][sandX-1]==0) {
                        sandX--;
                    }
                    else {
                        // probeer naar rechts te vallen
                        if (sandX>=mp[0].length-1) {
                            blocked = true;
                        }
                        else {
                            if (mp[sandY+1][sandX+1]==0) {
                                sandX++;
                            }
                            else {
                                blocked = true;
                            }
                        }

                    }
                }
            }

            if (!blocked) {
                sandY++;

                if (sandY == mp.length-3) {
                    firstDown = true;
                }
            }

        }
        if (sandY==0 && mp[sandY+1][sandX-1]!=0 && mp[sandY+1][sandX+1]!=0) {
            caveFull = true;
        }
        mp[sandY][sandX] = 2;
        return caveFull;
    }
}

class Coordinate {
    int x, y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
