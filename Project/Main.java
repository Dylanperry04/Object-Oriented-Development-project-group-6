import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.time.*;

/**
 * Command Line Interface (CLI) for managing the UL Payroll System.
 * Provides functionality for Employees, Admins, and HR to interact with payroll data.
 */
public class Main {
    /**
     * Main method to start the CLI application.
     *
     * @param args Command-line arguments (not used).
     */
    ///tommy
    public static void main(String[] args) {
        try {
            List<Employee> employees = CsvReader.readEmployees("Employees.csv");
            List<Payslip> payslips = CsvReader.readPayslips("PaySlip.csv");
            List<PayScale> payScales = CsvReader.readPayScales("PayScale.csv");
            List<Deductions> deductions = CsvReader.readDeductions("Deductions.csv");
            promoteEmployeesInOctober(employees, payScales);

            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("\n=== UL Payroll System ===");
                System.out.println("Login as:");
                System.out.println("E - Employee");
                System.out.println("A - Admin");
                System.out.println("HR - Human Resources");
                System.out.println("Q - Quit");
                System.out.print("Enter your role: ");
                String role = scanner.nextLine().trim().toUpperCase();

                if ("E".equals(role)) {
                    employeeMenu(employees, payslips, deductions, scanner);
                } else if ("A".equals(role)) {
                    adminMenu(employees, payScales, scanner);
                } else if ("HR".equals(role)) {
                    hrMenu(employees, payScales, scanner);
                } else if ("Q".equals(role)) {
                    System.out.println("Exiting system.");
                    break;
                } else {
                    System.out.println("Invalid input. Please try again.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays the Employee Menu and handles employee-specific functionality.
     *
     * @param employees  List of employees.
     * @param payslips   List of payslips.
     * @param deductions List of deductions.
     * @param scanner    Scanner for user input.
     */
    ///tommy
    private static void employeeMenu(List<Employee> employees, List<Payslip> payslips, List<Deductions> deductions, Scanner scanner) {
        System.out.println("\n=== Employee Menu ===");
        System.out.print("Enter your Employee ID: ");
        String employeeId = scanner.nextLine();

        Optional<Employee> matchingEmployee = employees.stream()
                .filter(e -> e.getEmployeeId().equals(employeeId))
                .findFirst();

        if (matchingEmployee.isEmpty()) {
            System.out.println("No employee found with ID: " + employeeId);
            return;
        }

        Employee employee = matchingEmployee.get();

        System.out.println("\n1. View Personal Details");
        System.out.println("2. View Payslip History");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            System.out.println("\n=== Personal Details ===");
            System.out.println("Name: " + employee.getName());
            System.out.println("Title: " + employee.getTitle());
            System.out.println("Point: " + employee.getPoint());
        } else if (choice == 2) {
            System.out.println("\n=== Payslip History ===");
            List<Payslip> employeePayslips = payslips.stream()
                    .filter(p -> p.getEmployeeId().equals(employeeId))
                    .collect(Collectors.toList());

            if (employeePayslips.isEmpty()) {
                System.out.println("No payslips found for Employee ID: " + employeeId);
            } else {
                for (Payslip payslip : employeePayslips) {
                    List<Deductions> employeeDeductions = deductions.stream()
                            .filter(d -> d.getEmployeeId().equals(employeeId))
                            .collect(Collectors.toList());
                    displayPayslip(payslip, employee, employeeDeductions);
                }
            }
        } else {
            System.out.println("Invalid choice.");
        }
    }
///sean
    private static void displayPayslip(Payslip payslip, Employee employee, List<Deductions> employeeDeductions) {
        System.out.println("\n=== Pay Slip ===");
        System.out.println("Employee ID: " + employee.getEmployeeId());
        System.out.println("Name: " + employee.getName());
        System.out.println("Title: " + employee.getTitle());
        System.out.println("Pay Date: " + payslip.getPayPeriod());

        // Calculate Gross Pay dynamically based on the employee type
        double grossPay = employee.getGrossPay();
        System.out.println("Gross Pay: $" + String.format("%.2f", grossPay));

        if (employee instanceof PartTimeEmployee) {
            PartTimeEmployee partTimeEmployee = (PartTimeEmployee) employee;
            System.out.println("Hours Worked: " + partTimeEmployee.getHoursWorked());
            System.out.println("Hourly Rate: $" + String.format("%.2f", partTimeEmployee.getHourlyRate()));
        }

        // Deductions Section
        System.out.println("\nDeductions:");
        if (employeeDeductions.isEmpty()) {
            System.out.println("  No deductions applied.");
        } else {
            for (Deductions deduction : employeeDeductions) {
                System.out.printf("  %-20s: $%.2f%n", deduction.getDeductionName(), deduction.getAmount());
            }
        }

        // Calculate total deductions
        double totalDeductions = Deductions.calculateTotalDeductions(employeeDeductions, employee.getEmployeeId());
        System.out.println("Total Deductions: $" + String.format("%.2f", totalDeductions));

        // Taxes
        double taxes = payslip.getTaxes();
        System.out.println("Taxes: $" + String.format("%.2f", taxes));

        // Calculate Net Pay
        double netPay = grossPay - taxes - totalDeductions;
        System.out.println("Net Pay: $" + String.format("%.2f", netPay));
        System.out.println("-----------------------------");
    }



    /**
     * Displays the Admin Menu and handles admin-specific functionality.
     *
     * @param employees List of employees.
     * @param payScales List of pay scales.
     * @param scanner   Scanner for user input.
     */
    ///tommy
    private static void adminMenu(List<Employee> employees, List<PayScale> payScales, Scanner scanner) {
        while (true) {
            try {
                System.out.println("\n=== Admin Menu ===");
                System.out.println("1. Add Employee");
                System.out.println("2. Logout");
                System.out.print("Choose an option: ");

                String input = scanner.nextLine().trim(); // Read input as a String

                // Validate input
                if (input.equals("1")) {
                    addEmployee(employees, payScales, scanner);
                } else if (input.equals("2")) {
                    System.out.println("Logging out...");
                    break; // Exit the menu loop
                } else {
                    System.out.println("Invalid option. Please enter 1 or 2.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred. Please try again.");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }


    /**
     * Displays the HR Menu and handles HR-specific functionality.
     *
     * @param employees List of employees.
     * @param payScales List of pay scales.
     * @param scanner   Scanner for user input.
     */
    ///tommy
    private static void addEmployee(List<Employee> employees, List<PayScale> payScales, Scanner scanner) {
        System.out.print("Enter Employee ID: ");
        String id = scanner.nextLine();

        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Title: ");
        String title = scanner.nextLine();

        System.out.print("Enter Point: ");
        int point = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter Employment Type (fulltime/parttime): ");
        String employmentType = scanner.nextLine().toLowerCase();

        Optional<PayScale> matchingPayScale = payScales.stream()
                .filter(p -> p.getTitle().equalsIgnoreCase(title) && p.getPoint() == point)
                .findFirst();

        if (matchingPayScale.isEmpty()) {
            System.out.println("Error: No matching pay scale found for the specified title and point.");
            return;
        }

        PayScale payScale = matchingPayScale.get();

        if ("fulltime".equalsIgnoreCase(employmentType)) {
            FullTimeEmployee newEmployee = new FullTimeEmployee(id, name, title, point, payScale.getAnnualRate());
            employees.add(newEmployee);
            System.out.println("Full-time employee added successfully!");
        } else if ("parttime".equalsIgnoreCase(employmentType)) {
            PartTimeEmployee newEmployee = new PartTimeEmployee(id, name, title, point, payScale.getHourlyRate(), 160);
            employees.add(newEmployee);
            System.out.println("Part-time employee added successfully!");
        } else {
            System.out.println("Invalid employment type.");
            return;
        }

        // Write to CSV after adding
        writeEmployeesToCSV(employees);
    }

    /**
     * Promotes an employee to a new title and point and updates the CSV file.
     *
     * @param employees List of employees.
     * @param payScales List of pay scales.
     * @param scanner   Scanner for user input.
     */
    ///sean
    private static void promoteEmployee(List<Employee> employees, List<PayScale> payScales, Scanner scanner) {
        System.out.print("Enter Employee ID to promote: ");
        String employeeId = scanner.nextLine();

        Optional<Employee> matchingEmployee = employees.stream()
                .filter(e -> e.getEmployeeId().equals(employeeId))
                .findFirst();

        if (matchingEmployee.isEmpty()) {
            System.out.println("No employee found with ID: " + employeeId);
            return;
        }

        Employee employee = matchingEmployee.get();

        System.out.println("\n=== Current Details ===");
        System.out.println("Name: " + employee.getName());
        System.out.println("Title: " + employee.getTitle());
        System.out.println("Point: " + employee.getPoint());

        System.out.print("\nEnter new Title: ");
        String newTitle = scanner.nextLine();

        System.out.print("Enter new Point: ");
        int newPoint = scanner.nextInt();
        scanner.nextLine();

        Optional<PayScale> matchingPayScale = payScales.stream()
                .filter(p -> p.getTitle().equalsIgnoreCase(newTitle) && p.getPoint() == newPoint)
                .findFirst();

        if (matchingPayScale.isEmpty()) {
            System.out.println("Error: No matching pay scale found for the specified title and point.");
            return;
        }

        PayScale newPayScale = matchingPayScale.get();

        // Update employee details
        employee.setTitle(newTitle);
        employee.setPoint(newPoint);

        if (employee instanceof FullTimeEmployee) {
            ((FullTimeEmployee) employee).setAnnualRate(newPayScale.getAnnualRate());
        } else if (employee instanceof PartTimeEmployee) {
            ((PartTimeEmployee) employee).setHourlyRate(newPayScale.getHourlyRate());
        }

        System.out.println("Employee promoted successfully!");

        // Write updated employees list to CSV
        writeEmployeesToCSV(employees);
    }
    ///sean
    private static void fireEmployee(List<Employee> employees, Scanner scanner) {
        System.out.print("Enter Employee ID to remove: ");
        String employeeId = scanner.nextLine().trim();

        // Find the employee by ID
        Optional<Employee> employeeToRemove = employees.stream()
                .filter(e -> e.getEmployeeId().equals(employeeId))
                .findFirst();

        if (employeeToRemove.isPresent()) {
            employees.remove(employeeToRemove.get());
            System.out.println("Employee with ID " + employeeId + " has been removed.");

            // Update the CSV file
            writeEmployeesToCSV(employees);
        } else {
            System.out.println("No employee found with ID: " + employeeId);
        }
    }
    ///dylan
    private static void promoteEmployeesInOctober(List<Employee> employees, List<PayScale> payScales) {
        // Get the current date
        Calendar calendar = Calendar.getInstance();
        int currentMonth = calendar.get(Calendar.MONTH);

        // Only promote if it's October (Month 9 in Calendar, since January = 0)
        if (currentMonth == Calendar.OCTOBER) {
            for (Employee employee : employees) {
                if (employee instanceof FullTimeEmployee) {
                    FullTimeEmployee fullTimeEmployee = (FullTimeEmployee) employee;

                    // Find the corresponding pay scale for the employee's title
                    Optional<PayScale> payScale = payScales.stream()
                            .filter(scale -> scale.getTitle().equalsIgnoreCase(fullTimeEmployee.getTitle())
                                    && scale.getPoint() == fullTimeEmployee.getPoint() + 1) // Next point in the scale
                            .findFirst();

                    if (payScale.isPresent()) {
                        // Promote the employee to the next point
                        fullTimeEmployee.setPoint(fullTimeEmployee.getPoint() + 1);
                        fullTimeEmployee.setAnnualRate(payScale.get().getAnnualRate());
                        System.out.printf("Promoted %s to Point %d, Annual Rate: %.2f%n",
                                fullTimeEmployee.getName(), fullTimeEmployee.getPoint(), fullTimeEmployee.getAnnualRate());
                    } else {
                        // Employee is already at the top of the scale
                        System.out.printf("%s is already at the top of their scale.%n", fullTimeEmployee.getName());
                    }
                }
            }

            // Write updated employee list back to the CSV file
            writeEmployeesToCSV(employees);
            System.out.println("Employee promotions applied and saved to CSV.");
        } else {
            System.out.println("It's not October. No promotions applied.");
        }
    }



///tommy
    private static void hrMenu(List<Employee> employees, List<PayScale> payScales, Scanner scanner) {
        while (true) {
            System.out.println("\n=== HR Menu ===");
            System.out.println("1. Promote Employee");
            System.out.println("2. Fire Employee");
            System.out.println("3. Logout");
            System.out.print("Choose an option: ");

            String input = scanner.nextLine().trim();

            if (input.equals("1")) {
                promoteEmployee(employees, payScales, scanner);
            } else if (input.equals("2")) {
                fireEmployee(employees, scanner);
            } else if (input.equals("3")) {
                System.out.println("Logging out...");
                break;
            } else {
                System.out.println("Invalid option. Please enter 1, 2, or 3.");
            }
        }
    }



    /**
     * Writes the updated list of employees to the Employees.csv file in the specified format.
     *
     * Format:
     * EmployeeId,Name,Role,Point,AnnualRate,HourlyRate,EmploymentType
     *
     * @param employees The list of employees to write to the file.
     */
    ///sean
    private static void writeEmployeesToCSV(List<Employee> employees) {
        File file = new File("Employees.csv");
        try (FileWriter writer = new FileWriter(file)) {
            // Write header
            writer.write("EmployeeId,Name,Role,Point,AnnualRate,HourlyRate,EmploymentType\n");

            // Write employee data
            for (Employee employee : employees) {
                if (employee instanceof FullTimeEmployee) {
                    FullTimeEmployee fullTimeEmployee = (FullTimeEmployee) employee;
                    double hourlyRate = fullTimeEmployee.getAnnualRate() / (52 * 40); // Assuming 52 weeks × 40 hours
                    writer.write(String.format("%s,%s,%s,%d,%.0f,%.2f,FullTime\n",
                            fullTimeEmployee.getEmployeeId(), fullTimeEmployee.getName(), fullTimeEmployee.getTitle(),
                            fullTimeEmployee.getPoint(), fullTimeEmployee.getAnnualRate(), hourlyRate));
                } else if (employee instanceof PartTimeEmployee) {
                    PartTimeEmployee partTimeEmployee = (PartTimeEmployee) employee;
                    writer.write(String.format("%s,%s,%s,%d,%.0f,%.2f,PartTime\n",
                            partTimeEmployee.getEmployeeId(), partTimeEmployee.getName(), partTimeEmployee.getTitle(),
                            partTimeEmployee.getPoint(), 0.0, partTimeEmployee.getHourlyRate())); // AnnualRate is 0 for part-time
                }
            }
            System.out.println("Employees.csv updated successfully.");
        } catch (IOException e) {
            System.err.println("Error writing to Employees.csv: " + e.getMessage());
        }
    }


}