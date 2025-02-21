package extra;
import java.io.*;
import java.util.*;

class User implements Serializable {
    private String username;
    private String password;
    private String email;
    
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
    
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    
    public void setPassword(String password) { this.password = password; }
    public void setEmail(String email) { this.email = email; }
}

class UserManager {
    private static final String FILE_NAME = "users.dat";
    private List<User> users1;
    
    public UserManager() {
        users1 = loadUsers();
    }
    
    public boolean registerUser1(String username, String password, String email, boolean isAdmin) {
        for (User user : users1) {
            if (user.getUsername().equals(username)) {
                return false; // Username already exists
            }
        }
        users1.add(new User(username, password, email));
        saveUsers();
        return true;
    }
    
    public User loginUser(String username, String password) {
        for (User user : users1) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null; // Login failed
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
            oos.writeObject(users1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean registerUser(String username, String password, String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'registerUser'");
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserManager userManager = new UserManager();
        
        System.out.println("1. Register\n2. Login");
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        if (choice == 1) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            System.out.print("Enter email: ");
            String email = scanner.nextLine();
            
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
        }
        
        scanner.close();
    }
}