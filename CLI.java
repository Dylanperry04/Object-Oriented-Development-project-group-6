import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Command Line Interface (CLI) for managing the UL Payroll System.
 * Provides functionality for Employees, Admins, and HR to interact with payroll data.
 */
public class CLI {
    /**
     * Main method to start the CLI application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        try {
            List<Employee> employees = CsvReader.readEmployees("Employees.csv");
            List<Payslip> payslips = CsvReader.readPayslips("PaySlip.csv");
            List<PayScale> payScales = CsvReader.readPayScales("PayScale.csv");
            List<Deductions> deductions = CsvReader.readDeductions("Deductions.csv");

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

    private static void displayPayslip(Payslip payslip, Employee employee, List<Deductions> employeeDeductions) {
        System.out.println("\n=== Pay Slip ===");
        System.out.println("Employee ID: " + employee.getEmployeeId());
        System.out.println("Name: " + employee.getName());
        System.out.println("Title: " + employee.getTitle());
        System.out.println("Pay Date: " + payslip.getPayPeriod());
        System.out.println("Gross Pay: $" + String.format("%.2f", payslip.getGrossPay()));

        if (employee instanceof PartTimeEmployee) {
            System.out.println("Hours Worked: " + payslip.getHoursWorked());
            System.out.println("Hourly Rate: $" + String.format("%.2f", ((PartTimeEmployee) employee).getHourlyRate()));
        }

        System.out.println("\nDeductions:");
        if (employeeDeductions.isEmpty()) {
            System.out.println("  No deductions applied.");
        } else {
            for (Deductions deduction : employeeDeductions) {
                System.out.printf("  %-20s: $%.2f (Effective: %s)%n",
                        deduction.getDeductionName(), deduction.getAmount(), deduction.getEffectiveDate());
            }
        }

        double totalDeductions = Deductions.calculateTotalDeductions(employeeDeductions, employee.getEmployeeId());
        System.out.println("Total Deductions: $" + String.format("%.2f", totalDeductions));

        System.out.println("Taxes: $" + String.format("%.2f", payslip.getTaxes()));
        System.out.println("Net Pay: $" + String.format("%.2f", payslip.getNetPay()));
        System.out.println("-----------------------------");
    }

    /**
     * Displays the Admin Menu and handles admin-specific functionality.
     *
     * @param employees List of employees.
     * @param payScales List of pay scales.
     * @param scanner   Scanner for user input.
     */
    private static void adminMenu(List<Employee> employees, List<PayScale> payScales, Scanner scanner) {
        System.out.println("\n=== Admin Menu ===");
        System.out.println("1. Add Employee");
        System.out.println("2. Logout");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            addEmployee(employees, payScales, scanner);
        } else if (choice == 2) {
            System.out.println("Logging out...");
        } else {
            System.out.println("Invalid option.");
        }
    }

    /**
     * Displays the HR Menu and handles HR-specific functionality.
     *
     * @param employees List of employees.
     * @param payScales List of pay scales.
     * @param scanner   Scanner for user input.
     */
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



    private static void hrMenu(List<Employee> employees, List<PayScale> payScales, Scanner scanner) {
        System.out.println("\n=== HR Menu ===");
        System.out.println("1. Promote Employee");
        System.out.println("2. Logout");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            promoteEmployee(employees, payScales, scanner);
        } else if (choice == 2) {
            System.out.println("Logging out...");
        } else {
            System.out.println("Invalid option.");
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
