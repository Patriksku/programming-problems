package optical_fiber_3472;

import java.util.*;
import java.lang.*;
import java.io.*;
import java.util.stream.Collectors;

public class Main {

    // A class to represent a graph edge
    class Edge implements Comparable<Edge> {
        int src, dest;
        double weight;

        // Comparator function used for sorting edges
        // based on their weight
        public int compareTo(Edge compareEdge) {
            return Double.compare(this.weight, compareEdge.weight);
        }
    }

    // A class to represent a subset for union-find
    class subset {
        int parent, rank;
    }

    ;

    int V, E;    // V-> no. of vertices & E->no.of edges
    Edge[] edge; // collection of all edges
    Edge[] resultEdges;

    // Creates a graph with V vertices and E edges
    Main(int v, int e) {
        V = v;
        E = e;
        edge = new Edge[E];
        for (int i = 0; i < e; ++i)
            edge[i] = new Edge();
    }

    // A utility function to find set of an element i
    // (uses path compression technique)
    int find(subset[] subsets, int i) {
        // find root and make root as parent of i (path compression)
        if (subsets[i].parent != i)
            subsets[i].parent = find(subsets, subsets[i].parent);

        return subsets[i].parent;
    }

    // A function that does union of two sets of x and y
    // (uses union by rank)
    void Union(subset[] subsets, int x, int y) {
        int xroot = find(subsets, x);
        int yroot = find(subsets, y);

        // Attach smaller rank tree under root of high rank tree
        // (Union by Rank)
        if (subsets[xroot].rank < subsets[yroot].rank)
            subsets[xroot].parent = yroot;
        else if (subsets[xroot].rank > subsets[yroot].rank)
            subsets[yroot].parent = xroot;

            // If ranks are same, then make one as root and increment
            // its rank by one
        else {
            subsets[yroot].parent = xroot;
            subsets[xroot].rank++;
        }
    }

    public Edge[] getResultEdges() {
        return resultEdges;
    }

    // The main function to construct MST using Kruskal's algorithm
    void KruskalMST() {
        Edge[] result = new Edge[V-1];  // This will store the resultant MST (added "-1" as I think it was incorrect)
        int e = 0;  // An index variable, used for result[]
        int i = 0;  // An index variable, used for sorted edges
        for (i = 0; i < V-1; ++i)
            result[i] = new Edge();

        // Step 1:  Sort all the edges in non-decreasing order of their
        // weight.  If we are not allowed to change the given graph, we
        // can create a copy of array of edges
        Arrays.sort(edge);

        // Allocate memory for creating V subsets
        subset[] subsets = new subset[V];
        for (i = 0; i < V; ++i)
            subsets[i] = new subset();

        // Create V subsets with single elements
        for (int v = 0; v < V; ++v) {
            subsets[v].parent = v;
            subsets[v].rank = 0;
        }

        i = 0;  // Index used to pick next edge

        // Number of edges to be taken is equal to V-1
        while (e < V - 1) {
            // Step 2: Pick the smallest edge. And increment
            // the index for next iteration
            Edge next_edge = new Edge();
            next_edge = edge[i++];

            int x = find(subsets, next_edge.src);
            int y = find(subsets, next_edge.dest);

            // If including this edge does't cause cycle,
            // include it in result and increment the index
            // of result for next edge
            if (x != y) {
                result[e++] = next_edge;
                Union(subsets, x, y);
            }
            // Else discard the next_edge
        }

        // print the contents of result[] to display
        // the built MST
        System.out.println("Following are the edges in " +
                "the constructed MST");
        /*for (i = 0; i < e; ++i)
            System.out.println(result[i].src + " -- " +
                    result[i].dest + " == " + result[i].weight);*/

        this.resultEdges = result;
    }

    // Driver Program
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int line = 1;
        int n = 0;

        while(true) {
            if (line == 1) {
                n = Integer.parseInt(br.readLine());
                if (n == 0) { // termination point
                    break;
                } else {
                    line++;
                }

            } else {

                StringBuilder coordinatesBuilder = new StringBuilder();
                StringBuilder connectingCitiesBuilder = new StringBuilder();

                HashMap<String, Integer> mapOfSites = new HashMap<>();
                HashMap<String, Integer> startIndex = new HashMap<>();
                String[] citiesInInputOrder = new String[n];

                String city = "";
                int currentCoordinateIndex = 0;

                int ax = 0;
                int ay = 0;
                int bx = 0;
                int by = 0;

                int V = 0; // vertices --> amount of coordinates
                int E = 0; // edges

                double result = 0;

                for (int i = 0; i < n; i++) {

                    city = br.readLine();
                    String[] citySplitted = city.split(" ");
                    V += Integer.parseInt(citySplitted[1]);
                    E *= Integer.parseInt(citySplitted[1]);


                    mapOfSites.put(citySplitted[0], Integer.parseInt(citySplitted[1])); // AUSTIN, 1
                    citiesInInputOrder[i] = citySplitted[0];
                    startIndex.put(citySplitted[0], currentCoordinateIndex);

                    for (int j = 0; j < Integer.parseInt(citySplitted[1]); j++) {
                        coordinatesBuilder.append(br.readLine());
                        coordinatesBuilder.append("/");

                        currentCoordinateIndex++;
                    }
                }

                String coordinatesString = coordinatesBuilder.toString();
                String[] coordinates = coordinatesString.split("/");

                for (String co : coordinates) {
                    System.out.println(co);
                }


                // Cities to connect, only string input, N-1 times in total...
                for (int i = 0; i < n - 1; i++) {
                    connectingCitiesBuilder.append(br.readLine()).append(" ");
                }

                String[] connectingCities = connectingCitiesBuilder.toString().split(" ");

                if (connectingCities.length == 1) { // If N == 1, that is if we are not going to build any edges, then we just print 0.0 and continue on with the next input (line = 1)
                    System.out.println("0.0");
                } else {
                    // Calculating the amount of edges needed for this graph
                    for (int i = 0; i < connectingCities.length; i += 2) {
                        E += (mapOfSites.get(connectingCities[i]) * mapOfSites.get(connectingCities[i + 1]));
                    }

                    String inputOfCities = Arrays.stream(connectingCitiesBuilder.toString().split(" ")).distinct().collect(Collectors.joining(" ")); // Removes duplicates from cities-to-connect input.
                    String[] connectingCitiesTravellingPath = inputOfCities.split(" "); // Stores each city in an array. --> Traveling path in the MST, final thing to do...

                    // Build graph with specified amount of V and E.
                    Main graph = new Main(V, E);
                    int currentEdge = 0;

                    // Building the edges
                    for (int i = 0; i < connectingCities.length; i += 2) {
                        String cityA = connectingCities[i];
                        String cityB = connectingCities[i + 1];

                        // SA = Starting Index
                        int SA_cityA = startIndex.get(cityA);
                        int sitesOfCityA = mapOfSites.get(cityA);
                        int SA_cityB = startIndex.get(cityB);
                        int sitesOfCityB = mapOfSites.get(cityB);

                        for (int a = SA_cityA; a < (SA_cityA + sitesOfCityA); a++) {
                            for (int b = SA_cityB; b < (SA_cityB + sitesOfCityB); b++) {
                                String[] cityA_coordinates = coordinates[a].split(" ");
                                String[] cityB_coordinates = coordinates[b].split(" ");

                                ax = Integer.parseInt(cityA_coordinates[0]);
                                ay = Integer.parseInt(cityA_coordinates[1]);
                                bx = Integer.parseInt(cityB_coordinates[0]);
                                by = Integer.parseInt(cityB_coordinates[1]);

                                graph.edge[currentEdge].src = a;
                                graph.edge[currentEdge].dest = b;
                                graph.edge[currentEdge].weight = graph.euclideanDistance(ax, ay, bx, by);

                                currentEdge++;
                            }
                        }
                    }
                    graph.KruskalMST();
                    Edge[] resultEdges = graph.getResultEdges();
                    System.out.println();

                    for (int i = 0; i < V - 1; ++i)
                        System.out.println(resultEdges[i].src + " -- " +
                                resultEdges[i].dest + " == " + resultEdges[i].weight);


                    HashMap<Integer, String> setsInnerMap = new HashMap<>();
                    HashMap<Integer, HashMap<Integer, String>> outerMap = new HashMap<>();
                    int[][] elementSetNeighbors = new int[coordinates.length][];

                    int[] city_elements = new int[coordinates.length]; // Array of 0's, the size of all possible coordinates (that is, vertices!)
                    for (int c : city_elements) {
                        c = 0;
                    }

                    //Example: we find that the first element that belongs in every set, by correct ordering through MST traversal,
                    // lets say 0 belongs in every set. By looking up 0 in this array, we immediately get access to the total (shortest) distance
                    // connecting this element with all other sets :)
                    double[] city_elements_results = new double[coordinates.length]; // Array of 0's --> Corresponds to the total distance of elements from the array above!
                    for (double c : city_elements_results) {
                        c = 0;
                    }

                    int allSetsVisited = (n - 1); // The amount of cities to be connected - 1, as we count from 0.
                    double lengthThisFar = 0;
                    int trollCounterNeeded = 0;

                    //correspond indices (elements) with right cities

                    // Fills the sets-hashmap with elements [0..V-1, name of corresponding city]
                    for (int i = 0; i < citiesInInputOrder.length; i++) {
                        int start = startIndex.get(citiesInInputOrder[i]);
                        int end = mapOfSites.get(citiesInInputOrder[i]);

                        for (int x = start; x < (start + end); x++) {
                            setsInnerMap.put(x, citiesInInputOrder[i]);
                            elementSetNeighbors[trollCounterNeeded++] = new int[end]; // Add a new array for every element that corresponds to its size.
                        }
                    }

                    // Fill the arrays inside of the elementSetNeighbors-array with the right set neighbors.
                    for (int i = 0; i < citiesInInputOrder.length; i++) {
                        int start = startIndex.get(citiesInInputOrder[i]);
                        int end = mapOfSites.get(citiesInInputOrder[i]);
                        trollCounterNeeded = 0;

                        for (int a = start; a < (start + end); a++) {
                            for (int x = start; x < (start + end); x++) {
                                elementSetNeighbors[a][trollCounterNeeded++] = x; // Add a new array for every element that corresponds to its size.
                            }
                            trollCounterNeeded = 0;
                        }
                    }

                    // Fills the outer-map with all the fresh innermaps (outermap is used to correspond element to its visited sets)
                    // Example: Element 1 discovered a previously uncovered set which contained 4 --> This means that next time we try to look
                    // for element 1 in the same set as 4 belongs, we will get a null.
                    for (int i = 0; i < coordinates.length; i++) { // WAS LENGTH-1
                        HashMap<Integer, String> temp = new HashMap<>();
                        temp.putAll(setsInnerMap);
                        outerMap.put(i, temp);
                    }

                    //Calculating the shortest path! --> All settings need to be mutual, important.
                    for (int i = 0; i < resultEdges.length; i++) { // Loop through edges
                        HashMap<Integer, String> setController = outerMap.get(resultEdges[i].src); // setController corresponds to the setsInnerMap of that element. //PUT-AGAIN(?)
                        if (setController.get(resultEdges[i].dest) != null) { // If this elements hashmap has not previously visited the same set that the destination node belongs to
                            int[] neighborsOfElement = elementSetNeighbors[resultEdges[i].dest];
                            for (int j = 0; j < neighborsOfElement.length; j++) {
                                setController.put(neighborsOfElement[j], null); // Make sure that all this elements neighbors get set to null so that we don't accidentally think that this set was previously unvisited,
                            }
                            city_elements[resultEdges[i].src]++;
                            city_elements_results[resultEdges[i].src] += resultEdges[i].weight;
                            if (city_elements[resultEdges[i].src] == allSetsVisited) { // We have found a WINNER;
                                System.out.println(city_elements_results[resultEdges[i].src]);
                                break;
                            }
                        }

                        // Same for reverse the reverse element, the source of the destination
                        HashMap<Integer, String> setControllerReverse = outerMap.get(resultEdges[i].dest); // setController corresponds to the setsInnerMap of that element. //PUT-AGAIN(?)
                        if (setControllerReverse.get(resultEdges[i].src) != null) {
                            int[] neighborsOfElement = elementSetNeighbors[resultEdges[i].src];
                            for (int j = 0; j < neighborsOfElement.length; j++) {
                                setControllerReverse.put(neighborsOfElement[j], null);
                            }
                            city_elements[resultEdges[i].dest]++;
                            city_elements_results[resultEdges[i].dest] += resultEdges[i].weight;
                            if (city_elements[resultEdges[i].dest] == allSetsVisited) { // We have found a WINNER;
                                System.out.println(city_elements_results[resultEdges[i].dest]);
                                break;
                            }
                        }
                    }
                    connectingCitiesBuilder.setLength(0);
                    coordinatesBuilder.setLength(0);
                }
                line = 1;
            }
        }
    }

    private double euclideanDistance(int ax, int ay, int bx, int by) {
        return Math.sqrt((bx - ax)*(bx - ax) + (by - ay)*(by - ay));
    }
}
