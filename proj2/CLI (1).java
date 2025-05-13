import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class CLI {
    public static void main(String[] args) {
        try {

            List<Employee> employees = CsvReader.readEmployees("Employees.csv");
            List<Payslip> payslips = CsvReader.readPayslips("PaySlip.csv");
            List<PayScale> payScales = CsvReader.readPayScales("PayScale.csv");

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
                    employeeMenu(employees, payslips, scanner);
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

    private static void employeeMenu(List<Employee> employees, List<Payslip> payslips, Scanner scanner) {
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
                    System.out.println(payslip);
                }
            }
        } else {
            System.out.println("Invalid choice.");
        }
    }

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
        System.out.println(employee);

        System.out.print("\nEnter new Title: ");
        String newTitle = scanner.nextLine().toUpperCase();

        System.out.print("Enter new Point: ");
        int newPoint = scanner.nextInt();
        scanner.nextLine();

        Optional<PayScale> matchingPayScale = payScales.stream()
                .filter(p -> p.getTitle().equalsIgnoreCase(newTitle) && p.getPoint() == newPoint)
                .findFirst();

        if (matchingPayScale.isEmpty()) {
            System.out.println("Error: No matching pay scale found for the new title and point.");
            return;
        }

        PayScale newPayScale = matchingPayScale.get();

        System.out.println("\n=== New Details ===");
        System.out.printf("Title: %s, Point: %d\n", newTitle, newPoint);
        if (employee instanceof FullTimeEmployee) {
            System.out.printf("Annual Rate: %.2f\n", newPayScale.getAnnualRate());
        } else if (employee instanceof PartTimeEmployee) {
            System.out.printf("Hourly Rate: %.2f\n", newPayScale.getHourlyRate());
        }

        System.out.print("Confirm promotion? (Y/N): ");
        String confirm = scanner.nextLine().trim().toUpperCase();
        if (!confirm.equals("Y")) {
            System.out.println("Promotion cancelled.");
            return;
        }

        employee.setTitle(newTitle);
        employee.setPoint(newPoint);
        if (employee instanceof FullTimeEmployee) {
            ((FullTimeEmployee) employee).setAnnualRate(newPayScale.getAnnualRate());
        } else if (employee instanceof PartTimeEmployee) {
            ((PartTimeEmployee) employee).setHourlyRate(newPayScale.getHourlyRate());
        }

        System.out.println("Promotion applied successfully!");
        updateEmployeeInCSV(employees);
    }


    private static void addEmployee(List<Employee> employees, List<PayScale> payScales, Scanner scanner) {
        System.out.print("Enter Employee ID: ");
        String id = scanner.nextLine();

        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Title: ");
        String title = scanner.nextLine().toUpperCase();

        System.out.print("Enter Point: ");
        int point = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter Employment Type (fulltime/parttime): ");
        String employmentType = scanner.nextLine().toLowerCase();

        Optional<PayScale> matchingPayScale = payScales.stream()
                .filter(p -> p.getTitle().equalsIgnoreCase(title) && p.getPoint() == point)
                .findFirst();

        if (matchingPayScale.isPresent()) {
            PayScale payScale = matchingPayScale.get();

            if (employmentType.equals("fulltime")) {
                FullTimeEmployee newEmployee = new FullTimeEmployee(id, name, title, point, payScale.getAnnualRate());
                employees.add(newEmployee);
                writeEmployeeToCSV(id, name, title, point, payScale.getAnnualRate(), payScale.getHourlyRate(), "FullTime");
                System.out.println("Full-time employee added successfully!");
            } else if (employmentType.equals("parttime")) {
                PartTimeEmployee newEmployee = new PartTimeEmployee(id, name, title, point, payScale.getHourlyRate(), 0);
                employees.add(newEmployee);
                writeEmployeeToCSV(id, name, title, point, payScale.getAnnualRate(), payScale.getHourlyRate(), "PartTime");
                System.out.println("Part-time employee added successfully!");
            } else {
                System.out.println("Invalid employment type. Employee not added.");
            }
        } else {
            System.out.println("Error: No matching pay scale found for the specified title and point.");
        }
    }

    private static void writeEmployeeToCSV(String id, String name, String title, int point, double annualRate, double hourlyRate, String employmentType) {
        try (FileWriter writer = new FileWriter("Employees.csv", true)) {
            String newRow = String.format("%s,%s,%s,%d,%.2f,%.2f,%s",
                    id, name, title, point, annualRate, hourlyRate, employmentType);
            writer.write(newRow + "\n");
        } catch (IOException e) {
            System.out.println("Error writing to Employees.csv: " + e.getMessage());
        }
    }

    private static void updateEmployeeInCSV(List<Employee> employees) {
        try (FileWriter writer = new FileWriter("Employees.csv")) {
            for (Employee employee : employees) {
                writer.write(employee.toCSV() + "\n");
            }
            System.out.println("Employee data updated in CSV.");
        } catch (IOException e) {
            System.out.println("Error updating Employees.csv: " + e.getMessage());
        }
    }

}
