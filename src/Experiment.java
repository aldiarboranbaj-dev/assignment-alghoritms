import java.util.*;

public class Experiment {

    private long[][] results;
    private int[] sizes;

    public Experiment() {
        sizes   = new int[]{10, 30, 100};
        results = new long[sizes.length][2];
    }

    public void runTraversals(Graph g) {
        System.out.println("--- Traversal output for graph with "
                + g.getVertexCount() + " vertices ---");
        g.bfs(0);
        g.dfs(0);
        System.out.println();
    }

    public void runMultipleTests() {
        System.out.println("--- Performance Tests ---");
        for (int i = 0; i < sizes.length; i++) {
            int size = sizes[i];
            Graph g = buildGraph(size);

            long startBFS = System.nanoTime();
            bfsSilent(g, 0);
            long endBFS = System.nanoTime();
            results[i][0] = endBFS - startBFS;

            long startDFS = System.nanoTime();
            dfsSilent(g, 0);
            long endDFS = System.nanoTime();
            results[i][1] = endDFS - startDFS;

            System.out.println("Size " + size + " | V=" + g.getVertexCount()
                    + " E=" + g.getEdgeCount()
                    + " | BFS=" + results[i][0] + " ns"
                    + " | DFS=" + results[i][1] + " ns");
        }
    }

    public void printResults() {
        System.out.println();
        System.out.println("===== Performance Comparison Table =====");
        System.out.printf("%-14s %-20s %-20s%n",
                "Graph Size (V)", "BFS Time (ns)", "DFS Time (ns)");
        System.out.println("-".repeat(56));
        for (int i = 0; i < sizes.length; i++) {
            System.out.printf("%-14d %-20d %-20d%n",
                    sizes[i], results[i][0], results[i][1]);
        }
        System.out.println();
    }

    public static Graph buildGraph(int size) {
        Graph g = new Graph();
        for (int i = 0; i < size; i++) {
            g.addVertex(new Vertex(i));
        }
        for (int i = 0; i < size; i++) {
            g.addEdge(i, (i + 1) % size);
        }
        for (int i = 0; i < size - 2; i += 3) {
            g.addEdge(i, (i + 2) % size);
        }
        return g;
    }

    private void bfsSilent(Graph g, int start) {
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        visited.add(start);
        queue.offer(start);
        while (!queue.isEmpty()) {
            int current = queue.poll();
            for (int[] neighbor : g.getNeighbors(current)) {
                if (!visited.contains(neighbor[0])) {
                    visited.add(neighbor[0]);
                    queue.offer(neighbor[0]);
                }
            }
        }
    }

    private void dfsSilent(Graph g, int start) {
        Set<Integer> visited = new HashSet<>();
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(start);
        while (!stack.isEmpty()) {
            int current = stack.pop();
            if (!visited.contains(current)) {
                visited.add(current);
                List<int[]> neighbors = g.getNeighbors(current);
                for (int i = neighbors.size() - 1; i >= 0; i--) {
                    if (!visited.contains(neighbors.get(i)[0])) {
                        stack.push(neighbors.get(i)[0]);
                    }
                }
            }
        }
    }
}