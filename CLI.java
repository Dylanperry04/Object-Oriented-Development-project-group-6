import java.io.*;
import java.util.Scanner;

public class CLI {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Payroll System");
        System.out.print("Enter your User ID: ");
        String userId = scanner.nextLine();

        if ("A".equalsIgnoreCase(userId)) {
            showAdminMenu(scanner);
        } else {
            System.out.println("Invalid User ID. Access denied.");
        }

        scanner.close();
    }

    private static void showAdminMenu(Scanner scanner) {
        System.out.println("Admin Menu");
        boolean exit = false;

        while (!exit) {
            System.out.println("1. Add New Employee");
            System.out.println("2. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addNewEmployee(scanner);
                    break;
                case 2:
                    exit = true;
                    System.out.println("Exiting Admin Menu.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addNewEmployee(Scanner scanner) {
        System.out.println("Enter new employee details:");

        System.out.print("Employee ID: ");
        String employeeId = scanner.nextLine();

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Title: ");
        String title = scanner.nextLine();

        System.out.print("Point on Scale: ");
        int point = scanner.nextInt();
        scanner.nextLine();

        double[] rates = fetchPayScaleDetails(title, point);
        if (rates == null) {
            System.out.println("No matching title and point found in PayScale.csv. Employee not added.");
            return;
        }

        double annualRate = rates[0];
        double hourlyRate = rates[1];

        String employeesFilePath = "";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(employeesFilePath, true))) {
            String newEmployee = String.format("%s,%s,%s,%d,%.2f,%.2f",
                    employeeId, name, title, point, annualRate, hourlyRate);
            writer.write(newEmployee);
            writer.newLine();
            System.out.println("New employee added successfully!");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the Employees.csv file.");
            e.printStackTrace();
        }
    }

    private static double[] fetchPayScaleDetails(String title, int point) {
        String payScaleFilePath = "PayScale.csv";

        try (BufferedReader reader = new BufferedReader(new FileReader(payScaleFilePath))) {
            String line;
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");
                String payScaleTitle = columns[0].trim();
                int payScalePoint = Integer.parseInt(columns[1].trim());
                double annualRate = Double.parseDouble(columns[2].trim());
                double hourlyRate = Double.parseDouble(columns[3].trim());

                if (payScaleTitle.equalsIgnoreCase(title) && payScalePoint == point) {
                    return new double[]{annualRate, hourlyRate};
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("The PayScale.csv file was not found at: " + payScaleFilePath);
        } catch (IOException e) {
            System.out.println("An error occurred while reading the PayScale.csv file.");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Invalid data format in PayScale.csv.");
            e.printStackTrace();
        }
        return null;
    }
}
