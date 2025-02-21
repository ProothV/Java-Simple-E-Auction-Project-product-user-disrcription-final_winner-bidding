import java.util.*;

class Product {
    int id;
    String name;
    String description;
    String sellerName;
    double startingPrice;
    double highestBid;
    String highestBidder;
    boolean isAuctionActive;

    public Product(int id, String name, String description, String sellerName, double startingPrice) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sellerName = sellerName;
        this.startingPrice = startingPrice;
        this.highestBid = startingPrice;
        this.highestBidder = "None";
        this.isAuctionActive = true;
    }

    public Product() {
    }
}

class WinnerDetails {
    String firstName;
    String lastName;
    String productName;
    double winningBid;

    public WinnerDetails(String firstName, String lastName, String productName, double winningBid) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.productName = productName;
        this.winningBid = winningBid;
    }

    public void displayWinner() {
        System.out.println("\n=== Auction Winner Details ===");
        System.out.println("Winner Name: " + firstName + " " + lastName);
        System.out.println("Winning Product: " + productName);
        System.out.println("Winning Bid: $" + winningBid);
    }
}

public class OnlineAuctionSystem {
    private static List<Product> products = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== Online Auction System ===");
            System.out.println("1. Add Product (Seller)");
            System.out.println("2. View Products");
            System.out.println("3. Place Bid (Buyer)");
            System.out.println("4. End Auction");
            System.out.println("5. Bidding Report");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    viewProducts();
                    break;
                case 3:
                    placeBid();
                    break;
                case 4:
                    endAuction();
                    break;
                case 5:
                    displayBiddingReport();
                    break;
                case 6:
                    System.out.println("Exiting... Thank you!");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void addProduct() {
        System.out.print("Enter Product Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Product Description: ");
        String description = scanner.nextLine();
        System.out.print("Enter Seller Name: ");
        String sellerName = scanner.nextLine();
        System.out.print("Enter Starting Price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); 
        int id = products.size() + 1;
        products.add(new Product(id, name, description, sellerName, price));
        System.out.println("Product added successfully!");
    }

    private static void viewProducts() {
        if (products.isEmpty()) {
            System.out.println("No products available.");
            return;
        }
        System.out.println("\nAvailable Products:");
        for (Product p : products) {
            System.out.println("ID: " + p.id + " | Name: " + p.name + " | Description: " + p.description + " | Seller: " + p.sellerName + " | Highest Bid: $" + p.highestBid + " | Highest Bidder: " + p.highestBidder);
        }
    }

    private static void placeBid() {
        System.out.print("Enter Product ID to bid on: ");
        int id = scanner.nextInt();
        scanner.nextLine(); 
        boolean found = false;
        for (Product p : products) {
            if (p.id == id && p.isAuctionActive) {
                found = true;
                System.out.print("Enter Your First Name: ");
                String firstName = scanner.nextLine();
                System.out.print("Enter Your Last Name: ");
                String lastName = scanner.nextLine();
                System.out.print("Enter Your Bid: ");
                double bidAmount = scanner.nextDouble();
                scanner.nextLine(); 
                if (bidAmount >= p.highestBid) {
                    p.highestBid = bidAmount;
                    p.highestBidder = firstName + " " + lastName;
                    System.out.println("Bid placed successfully!");
                } else {
                    System.out.println("Your bid must be equal to or higher than the current highest bid!");
                }
            }
        }
        if (!found) {
            System.out.println("Invalid Product ID or Auction has ended.");
        }
    }

    private static void endAuction() {
        System.out.print("Enter Product ID to end auction: ");
        int id = scanner.nextInt();
        scanner.nextLine(); 
        boolean found = false;
        for (Product p : products) {
            if (p.id == id && p.isAuctionActive) {
                found = true;
                p.isAuctionActive = false;
                System.out.println("Auction ended! Winner: " + p.highestBidder + " with $" + p.highestBid);
                WinnerDetails winner = new WinnerDetails(p.highestBidder.split(" ")[0], p.highestBidder.split(" ")[1], p.name, p.highestBid);
                winner.displayWinner();
            }
        }
        if (!found) {
            System.out.println("Invalid Product ID or Auction already ended.");
        }
    }

    private static void displayBiddingReport() {
        System.out.println("\n=== Bidding Report ===");
        for (Product p : products) {
            System.out.println("Product: " + p.name + " | Highest Bid: $" + p.highestBid + " | Winner: " + p.highestBidder);
        }
    }
}
