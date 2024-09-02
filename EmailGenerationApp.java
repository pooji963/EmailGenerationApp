/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */


/**
 *
 * @author SAGAR
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public static String generateEmail(String name, List<String> existingEmails) {
        String[] nameParts = name.split(" ");
        if (nameParts.length < 2) {
            throw new IllegalArgumentException("Please enter both first and last names.");
        }

        StringBuilder emailBuilder = new StringBuilder();
        emailBuilder.append(nameParts[0].charAt(0)).append(nameParts[1]).append(DOMAIN);
        String email = emailBuilder.toString().toLowerCase();

        int count = 1;
        while (existingEmails.contains(email)) {
            email = emailBuilder.toString().toLowerCase().replace(DOMAIN, "") + count + DOMAIN;
            count++;
        }
        return email;
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

public class EmailGenerationApp extends JFrame {
    private List<Employee> employees;

    public EmailGenerationApp() {
        employees = new ArrayList<>();
        setupUI();
    }

    private void setupUI() {
        setTitle("Email Generation App");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Container container = getContentPane();
        container.setLayout(new GridLayout(6, 2));

        JLabel nameLabel = new JLabel("Enter Name (First Last):");
        JTextField nameField = new JTextField();

        JLabel emailLabel = new JLabel("Generated Email ID:");
        JTextField emailField = new JTextField();
        emailField.setEditable(false);

        JLabel passwordLabel = new JLabel("Generated Password:");
        JTextField passwordField = new JTextField();
        passwordField.setEditable(false);

        JButton generateButton = new JButton("Generate");
        JButton addButton = new JButton("Add Employee");
        JButton showButton = new JButton("Show Employees");

        JTextArea displayArea = new JTextArea(10, 30);
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                List<String> existingEmails = new ArrayList<>();
                for (Employee emp : employees) {
                    existingEmails.add(emp.getEmailId());
                }

                try {
                    String email = EmailGenerator.generateEmail(name, existingEmails);
                    String randomPassword = EmailGenerator.generatePassword();

                    emailField.setText(email);
                    passwordField.setText(randomPassword);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String email = emailField.getText();
                String password = passwordField.getText();

                Employee employee = new Employee(name, email, password);
                employees.add(employee);

                JOptionPane.showMessageDialog(null, "Employee added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayArea.setText("");
                for (Employee emp : employees) {
                    displayArea.append(emp.toString() + "\n");
                }
            }
        });

        container.add(nameLabel);
        container.add(nameField);
        container.add(emailLabel);
        container.add(emailField);
        container.add(passwordLabel);
        container.add(passwordField);
        container.add(generateButton);
        container.add(addButton);
        container.add(showButton);
        container.add(scrollPane);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new EmailGenerationApp().setVisible(true);
            }
        });
    }
}

