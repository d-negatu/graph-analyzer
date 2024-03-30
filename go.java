import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: Driver <path_to_file>");
            return;
        }

        String filePath = args[0];
        Graph<String> graph = new Graph<>(100); // Assuming a maximum of 100 vertices for simplicity

        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] vertices = line.split(" "); // Assuming a space separates the two vertex identifiers
                if (vertices.length == 2) {
                    Vertex<String> from = new Vertex<>(vertices[0]);
                    Vertex<String> to = new Vertex<>(vertices[1]);
                    graph.addVertex(from);
                    graph.addVertex(to);
                    graph.addEdge(from, to, true); // Assuming directed edges for simplicity
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filePath);
        }

        // After building the graph, you can perform various operations on it
        // For example, graph.depthFirstSearch(), graph.breadthFirstSearch(), etc.
    }
}
