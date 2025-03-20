package com.service;

import com.DataBaseUtil;
import com.model.Order;
import com.model.Route;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class JourneyService {

    private Scanner scanner = new Scanner(System.in);
    private Map<String, String[]> scheduleMap = new HashMap<>();

    public JourneyService() {
        initializeSchedule();
    }

    private void initializeSchedule() {
        scheduleMap.put("MONDAY", new String[]{
                "Hyderabad (MGBS) - Vijayawada",
                "Visakhapatnam - Rajahmundry",
                "Tirupati - Chennai",
                "Kurnool - Bangalore",
                "Guntur - Ongole"
        });
        scheduleMap.put("TUESDAY", new String[]{
                "Hyderabad (MGBS) - Vijayawada",
                "Visakhapatnam - Rajahmundry",
                "Tirupati - Chennai",
                "Kurnool - Bangalore",
                "Guntur - Ongole"
        });
        scheduleMap.put("WEDNESDAY", new String[]{
                "Hyderabad (MGBS) - Vijayawada",
                "Visakhapatnam - Rajahmundry",
                "Tirupati - Chennai",
                "Kurnool - Bangalore",
                "Guntur - Ongole"
        });
        scheduleMap.put("THURSDAY", new String[]{
                "Hyderabad (MGBS) - Vijayawada",
                "Visakhapatnam - Rajahmundry",
                "Tirupati - Chennai",
                "Kurnool - Bangalore",
                "Guntur - Ongole"
        });
        scheduleMap.put("FRIDAY", new String[]{
                "Hyderabad (MGBS) - Vijayawada",
                "Visakhapatnam - Rajahmundry",
                "Tirupati - Chennai",
                "Kurnool - Bangalore",
                "Guntur - Ongole"
        });
        scheduleMap.put("SATURDAY", new String[]{
                "Hyderabad (MGBS) - Vijayawada",
                "Visakhapatnam - Rajahmundry",
                "Tirupati - Chennai",
                "Kurnool - Bangalore",
                "Guntur - Ongole"
        });
        scheduleMap.put("SUNDAY", new String[]{
                "Hyderabad (MGBS) - Vijayawada",
                "Visakhapatnam - Rajahmundry",
                "Tirupati - Chennai",
                "Kurnool - Bangalore",
                "Guntur - Ongole"
        });
        
    }

    public void planJourney() {
        System.out.println("\nPlan Journey");
        System.out.print("Enter date (yyyy-MM-dd): ");
        String dateInput = scanner.nextLine();
        LocalDate date = LocalDate.parse(dateInput, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        String dayName = dayOfWeek.name();

        String[] routes = scheduleMap.get(dayName);
        if (routes != null) {
            System.out.println("\nAvailable routes on " + dayName + ":");
            for (int i = 0; i < routes.length; i++) {
                System.out.println((i + 1) + ". " + routes[i]);
            }
            System.out.print("\nSelect a route by entering the number: ");
            int routeChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (routeChoice > 0 && routeChoice <= routes.length) {
                System.out.println("\nYou have selected: " + routes[routeChoice - 1]);
                System.out.print("Enter number of passengers: ");
                int numPassengers = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                double ticketPrice = 500.0; // Ticket price per passenger
                double totalCost = numPassengers * ticketPrice;

                System.out.println("Ticket Price per Passenger: $" + ticketPrice);
                System.out.println("Total Passengers: " + numPassengers);
                System.out.println("Total Cost: $" + totalCost);

                System.out.println("\nDo you want to reschedule or exit?");
                System.out.println("1. Reschedule");
                System.out.println("2. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                if (choice == 2) {
                    System.out.println("Exiting the system. Thank you!");
                    System.exit(0);  // Ensures immediate exit
                } else if (choice == 1) {
                    reScheduleJourney();
                } else {
                    System.out.println("Invalid choice.");
                }

            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        } else {
            System.out.println("No routes available for the selected date.");
        }
    }


    public void reScheduleJourney() {
        System.out.println("\nReschedule Journey");

        // Prompt the user to enter a new date for the journey
        String newDate;
        LocalDate newJourneyDate = null;

        while (newJourneyDate == null) {
            System.out.print("Enter your new journey date (yyyy-MM-dd): ");
            newDate = scanner.nextLine().trim();

            if (newDate.isEmpty()) {
                System.out.println("Date cannot be empty. Please enter a valid date.");
                continue;
            }

            try {
                newJourneyDate = LocalDate.parse(newDate, DateTimeFormatter.ISO_LOCAL_DATE);
            } catch (Exception e) {
                System.out.println("Invalid date format. Please enter the date in yyyy-MM-dd format.");
            }
        }

        System.out.println("New journey date selected: " + newJourneyDate);

        // Ask for new number of passengers
        System.out.print("Enter number of passengers: ");
        int passengers = scanner.nextInt();
        double ticketPrice = 500.0; // Assuming fixed price per passenger
        double totalCost = passengers * ticketPrice;

        System.out.println("Ticket Price per Passenger: $" + ticketPrice);
        System.out.println("Total Passengers: " + passengers);
        System.out.println("Total Cost: $" + totalCost);

        System.out.println("Do you want to exit?");
        System.out.println("1. Exit");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice == 1) {
            System.out.println("Exiting the system. Thank you!");
            System.exit(0);  // Ensures immediate exit
        }
    }
}