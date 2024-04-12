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
