package com.service;

import com.DataBaseUtil;
import com.model.User;

import java.sql.*;
import java.util.Scanner;
import java.util.regex.Pattern;

public class UserService {

    Travels travel = new Travels();

    public UserService() {
    }

    // Email validation method
    private static boolean isValidEmail(String email) {
        // Regular expression for email validation
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return Pattern.compile(emailRegex).matcher(email).matches();
    }

    // Phone number validation method
    private static boolean isValidPhoneNumber(String phoneNumber) {
        // Regular expression for a 10-digit phone number
        String phoneRegex = "^[0-9]{10}$";
        return Pattern.compile(phoneRegex).matcher(phoneNumber).matches();
    }

    // Gender validation method
    private static boolean isValidGender(String gender) {
        return gender.equalsIgnoreCase("male") ||
               gender.equalsIgnoreCase("female") ||
               gender.equalsIgnoreCase("other");
    }

    public void registerNewAdmin() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nNew Admin User Registration");

        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter mobile number: ");
        String mobileNumber = scanner.nextLine();

        // Phone number validation check
        if (!isValidPhoneNumber(mobileNumber)) {
            System.out.println("Invalid phone number. It must contain exactly 10 digits. Please try again.");
            return;
        }

        System.out.print("Enter gender: ");
        String gender = scanner.nextLine();

        // Gender validation check
        if (!isValidGender(gender)) {
            System.out.println("Invalid gender. Please enter 'male', 'female', or 'other'.");
            return;
        }

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        // Email validation check
        if (!isValidEmail(email)) {
            System.out.println("Invalid email format. Please try again.");
            return;
        }

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        // Password strength validation
        int missingUpper = 1, missingLower = 1, missingDigit = 1, missingSpecial = 1;
        String specialCharacters = "!@#$%^&*()-+";

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) missingUpper = 0;
            else if (Character.isLowerCase(c)) missingLower = 0;
            else if (Character.isDigit(c)) missingDigit = 0;
            else if (specialCharacters.indexOf(c) != -1) missingSpecial = 0;
        }

        int requiredChanges = missingUpper + missingLower + missingDigit + missingSpecial;
        int minLengthRequired = Math.max(requiredChanges, 8 - password.length());

        if (minLengthRequired > 0) {
            System.out.println("Password is weak, try again.");
            return;
        }

        // Check if the user already exists
        if (isUserExists(email)) {
            System.out.println("User with this email: " + email + " already exists.");
            return;
        }

        String sql = "INSERT INTO users (first_name, last_name, mobile_number, gender, email, password, failed_count, account_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, mobileNumber);
            pstmt.setString(4, gender);
            pstmt.setString(5, email);
            pstmt.setString(6, password);
            pstmt.setInt(7, 0); // failed_count
            pstmt.setString(8, "Active"); // account_status
            pstmt.executeUpdate();
            System.out.println("Registration successful. Please log in to continue.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User login() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nUser Login");

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        // Email validation check
        if (!isValidEmail(email)) {
            System.out.println("Invalid email format. Please try again.");
            return null;
        }

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                if (rs.getString("password").equals(password)) {
                    System.out.println("\nLogin Successful");
                    return new User(rs.getString("first_name"), rs.getString("last_name"),
                            rs.getString("mobile_number"), rs.getString("gender"),
                            email, password, 0, rs.getString("account_status"));
                } else {
                    System.out.println("\nInvalid Credentials.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private boolean isUserExists(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";

        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}