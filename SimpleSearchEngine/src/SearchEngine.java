import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class SearchEngine {


    public static void main(String[] args) {

        InvertedIndex invertedIndex = new InvertedIndex();
        Scanner scanner = new Scanner(System.in);
        int input;
        String filePath = "";

        if (args.length != 0) {filePath = args[1];}
        else {
            System.err.println("No command-line arguments");
            System.exit(1);
        }

        String data;
        List<String> lines = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))){

            while ((data = bufferedReader.readLine()) != null) {lines.add(data.trim());}

            for (int i = 0; i < lines.size(); i++) {invertedIndex.addLine(lines.get(i), i);}

            do {
                System.out.println("=== Menu ===");
                System.out.println("1. Find a person");
                System.out.println("2. Print all people");
                System.out.println("0. Exit");

                input = scanner.nextInt();
                scanner.nextLine();

                if (input > 2) {
                    System.out.println("Incorrect option! Try again.");
                    continue;
                }

                switch (input) {

                    case 1 -> {

                        System.out.println("Enter a name or email to search all suitable people.");
                        String query = scanner.nextLine().trim();
                        List<Integer> results = invertedIndex.search(query);

                        if(!results.isEmpty()) {

                            System.out.println(results.size() + " persons found:");
                            for (Integer result : results) {System.out.println(lines.get(result));}

                        } else {System.out.println("No matching people found");}

                    }

                    case 2 -> {

                        System.out.println("=== List of people ===");
                        for (String a : lines) {System.out.println(a);}

                    }
                }

            } while (input != 0);

            System.out.println("Bye!");

        } catch (IOException b) {System.err.println(b.getMessage());}
    }
}
class InvertedIndex {
    private Map<String, List<Integer>> index;

    public InvertedIndex() {
        index = new HashMap<>();
    }

    public void addLine(String line, int lineNumber) {
        String[] words = line.split(" ");
        for (String word : words) {

            word = word.toLowerCase();

            if (index.containsKey(word)) {index.get(word).add(lineNumber);}
            else {

                List<Integer> lines = new ArrayList<>();
                lines.add(lineNumber);
                index.put(word, lines);

            }
        }
    }

    public List<Integer> search(String query) {
        query = query.toLowerCase();
        return index.getOrDefault(query, new ArrayList<>());

    }
}