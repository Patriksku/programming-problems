package best_friends_4647;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 ---All of these nodes below are valid from plane 2 and forward---

 [NDL] Node down-left --> starts 2, each level increment 1
 [NDR] Node down-right --> starts 3, each level increment 1
 [NL] Node left --> [currentNode]-1
 [NR] Node right --> [currentNode]+1
 [NUL] Node up-left --> starts 2, each level increment 1
 [NUR] Node up-right --> starts 1, each level increment 1

 plane --> starts 1, each level increment 2 [Last plane is 141]

 Special case 1st plane, neighbors of V1 --> V2 & V3

 After plane 1, each plane will have if-statements deciding if current node is mostleft, middle or mostright.
 - leftmost: 4 neighbors
 - rightmost: 4 neighbors
 - middle: 6 neighbors

 Special case last plane (141) {
 - leftmost: 2 neighbors
 - middle: 4 neighbors
 - rightmost: 2 neighbors
 }

 Illustration:

    [NUL]	[NUR]
 [NL]	  X 	 [NR]
    [NDL]	[NDR]

 */
public class Main {

    private void addEdge(ArrayList<ArrayList<Integer>> adj, int i, int j) {
        adj.get(i).add(j);
    }

    // function to print the shortest distance and path between source vertex and destination vertex
    private void printShortestDistance(ArrayList<ArrayList<Integer>> adj, int s, int dest, int V) {

        // predecessor[i] array stores predecessor of i, and distance array stores distance of i from s
        int pred[] = new int[V];
        int dist[] = new int[V];

        if (BFS(adj, s, dest, V, pred, dist) == false) {
            /*System.out.println("Given source and destination are not connected");*/
            return;
        }

        // LinkedList to store path - No real function in this assignment other than experimentation.
        LinkedList<Integer> path = new LinkedList<Integer>();
        int crawl = dest;
        path.add(crawl);
        while (pred[crawl] != -1) {
            path.add(pred[crawl]);
            crawl = pred[crawl];
        }

        // Print distance
        System.out.println(dist[dest]);

        // Print path
        /*System.out.println("Path is ::");
        for (int i = path.size() - 1; i >= 0; i--) {
            System.out.print((path.get(i) + 1) + " ");
        }*/
    }

    // a modified version of BFS that stores predecessor
    // of each vertex in array pred
    // and its distance from source in array dist
    private boolean BFS(ArrayList<ArrayList<Integer>> adj, int src, int dest, int V, int pred[], int dist[]) {

        // a queue to maintain queue of vertices whose
        // adjacency list is to be scanned as per normal
        // BFS algorithm using LinkedList of Integer type
        LinkedList<Integer> queue = new LinkedList<Integer>();

        // boolean array visited[] which stores the
        // information whether ith vertex is reached
        // at least once in the Breadth first search
        boolean visited[] = new boolean[V];

        // initially all vertices are unvisited
        // so v[i] for all i is false
        // and as no path is yet constructed
        // dist[i] for all i set to infinity
        for (int i = 0; i < V; i++) {
            visited[i] = false;
            dist[i] = Integer.MAX_VALUE;
            pred[i] = -1;
        }

        // now source is first to be visited and
        // distance from source to itself should be 0
        visited[src] = true;
        dist[src] = 0;
        queue.add(src);

        // bfs Algorithm
        while (!queue.isEmpty()) {
            int u = queue.remove();
            for (int i = 0; i < adj.get(u).size(); i++) {
                if (visited[adj.get(u).get(i)] == false) {
                    visited[adj.get(u).get(i)] = true;
                    dist[adj.get(u).get(i)] = dist[u] + 1;
                    pred[adj.get(u).get(i)] = u;
                    queue.add(adj.get(u).get(i));

                    // stopping condition (when we find our destination)
                    if (adj.get(u).get(i) == dest)
                        return true;
                }
            }
        }
        return false;
    }

    // Builds the graph according to the problem formulation.
    public void buildGraph(int V, ArrayList<ArrayList<Integer>> adj) {
        int plane = 1;
        int leftMost = 1; // In reality 2
        int rightMost = 2; // In reality 3
        int NDL = 2;
        int NDR = 3;
        int NUL = 2;
        int NUR = 1;

        // [Vertices start at 0, so each i and j is to be incremented with 1 to get an actual representation of the geometrical pattern]
        for (int i = 0; i < V; i++) {

            if (plane == 1) { // Cover special-case where plane equals 1.
                addEdge(adj, 0, 1); // 1 --> 2
                addEdge(adj, 0, 2); // 1 --> 3
                plane++;

            } else if (plane != 141) { // Cover all the planes after the first one, except for the last plane.

                if (i == leftMost) { // Current vertex is a left-most node. (4 neighbors)
                    addEdge(adj, i, (i - NUR));
                    addEdge(adj, i, (i + 1));
                    addEdge(adj, i, (i + NDR));
                    addEdge(adj, i, (i + NDL));

                    leftMost += NDL;

                } else if (i == rightMost) { // Current vertex is a right-most node. (4 neighbors)
                    addEdge(adj, i, (i - NUL));
                    addEdge(adj, i, (i - 1));
                    addEdge(adj, i, (i + NDL));
                    addEdge(adj, i, (i + NDR));

                    rightMost += NDR;

                    NDL++;
                    NDR++;
                    NUL++;
                    NUR++;

                    plane++;

                } else { // Current vertex is a middle node. (6 neighbors)
                    addEdge(adj, i, (i - NUL));
                    addEdge(adj, i, (i - 1));
                    addEdge(adj, i, (i + NDL));
                    addEdge(adj, i, (i + NDR));
                    addEdge(adj, i, (i + 1));
                    addEdge(adj, i, (i - NUR));
                }

            } else { // Cover special-case where plane equals 141 (last plane out of 10000 nodes).

                if (i == leftMost) {
                    addEdge(adj, i, (i - NUR));
                    addEdge(adj, i, (i + 1));
                    leftMost += NDL;
                }

                else if (i == 9999) {
                    addEdge(adj, i, (i - NUL));
                    addEdge(adj, i, (i - 1));
                    break;

                } else {
                    addEdge(adj, i, (i - NUL));
                    addEdge(adj, i, (i - 1));
                    addEdge(adj, i, (i + 1));
                    addEdge(adj, i, (i - NUR));
                }
            }
        }
    }

    // Visualization of the entire graph from the problem formulation.
    public void printGraph(int V) {
        System.out.println();

        int plane = 1;
        int counter = 0;
        for (int i = 1; i <= V; i++) {
            System.out.print(i + " ");
            if (i == V) {
                System.out.print(" --> Plane: " + plane);
            }
            counter++;
            if (plane == counter) {
                counter = 0;

                System.out.print(" --> Plane: " + plane++);
                System.out.println();
            }
        }
    }

    public static void main(String[]args) throws IOException {

        Main main = new Main();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = "";


        //-------------------------------------------------------------------------------------------------------------
        int V = 10000;

        // Adjacency list for storing which vertices are connected
        ArrayList<ArrayList<Integer>> adj = new ArrayList<ArrayList<Integer>>(V);
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<Integer>());
        }

        main.buildGraph(V, adj);
        //-------------------------------------------------------------------------------------------------------------


        while(!((input = br.readLine()).equals("0 0"))) {
            String[] arrOfInput = input.split(" ");
            int[] values = new int[arrOfInput.length];

            for (int i = 0; i < arrOfInput.length; i++) {
                values[i] = Integer.parseInt(arrOfInput[i]);
            }

            if (values[0] == values[1]) { // If Petey and Patty or standing on the same circle.
                System.out.println(0);
            } else {
                main.printShortestDistance(adj, values[0] - 1, values[1] - 1, V);
            }
        }
    }
}
