import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.InputMismatchException;

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
        System.out.println(findSource("6").toString());

        //graph.printGraphDetails();
    }

   public Vertex<String> findSource(String input){

       return (graph.vertexExists(input));
   }

   public Vertex<String> findDestination(String input){

       return (graph.vertexExists(input));

   } 

  import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public List<Vertex<String>> findPathDFS(Vertex<String> startVertex, Vertex<String> destinationVertex) {
    // Initialize all vertices as UNVISITED and predecessor map
    Map<Vertex<String>, Vertex<String>> predecessors = new HashMap<>();
    for (Vertex<String> vertex : graph.getVertices()) {
        vertex.setState(VertexState.UNVISITED);
        predecessors.put(vertex, null); // Initially, all predecessors are null
    }

    Stack<Vertex<String>> stack = new Stack<>();
    stack.push(startVertex);

    while (!stack.isEmpty()) {
        Vertex<String> current = stack.pop();

        if (current.equals(destinationVertex)) {
            // Destination found, construct the path from start to destination
            return constructPath(predecessors, startVertex, destinationVertex);
        }

        if (current.getState() == VertexState.UNVISITED) {
            // Mark the vertex as visited
            current.setState(VertexState.VISITED);

            // Push all unvisited neighbors to the stack and update their predecessors
            for (Vertex<String> neighbor : graph.getAdjacencyList(current)) {
                if (neighbor.getState() == VertexState.UNVISITED) {
                    stack.push(neighbor);
                    predecessors.put(neighbor, current);
                }
            }
        }
    }
    // If the destination is not found, return an empty path
    return List.of();
}

private List<Vertex<String>> constructPath(Map<Vertex<String>, Vertex<String>> predecessors, Vertex<String> start, Vertex<String> destination) {
    Stack<Vertex<String>> pathStack = new Stack<>();
    Vertex<String> current = destination;

    while (current != null && !current.equals(start)) {
        pathStack.push(current);
        current = predecessors.get(current);
    }
    if (current == null) {
        return List.of(); // No path found
    }
    pathStack.push(start); // Add the start vertex to the path

    List<Vertex<String>> path = new ArrayList<>();
    while (!pathStack.isEmpty()) {
        path.add(pathStack.pop());
    }
    return path;
}


   public static void go(){

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
                    break;
                case 2:
                    System.out.println("DFS Path Discovery + Cycle Detection selected.");
                    // Implement DFS + Cycle Detection
                    break;
                case 3:
                    System.out.println("Cycle Detection selected.");
                    // Implement Cycle Detection
                    break;
                case 4:
                    System.out.println("Breadth First Search selected.");
                    // Implement BFS
                    break;
                case 5:
                    System.out.println("Transitive Closure selected.");
                    // Implement Transitive Closure
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
                    System.out.println("Initializing New Graph...");
                    // Initialize a new graph
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
