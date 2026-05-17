import java.util.*;

public class Graph {

    private Map<Integer, Vertex> vertices;
    private Map<Integer, List<Vertex>> adjacencyList;

    public Graph() {
        vertices = new LinkedHashMap<>();
        adjacencyList = new LinkedHashMap<>();
    }

    public void addVertex(Vertex v) {
        if (!vertices.containsKey(v.getId())) {
            vertices.put(v.getId(), v);
            adjacencyList.put(v.getId(), new ArrayList<>());
        }
    }

    public void addEdge(int from, int to) {
        if (!vertices.containsKey(from) || !vertices.containsKey(to)) {
            System.out.println("Vertex " + from + " does not exist");
            return;
        }
        Vertex destination = vertices.get(to);
        adjacencyList.get(from).add(destination);
    }

    public void printGraph() {
        System.out.println("Graph adjacency list:");
        for (Map.Entry<Integer, List<Vertex>> entry : adjacencyList.entrySet()) {
            Vertex v = vertices.get(entry.getKey());
            List<Vertex> neighbors = entry.getValue();
            System.out.print("  " + v + " -> [");
            for (int i = 0; i < neighbors.size(); i++) {
                System.out.print(neighbors.get(i));
                if (i < neighbors.size() - 1) System.out.print(", ");
            }
            System.out.println("]");
        }
    }

    public List<Integer> bfs(int start) {
        List<Integer> order = new ArrayList<>();

        if (!vertices.containsKey(start)) {
            System.out.println("Start vertex " + start + " not found.");
            return order;
        }

        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();

        visited.add(start);
        queue.add(start);

        System.out.print("BFS from V" + start + ": ");

        while (!queue.isEmpty()) {
            int currentId = queue.poll();
            order.add(currentId);
            System.out.print("V" + currentId + " ");

            for (Vertex neighbor : adjacencyList.get(currentId)) {
                if (!visited.contains(neighbor.getId())) {
                    visited.add(neighbor.getId());
                    queue.add(neighbor.getId());
                }
            }
        }
        System.out.println();
        return order;
    }

    public List<Integer> dfs(int start) {
        List<Integer> order = new ArrayList<>();

        if (!vertices.containsKey(start)) {
            System.out.println("Start vertex " + start + " not found.");
            return order;
        }

        Set<Integer> visited = new HashSet<>();
        Deque<Integer> stack = new ArrayDeque<>();

        stack.push(start);

        System.out.print("DFS from V" + start + ": ");

        while (!stack.isEmpty()) {
            int currentId = stack.pop();

            if (!visited.contains(currentId)) {
                visited.add(currentId);
                order.add(currentId);
                System.out.print("V" + currentId + " ");

                List<Vertex> neighbors = adjacencyList.get(currentId);
                for (int i = neighbors.size() - 1; i >= 0; i--) {
                    Vertex neighbor = neighbors.get(i);
                    if (!visited.contains(neighbor.getId())) {
                        stack.push(neighbor.getId());
                    }
                }
            }
        }
        System.out.println();
        return order;
    }

    public int getVertexCount() {
        return vertices.size();
    }

    public int getEdgeCount() {
        int count = 0;
        for (List<Vertex> neighbors : adjacencyList.values()) {
            count += neighbors.size();
        }
        return count;
    }
}