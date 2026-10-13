package implementation;

import java.util.*;

/**
 * Implements a graph. We use two maps: one map for adjacency properties 
 * (adjancencyMap) and one map (dataMap) to keep track of the data associated 
 * with a vertex. 
 * 
 * @author cmsc132
 * 
 * @param <E>
 */
public class Graph<E> {
	/* You must use the following maps in your implementation */
	private HashMap<String, HashMap<String, Integer>> adjacencyMap;
	private HashMap<String, E> dataMap;

	// Constructor
	public Graph() {
        adjacencyMap = new HashMap<>();
        dataMap = new HashMap<>();
    }

    /**
     * Adds a vertex to the graph by adding to the adjacency map an entry for 
     * the vertex. This entry will be an empty map. An entry in the dataMap will 
     * store the provided data.
     */
    public void addVertex(String vName, E data) {
        if (adjacencyMap.containsKey(vName)) {
            throw new IllegalArgumentException("Vertex already exists: " +
            														vName);
        }
        
        adjacencyMap.put(vName, new HashMap<>());
        dataMap.put(vName, data);
    }

    /**
     * Adds or updates a directed edge with the specified cost.
     */
    public void addDirectedEdge(String startVName, String endVName, int cost) {
        if (!adjacencyMap.containsKey(startVName) || 
        							!adjacencyMap.containsKey(endVName)) {
            throw new IllegalArgumentException("One or both vertices not found: "
                    + startVName + ", " + endVName);
        }
        
        adjacencyMap.get(startVName).put(endVName, cost);
    }

    /**
     * Returns a map with information about vertices adjacent to vertexName. 
     * If the vertex has no adjacents, an empty map is returned.
     */
    public Map<String, Integer> getAdjacentVertices(String vName) {
        if (!adjacencyMap.containsKey(vName)) {
            throw new IllegalArgumentException("Vertex not found: " + vName);
        }
        
        return new HashMap<>(adjacencyMap.get(vName));
    }

    /**
     * Returns the cost associated with the specified edge.
     */
    public int getCost(String startVName, String endVName) {
        if (!adjacencyMap.containsKey(startVName)
                					|| !adjacencyMap.containsKey(endVName)) {
            throw new IllegalArgumentException("Vertex not found");
        }
        
        return adjacencyMap.get(startVName).get(endVName);
    }

    /**
     * Returns a Set with all the graph vertices.
     */
    public Set<String> getVertices() {
        return new HashSet<>(adjacencyMap.keySet());
    }

    /**
     * Returns the data component associated with the specified vertex
     */
    public E getData(String v) {
        if (!dataMap.containsKey(v)) {
            throw new IllegalArgumentException("Vertex not found: " + v);
        }
        
        return dataMap.get(v);
    }

    /** 
     * Computes Breadth-First Search of the specified graph.
     */
    public void doBreadthFirstSearch(String startVName, CallBack<E> callback) {
        if (!adjacencyMap.containsKey(startVName)) {
            throw new IllegalArgumentException("Start vertex not found: " + 
            														startVName);
        }
        
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        visited.add(startVName);
        queue.add(startVName);
        
        while (!queue.isEmpty()) {
            String curr = queue.poll();
            callback.processVertex(curr, dataMap.get(curr));
            TreeSet<String> neighbors = new TreeSet<>(adjacencyMap.get(curr).keySet());
            
            for (String nbr : neighbors) {
                if (!visited.contains(nbr)) {
                    visited.add(nbr);
                    queue.add(nbr);
                }
            }
        }
    }

    /** 
     * Computes Depth-First Search of the specified graph.
     */
    public void doDepthFirstSearch(String startVName, CallBack<E> callback) {
        if (!adjacencyMap.containsKey(startVName)) {
            throw new IllegalArgumentException("Start vertex not found: " + 
            														startVName);
        }
        
        Set<String> visited = new HashSet<>();
        Stack<String> stack = new Stack<>();
        visited.add(startVName);
        stack.push(startVName);
        
        while (!stack.isEmpty()) {
            String curr = stack.pop();
            callback.processVertex(curr, dataMap.get(curr));
            TreeSet<String> neighbors = new TreeSet<>(adjacencyMap.get(curr).keySet());
        
            for (String nbr : neighbors) {
                if (!visited.contains(nbr)) {
                    visited.add(nbr);
                    stack.push(nbr);
                }
            }
        }
    }

    /** 
     * Computes the shortest path and shortest path cost using Dijkstras's 
     * algorithm. It initializes shortestPath with the names of the vertices 
     * corresponding to the shortest path. If there is no shortest path, 
     * shortestPath will be have entry "None".
     */
    public int doDijkstras(String startVName, String endVName,
                           						ArrayList<String> shortestPath) {
        if (!adjacencyMap.containsKey(startVName)
                						|| !adjacencyMap.containsKey(endVName)) {
            throw new IllegalArgumentException("Vertex not found");
        }
        
        Map<String, Integer> dist = new HashMap<>();
        Map<String, String> prev = new HashMap<>();
        
        for (String v : adjacencyMap.keySet()) {
            dist.put(v, Integer.MAX_VALUE);
        }
        
        dist.put(startVName, 0);
        PriorityQueue<String> pq = new PriorityQueue<>(Comparator.comparingInt(dist::get));
        pq.add(startVName);
        
        while (!pq.isEmpty()) {
            String u = pq.poll();
            
            if (u.equals(endVName)) {
            	break;
            }
            
            int currentDist = dist.get(u);
            
            for (Map.Entry<String, Integer> entry : adjacencyMap.get(u).entrySet()) {
                String v = entry.getKey();
                int cost = entry.getValue();
                int alt = currentDist + cost;
                
                if (alt < dist.get(v)) {
                    dist.put(v, alt);
                    prev.put(v, u);
                    pq.remove(v);
                    pq.add(v);
                }
            }
        }
        
        if (dist.get(endVName) == Integer.MAX_VALUE) {
            shortestPath.add("None");
            
            return -1;
        }
        
        LinkedList<String> path = new LinkedList<>();
        
        for (String at = endVName; at != null; at = prev.get(at)) {
            path.addFirst(at);
        }
        
        shortestPath.addAll(path);
        
        return dist.get(endVName);
    }
    
    /**
     * Returns a string with information about the Graph. Notice that vertices 
     * are printed in sorted order and information about adjacent edges is 
     * printed in sorted order (by vertex name). You may not use 
     * Collections.sort or Arrays.sort in order to implement this method. See 
     * the sample output for formatting details. return string with graph 
     * information
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        TreeSet<String> verts = new TreeSet<>(adjacencyMap.keySet());
        sb.append("Vertices: ").append(verts.toString()).append("\n");
        sb.append("Edges:\n");

        int count = 0;
        int total = verts.size();
        
        for (String v : verts) {
            TreeMap<String, Integer> neighbors = new TreeMap<>(adjacencyMap.get(v));
            sb.append("Vertex(").append(v).append(")--->");
            sb.append(neighbors.toString());
            count++;
            
            if (count < total) {
                sb.append("\n");
            }
        }
        
        return sb.toString();
    }
}