import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Day12 {
    public static void execute() {

        File file = new File("resources/day12.txt");
        ArrayList<String> map = new ArrayList<>();
        ArrayList<Node> allNodes = new ArrayList<>();
        ArrayList<Node> startingNodes = new ArrayList<>();
        int rows = 0;
        Node newNode;
        Node source = new Node("");
        Node target = new Node("");

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;

            while ((st = br.readLine()) != null) {


                for (int cols=0;cols<st.length();cols++) {
                    newNode = new Node(cols +","+ rows);
                    allNodes.add(newNode);
                    if (st.charAt(cols)=='S') {
                        source = newNode;
                        st = st.replace('S','a');
                    }
                    if (st.charAt(cols)=='E') {
                        target = newNode;
                        st = st.replace('E','z');
                    }
                    if (st.charAt(cols)=='S' || st.charAt(cols)=='a' ) {
                        startingNodes.add(newNode);
                    }
                }
                map.add(st);
                rows++;
            }

            int shortestPath = findShortestPath(map, allNodes, source, target);
            System.out.println("Day 12 - Question 2: "  + shortestPath);

            int bestPath = shortestPath;
            for (Node n: startingNodes) {

                shortestPath = findShortestPath(map, allNodes, n, target);
                if (shortestPath!=0 && shortestPath<bestPath) {
                    bestPath = shortestPath;
                }
            }


            System.out.println("Day 12 - Question 2: " + bestPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int findShortestPath(ArrayList<String> map, ArrayList<Node> allNodes, Node source, Node target) {
        Node currentNode;
        char up ;
        char down;
        char left;
        char right;
        int rows = 0;

        for (String nodes : map) {
            for (int cols = 0; cols < nodes.length(); cols++) {
                char currentHeight = nodes.charAt(cols);
                int currentPos = cols + ((nodes.length()) * rows);
                currentNode = allNodes.get(currentPos);

                // kijk boven
                if (rows > 0) {
                    up = map.get(rows - 1).charAt(cols);

                    if ((currentHeight + 1) - up >= 0) {
                        //System.out.println("UP " + currentHeight + " " + up);
                        currentNode.addDestination(allNodes.get(cols + (nodes.length() * (rows - 1))), 1);
                    }
                }
                // kijk rechts
                if (cols < nodes.length() - 1) {
                    right = nodes.charAt(cols + 1);
                    if ((currentHeight + 1) - right >= 0) {
                        //System.out.println("right " + currentHeight + " " + right);
                        currentNode.addDestination(allNodes.get(cols + 1 + (nodes.length() * (rows))), 1);
                    }

                }
                // kijk onder
                if (rows < map.size() - 1) {
                    down = map.get(rows + 1).charAt(cols);
                    if ((currentHeight + 1) - down >= 0) {
                        //System.out.println("down " + currentHeight + " " + down);
                        currentNode.addDestination(allNodes.get(cols + (nodes.length() * (rows + 1))), 1);
                    }
                }
                // kijk links
                if (cols > 0) {
                    left = nodes.charAt(cols - 1);
                    if ((currentHeight + 1) - left >= 0) {
                        //System.out.println("left " + currentHeight + " " + left);
                        currentNode.addDestination(allNodes.get(cols - 1 + (nodes.length() * (rows))), 1);
                    }
                }
                allNodes.set(currentPos, currentNode);

            }
            rows++;
        }
        Graph graph = new Graph();
        for (Node n : allNodes) {
            graph.addNode(n);
        }
        calculateShortestPathFromSource(source);
        Node n = graph.getNode(target);
        int size = n.getShortestPath().size();

        for (Node n1 : allNodes) {
            n1.setShortestPath(new LinkedList<>());
        }

        return size;
    }


    public static void calculateShortestPathFromSource(Node source) {
        source.setDistance(0);

        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();

        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {
            Node currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for (Map.Entry<Node, Integer> adjacencyPair :
                    currentNode.getAdjacentNodes().entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();
                Integer edgeWeight = adjacencyPair.getValue();
                if (!settledNodes.contains(adjacentNode)) {
                    calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }
    }

    private static Node getLowestDistanceNode(Set < Node > unsettledNodes) {
        Node lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (Node node: unsettledNodes) {
            int nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

    private static void calculateMinimumDistance(Node evaluationNode, Integer edgeWeigh, Node sourceNode) {
        Integer sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + edgeWeigh);
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }
}

class Graph {
    private final Map<String, Node> nodes = new HashMap<>();

    public void addNode(Node nodeA) {
        nodes.put(nodeA.getName(), nodeA);
    }

    public Node getNode(Node n) {
        return nodes.get(n.getName());
    }

}

class Node {

    private final String name;
    private List<Node> shortestPath = new LinkedList<>();
    private Integer distance = Integer.MAX_VALUE;

    Map<Node, Integer> adjacentNodes = new HashMap<>();


    public void addDestination(Node destination, int distance) {
        adjacentNodes.put(destination, distance);
    }

    public void setShortestPath(LinkedList<Node> shortestPath) {
        this.shortestPath = shortestPath;
    }

    public List<Node> getShortestPath(){
        return this.shortestPath;
    }

    public Map<Node,Integer> getAdjacentNodes() {
        return adjacentNodes;
    }
    public Node(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;

    }
    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Integer getDistance() {
        return this.distance;
    }


}