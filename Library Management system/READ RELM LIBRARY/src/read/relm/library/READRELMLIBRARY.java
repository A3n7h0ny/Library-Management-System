package read.relm.library;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class READRELMLIBRARY {

    static final String FILENAME = "Member.txt";
    static final String FILENAME2 = "Member.txt";
    static final String FILESTAFF = "Saff.txt";
    static  String BOOKS_FILE = "BookCatalog.txt";
    static  String LOANS_FILE = "loans.txt";
    static  String RESERVATIONS_FILE = "reservations.txt";
    private final String notificationFile = "notifications.txt";
    private final String enquiryFile = "enquiries.txt";
    private Scanner scanner = new Scanner(System.in);
    static double price;
    static String Name;
    static String User;
    static String bookId;
    static  String title;
    static  String author;
    static int num,uj;
    static String genre,Book,Author,A,L,R,PG;
    static String Surname,ID,OC,Email;
    static String Password;
    static int option,one,two;
    public static boolean yes = true; 
      
    static class Book {
        String bookId;
        String title;
        String author;
        String genre;
        double price;
        String status;
        String language;
        double rating;
        String reserved;

        public Book(String bookId, String title) {
            this.bookId = bookId;
            this.title = title;
            this.author = author;
        }

        @Override
        public String toString() {
            return "" + bookId + " " + title + "" + author;
        }
    }

    static class PurchaseTransaction {
        String userId;
        double amountReceived;
        Date purchaseDate;
        String bookId;

        public PurchaseTransaction(String userId, double amountReceived, String bookId) {
            this.userId = userId;
            this.amountReceived = amountReceived;
            this.bookId = bookId;
            this.purchaseDate = new Date();
        }

        @Override
        public String toString() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return "Purchase by User ID: " + userId + "\nAmount: R" + amountReceived +
                    "\nBook ID: " + bookId + "\nDate: " + sdf.format(purchaseDate);
        }
    }

    static Map<String, Book> bookDatabase = new HashMap<>();
    static List<PurchaseTransaction> transactions = new ArrayList<>();
    
    
                                                                                                                        //user login
    //=====================================================================================================================================
        static void UserLogin() {
    Scanner s = new Scanner(System.in);
    System.out.println("Please Enter your credentials to Proceed");
    System.out.print("Enter Your Username: ");
    String enteredUser = s.nextLine().trim();
    System.out.print("Enter Your Password: ");
    String enteredPassword = s.nextLine().trim();

    try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME2))) {
        String line;
        boolean found = false;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(","); // Splits by comma

            String fileUser = "", filePass = "";
            for (String part : parts) {
                part = part.trim();
                if (part.startsWith("Username:")) {
                    fileUser = part.substring(9).trim(); // after "Username:"
                } else if (part.startsWith("Password:")) {
                    filePass = part.substring(9).trim(); // after "Password:"
                }
            }

            if (enteredUser.equals(fileUser) && enteredPassword.equals(filePass)) {
                System.out.println("Login successful!");
                menu(); // your user menu function
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Invalid username or password. Try again.");
            UserLogin();
        }

    } catch (IOException e) {
        System.out.println("Error reading file: " + e.getMessage());
    }
}                                                                                                          //User Rgistration
    //===================================================================================================================================
    static void RegisterUserFile() {
        Scanner s = new Scanner(System.in);
        System.out.println("Please In Put Your Information");
        System.out.println("Please Enter your Name");
        Name = s.nextLine();
        System.out.println("Please Enter your Surname");
        Surname = s.nextLine();
        System.out.println("Please Enter your Username");
        User = s.nextLine();
        System.out.println("Please Enter your Password");
        Password = s.nextLine();
        
    String convert = "ID number: " + ID + ", Name: " + Name + ", Surname: " + Surname + ", Username: " + User + ", Password: " + Password + "\n";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME, true))) {
            writer.write(convert);
            writer.close();
            System.out.println("Data written to file successfully.");
            Entry();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
            e.printStackTrace();
        }
    }  
    
    // Helper method to read the last used ID
static int readLastID() {
    try {
        List<String> lines = Files.readAllLines(Paths.get(FILENAME));
        if (lines.isEmpty()) return 300; // Start from M301 if file is empty
        
        // Get last line and extract ID
        String lastLine = lines.get(lines.size() - 1);
        String lastIDStr = lastLine.split(",")[0].split(":")[1].trim().substring(1);
        return Integer.parseInt(lastIDStr);
    } catch (IOException e) {
        System.out.println("Error reading IDs: " + e.getMessage());
        return 300; // Default starting point
    }
}

// Helper method to update the last used ID (optional)
static void updateLastID(int ID) {
    // You could store this in a separate config file if needed
    // For now we'll just rely on reading from the main file
}
                                                                                                                     //Staff Login
    //==================================================================================================================================
    static void StaffLogin() {
        Scanner s = new Scanner(System.in);
        System.out.println("Please Enter your  Staff credentials to Proceed");
        System.out.print("Enter Your Username: ");
        String enteredUser = s.nextLine();
        System.out.print("Enter Your Password: ");
        String enteredPassword = s.nextLine();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILESTAFF))) {
    String line;
    boolean found = false; 

    while ((line = reader.readLine()) != null) {
        if (line.contains("Username: " + enteredUser) && line.contains("Password: " + enteredPassword)) {
            System.out.println("Login successful!");
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
            Admin();
            found = true;
            break;
        }
    }
    if (!found) {
        System.out.println("Invalid username or password. Try again.");
        StaffLogin();
    }
} catch (IOException e) {
    System.out.println("Error reading file: " + e.getMessage());
}
    }  
    
    static void addBook(){
        Scanner s = new Scanner(System.in);
        System.out.println("Please In Put Book Information");
        System.out.println("Please Enter book Name");
        Name = s.nextLine();
        System.out.println("Please Enter Author");
        author = s.nextLine();
        System.out.println("Please Enter genre");
        genre = s.nextLine();
        System.out.println("Please Enter price");
        price = s.nextDouble();
        System.out.println("Please Enter Availability");
        A = s.nextLine();
        System.out.println("Please Enter book language");
        L = s.nextLine();
        System.out.println("Please Enter book rating");
        R = s.nextLine();
        System.out.println("age restriction (yes/no)");
        PG = s.nextLine();
        
    String convert = "|" + ID + "|  |" + Name + "|" + author + "|" + genre + "|" + price + "|" + A + "|" + L + "|" + R + "|" + PG + "|" + "\n";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME, true))) {
            writer.write(convert);
            writer.close();
            System.out.println("Data written to file successfully.");
            bookManage();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private static void updateBook(Scanner scanner) {
    System.out.print("Enter Book ID to update: ");
    String bookId = scanner.nextLine();

    Book book = bookDatabase.get(bookId);
    if (book == null) {
        System.out.println("❌ Book not found.");
        return;
    }

    System.out.println("Current Details: \n" + book);
    System.out.print("Enter new Title (or press Enter to keep): ");
    String title = scanner.nextLine();
    if (!title.isEmpty()) book.title = title;

    System.out.print("Enter new Author (or press Enter to keep): ");
    String author = scanner.nextLine();
    if (!author.isEmpty()) book.author = author;

    System.out.print("Enter new Genre (or press Enter to keep): ");
    String genre = scanner.nextLine();
    if (!genre.isEmpty()) book.genre = genre;

    System.out.print("Enter new Price (or press Enter to keep): ");
    String price = scanner.nextLine();
    if (!price.isEmpty()) book.price = Double.parseDouble(price);

    System.out.print("Enter new Status (Available/Sold/Loaned): ");
    String status = scanner.nextLine();
    if (!status.isEmpty()) book.status = status;

    System.out.print("Enter new Language (or press Enter to keep): ");
    String language = scanner.nextLine();
    if (!language.isEmpty()) book.language = language;

    System.out.print("Enter new Rating (or press Enter to keep): ");
    String rating = scanner.nextLine();
    if (!rating.isEmpty()) book.rating = Double.parseDouble(rating);

    System.out.print("Is Reserved? (Yes/No): ");
    String reserved = scanner.nextLine();
    if (!reserved.isEmpty()) book.reserved = reserved;

    System.out.println("✅ Book updated!");
    saveBooksToFile();
}
    private static void deleteBook(Scanner scanner) {
    System.out.print("Enter Book ID to delete: ");
    String bookId = scanner.nextLine();

    if (bookDatabase.remove(bookId) != null) {
        saveBooksToFile();
        System.out.println("✅ Book deleted successfully.");
    } else {
        System.out.println("❌ Book ID not found.");
    }
}
    private static void searchBooks(Scanner scanner) {
    System.out.print("Enter keyword to search (title/author/genre): ");
    String keyword = scanner.nextLine().toLowerCase();

    boolean found = false;
    for (Book book : bookDatabase.values()) {
        if (book.title.toLowerCase().contains(keyword) ||
            book.author.toLowerCase().contains(keyword) ||
            book.genre.toLowerCase().contains(keyword)) {
            System.out.println(book);
            found = true;
        }
    }

    if (!found) {
        System.out.println("❌ No matching books found.");
    }
}
    private static void listBooks() {
    if (bookDatabase.isEmpty()) {
        System.out.println("❌ No books in catalog.");
        return;
    }

    System.out.println("\n📚 Book Catalog:");
    for (Book book : bookDatabase.values()) {
        System.out.println(book);
    }
}
    static void bookManage(){
         Scanner s = new Scanner(System.in);
        while (true) {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Add Book");
            System.out.println("2. Update Book");
            System.out.println("3. Delete Book");
            System.out.println("4. List All Books");
            System.out.println("5. Search Books");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = s.nextInt();
            s.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    updateBook(s);
                    break;
                case 3:
                    deleteBook(s);
                    break;
                case 4:
                    listBooks();
                    break;
                case 5:
                    searchBooks(s);
                case 6:
                    Admin();
                default:
                    System.out.println("Invalid choice, please enter a number between 1 and 8.");
                    break;
            }
        }
        
        
    }
    static void viewNot(){
        
    File enquiryFile = new File("enquiry.txt");

    if (!enquiryFile.exists()) {
        System.out.println("❌ No enquiries found. The file does not exist.");
         Admin();
        return;
    }

    System.out.println("📥 USER ENQUIRIES:");
    try (BufferedReader reader = new BufferedReader(new FileReader(enquiryFile))) {
        String line;
        boolean empty = true;

        while ((line = reader.readLine()) != null) {
            if (!line.trim().isEmpty()) {
                System.out.println("- " + line);
                 Admin();
                empty = false;
            }
        }

        if (empty) {
            System.out.println("📭 The enquiry file is currently empty.");
            Admin();
        }

    } catch (IOException e) {
        System.out.println("⚠ Error reading from enquiry.txt: " + e.getMessage());
         Admin();
    }
}
                        
    
    
                                                                                                                         //register
  //====================================================================================================================================  
    static void Admin() {
        Scanner s = new Scanner(System.in);
            System.out.println("\nMenu:");
            System.out.println("1. Register new Employee");
            System.out.println("2. Read from file");
            System.out.println("3. View Enquiries");
            System.out.println("4. Manage Books");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            option = s.nextInt();
            
            switch (option) {
                case 1:
                   AdminFile();
                    break;
                case 2:
                    readFromFile();
                    break;
                case 3:
                    viewNot();
                    break;
                case 4:
                    bookManage();
                    break;
                case 5:
                    start();
                default:
                    System.out.println("Invalid option. Please choose again.");
            }
        }                                                                                                            //Admin section
      //===============================================================================================================================  
    static void  AdminFile() {
        Scanner s = new Scanner(System.in);
        System.out.println("Please In Put Staff Information");
        System.out.println("Please Enter Staff Name");
        Name = s.nextLine();
        System.out.println("Please Enter Staff Surname");
        Surname = s.nextLine();
        System.out.println("Please Enter your Username");
        User = s.nextLine();
        System.out.println("Please Enter your Password");
        Password = s.nextLine();
        System.out.println("Please Enter occupation");
        OC =s.nextLine();
        System.out.println("Please Enter Email address");
        Email =s.nextLine();
               
    String convert ="ID number: " + ID + ", Name: " + Name + ", Surname: " + Surname + ", Username: " + User + ", Password: " + Password + ", Occupation: " + OC + ", Email: " + Email + "\n";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILESTAFF, true))) {
            writer.write(convert);
            writer.close();
            System.out.println("Data written to file successfully.");
            Entry();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
            e.printStackTrace();
            Entry();
        }
    }                                                                                                               //readFromFile
//=======================================================================================================================================
    static void readFromFile() {
        Scanner s = new Scanner(System.in);
        System.out.println("Which File do you want to Access");
        System.out.println("1. User Informaton");
        System.out.println("2. Staff Informaton");
        option = s.nextInt();
        
        switch(option){
            case 1:
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME2))) {
            String line;
            System.out.println("Data from file:");
            while ((line = reader.readLine()) != null) {
            System.out.println(line); 
            }
            Admin(); 
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
        break;
        
        case 2:
        try (BufferedReader reader = new BufferedReader(new FileReader(FILESTAFF))) {
            String line;
            System.out.println("Data from file:");
            while ((line = reader.readLine()) != null) {
            System.out.println(line); 
            }
            Admin(); 
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
        break;
}                                                                                                                           //Login
      //==============================================================================================================================  
    }
     static void Entry(){
        Scanner s = new Scanner(System.in);
        System.out.println("Please Choose Type of Login");
        System.out.println("1. User Loging");
        System.out.println("2. Admin Loging");
        System.out.println("3. User Registeration");
        System.out.println("4. Exit");
        option = s.nextInt();
        
       switch(option){
           case 1: 
               UserLogin();
           break;
           case 2: 
               StaffLogin();
           break;
           case 3:
               RegisterUserFile();
           break;
           case 4:
                    System.out.println("Exiting...");
                    return;
        default:
            System.out.println("Invalid option. Please choose again.");  
       }
    }                                                                                                                       //about
     //====================================================================================================================================
    static void About(){ //THIS METHOD IS FOR ABOUT
               Scanner s = new Scanner(System.in);
               int choice;

        do {
            showMenu();
            System.out.print("Enter your choice (0 to exit): ");
            choice = s.nextInt();
            s.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    printContactInformation();
                    break;
                case 2:
                    printLocation();
                    break;
                case 3:
                    printRulesAndGuidelines();
                    break;
                case 4:
                    printSocialMediaLinks();
                    break;
                case 5:
                    printLibraryProfile();
                    break;
                case 0:
                    start();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.\n");
            }

        } while (choice != 0);

        s.close();
        
    }
        // Menu display
    public static void showMenu() {
        System.out.println("WELCOME TO THE READ REALM LIBRARY");
        System.out.println(" 📚===== ABOUT US MENU =====📚");
        System.out.println("1. Contact Information");
        System.out.println("2. Location / Map");
        System.out.println("3. Rules and Guidelines");
        System.out.println("4. Social Media Links");
        System.out.println("5. Library Profile");
        System.out.println("0. Exit");
        System.out.println("--------------------------------");
    }

    public static void printContactInformation() {
        System.out.println("Contact Information:");
        System.out.println("Email: contact@READREALM.org");
        System.out.println("Phone: 065 588 8114");
    }

    public static void printLocation() {
        System.out.println("Location:");
        System.out.println("JOHANNESBURG, SANDTON, BK 12345");
        System.out.println("Map: https://maps.google.com/?q=Library+Booktown\n");
    }

    public static void printRulesAndGuidelines() {
        System.out.println(" Rules and Guidelines:");
        System.out.println("- Keep noise levels low.");
        System.out.println("- Return books on time.");
        System.out.println("- No food or drinks in the reading areas.");
    }

public static void printSocialMediaLinks() {
        System.out.println(" Social Media Links:");
        System.out.println("Facebook: https://facebook.com/READREALM");
        System.out.println("Twitter: https://twitter.com/READREALM");
        System.out.println("Instagram: https://instagram.com/READREALM");
    }

    public static void printLibraryProfile() {
        System.out.println(" Library Profile:");
        System.out.println("Opening Hours:");
        System.out.println("  Monday - Friday: 9:00 AM to 6:00 PM");
        System.out.println("  Saturday: 10:00 AM to 4:00 PM");
        System.out.println("  Sunday: Closed");
        System.out.println("Branches:");
        System.out.println("  - SOWETO Branch");
        System.out.println("  - PRETORIA Branch");
        System.out.println("  - CAPETOWN Branch");
    }  
                                                                                                            //Book Catalog
        //===========================================================================================================================
        static void Thriller(){
        Scanner s = new Scanner(System.in);
        while(true){
        try {
        File file = new File("BookCatalog.txt"); // Make sure the genre is included in each line
        Scanner fileScanner = new Scanner(file);
        
        System.out.println(" Thriller Books:");
        System.out.println(" BOOK ID_____BOOK NAME_________________________________AUTHOR____________________GENRE_______________PRICE_______STATUS_________LANGUAGE____RATINGS__PG   ");
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            if (line.toLowerCase().contains(""+genre)) {
                System.out.println(line);
            }
        }
        fileScanner.close();
    } catch (Exception e) {
        System.out.println("Error reading file: " + e.getMessage());
    }
        System.out.println("________________________________________________________________________________________________________________________________________________________");
         System.out.println("");   
        System.out.println("1.Back");
        System.out.println("2.Menu");
            uj=s.nextInt();
            switch(uj){
                case 1:
                    Catalog();
                    break;
                case 2:
                    menu();
                    break;
            }
        }   
    }        
    static void Catalog(){
         Scanner s = new Scanner(System.in);
         System.out.println("Select genre of interest");
                    System.out.println(" 1. Thriller");
                    System.out.println(" 2. Self-Help");
                    System.out.println(" 3. Fiction");
                    System.out.println(" 4. Classic");
                    System.out.println(" 5. Dystopian");
                    System.out.println(" 6. Biography");
                    System.out.println(" 7. Horror");
                    System.out.println(" 8. Non-Fiction");
                    System.out.println(" 9. Finance");
                    System.out.println(" 10. Children");
                    System.out.println(" 11. Sci-Fi");
                    System.out.println(" 12. Romance");
                    System.out.println(" 13. Adventure");
                    System.out.println(" 14. Historical Fiction");
                    System.out.println(" 15. Post-Apocalyptic");
                    System.out.println(" 16. Fantasy");
                    System.out.println(" 17. All");
         num =s.nextInt();
             
            if (num ==1) {
                genre="thriller";
                 Thriller(); 
            } else if (num == 2){
                genre="self-help";
                 Thriller(); 
            }    else if (num == 3){
                genre="fiction";
                 Thriller(); 
            }    else if (num == 4){
                genre="classic";
                 Thriller(); 
            }    else if (num == 5){
                genre="dystopian";
                 Thriller(); 
            }    else if (num == 6){
                genre="biography";
                 Thriller(); 
            }    else if (num == 7){
                genre="horror";
                 Thriller(); 
            }    else if (num == 8){
                genre="non-fiction";
                 Thriller(); 
            }    else if (num == 9){
                genre="finance";
                 Thriller(); 
            }    else if (num == 10){
                genre="children";
                 Thriller(); 
            }    else if (num == 11){
                genre="sci-fi";
                 Thriller(); 
            }    else if (num == 12){
                genre="romance";
                 Thriller(); 
            }    else if (num == 13){
                genre="adventure";
                 Thriller(); 
            }    else if (num == 14){
                genre="historical fiction";
                 Thriller(); 
            }    else if (num == 15){
                genre="post-apocalyptic";
                 Thriller(); 
            }    else if (num == 16){
                genre="fantasy";
                 Thriller(); 
            }    else if (num == 17){
                genre="|";
                 Thriller(); 
            }   
         
    }                 
       
                                                                                                                 //Search function
    //===============================================================================================================================
    static void AuthorName() {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter book name: ");
        Book = s.nextLine();
        try {
            File file = new File("BookCatalog.txt");
            Scanner fileScanner = new Scanner(file);
            boolean found = false;
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                if (line.toLowerCase().contains(Book.toLowerCase())) {
                    System.out.println("Book found: " + line);
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Book not found.");
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        }
    }
    static void BookName() {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter book name: ");
        Book = s.nextLine();
        try {
            File file = new File("BookCatalog.txt");
            Scanner fileScanner = new Scanner(file);
            boolean found = false;
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                if (line.toLowerCase().contains(Book.toLowerCase())) {
                    System.out.println("Book found:");
                    System.out.println(" BOOK ID_____BOOK NAME_________________________________AUTHOR____________________GENRE_______________PRICE_______STATUS_________LANGUAGE____RATINGS__PG");
                    System.out.println(""+ line);
                    filter();
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Book not found.");
                filter();
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        }

    }
    static void filter(){
         Scanner s = new Scanner(System.in);
        System.out.println(" 1. Type in specific book/author");
        System.out.println(" 2. Exit");
   
        option = s.nextInt();

        switch (option) {
            case 1:
                BookName();
                break;
            case 2:
                menu();
                break;
        }
    }                                                                                                           //Loan/return books
    //================================================================================================================================
    static String getDueDate(int days) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, days);   
        return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
    }
        // ==== RESERVE BOOK ====
    static void reserveBook(String userId, String bookId) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RESERVATIONS_FILE, true))) {
            bw.write(bookId + "," + userId);
            bw.newLine();
            System.out.println("Book reserved successfully.");
        } catch (IOException e) {
            System.out.println("Error reserving book.");
        }
    }
    
    
        public static boolean isValidUser(String userId) {
    try (BufferedReader reader = new BufferedReader(new FileReader("Member.txt"))) {
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.trim().toLowerCase().contains(("id number: " + userId).toLowerCase())) {
                return true;
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return false;
}
        
    
static boolean isBookAvailable(String bookId) {
    boolean existsInCatalog = false;

    // 1. Check if book exists in BookCatalog.txt
    try (Scanner scanner = new Scanner(new File("BookCatalog.txt"))) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split("\\|");
            if (parts.length > 1 && parts[1].trim().equalsIgnoreCase(bookId)) {
                existsInCatalog = true;
                break;
            }
        }
    } catch (IOException e) {
        System.out.println("Error reading BookCatalog.txt");
        return false;
    }

    if (!existsInCatalog) return false;

    // 2. Check if loan.txt exists
    File loanFile = new File("loan.txt");
    if (!loanFile.exists()) {
        return true; // No loans yet
    }

    // 3. Check if book is already loaned
    try (Scanner scanner = new Scanner(loanFile)) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");
            if (parts.length > 0 && parts[0].trim().equalsIgnoreCase(bookId)) {
                return false; // Book already loaned
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }

    return true;
}

    static void returnBook() {
        Scanner s=new Scanner(System.in);
        System.out.print("Enter your User ID: ");
        String userId = s.nextLine();
        if (!isValidUser(userId)) {
        System.out.println("Invalid User ID. Access denied.");
        return; // Exit the method
    }
        System.out.print("Enter Book ID to return: ");
        String bookId = s.nextLine();

        File loanFile = new File(LOANS_FILE);
        List<String> updatedLines = new ArrayList<>();
        boolean returned = false;

        try (BufferedReader br = new BufferedReader(new FileReader(loanFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(bookId) && data[1].equals(userId)) {
                    returned = true; // Skip this line (returning)
                } else {
                    updatedLines.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading loans file.");
        }

        if (returned) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(LOANS_FILE))) {
                for (String line : updatedLines) {
                    bw.write(line);
                    bw.newLine();
                }
                System.out.println("Book returned successfully.");
            } catch (IOException e) {
                System.out.println("Error updating loans file.");
            }
        } else {
            System.out.println("No matching loan record found.");
        }
    }
    static void loanBook() {
        Scanner s=new Scanner(System.in);
        System.out.print("Enter your User ID: ");
        String userId = s.nextLine();
        
            if (!isValidUser(userId)) {
              System.out.println("Invalid User ID. Access denied.");
                 return; // Exit the method
}
        System.out.print("Enter Book ID to loan: ");
        String bookId = s.nextLine();

        if (isBookAvailable(bookId)) {
            String dueDate = getDueDate(30);
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(LOANS_FILE, true))) {
                bw.write(bookId + "," + userId + "," + dueDate);
                bw.newLine();
                System.out.println("Book loaned successfully. Due in 30 days: " + dueDate);
            } catch (IOException e) {
                System.out.println("Error writing to loans file.");
            }
        } else {
            System.out.println("Book is not available. Would you like to reserve it? (yes/no)");
            if (s.nextLine().equalsIgnoreCase("yes")) {
                reserveBook(userId, bookId);
            }
        }
    }
    static void loan(){
        Scanner s=new Scanner(System.in);
        while (true) {
            System.out.println("\n===== Service menu =====");
            System.out.println("1. Loan a Book");
            System.out.println("2. Return a Book"); 
            System.out.println("3. Exit");
            System.out.print("Choose option: ");

            String choice = s.nextLine();

            switch (choice) {
                case "1": loanBook(); break;
                case "2": returnBook(); break;
                case "3": menu();
                default: System.out.println("Invalid option.");
            }
        }
    }                                                                                                              //buy book
    //===============================================================================================================================
    
    static void buy(){
         Scanner s = new Scanner(System.in);
        loadBooksFromFile();

        System.out.println("=== BOOK PURCHASE ===");

        while (true) {
            System.out.println("\n1. View Available Books");
            System.out.println("2. Buy a Book");
            System.out.println("3. View All Transactions");
            System.out.println("4. Exit");
            System.out.print("Choose option: ");
            int option = s.nextInt();
            s.nextLine();

            switch (option) {
                case 1:
                    viewBooks();
                    break;
                case 2:
                    buyBook(s);
                    break;
                case 3:
                    viewTransactions();
                    break;
                case 5:
                    menu(); 
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
        
    }
    
     // Load books from books.txt file
    private static void loadBooksFromFile() {
        bookDatabase.clear();
        File file = new File(BOOKS_FILE);
        if (!file.exists()) {
            System.out.println("⚠️ No books.txt file found. Creating a new one...");
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("❌ Could not create books.txt.");
            }
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 2);
                if (parts.length == 2) {
                    Book book = new Book(parts[0].trim(), parts[1].trim());
                    bookDatabase.put(book.bookId, book);
                }
            }
        } catch (IOException e) {
            System.err.println("❌ Error reading books.txt: " + e.getMessage());
        }
    }

    // Write current bookDatabase to books.txt
    private static void saveBooksToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(BOOKS_FILE))) {
            for (Book book : bookDatabase.values()) {
                bw.write(book.bookId + "," + book.title);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("❌ Error writing to books.txt: " + e.getMessage());
        }
    }

    private static void viewBooks() {
        if (bookDatabase.isEmpty()) {
            System.out.println("No books available.");
            return;
        }
        System.out.println("Available Books:");
        for (Book Available : bookDatabase.values()) {
            System.out.println(Available);
        }
    }

    private static void buyBook(Scanner scanner) {
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();

        System.out.print("Enter Book ID to Buy: ");
        String bookId = scanner.nextLine();

        if (!bookDatabase.containsKey(bookId)) {
            System.out.println("❌ Book ID not found.");
            return;
        }

        System.out.print("Enter Amount  (R): ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        PurchaseTransaction transaction = new PurchaseTransaction(userId, amount, bookId);
        transactions.add(transaction);
        bookDatabase.remove(bookId);
        saveBooksToFile();

        System.out.println("\n✅ Transaction Completed Successfully:");
        System.out.println(transaction);
    }

    private static void viewTransactions() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions recorded.");
            return;
        }

        System.out.println("All Purchase Transactions:");
        for (PurchaseTransaction pt : transactions) {
            System.out.println("------------------------------");
            System.out.println(pt);
        }
    }

    private static void deleteBookById(String bookId) {
        if (bookDatabase.containsKey(bookId)) {
            bookDatabase.remove(bookId);
            saveBooksToFile();
            System.out.println(" Book with ID " + bookId + " deleted successfully.");
        } else {
            System.out.println(" Book ID not found. No deletion occurred.");
        }                                                                                                              //com
    }  //===========================================================================================================================
    static void Com() {
    Scanner scanner = new Scanner(System.in);
    
    System.out.print("Enter your User ID: ");
    String userId = scanner.nextLine().trim();

    int choice;

    do {
        show();
        System.out.print("Enter your choice (0 to Exit): ");

        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next();
        }

        choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        switch (choice) {
            case 1 -> viewNotifications();
            case 2 -> submitEnquiry(userId);
            case 3 -> viewMyEnquiries(userId);
            case 0 -> menu();
            default -> System.out.println("Invalid option. Please try again.");
        }

    } while (choice != 0);
}
    static void show() {
    System.out.println("\n---------- USER COMMUNICATION SYSTEM ----------");
    System.out.println("1. View Notifications from Admin");
    System.out.println("2. Submit Enquiry to Management");
    System.out.println("3. View My Submitted Enquiries");
    System.out.println("0. Go back");
    System.out.println("-----------------------------------------------");
}
    static void submitEnquiry(String userId) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Submit an Enquiry:");
    System.out.print("Enter your enquiry message: ");
    String message = scanner.nextLine().trim();

    if (message.isEmpty()) {
        System.out.println("❌ Enquiry cannot be empty.");
        return;
    }

    try (BufferedWriter writer = new BufferedWriter(new FileWriter("enquiry.txt", true))) {
        writer.write(userId + ": " + message);
        writer.newLine();
        System.out.println("✅ Enquiry submitted successfully.");
    } catch (IOException e) {
        System.out.println("⚠ Error writing to enquiry file.");
    }
}
    static void viewMyEnquiries(String userId) {
    System.out.println("MY SUBMITTED ENQUIRIES:");

    try (BufferedReader reader = new BufferedReader(new FileReader("enquiry.txt"))) {
        String line;
        boolean found = false;

        while ((line = reader.readLine()) != null) {
            if (line.startsWith(userId + ":")) {
                System.out.println("- " + line.substring(userId.length() + 1).trim());
                found = true;
            }
        }

        if (!found) {
            System.out.println("You have not submitted any enquiries yet.");
        }

    } catch (IOException e) {
        System.out.println("⚠ Error reading enquiries.");
    }
}
    static void viewNotifications() {
    System.out.println("ADMIN NOTIFICATIONS:");

    try (BufferedReader reader = new BufferedReader(new FileReader("notifications.txt"))) {
        String line;
        boolean empty = true;

        while ((line = reader.readLine()) != null) {
            System.out.println("- " + line);
            empty = false;
        }

        if (empty) {
            System.out.println("No notifications found.");
        }

    } catch (IOException e) {
        System.out.println("⚠ Error reading notifications. Make sure 'notifications.txt' exists.");
    }
}                                                                                                                    //reserve book
    //===============================================================================================================================
         static void reserveB() {
    Scanner scanner = new Scanner(System.in);

    System.out.print("Enter your User ID: ");
    String userId = scanner.nextLine().trim();

    // Validate user (optional)
    if (!isValidUser(userId)) {
        System.out.println("❌ Invalid User ID. Reservation failed.");
        return;
    }

    System.out.print("Enter Book ID to reserve: ");
    String bookId = scanner.nextLine().trim();

    // Check if book exists
    if (!isBookAvailable(bookId)) {
        System.out.println("❌ Book ID not found in catalog.");
        menu();
        return;
    }

    // Check if already reserved
    if (isBookAvailable(bookId)) {
        System.out.println("⚠ You already reserved this book.");
        menu();
        return;
    }

    String reservationDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    try (BufferedWriter writer = new BufferedWriter(new FileWriter("reservations.txt", true))) {
        writer.write(bookId + "," + userId + "," + reservationDate);
        writer.newLine();
        System.out.println("✅ Book reserved successfully on " + reservationDate);
    } catch (IOException e) {
        System.out.println("⚠ Error writing reservation.");
    }
}
    
    
    
    
                                                                                                                          //Menu
    //=================================================================================================================================
    static void menu(){
        Scanner s=new Scanner(System.in);
        System.out.println("-----------------------------------------------------------------------------------------------------------------");
        System.out.println(" Hello there you bookworm, what's in your agenda today!! ");
                                System.out.println("   1.Book Catalogue");
                                System.out.println("   2.Search Book");
                                System.out.println("   3.Borrow/Return books");
                                System.out.println("   4.Purchase book");
                                System.out.println("   5.Enquiry");
                                System.out.println("   6.Set Reservation");
                                System.out.println("   7.Log out");
                                System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
                                two=s.nextInt();
                                switch(two){
                                    case 1:
                                       Catalog();
                                       break;
                                    case 2:
                                        filter();
                                        break;
                                    case 3:
                                        loan();
                                        break;
                                    case 4:
                                        buy();
                                        break;
                                    case 5:
                                        Com();
                                        break;
                                    case 6:
                                        reserveB();
                                        break;
                                    case 7:
                                        start();
                                        break;
                                        
                                }
    }
    
    
   //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
 
    
                                                                                                                            //start()
    //==================================================================================================================================
    static void start(){
        Scanner s = new Scanner(System.in);
        System.out.println(" ======READ REALM LIBRARY=======   ");
        System.out.println("");
        System.out.println("       1.Log in ");
        System.out.println("       2.About Library");
        System.out.println("       3.Exit ");
        System.out.println(" ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        one=s.nextInt();
        switch(one){
            case 1:
                Entry();
                break;
            case 2:
                About();
                break;
            case 3:
                System.out.println("Exiting program. Goodbuy!");
                System.exit(0);
                break;
        }
        

    }                                                                                                               //main
    //================================================================================================================================
    public static void main(String[] args) {
   start();
    }
    
    
}
