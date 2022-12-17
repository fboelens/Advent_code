import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day15 {

    public static void execute() {
        File file = new File("resources/day15.txt");
        Set<SensorItem> sensorMap = new HashSet<>();

boolean once = true;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            String st;
            ArrayList<String> s = new ArrayList<>();
            int sX, sY, bX, bY;
            int minX = 0;
            int maxX = 0;
            int minY = 0;
            int maxY = 0;
            while ((st = br.readLine()) != null) {
                Pattern p = Pattern.compile("-?\\d+");
                Matcher m = p.matcher(st);
                while (m.find()) {
                    s.add(m.group());
                }

                sX = Integer.parseInt(s.get(0));
                sY = Integer.parseInt(s.get(1));

                bX = Integer.parseInt(s.get(2));
                bY = Integer.parseInt(s.get(3));

                if (sX<minX) {
                    minX = sX;
                }
                if (sX>maxX) {
                    maxX = sX;
                }
                if (sY<minY) {
                    minY = sY;
                }
                if (sY>maxY) {
                    maxY = sY;
                }

                SensorItem beacon = new SensorItem(new Point(bX, bY), "B");
                SensorItem sensor = new SensorItem(new Point(sX, sY), "S");

                sensorMap.add(sensor); // add beacon
                sensorMap.add(beacon); // add sensor

                if (once) {
                    int distance = Math.abs(sX - bX) + Math.abs((sY - bY));
                    for (int i = sY - distance; i < sY + distance; i++) {
                        for (int j = sX - distance; j < sX + distance; j++) {
                            sensorMap.add(new SensorItem(new Point(j, i), "C")); // covered
                        }

                    }
                    once = false;
                }
                s.clear();


            }
            System.out.println(sensorMap.size());

            for (int i = minY; i < maxY; i++) {
                for (int j = minX; j < maxX; j++) {
                    if (sensorMap.contains(new SensorItem(new Point(j, i), "S"))) {
                        System.out.print("S");
                    } else {
                        if (sensorMap.contains(new SensorItem(new Point(j, i), "C"))) {
                            System.out.print("#");
                        }
                        else {
                            if (sensorMap.contains(new SensorItem(new Point(j, i), "B"))) {
                                System.out.print("B");
                            } else {
                                System.out.print(".");
                            }
                        }
                    }
                }
                System.out.println();
            }


            System.out.println("Day 15 - Question 1: ");
            System.out.println("Day 15 - Question 2: ");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class SensorItem {
        String type;
        Point p;
        SensorItem beacon;

        public SensorItem(Point p, String type) {
            this.type = type;
            this.p = p;
        }

        public void addRelatedBeacon(SensorItem beacon) {
            this.beacon = beacon;
        }

        private int getId() {
            return Integer.toString(p.x+100).hashCode() + Integer.toString(p.y+100).hashCode() + type.hashCode() ;
        }

        @Override
        public int hashCode() {
            return p.hashCode();
        }

        @Override
        public boolean equals(Object o) {
            if (o == null) {
                return false;
            }
            if (o == this) {
                return true;
            }
            if (getClass() != o.getClass()) {
                return false;
            }

            SensorItem e = (SensorItem) o;
            return (this.getId() == e.getId());
        }
    }
}
