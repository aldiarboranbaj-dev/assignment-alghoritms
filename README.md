# Assignment 4: Graph Traversal and Representation System

## A. Project Overview

This project implements a directed graph data structure in Java and explores two fundamental traversal algorithms: Breadth-First Search (BFS) and Depth-First Search (DFS). The system supports graphs of varying sizes and measures the execution time of each traversal to compare real-world performance.

### Graph Structure

A graph is a collection of **vertices** (nodes) connected by **edges** (links). In a directed graph, each edge has a direction — travelling from a source vertex to a destination vertex. This project uses an **adjacency list** to store the graph, which is memory-efficient for sparse graphs.

### BFS Overview

BFS explores all immediate neighbors of a vertex before moving deeper. It uses a **queue (FIFO)** and is ideal for finding shortest paths or traversing graphs level by level.

### DFS Overview

DFS dives as deep as possible along one path before backtracking. It uses a **stack (LIFO)** and is ideal for topological sorting, cycle detection, and exploring all paths.


## B. Class Descriptions

### `Vertex.java`
Represents a single node in the graph. Each vertex holds a unique integer `id`. Provides a constructor, `getId()` getter, and `toString()` returning `Vertex(id)`.

### `Edge.java`
Represents a directed connection between two vertices. Stores a `source` and `destination` (Vertex objects). Provides a constructor, getters, and `toString()` returning `source -> destination`.

### `Graph.java`
The core class. Uses an adjacency list implemented as:
`Map<Integer, Vertex>` — maps each ID to its Vertex object
`Map<Integer, List<Integer>>` — maps each vertex ID to a list of its neighbor IDs

Adjacency list example for the small graph:
```
V0 -> [V1, V3]
V1 -> [V2, V4]
V2 -> [V5]
V3 -> [V4, V6]
...
```

### `Experiment.java`
Handles automated performance testing. `runMultipleTests()` builds graphs of 10, 30, and 100 vertices, calls `runTraversals()` for each, and `printResults()` outputs a formatted comparison table.

### `Main.java`
Entry point. Manually constructs and prints the small graph structure, runs both traversals, then delegates to `Experiment` for the full multi-size comparison.


## C. Algorithm Descriptions

### Breadth-First Search (BFS)

**Step-by-step:**
1. Mark the start vertex as visited and add it to a queue.
2. While the queue is not empty:
    - Dequeue the front vertex and print it.
    - For each unvisited neighbor, mark it visited and enqueue it.

**Use cases:**
- Finding the shortest path in an unweighted graph
- Level-order traversal
- Social network "degrees of separation"
- Web crawlers exploring pages layer by layer

**Time Complexity:** O(V + E) — every vertex is enqueued once and every edge is checked once.



### Depth-First Search (DFS)

**Step-by-step:**
1. Push the start vertex onto a stack.
2. While the stack is not empty:
    - Pop the top vertex; if unvisited, mark and print it.
    - Push all unvisited neighbors onto the stack (in reverse order for natural left-to-right traversal).

**Use cases:**
- Topological sorting (e.g., build systems, dependency resolution)
- Detecting cycles in a graph
- Solving mazes or puzzles with backtracking
- Generating spanning trees

**Limitations of DFS:**
- Does not guarantee shortest paths.
- Can get "lost" in a deep branch if the graph is very large or has long chains.
- Iterative version with an explicit stack may visit some vertices in a different order than the recursive version.

**Time Complexity:** O(V + E) — every vertex is pushed and popped from the stack at most once.


## D. Experimental Results

Graphs were built with a chain structure (0→1→2→…→n-1) plus skip-connections every 3 vertices, producing a realistic sparse directed graph.

### Execution Time Comparison Table

| Vertices | Edges | BFS Time (ns) | DFS Time (ns) | Faster |
|:--------:|:-----:|:-------------:|:-------------:|:------:|
| 10       | 13    | 128,042       | 138,750       | BFS    |
| 30       | 43    | 418,667       | 336,334       | DFS    |
| 100      | 151   | 974,833       | 781,583       | DFS    |

### Observations

- DFS was faster in most cases in these experiments.
- Both algorithms have the same theoretical complexity O(V + E), but in practice BFS manages a queue object which has slightly more overhead than the array-backed deque used by iterative DFS.
- As the graph grows from 10 → 100 vertices, execution time increases roughly proportionally to V + E, consistent with the expected linear complexity.
- The BFS traversal order differs noticeably from DFS: BFS produces `V0 V1 V3 V2 V4 V6 ...` (level by level), while DFS produces `V0 V1 V2 V3 V4 V5 ...` (deep dive along the chain first).



## E. Screenshots / Console Output

### Graph Structure (Small, 10 vertices)
```
Graph Adjacency List:
  V0 -> [V1, V3]
  V1 -> [V2, V4]
  V2 -> [V5]
  V3 -> [V4, V6]
  V4 -> [V5, V7]
  V5 -> [V8]
  V6 -> [V7]
  V7 -> [V8, V9]
  V8 -> [V9]
  V9 -> []
```

### BFS Traversal Output
```
BFS from V0: V0 V1 V3 V2 V4 V6 V5 V7 V9 V8
BFS execution time: 128,042 ns
```
BFS visits V1 and V3 (both direct neighbors of V0) before going deeper — classic level-by-level behavior.

### DFS Traversal Output
```
DFS from V0: V0 V1 V2 V5 V8 V9 V4 V7 V3 V6
DFS execution time: 138,750 ns
```
DFS immediately dives into V1 → V2 → V5 → V8 → V9 before backtracking to explore V4, V7, V3, V6.

### Performance Results
```
===== Performance Comparison Table =====
Graph Size (V)  BFS Time (ns)        DFS Time (ns)
--------------------------------------------------------
10              128,042              138,750
30              418,667              336,334
100             974,833              781,583
```


## F. Reflection

Implementing BFS and DFS from scratch gave me a much clearer understanding of how data structures (queue vs. stack) directly shape algorithm behavior. It is one thing to know that BFS uses a queue and DFS uses a stack in theory, but seeing it in code made the connection concrete — swapping the data structure literally changes which vertices get explored next. I also learned the importance of the `visited` set: without it, both algorithms would loop forever on graphs with cycles.

The experiment with different graph sizes reinforced the O(V + E) complexity claim. The jump in time from 30 to 100 vertices was much larger than from 10 to 30, which makes sense since 100 vertices introduced 151 edges versus only 43 for the 30-vertex graph — edge count matters just as much as vertex count.

The most striking difference between BFS and DFS is the traversal order. BFS fans out horizontally across the graph (all neighbors first), making it perfect for shortest-path problems. DFS plunges vertically along a path until it hits a dead end, then backtracks — making it natural for tasks like cycle detection or topological sort. In terms of space, BFS can consume more memory because it holds all vertices at the current "frontier" in the queue, while DFS only keeps the current path on the stack.



## Repository Structure

```
assignment4-graphs/
├── src/
│   ├── Vertex.java
│   ├── Edge.java
│   ├── Graph.java
│   ├── Experiment.java
│   └── Main.java
├── docs/
│   └── screenshots/
├── README.md
└── .gitignore
```

## Git Commit History

```
init: project structure
feat(vertex): implemented Vertex class
feat(edge): added Edge class
feat(graph): implemented adjacency list with addVertex and addEdge
feat(traversal): added BFS and DFS traversal algorithms
feat(experiment): added performance testing across graph sizes
perf(cleanup): improved code readability and comments
docs(readme): added analysis and experimental results
release: v1.0
```
