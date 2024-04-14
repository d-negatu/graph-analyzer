import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.LinkedList; // Import for LinkedList
import java.util.List;       // Import for using List


public class Driver{
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: Driver <path_to_file>");
            return;
        }

        String filePath = args[0];

        GraphAnalyzer analyzer = new GraphAnalyzer(filePath);


        analyzer.go();
        //graph.printGraphDetails();


        analyzer.graphDet();

    }         
}
