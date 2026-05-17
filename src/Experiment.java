import java.util.*;

public class Experiment {
    private List<long[]> results;

    public Experiment() {
        results = new ArrayList<>();
    }

    public void runTraversals(Graph g, String label, int start) {
        System.out.println("\n--- " + label + " ---");
        System.out.println("Vertices: " + g.getVertexCount() + " | Edges: " + g.getEdgeCount());
        long bfsStart = System.nanoTime();
        g.bfs(start);
        long bfsEnd = System.nanoTime();
        long bfsTime = bfsEnd - bfsStart;

        long dfsStart = System.nanoTime();
        g.dfs(start);
        long dfsEnd = System.nanoTime();
        long dfsTime = dfsEnd - dfsStart;

        System.out.println("BFS time: " + bfsTime + " ns");
        System.out.println("DFS time: " + dfsTime + " ns");

        results.add(new long[]{g.getVertexCount(), bfsTime, dfsTime});
    }

    public void runMultipleTests() {
        System.out.println("GRAPH TRAVERSAL EXPERIMENT RESULTS");

        Graph small = buildGraph(10);
        runTraversals(small, "Small Graph (10 vertices)", 0);

        Graph medium = buildGraph(30);
        runTraversals(medium, "Medium Graph (30 vertices)", 0);

        Graph large = buildGraph(100);
        runTraversals(large, "Large Graph (100 vertices)", 0);
    }

    private Graph buildGraph(int n) {
        Graph g = new Graph();

        for (int i = 0; i < n; i++) {
            g.addVertex(new Vertex(i));
        }
        for (int i = 0; i < n - 1; i++) {
            g.addEdge(i, i + 1);
        }
        for (int i = 0; i + 3 < n; i += 3) {
            g.addEdge(i, i + 3);
        }
        for (int i = 5; i < n; i += 5) {
            g.addEdge(i, i - 5);
        }
        return g;
    }

    public void printResults() {
        System.out.println("\nPERFORMANCE SUMMARY TABLE");

        for (long[] row : results) {
            long vertices = row[0];
            long bfsTime  = row[1];
            long dfsTime  = row[2];
            String faster = bfsTime < dfsTime ? "BFS" : "DFS";

            System.out.println("Vertices: " + vertices + " | BFS: " + bfsTime + " ns" + " | DFS: " + dfsTime + " ns" + " | Faster: " + faster);
        }
    }
}