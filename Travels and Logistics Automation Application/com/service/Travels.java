
package com.service;

import java.util.Scanner;

public class Travels {

    private static JourneyService journeyService = new JourneyService();

    public static void showMenuOptions2() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        boolean running = true;

        while (running) {
            System.out.println("3. Plan journey");
            System.out.println("4. Reschedule booking date");

            System.out.print("Enter your choice: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 3:
                    journeyService.planJourney();
                    break;
                case 4:
                    journeyService.reScheduleJourney();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    running = false ;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a correct option.");
                    break;
            }
        }
        scanner.close();  // Close the scanner when we're done with it
    }
}