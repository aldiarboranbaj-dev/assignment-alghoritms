public class Main {

    public static void main(String[] args) {

        System.out.println("  SMALL GRAPH (10 vertices)");

        Graph smallGraph = Experiment.buildGraph(10);
        smallGraph.printGraph();
        System.out.println();

        Experiment exp = new Experiment();
        exp.runTraversals(smallGraph);

        System.out.println("  DIJKSTRA (weighted graph, 6 vertices)");


        Graph weighted = new Graph();
        for (int i = 0; i < 6; i++) weighted.addVertex(new Vertex(i));

        weighted.addEdge(0, 1, 4);
        weighted.addEdge(0, 2, 1);
        weighted.addEdge(2, 1, 2);
        weighted.addEdge(1, 3, 1);
        weighted.addEdge(2, 3, 5);
        weighted.addEdge(3, 4, 3);
        weighted.addEdge(4, 5, 2);

        weighted.printGraph();
        System.out.println();
        weighted.dijkstra(0);

        System.out.println("  PERFORMANCE EXPERIMENTS");

        exp.runMultipleTests();
        exp.printResults();
    }
}