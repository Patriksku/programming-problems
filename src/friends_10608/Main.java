package friends_10608;

import java.io.IOException;
import java.util.*;

public class Main {

    // Result object used for keeping track of the people that have already been visited by DFS,
    // and also to return the current longest chain of friends in case where the DFS tries to start
    // a traverse on a already visited node. This object allows for skipping to the next node if it
    // has already been visited.
    public static class Result {
        boolean[] visited;
        int chainOfFriends;

        public Result() {}

        public Result(boolean[] visited, int chainOfFriends) {
            this.visited = visited;
            this.chainOfFriends = chainOfFriends;
        }
    }

    // Edge class for maintaining a list of "friends of friends".
    public static class Edge {
        int from, to;

        public Edge(int from, int to) {
            this.from = from;
            this.to = to;
        }
    }

    // The DFS
    static Result DFS(Map<Integer, List<Edge>> graph, boolean[] visited, int longestChainOfFriends, int start) {

        int chainOfFriends = 0;
        Stack<Integer> stack = new Stack<>();

        // If this node has already been visited, simply return the visited array and the current
        // longest chain of friends. This will make sure that everything stays exactly the same, and we
        // can move along to the next node.
        if (visited[start]) {
            return new Result(visited, longestChainOfFriends);
        }

        // Push the node onto the stack.
        stack.push(start);

        while (!stack.isEmpty()) {
            int currentNode = stack.pop();

            if (!visited[currentNode]) {

                chainOfFriends++;
                visited[currentNode] = true;

                // Get the list of edges, i.e. the friends of this friend.
                List<Edge> edges = graph.get(currentNode);

                if (edges != null) {
                    for (Edge edge : edges) {
                        if (!visited[edge.to]) {
                            stack.push(edge.to);
                        }
                    }
                }
            }
        }
        return new Result(visited, chainOfFriends);
    }

    // Method for adding edges, that is, friend-relations between "from" and "to".
    private static void addEdge(Map<Integer, List<Edge>> graph, int from, int to) {

        // Get the list of friends for "from".
        List<Edge> list = graph.get(from);

        // If it does not yet exist, create it and put it in the graph.
        if (list == null) {
            list = new ArrayList<Edge>();
            graph.put(from, list);
        }

        // Add friendship.
        list.add(new Edge(from, to));
    }


    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        int testCases;
        int currentTestCase = 0;

        testCases = scanner.nextInt();

        while (currentTestCase != testCases) {

            // Object used for conveying information to and from the DFS, that is,
            // The list of visited nodes and the current longest chain of friends.
            Result resultObject = new Result();
            int longestChainOfFriends = 0;

            int n = scanner.nextInt();
            int m = scanner.nextInt();

            boolean[] visited = new boolean[n]; // Every citizen

            // Create the graph.
            // Integer identifies the person, whilst the List contains that persons friends.
            Map<Integer, List<Edge>> graph = new HashMap<>();

            for (int i = 0; i < m; i++) {
                int a = (scanner.nextInt() - 1);
                int b = (scanner.nextInt() - 1);

                // Undirected graph.
                addEdge(graph, a, b);
                addEdge(graph, b, a);
            }

            // For every citizen
            for (int i = 0; i < n; i++) {
                resultObject = DFS(graph, visited, longestChainOfFriends, i);

                visited = resultObject.visited;

                // If we have found a longer chain of friends with this DFS, update the value.
                if (resultObject.chainOfFriends > longestChainOfFriends) {
                    longestChainOfFriends = resultObject.chainOfFriends;
                }
            }

            System.out.println(longestChainOfFriends);
            currentTestCase++;
        }
    }
}
