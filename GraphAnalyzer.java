import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.*; // Wildcard import for everything under java.util.stream




public class GraphAnalyzer{
    private Graph<String> graph = new Graph<>(100);
    // Assuming you have a global List to store results:
    private List<String> results = new ArrayList<>();

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
            System.exit(1);
            return;
        }
    
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



  public String generateBFSOrder(List<Vertex<String>> bfsOrder) {
    if (bfsOrder.isEmpty()) {
        return "[BFS Vertices Ordering: 0] No vertices visited.";
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

    return sb.toString();
 }



   private List<Vertex<String>> constructPath(Map<Vertex<String>, Vertex<String>> predecessors, Vertex<String> start, Vertex<String> destination) {
    LinkedList<Vertex<String>> path = new LinkedList<>();
    Set<Vertex<String>> visited = new HashSet<>(); // to track visited vertices

    for (Vertex<String> at = destination; at != null; at = predecessors.get(at)) {
        if (visited.contains(at)) {
            // Cycle detected, break to prevent infinite loop
            break;
        }
        visited.add(at); // Mark this vertex as visited
        path.addFirst(at);

        if (at.equals(start)) {
            break; // Reached the start, complete the path
        }
    }

    if (!path.isEmpty() && path.getFirst().equals(start)) {
        return path;
    }

    return List.of(); // No path found or cycle was detected
   }

private String pathToString(List<Vertex<String>> path) {
    return path.stream()
               .map(vertex -> vertex.getId())
               .collect(Collectors.joining(" -> "));
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

  public String transitiveStats(boolean[][] T) {
        boolean[][] matrix = graph.getAdjacencyMatrix();
        boolean[][] newEdges = new boolean[matrix.length][matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
               //  Check if an edge in T is not in the original graph and is not a self-loop
                if (T[i][j] && !matrix[i][j] && i != j) {
                   newEdges[i][j] = true; // Mark the new edge in the matrix
                }else{
                    newEdges[i][j] = false;
                }
            }
       }

       return (generateEdgesString("New Edges Matrix", newEdges));
    }

  private String generateEdgesString(String title, boolean[][] matrix) {
    StringBuilder sb = new StringBuilder();
    sb.append(title).append(":\n");
    
    for (int i = 0; i < matrix.length; i++) {
        for (int j = 0; j < matrix[i].length; j++) {
            if (matrix[i][j]) {
                Vertex<String> fromVertex = graph.getVertexByIndex(i);
                Vertex<String> toVertex = graph.getVertexByIndex(j);
                sb.append(fromVertex.getId()).append(" -> ").append(toVertex.getId()).append("\n");
            }
        }
    }
    sb.append("\n"); // Append a blank line for better separation between sections
    return sb.toString();
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

 public String dfsStats(String source, String destination) {
    StringBuilder output = new StringBuilder();
    List<Vertex<String>> path = findPathDFS(source, destination);
    
    if (!path.isEmpty()) {
        // Construct the DFS Ordering String
        output.append("[DFS Ordering: ")
              .append(path.get(0).getId())
              .append(", ")
              .append(path.get(path.size() - 1).getId())
              .append("] ");
        for (int i = 0; i < path.size(); i++) {
            if (i > 0) output.append(", ");
            output.append("Vertex ").append(path.get(i).getId());
        }
        output.append("\n"); // Move to the next line
        
        // Construct the DFS Path String
        output.append("[DFS Path: ")
              .append(path.get(0).getId())
              .append(", ")
              .append(path.get(path.size() - 1).getId())
              .append("] ");
        for (int i = 0; i < path.size(); i++) {
            if (i > 0) output.append(" -> ");
            output.append("Vertex ").append(path.get(i).getId());
        }
        output.append("\n"); // Move to the next line
    } else {
        output.append("No path found.\n");
    }
    
    return output.toString();
 }
   public void go(){

        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        String dfsResult;
        List<Vertex<String>> bfsPath;
        String bfsResult;
        boolean[][] closureMatrix;
        String closureResults;
        String source = "";
        String destination = "";


        while (running){
            System.out.println("\nWelcome to GraphAnalyzer 2025>>");
        displayMenu();

        int choice = getUserChoice(scanner);
        if (choice == 0) {
            System.out.println("Goodbye!");
            running = false;
            continue;
        }

        // Depending on the operation, some require specific vertex inputs
        if (Arrays.asList(1, 2, 4, 6).contains(choice)) { // If the operation requires source and destination
            System.out.print("Please enter a valid source vertex >>> ");
            source = scanner.nextLine();
            System.out.print("Please enter a valid destination vertex >>> ");
            destination = scanner.nextLine(); 
        }

            switch (choice) {
                case 1:
                    results.clear();
                    System.out.println("DFS Path Discovery selected.");
                    dfsResult = dfsStats(source, destination);  // Assuming source and destination are known
                    results.add(dfsResult);  // Store the result instead of printing
                    break;
                case 2:
                    results.clear();
                    System.out.println("DFS Path Discovery + Cycle Detection selected.");
                    // Implement DFS + Cycle Detection
                    
                    break;
                case 3:
                    results.clear();
                    System.out.println("Cycle Detection selected.");
                    // Implement Cycle Detection
                    if(!cycleSearch()){
                    System.out.println("Cycle detected");}
                    break;
                case 4:
                    System.out.println("Breadth First Search selected.");
                    results.clear();
                    bfsPath = findPathBFS(source, destination); // Assuming source and destination are known
                    bfsResult = generateBFSOrder(bfsPath);
                    results.add(bfsResult);  // Store the result instead of printing
                    // Implement BFS
                    break;
                case 5:
                    System.out.println("Transitive Closure selected.");
                    // Implement Transitive Closure
                    // 3. Transitive Closure
                    results.clear();
                    closureMatrix = transitiveClosure(graph.getAdjacencyMatrix());
                    closureResults = generateEdgesString("Transitive Closure", closureMatrix);
                    results.add(closureResults);
                    break;
                case 6:
                    // Implement all tests
                    
                    System.out.println("All Tests selected.");
    
                    // Clear previous results if any
                    results.clear();

                    // 1. DFS Path Discovery (Assuming dfsStats returns a String or similar)
                    dfsResult = dfsStats(source, destination);  // Modify to your parameters
                    results.add(dfsResult);

                    // 2. BFS Discovery
                    bfsPath = findPathBFS(source, destination);  // Assuming these parameters make sense
                    bfsResult = generateBFSOrder(bfsPath);  // Assuming this method is refactored to return String
                    results.add(bfsResult);

                    // 3. Transitive Closure
                    closureMatrix = transitiveClosure(graph.getAdjacencyMatrix());
                    closureResults = generateEdgesString("Transitive Closure", closureMatrix);
                    results.add(closureResults);

                    break;
                case 7:
                    System.out.println("Displaying Results...");
                    for (String result : results) {
                        System.out.println(result);
                    }
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

    private void displayMenu() {
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
    }

private int getUserChoice(Scanner scanner) {
    int choice = -1; // Default or sentinel value indicating no valid choice made
    try {
        choice = scanner.nextInt();
    } catch (InputMismatchException e) {
        System.out.println("Please enter a valid integer choice.");
    }
    scanner.nextLine(); // Important to consume the newline
    return choice;
}


}    
