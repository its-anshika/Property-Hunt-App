package org.example;

import org.example.entities.ListType;
import org.example.entities.User;
import org.example.services.PropertyService;
import org.example.services.ShortlistService;
import org.example.services.UserService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("Welcome to Property Hunt App!");
        Database db = Database.getInstance();
        UserService userService = new UserService(db);
        PropertyService propertyService = new PropertyService(db, userService);
        ShortlistService shortlistService = new ShortlistService(db , propertyService);
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nChoose an option: ");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Logout");
            System.out.println("4. List Property");
            System.out.println("5. Search Property");
            System.out.println("6. Shortlist Property");
            System.out.println("7. View Shortlisted Properties");
            System.out.println("8. View Listed Properties");
            System.out.println("9. Mark Property as Sold");
            System.out.println("0. Exit");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter username: ");
                    userService.register(sc.nextLine());
                    break;
                case "2":
                    System.out.print("Enter username: ");
                    userService.login(sc.nextLine());
                    break;
                case "3":
                    System.out.print("Enter username: ");
                    userService.logout(sc.nextLine());
                    break;
                case "4":
                    User lister = userService.getActiveUser();
                    if (lister == null) {
                        System.out.println("Please login first.");
                        break;
                    }
                    System.out.print("Enter property location: ");
                    String loc = sc.nextLine();
                    System.out.print("Enter price: ");
                    Integer price = Integer.parseInt(sc.nextLine());
                    System.out.print("Enter listing type (SELL/RENT): ");
                    String type = sc.nextLine();
                    System.out.print("Enter size (in sq.ft): ");
                    Double size = Double.parseDouble(sc.nextLine());
                    System.out.print("Enter number of rooms (1/2/3): ");
                    int rooms = Integer.parseInt(sc.nextLine());

                    propertyService.listProperty(loc, price, size, rooms, type);
                    break;
                case "5":
                    System.out.print("Enter location (or leave blank): ");
                    String location = sc.nextLine();
                    System.out.print("Enter price range (e.g. 1000-5000 or 3000): ");
                    String priceRange = sc.nextLine();
                    System.out.print("Enter listing type (SELL/RENT): ");
                    String listingType = sc.nextLine();
                    System.out.print("Enter size range (e.g. 500-1000): ");
                    String sizeRange = sc.nextLine();
                    System.out.print("Enter rooms (1/2/3): ");
                    String roomCount = sc.nextLine();
                    System.out.print("Sort by (price/size): ");
                    String sortBy = sc.nextLine();
                    propertyService.printProperties(propertyService.search(location, priceRange, listingType, sizeRange, roomCount, sortBy));
                    break;
                case "6":
                    User shortlister = userService.getActiveUser();
                    if (shortlister == null) {
                        System.out.println("Login to shortlist.");
                        break;
                    }
                    System.out.print("Enter property ID to shortlist: ");
                    String pid = sc.nextLine();
                    shortlistService.addToShortlist(shortlister, pid);
                    break;
                case "7":
                    User active = userService.getActiveUser();
                    if (active != null) {
                        shortlistService.viewShortlistedProperties(active);
                    } else {
                        System.out.println("Login first.");
                    }
                    break;
                case "8":
                    User current = userService.getActiveUser();
                    if (current != null) {
                        propertyService.viewListedProperties();
                    } else {
                        System.out.println("Login first.");
                    }
                    break;
                case "9":
                    User seller = userService.getActiveUser();
                    if (seller != null) {
                        System.out.print("Enter property ID to mark as sold: ");
                        String soldId = sc.nextLine();
                        propertyService.markSold(soldId);
                    } else {
                        System.out.println("Login first.");
                    }
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
