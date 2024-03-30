import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Driver{
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: Driver <path_to_file>");
            return;
        }

        String filePath = args[0];

        GraphAnalyzer analyzer = new GraphAnalyzer(filePath);
        analyzer.go();

    }         
}
