import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph<E> {
    // For adjacency list representation
    private Map<Vertex<E>, List<Vertex<E>>> adjListMap;
    private Map<E, Vertex<E>> vertices; // Maps ids to vertices
    // For adjacency matrix representation
    private boolean[][] adjMatrix;
    private List<Vertex<E>> indexVertexMap; // Inverse mapping
    private int vertexCount = 0; // To keep track of the number of vertices
    private Map<Vertex<E>, Integer> vertexIndexMap; // Maps each vertex to its index in the matrix

    // Constructor
    public Graph(int maxVertices) {
        adjListMap = new HashMap<>();
        adjMatrix = new boolean[maxVertices][maxVertices]; // Assuming a maximum number of vertices
        vertexIndexMap = new HashMap<>();
        indexVertexMap = new ArrayList<>(maxVertices); // Initialize with the maximum capacity
        vertices = new HashMap<>();
    }

    // Method to add a vertex
    public void addVertex(E id) {
        if (vertices == null || 
        vertices.get(id) == null) {
            Vertex<E> vertex = new Vertex<E>(id);
            vertices.put(id, vertex);
            adjListMap.put(vertex, new ArrayList<>());
            vertexIndexMap.put(vertex, vertexCount++);
            indexVertexMap.add(vertex);
        }

    }

    public void addEdge(Vertex<E> from, Vertex<E> to, boolean isDirected) {
        adjListMap.get(from).add(to);
        int fromIndex = vertexIndexMap.get(from);
        int toIndex = vertexIndexMap.get(to);
        adjMatrix[fromIndex][toIndex] = true;

        if (!isDirected) {
            // For undirected graphs, also add the reverse edge
            adjListMap.get(to).add(from);
            adjMatrix[fromIndex][toIndex] = true;
        }
    }

    // Method to check if a vertex exists
    public Vertex<E> vertexExists(E id) {
        return vertices.get(id);
    }
    
    // Get all vertices in the graph
    public List<Vertex<E>> getVertices() {
        return new ArrayList<>(vertices.values());
    }

    // Get adjacency list of a particular vertex
    public List<Vertex<E>> getAdjacencyList(Vertex<E> vertex) {
        return adjListMap.getOrDefault(vertex, new ArrayList<>());
    }

    // Getter method for the adjacency matrix
    public boolean[][] getAdjacencyMatrix() {
        return adjMatrix;
    }

    public int getVertexCount(){

        return (vertexCount);
    }

    // Use this method to get a vertex by its index
    public Vertex<E> getVertexByIndex(int index) {
        return indexVertexMap.get(index);
    }
 
    public void printGraphDetails() {
        System.out.println("Graph Details:");
        System.out.println("Vertices:");
        for (Vertex<E> vertex : adjListMap.keySet()) {
        System.out.print(vertex + " => ");
        List<Vertex<E>> edges = adjListMap.get(vertex);
        if (edges.isEmpty()) {
            System.out.println("No adjacent vertices.");
        } else {
            StringBuilder edgesList = new StringBuilder();
            for (Vertex<E> edge : edges) {
                edgesList.append(edge).append(", ");
            }
            // Remove the last comma and space
            if (edgesList.length() > 0) {
                edgesList.setLength(edgesList.length() - 2);
            }
            System.out.println(edgesList);
        }
    }

        System.out.println("\nAdjacency Matrix:");
        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                System.out.print((adjMatrix[i][j] ? 1 : 0) + " ");
            }
            System.out.println();
        }
    }

    // Additional methods can be implemented to support specific graph operations
}
