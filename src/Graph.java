import java.util.*;

public class Graph {

    private Map<Integer, List<int[]>> adjacencyList;
    private Map<Integer, Vertex> vertices;

    public Graph() {
        adjacencyList = new HashMap<>();
        vertices = new HashMap<>();
    }

    public void addVertex(Vertex v) {
        if (!vertices.containsKey(v.getId())) {
            vertices.put(v.getId(), v);
            adjacencyList.put(v.getId(), new ArrayList<>());
        }
    }

    public void addEdge(int from, int to) {
        addEdge(from, to, 1);
    }

    public void addEdge(int from, int to, int weight) {
        if (!adjacencyList.containsKey(from) || !adjacencyList.containsKey(to)) {
            throw new IllegalArgumentException(
                    "Both vertices must exist: " + from + " -> " + to
            );
        }
        adjacencyList.get(from).add(new int[]{to, weight});
    }

    public List<int[]> getNeighbors(int id) {
        return adjacencyList.getOrDefault(id, Collections.emptyList());
    }

    public Set<Integer> getVertexIds() {
        return adjacencyList.keySet();
    }

    public void printGraph() {
        System.out.println("Graph adjacency list (" + getVertexCount()
                + " vertices, " + getEdgeCount() + " edges):");
        List<Integer> sortedKeys = new ArrayList<>(adjacencyList.keySet());
        Collections.sort(sortedKeys);
        for (int id : sortedKeys) {
            System.out.print("  " + id + " -> ");
            List<int[]> neighbors = adjacencyList.get(id);
            List<String> parts = new ArrayList<>();
            for (int[] n : neighbors) {
                parts.add(n[0] + "(w=" + n[1] + ")");
            }
            System.out.println(parts);
        }
    }

    public void bfs(int start) {
        if (!adjacencyList.containsKey(start)) return;

        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        visited.add(start);
        queue.offer(start);

        System.out.print("BFS from " + start + ": ");
        while (!queue.isEmpty()) {
            int current = queue.poll();
            System.out.print(current + " ");
            for (int[] neighbor : adjacencyList.get(current)) {
                if (!visited.contains(neighbor[0])) {
                    visited.add(neighbor[0]);
                    queue.offer(neighbor[0]);
                }
            }
        }
        System.out.println();
    }

    public void dfs(int start) {
        if (!adjacencyList.containsKey(start)) return;

        Set<Integer> visited = new HashSet<>();
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(start);

        System.out.print("DFS from " + start + ": ");
        while (!stack.isEmpty()) {
            int current = stack.pop();
            if (!visited.contains(current)) {
                visited.add(current);
                System.out.print(current + " ");
                List<int[]> neighbors = adjacencyList.get(current);
                for (int i = neighbors.size() - 1; i >= 0; i--) {
                    if (!visited.contains(neighbors.get(i)[0])) {
                        stack.push(neighbors.get(i)[0]);
                    }
                }
            }
        }
        System.out.println();
    }

    public void dijkstra(int start) {
        int V = vertices.size();

        List<Integer> ids = new ArrayList<>(vertices.keySet());
        Collections.sort(ids);

        int[] dist = new int[V];
        boolean[] visited = new boolean[V];

        Arrays.fill(dist, Integer.MAX_VALUE);

        int startIndex = ids.indexOf(start);
        dist[startIndex] = 0;

        System.out.println("Dijkstra from vertex " + start + ":");

        for (int i = 0; i < V; i++) {

            int uIndex = -1;
            for (int j = 0; j < V; j++) {
                if (!visited[j] && (uIndex == -1 || dist[j] < dist[uIndex])) {
                    uIndex = j;
                }
            }

            if (dist[uIndex] == Integer.MAX_VALUE) break;

            visited[uIndex] = true;
            int u = ids.get(uIndex);

            for (int[] neighbor : adjacencyList.get(u)) {
                int vId = neighbor[0];
                int weight = neighbor[1];
                int vIndex = ids.indexOf(vId);

                if (dist[uIndex] + weight < dist[vIndex]) {
                    dist[vIndex] = dist[uIndex] + weight;
                }
            }
        }

        for (int i = 0; i < V; i++) {
            if (dist[i] == Integer.MAX_VALUE) {
                System.out.println("  " + start + " -> " + ids.get(i) + " : unreachable");
            } else {
                System.out.println("  " + start + " -> " + ids.get(i) + " : " + dist[i]);
            }
        }
        System.out.println();
    }

    public int getVertexCount() {
        return vertices.size();
    }

    public int getEdgeCount() {
        int count = 0;
        for (List<int[]> neighbors : adjacencyList.values()) {
            count += neighbors.size();
        }
        return count;
    }
}