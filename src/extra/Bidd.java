package extra;
import java.io.*;
import java.util.*;

class User implements Serializable {
    private String username;
    private String password;
    private String email;
    private boolean isAdmin;
    
    public User(String username, String password, String email, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.isAdmin = isAdmin;
    }
    
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public boolean isAdmin() { return isAdmin; }
    
    public void setPassword(String password) { this.password = password; }
    public void setEmail(String email) { this.email = email; }
}

class Bid implements Serializable {
    private String username;
    private String productName;
    private double amount;
    
    public Bid(String username, String productName, double amount) {
        this.username = username;
        this.productName = productName;
        this.amount = amount;
    }
    
    public String getUsername() { return username; }
    public String getProductName() { return productName; }
    public double getAmount() { return amount; }
}

class BiddingSystem {
    private static final String FILE_NAME = "bids.dat";
    private List<Bid> bids;
    
    public BiddingSystem() {
        bids = loadBids();
    }
    
    public void placeBid(String username, String productName, double amount) {
        bids.add(new Bid(username, productName, amount));
        saveBids();
    }
    
    public List<Bid> getBidsForAuction(String productName) {
        List<Bid> auctionBids = new ArrayList<>();
        for (Bid bid : bids) {
            if (bid.getProductName().equals(productName)) {
                auctionBids.add(bid);
            }
        }
        return auctionBids;
    }
    
    private List<Bid> loadBids() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<Bid>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }
    
    private void saveBids() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(bids);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Auction implements Serializable {
    private String productName;
    private String category;
    private String description;
    private double reservePrice;
    private Date startTime;
    private Date endTime;
    
    public Auction(String productName, String category, String description, double reservePrice, Date startTime, Date endTime) {
        this.productName = productName;
        this.category = category;
        this.description = description;
        this.reservePrice = reservePrice;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    
    public String getProductName() { return productName; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }
    public double getReservePrice() { return reservePrice; }
    public Date getStartTime() { return startTime; }
    public Date getEndTime() { return endTime; }
}

public class Bidd {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserManager userManager = new UserManager();
        BiddingSystem biddingSystem = new BiddingSystem();
        
        System.out.println("1. Register\n2. Login\n3. Place Bid");
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        if (choice == 1) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            System.out.print("Enter email: ");
            String email = scanner.nextLine();
            System.out.print("Is Admin (true/false): ");
            boolean isAdmin = scanner.nextBoolean();
            
            if (userManager.registerUser(username, password, email)) {
                System.out.println("Registration successful!");
            } else {
                System.out.println("Username already taken!");
            }
        } else if (choice == 2) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            
            User user = userManager.loginUser(username, password);
            if (user != null) {
                System.out.println("Login successful! Welcome, " + user.getUsername());
            } else {
                System.out.println("Invalid credentials!");
            }
        } else if (choice == 3) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter product name: ");
            String productName = scanner.nextLine();
            System.out.print("Enter bid amount: ");
            double amount = scanner.nextDouble();
            
            biddingSystem.placeBid(username, productName, amount);
            System.out.println("Bid placed successfully!");
        }
        
        scanner.close();
    }
}
