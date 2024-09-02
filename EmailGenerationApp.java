/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */


/**
 *
 * @author POOJITHA
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

class Employee {
    private String name;
    private String emailId;
    private String password;

    public Employee(String name, String emailId, String password) {
        this.name = name;
        this.emailId = emailId;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Employee Name: " + name + ", Email ID: " + emailId + ", Password: " + password;
    }
}

class EmailGenerator {
    private static final String DOMAIN = "@organization.com";
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int PASSWORD_LENGTH = 8;

    public static String generateEmail(String name) {
        String[] nameParts = name.split(" ");
        if (nameParts.length < 2) {
            throw new IllegalArgumentException("Please enter both first and last names.");
        }

        StringBuilder emailBuilder = new StringBuilder();
        emailBuilder.append(nameParts[0].charAt(0)).append(nameParts[1]).append(DOMAIN);
        return emailBuilder.toString().toLowerCase();
    }

    public static String generatePassword() {
        Random random = new Random();
        StringBuilder passwordBuilder = new StringBuilder(PASSWORD_LENGTH);
        
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            passwordBuilder.append(CHARACTERS.charAt(index));
        }
        
        return passwordBuilder.toString();
    }

    
    public static String checkPasswordStrength(String password) {
        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasDigit = false;

        
        if (password.length() >= 8) {
            for (char ch : password.toCharArray()) {
                if (Character.isUpperCase(ch)) hasUpperCase = true;
                if (Character.isLowerCase(ch)) hasLowerCase = true;
                if (Character.isDigit(ch)) hasDigit = true;
            }

            if (hasUpperCase && hasLowerCase && hasDigit) {
                return "Strong";
            } else {
                return "Weak";
            }
        } else {
            return "Weak";
        }
    }
}

public class EmailGenerationApp {
    private List<Employee> employees;

    public EmailGenerationApp() {
        employees = new ArrayList<>();
    }

    public void addEmployee(String name) {
        try {
            String email = EmailGenerator.generateEmail(name);
            System.out.println("Generated Email ID: " + email);
            String randomPassword = EmailGenerator.generatePassword();
            System.out.println("Generated Random Password: " + randomPassword);
            
            Scanner scanner = new Scanner(System.in);
            System.out.print("Would you like to change the password? (yes/no): ");
            String changePassword = scanner.nextLine();

            String finalPassword;
            if (changePassword.equalsIgnoreCase("yes")) {
                do {
                    System.out.print("Enter your desired password: ");
                    finalPassword = scanner.nextLine();
                    String strength = EmailGenerator.checkPasswordStrength(finalPassword);
                    System.out.println("Password Strength: " + strength);
                    
                    if (strength.equals("Weak")) {
                        System.out.println("Please choose a stronger password.");
                    }
                } while (EmailGenerator.checkPasswordStrength(finalPassword).equals("Weak"));
            } else {
                finalPassword = randomPassword; // Use random password if the user chooses not to change it
            }

            Employee employee = new Employee(name, email, finalPassword);
            employees.add(employee);
            System.out.println("Employee added: " + employee);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void displayEmployees() {
        System.out.println("Employee List:");
        for (Employee emp : employees) {
            System.out.println(emp);
        }
    }

    public static void main(String[] args) {
        EmailGenerationApp app = new EmailGenerationApp();
        Scanner scanner = new Scanner(System.in);
        String continueInput;

        do {
            System.out.print("Enter employee name (First Last): ");
            String name = scanner.nextLine();
            app.addEmployee(name);

            System.out.print("Do you want to add another employee? (yes/no): ");
            continueInput = scanner.nextLine();
        } while (continueInput.equalsIgnoreCase("yes"));

        app.displayEmployees();
        scanner.close();
    }
}

