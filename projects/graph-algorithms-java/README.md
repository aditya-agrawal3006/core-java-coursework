# Graph Algorithms in Java

A generic, directed weighted graph library built around adjacency maps. Vertices are identified by name and can store application-defined data, while directed edges carry integer costs.

## Supported operations

- Add vertices and directed weighted edges.
- Query neighbors, edge costs, vertex names, and stored vertex data.
- Traverse graphs with **breadth-first search (BFS)** and **depth-first search (DFS)**.
- Find a minimum-cost route using **Dijkstra's shortest-path algorithm**.
- Use callbacks to observe the order in which graph vertices are visited.

## Implementation notes

The graph maintains two hash maps: one for adjacency/cost information and one for generic vertex data. BFS and DFS visit vertices through the adjacency structure; shortest-path calculations use a priority queue to choose the next candidate.

## Code structure

- [`Graph.java`](src/implementation/Graph.java) — adjacency representation, graph API, traversals, and shortest paths.
- [`CallBack.java`](src/implementation/CallBack.java) — callback interface for visited vertices.
- [`PrintCallBack.java`](src/implementation/PrintCallBack.java) — example callback implementation.

## Compile

This is a **library project**, not a standalone application with a `main` method. From its directory, use:

```bash
javac -d out $(find src -name '*.java')
```

The main entry points for reading the code are `doBreadthFirstSearch`, `doDepthFirstSearch`, and `doDijkstras` in `Graph.java`.

**Technical focus:** graph representations, maps/sets, queues, priority queues, traversals, weighted paths, and algorithm design.

> University of Maryland Java coursework. The graph API and certain interfaces originated from the course.
