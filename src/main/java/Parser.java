import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Parser {
    static List<Game> games = new ArrayList<>();

    public static List<Game> sortByName(){
        List<Game> sortedByName = new ArrayList<>(games);

        // Sort games alphabetically by name
        sortedByName.sort(Comparator.comparing(Game::getName));

        return sortedByName;
    }

    public static List<Game> sortByRating(){
        List<Game> sortedByRating = new ArrayList<>(games);

        // Sort games by rating (most)
        sortedByRating.sort(Comparator.comparing(Game::getRating).reversed());

        return sortedByRating;
    }

    public static List<Game> sortByPrice(){
        List<Game> sortedByPrice = new ArrayList<>(games);

        // Sort games by price (most)
        sortedByPrice.sort(Comparator.comparing(Game::getPrice).reversed());

        return sortedByPrice;
    }

    public static void setUp() throws IOException {

        //Parse the HTML file using Jsoup
        File input = new File("C:\\Users\\Notebook\\Ap-course\\Fourth-Assignment-Steam-Scraper\\src\\Resources\\Video_Games.html");
        Document doc = Jsoup.parse(input, "UTF-8");

        // Extract data from the HTML
        Elements gameElements = doc.select("div.col-md-4.game");

        // Iterate through each Game div to extract Game data
        for (Element gameElement : gameElements) {
            String title = gameElement.selectFirst("h3.game-name").text();

            String priceText = gameElement.selectFirst("span.game-price").text(); // "91 €"
            int price = Integer.parseInt(priceText.replaceAll("[^\\d]", "")); // 91

            String ratingText = gameElement.selectFirst("span.game-rating").text(); // "4.8/5"
            double rating = Double.parseDouble(ratingText.split("/")[0]); // 4.8

            // Create a Game object using the extracted data
            Game game = new Game(title, rating, price);

            // Add the game to the static list
            games.add(game);
        }
    }

    public static void main(String[] args) throws IOException {
        // A user interface for interactive querying and sorting
        setUp();

        Scanner scanner = new Scanner(System.in);
        int choice;

        while (true) {
            System.out.println("\n=== Video Game Menu ===");
            System.out.println("1. View all games");
            System.out.println("2. Sort by name (A-Z)");
            System.out.println("3. Sort by rating (High to Low)");
            System.out.println("4. Sort by price (High to Low");
            System.out.println("5. Exit");
            System.out.println("\nEnter your choice: ");

            // Validate input
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
                if (choice <= 5 || choice >= 1) {
                    break; // Exit input loop if valid choice
                }
            } else {
                scanner.nextLine(); // Discard invalid input
            }
            System.out.println("Invalid choice. Try again.");
        }

        switch (choice) {
            case 1:
                for (int i = 0; i < games.size(); i++) {
                    System.out.println(i+1 + ". " + games.get(i).toString());
                }
                break;
            case 2:
                List<Game> sortedByName = sortByName();
                for (int i = 0; i < sortedByName.size(); i++) {
                    System.out.println(i+1 + ". " + sortedByName.get(i).toString());
                }
                break;
            case 3:
                List<Game> sortedByRating = sortByRating();
                for (int i = 0; i < sortedByRating.size(); i++) {
                    System.out.println(i+1 + ". " + sortedByRating.get(i).toString());
                }
                break;
            case 4:
                List<Game> sortedByPrice = sortByPrice();
                for (int i = 0; i < sortedByPrice.size(); i++) {
                    System.out.println(i+1 + ". " + sortedByPrice.get(i).toString());
                }
                break;
            case 5:
                System.exit(0);
        }

        // Prompt user to press Enter before exiting
        System.out.println("\nPress Enter to return to the menu...");
        String wait = scanner.nextLine(); // Wait for Enter key
        main(null); // Recursively call main to show the menu again
    }
}