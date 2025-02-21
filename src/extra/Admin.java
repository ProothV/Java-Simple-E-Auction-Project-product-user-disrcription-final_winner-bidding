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

class UserManager {
    private static final String FILE_NAME = "users.dat";
    private List<User> users;
    
    public UserManager() {
        users = loadUsers();
    }
    
    public boolean registerUser(String username, String password, String email) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return false; // Username already exists
            }
        }
        boolean isAdmin;
        users.add(new User(username, password, email, isAdmin));
        saveUsers();
        return true;
    }
    
    public User loginUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null; // Login failed
    }
    
    public List<User> getAllUsers() {
        return users;
    }
    
    private List<User> loadUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }
    
    private void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(users);
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

class AuctionManager {
    private static final String FILE_NAME = "auctions.dat";
    private List<Auction> auctions;
    
    public AuctionManager() {
        auctions = loadAuctions();
    }
    
    public void createAuction(String productName, String category, String description, double reservePrice, Date startTime, Date endTime) {
        auctions.add(new Auction(productName, category, description, reservePrice, startTime, endTime));
        saveAuctions();
    }
    
    public List<Auction> getAllAuctions() {
        return auctions;
    }
    
    private List<Auction> loadAuctions() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<Auction>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }
    
    private void saveAuctions() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(auctions);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public class Admin {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserManager userManager = new UserManager();
        AuctionManager auctionManager = new AuctionManager();
        
        System.out.println("1. Register\n2. Login\n3. Admin Panel");
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
            System.out.println("Admin Panel");
            System.out.println("Registered Users:");
            for (User user : userManager.getAllUsers()) {
                System.out.println(user.getUsername() + " - " + user.getEmail());
            }
            System.out.println("Active Auctions:");
            for (Auction auction : auctionManager.getAllAuctions()) {
                System.out.println(auction.getProductName() + " - " + auction.getCategory() + " - " + auction.getDescription());
            }
        }
        
        scanner.close();
    }
}
