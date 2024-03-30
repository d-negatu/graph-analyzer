import java.util.List;

public class GraphAnalyzer{
    private Graph<String> graph = new Graph<>(100);

    public GraphAnalyzer(String fileName){
        loadGraph(filename);
    }

    private void loadGraph(String fileName){

         try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] vertices = line.split(" "); // Assuming a space separates the two vertex identifiers
                if (vertices.length == 2) {
                    graph.addVertex(vertices[0]);
                    graph.addVertex(vertices[1]);
                    Vertex<String> vertex1 = new Vertex<String>(vertices[0]);
                    Vertex<String> vertex2 = new Vertex<String>(vertices[1]);
                    graph.addEdge(vertex1, vertex2, true); // Assuming directed edges for simplicity
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filePath);
            return;
        }
    }




}    
