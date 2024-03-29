import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph<E> {
    // For adjacency list representation
    private Map<Vertex<E>, List<Vertex<E>>> adjListMap;

    // For adjacency matrix representation
    private boolean[][] adjMatrix;
    private int vertexCount = 0; // To keep track of the number of vertices
    private Map<Vertex<E>, Integer> vertexIndexMap; // Maps each vertex to its index in the matrix

    // Constructor
    public Graph(int maxVertices) {
        adjListMap = new HashMap<>();
        adjMatrix = new boolean[maxVertices][maxVertices]; // Assuming a maximum number of vertices
        vertexIndexMap = new HashMap<>();
    }

    // Method to add a vertex
    public void addVertex(Vertex<E> vertex) {
        adjListMap.putIfAbsent(vertex, new ArrayList<>());
        if (!vertexIndexMap.containsKey(vertex)) {
            vertexIndexMap.put(vertex, vertexCount++);
        }
    }

    // Method to add an edge
    public void addEdge(Vertex<E> from, Vertex<E> to) {
        // Update adjacency list
        adjListMap.get(from).add(to);
        
        // Update adjacency matrix
        int fromIndex = vertexIndexMap.get(from);
        int toIndex = vertexIndexMap.get(to);
        adjMatrix[fromIndex][toIndex] = true;
    }

    // Additional methods can be implemented to support specific graph operations
}
