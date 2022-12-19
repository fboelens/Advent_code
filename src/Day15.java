import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day15 {

    public static void execute() {
        File file = new File("resources/day15.txt");
        Set<SensorItem> sensorMap = new HashSet<>();
        Set<SensorItem> beaconMap = new HashSet<>();
        long line = 2000000;
        long covered = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            String st;
            ArrayList<String> s = new ArrayList<>();
            long sX, sY, bX, bY;
            long minX = 0;
            long maxX = 0;
            long minY = 0;
            long maxY = 0;
            long maxDistance = 0;
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

                minX = Math.min(sX,minX);
                minX = Math.min(bX,minX);
                maxX = Math.max(sX,maxX);
                maxX = Math.max(bX,maxX);
                minY = Math.min(sY,minY);
                minY = Math.min(bY,minY);
                maxY = Math.max(sY,maxY);
                maxY = Math.max(bY,maxY);

                SensorItem beacon = new SensorItem(bX, bY, "B");
                SensorItem sensor = new SensorItem(sX, sY, "S");
                long distance = Math.abs(sX - bX) + Math.abs((sY - bY));
                maxDistance = Math.max(distance, maxDistance);
                sensor.addBeaconDistance(distance);
                sensorMap.add(sensor); //
                beaconMap.add(beacon); //

                s.clear();
            }

            for (long i = minX - maxDistance ; i<=maxX + maxDistance; i++) {
                // check alle sensors
                boolean found = false;
                for (SensorItem si : sensorMap) {
                    long distance = Math.abs(si.x - i) + Math.abs((si.y - line));

                    if (distance <= si.beaconDistance && !beaconMap.contains(new SensorItem(i, line, "B")) ) {
                        covered++;
                        found = true;
                        break;
                    }
                }
            }

            // zoek om de randen van de sensoren
            long beaconResult = 0;

            // doorloop alle sensoren en bekijke alle positities buiten de controlestraal
            int[][] cl = {
                    { -1,-1},
                    {-1,1} ,
                    {1,-1},
                    {1,1}
            };
            long fx,fy;
            for (SensorItem si : sensorMap) {
                long distance = si.beaconDistance;
                for (int i=0;i<distance;i++) {
                    for (int[] c:cl) {
                        fx = si.x+i * c[0];
                        fy = si.y+i * c[1];
                        System.out.println(fx + "," + fy);
                    }
                }
                break;
            }

            System.out.println("Day 15 - Question 1: " + covered);
            System.out.println("Day 15 - Question 2: " + beaconResult);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class SensorItem {
        String type;
        Long x;
        Long y;
        SensorItem beacon;
        Long beaconDistance;

        public SensorItem(Long x, Long y, String type) {
            this.type = type;
            this.x = x;
            this.y = y;
            this.beaconDistance = Long.valueOf(0);
        }

        public void addBeaconDistance(Long beaconDistance) {
            this.beaconDistance = beaconDistance;
        }

        public void addRelatedBeacon(SensorItem beacon) {
            this.beacon = beacon;
        }

        private int getId() {
            return new Point(x.intValue()+200,y.intValue()+200).hashCode() ;
        }

        @Override
        public int hashCode() {
            return getId();
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
