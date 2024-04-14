import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.*; // Wildcard import for everything under java.util.stream




public class GraphAnalyzer{
    private Graph<String> graph = new Graph<>(100);

    public GraphAnalyzer(String fileName){
        loadGraph(fileName);
    }

    private void loadGraph(String fileName){

         try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] vertices = line.split(" "); 
                if (vertices.length == 2) {
                    graph.addVertex(vertices[0]);
                    graph.addVertex(vertices[1]);
                    Vertex<String> vertex1 = new Vertex<String>(vertices[0]);
                    Vertex<String> vertex2 = new Vertex<String>(vertices[1]);
                    graph.addEdge(vertex1, vertex2, true); 
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + fileName);
            return;
        }
        //System.out.println(findSource("6").toString());
        
        //graph.printGraphDetails();
    }

   
   public Vertex<String> findSource(String input){

       return (graph.vertexExists(input));
   }

   public Vertex<String> findDestination(String input){

       return (graph.vertexExists(input));

   }

   public List<Vertex<String>> findPathDFS(String start, String destination) {  
    Map<Vertex<String>, Vertex<String>> predecessors = new HashMap<>();         
    Vertex<String> startVertex = findSource(start);                             
    Vertex<String> destinationVertex = findDestination(destination);            
    Stack<Vertex<String>> stack = new Stack<>();                                
   

    stack.push(startVertex);                                                    

    while (!stack.isEmpty()) {                                                  
        Vertex<String> current = stack.pop();                                   
        if (current.getState() == VertexState.UNVISITED) {                      
            current.setState(VertexState.VISITED);                               

            if (current.equals(destinationVertex)) {


                return constructPath(predecessors, startVertex, destinationVertex); 
            }                                                                    
            for (Vertex<String> neighbor : graph.getAdjacencyList(current)) {   
                if (neighbor.getState() == VertexState.UNVISITED) {             
                    stack.push(neighbor);                                       
                    if (!predecessors.containsKey(neighbor)) { // Only set predecessor if not already set
                        predecessors.put(neighbor, current);                    
                    }                                                           
                }                                                               
            }                                                                   
        }                                                                       
    }                                                                           
    return List.of(); // Empty path if destination not found                    
  }   

   public List<Vertex<String>> findPathBFS(String start, String destination) {
    List<Vertex<String>> visitedOrder = new ArrayList<>(); // List to track the order of visited nodes
    Vertex<String> startVertex = findSource(start);
    Vertex<String> destinationVertex = findDestination(destination);
    Queue<Vertex<String>> queue = new LinkedList<>();

    startVertex.setState(VertexState.VISITED);
    queue.add(startVertex);
    visitedOrder.add(startVertex); // Record the start vertex as visited

    while (!queue.isEmpty()) {
        Vertex<String> current = queue.poll();

        if (current.equals(destinationVertex)) {
            return visitedOrder; // Return the list of visited nodes up to and including the destination
        }

        for (Vertex<String> neighbor : graph.getAdjacencyList(current)) {
            if (neighbor.getState() == VertexState.UNVISITED) {
                neighbor.setState(VertexState.VISITED);
                queue.add(neighbor);
                if (!visitedOrder.contains(neighbor)) { // Check if already in the visited order
                    visitedOrder.add(neighbor);
                }

            }
        }
    }

    return visitedOrder; // Return the list of visited nodes if the destination is not found
  }



  public void printBFSOrder(List<Vertex<String>> bfsOrder) {
    if (bfsOrder.isEmpty()) {
        System.out.println("[BFS Vertices Ordering: 0] No vertices visited.");
        return;
    }

    StringBuilder sb = new StringBuilder("[BFS Vertices Ordering: ");
    sb.append(bfsOrder.size()).append("] "); // Add the count of vertices visited

    boolean first = true;
    for (Vertex<String> vertex : bfsOrder) {
        if (!first) {
            sb.append(", ");
        } else {
            first = false;
        }
        sb.append("Vertex ").append(vertex.getId());
    }

    System.out.println(sb.toString());
  }





   private List<Vertex<String>> constructPath(Map<Vertex<String>, Vertex<String>> predecessors, Vertex<String> start, Vertex<String> destination) {
    LinkedList<Vertex<String>> path = new LinkedList<>();
    Set<Vertex<String>> visited = new HashSet<>(); // to track visited vertices
    System.out.println("In constructPath");

    for (Vertex<String> at = destination; at != null; at = predecessors.get(at)) {
        if (visited.contains(at)) {
            // Cycle detected, break to prevent infinite loop
            System.out.println("Cycle detected at vertex: " + at.getId() + ", breaking...");
            break;
        }
        visited.add(at); // Mark this vertex as visited
        path.addFirst(at);

        // Debug: Print current path step and the predecessor of the current vertex
        System.out.println("Current path: " + pathToString(path));
        System.out.println("Current vertex: " + at.getId() + ", Predecessor: " + (predecessors.get(at) != null ? predecessors.get(at).getId() : "None"));

        if (at.equals(start)) {
            System.out.println("Reached the start vertex: " + start.getId() + ", complete the path.");
            break; // Reached the start, complete the path
        }
    }

    if (!path.isEmpty() && path.getFirst().equals(start)) {
        return path;
    }

    System.out.println("No path found or cycle detected.");
    return List.of(); // No path found or cycle was detected
   }

private String pathToString(List<Vertex<String>> path) {
    return path.stream()
               .map(vertex -> vertex.getId())
               .collect(Collectors.joining(" -> "));
}


 
   
   public void graphDet(){
       graph.printGraphDetails();
   }



  public boolean[][] transitiveClosure(boolean[][] G) {
        int n = G.length;  // Number of vertices
        boolean[][] T = new boolean[n][n];  // Transitive closure matrix initialization

        // Initialize the transitive closure matrix based on direct edges or self-loops
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ( G[i][j]) {
                    T[i][j] = true;
                } else {
                    T[i][j] = false;
                }
            }
        }

        // Applying the transitive closure logic
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    T[i][j] = T[i][j] || (T[i][k] && T[k][j]);
                }
            }
        }

        return T;
  }

  public void transitiveStats(boolean[][] T) {
        boolean[][] matrix = graph.getAdjacencyMatrix();
        boolean[][] newEdges = new boolean[matrix.length][matrix.length];

        // Print the original adjacency matrix
        System.out.println("Original Adjacency Matrix:");
        printMatrix(matrix);

        // Print the transitive closure matrix
        System.out.println("Transitive Closure Matrix:");
        printMatrix(T);

        //System.out.println("[TC: New Edges]");
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
               //  Check if an edge in T is not in the original graph and is not a self-loop
                if (T[i][j] && !matrix[i][j] && i != j) {
                   newEdges[i][j] = true; // Mark the new edge in the matrix
                    //System.out.println(i + " -> " + j);
                }else{
                    newEdges[i][j] = false;
                }
            }
       }

        // Optionally, print the matrix for new edges if needed
        System.out.println("New Edges Matrix:");
        printMatrix(newEdges);

        printEdges("Original Adjacency Matrix", matrix);
        printEdges("Transitive Closure Matrix", T);
        printEdges("New Edges Matrix", newEdges);
    }

   private void printEdges(String title, boolean[][] matrix) {
        System.out.println(title + ":");
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j]) {
                    
                    Vertex<String> fromVertex = graph.getVertexByIndex(i);
                    Vertex<String> toVertex = graph.getVertexByIndex(j);
                    System.out.println(fromVertex.getId() + " -> " + toVertex.getId());
                }
            }
        }
        System.out.println(); // Print a blank line for better separation between sections
   }

   private void printMatrix(boolean[][] matrix) {
        // Print column headers

        for (int i = 0; i < graph.getVertexCount(); i++) {
            for (int j = 0; j < graph.getVertexCount(); j++) {
                System.out.print((matrix[i][j] ? 1 : 0) + " ");
            }
            System.out.println();
        }
    }

  public boolean cycleSearch() {
    Map<Vertex<String>, VertexState> state = new HashMap<>();
    Map<Vertex<String>, Vertex<String>> predecessors = new HashMap<>();
    Stack<Vertex<String>> stack = new Stack<>();

    // Check each vertex if not already visited
    for (Vertex<String> vertex : graph.getVertices()) {
        if (state.get(vertex) == VertexState.UNVISITED) {
            stack.push(vertex);
            while (!stack.isEmpty()) {
                Vertex<String> current = stack.peek();

                if (state.get(current) == VertexState.UNVISITED) {
                    state.put(current, VertexState.PROCESSING);
                    for (Vertex<String> neighbor : graph.getAdjacencyList(current)) {
                        if (state.get(neighbor) == VertexState.UNVISITED) {
                            stack.push(neighbor);
                            predecessors.put(neighbor, current);
                        } else if (state.get(neighbor) == VertexState.PROCESSING && !predecessors.get(current).equals(neighbor)) {
                            // A cycle is detected
                            return true;
                        }
                    }
                } else if (state.get(current) == VertexState.PROCESSING) {
                    state.put(current, VertexState.VISITED);  // Mark as fully visited
                    stack.pop();  // Remove from stack after all neighbors are processed
                }
            }
        }
    }
    return false;
 }

 public void dfsStats(String source, String destination){

      List<Vertex<String>> path = findPathDFS(source, destination);
        if (!path.isEmpty()) {

             System.out.print("[DFS Ordering: " + path.get(0).getId() + ", " + path.get(path.size() - 1).getId() + "] ");
        for (int i = 0; i < path.size(); i++) {
            if (i > 0) System.out.print(", ");
                System.out.print("Vertex " + path.get(i).getId());
        }
        System.out.println();


            System.out.print("[DFS Path: " + path.get(0).getId() + ", " + path.get(path.size() - 1).getId() + "] ");
            for (int i = 0; i < path.size(); i++) {
                if (i > 0) System.out.print(" -> ");
                    System.out.print("Vertex " + path.get(i).getId());
            }
            System.out.println();  // Move to the next line after printing the path
        }    else {
            System.out.println("No path found.");
        }
  }
   public void go(){

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running){
            System.out.println("\nWelcome to GraphAnalyzer 2024 >>");
            System.out.println("1) Depth First Search Path Discovery");
            System.out.println("2) Depth First Search Path Discovery + Cycle Detection");
            System.out.println("3) Cycle Detection");
            System.out.println("4) Breadth First Search");
            System.out.println("5) Transitive Closure");
            System.out.println("6) All Tests");
            System.out.println("7) Display Results");
            System.out.println("8) Initialize New Graph");
            System.out.println("0) Quit");
            System.out.println("-------------------------------------------------------");
            System.out.print("Enter menu choice >>> ");

            int choice = -1; // Default or sentinel value indicating no valid choice made
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid integer choice.");
                scanner.nextLine(); // Consume the invalid input
                continue; // Skip the rest of the loop iteration and prompt again
            }// Method to check if a vertex exists
    

            switch (choice) {
                case 1:
                    System.out.println("DFS Path Discovery selected.");
                    // Implement DFS Path Discovery
                    dfsStats("0", "3");
                    break;
                case 2:
                    System.out.println("DFS Path Discovery + Cycle Detection selected.");
                    // Implement DFS + Cycle Detection
                    
                    break;
                case 3:
                    System.out.println("Cycle Detection selected.");
                    // Implement Cycle Detection
                    if(!cycleSearch()){
                    System.out.println("Cycle detected");}
                    break;
                case 4:
                    System.out.println("Breadth First Search selected.");
                    List<Vertex<String>> pathD = findPathBFS("0", "3");
                    printBFSOrder(pathD);
                    // Implement BFS
                    break;
                case 5:
                    System.out.println("Transitive Closure selected.");
                    // Implement Transitive Closure
                    transitiveStats(transitiveClosure(graph.getAdjacencyMatrix()));
                    break;
                case 6:
                    System.out.println("All Tests selected.");
                    // Implement all tests
                    break;
                case 7:
                    System.out.println("Displaying Results...");
                    // Display results
                    break;
                case 8:
                    try {
            System.out.println("Please enter the file path:");
            String filePath = scanner.next();  // Correctly read the file path
            graph = new Graph<>(100);
            loadGraph(filePath); // Initialize the analyzer with the file path
            
        } catch (NoSuchElementException e) {
            System.out.println("No line was found");
        } catch (IllegalStateException e) {
            System.out.println("Scanner is closed");
        }
                    break;
                case 0:
                    System.out.println("Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }

        scanner.close();
    }
}    
