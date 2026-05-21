/**
 * Edge represents a directed connection between two vertices.
 * Now includes a weight field for Dijkstra's algorithm.
 */
public class Edge {

    private Vertex source;
    private Vertex destination;
    private int weight;  // added for Dijkstra

    /**
     * Constructs a weighted edge from source to destination.
     */
    public Edge(Vertex source, Vertex destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    public Vertex getSource() {
        return source;
    }

    public Vertex getDestination() {
        return destination;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return source.getId() + " -(" + weight + ")-> " + destination.getId();
    }
}