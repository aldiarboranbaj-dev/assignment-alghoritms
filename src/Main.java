public class Main {

    public static void main(String[] args) {

        System.out.println("\nSMALL GRAPH (10 vertices) - DEMO\n");

        Graph smallGraph = new Graph();

        for (int i = 0; i < 10; i++) {
            smallGraph.addVertex(new Vertex(i));
        }

        smallGraph.addEdge(0, 1);
        smallGraph.addEdge(0, 3);
        smallGraph.addEdge(1, 2);
        smallGraph.addEdge(1, 4);
        smallGraph.addEdge(2, 5);
        smallGraph.addEdge(3, 4);
        smallGraph.addEdge(3, 6);
        smallGraph.addEdge(4, 5);
        smallGraph.addEdge(4, 7);
        smallGraph.addEdge(5, 8);
        smallGraph.addEdge(6, 7);
        smallGraph.addEdge(7, 8);
        smallGraph.addEdge(7, 9);
        smallGraph.addEdge(8, 9);

        smallGraph.printGraph();
        System.out.println();

        long bfsStart = System.nanoTime();
        smallGraph.bfs(0);
        long bfsEnd = System.nanoTime();
        System.out.println("BFS execution time: " + (bfsEnd - bfsStart) + " ns");

        long dfsStart = System.nanoTime();
        smallGraph.dfs(0);
        long dfsEnd = System.nanoTime();
        System.out.println("DFS execution time: " + (dfsEnd - dfsStart) + " ns");

        System.out.println();
        Experiment experiment = new Experiment();
        experiment.runMultipleTests();
        experiment.printResults();
    }
}
