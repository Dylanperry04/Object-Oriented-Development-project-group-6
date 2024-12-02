import java.io.*;
import java.util.*;

/**
 * Utility class for reading and parsing various CSV files related to employees, deductions, taxes, pay scales, and payslips.
 */
public class CsvReader {

    /**
     * Reads a list of employees from a CSV file.
     *
     * @param fileName The name of the file containing employee data.
     * @return A list of employees parsed from the file.
     * @throws IOException If an I/O error occurs during file reading.
     */
    public static List<Employee> readEmployees(String fileName) throws IOException {
        List<Employee> employees = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        reader.readLine(); // Skip the header row

        while ((line = reader.readLine()) != null) {
            String[] columns = line.split(",");

            if (columns.length < 7) {
                System.err.println("Skipping malformed line: " + line);
                continue;
            }

            try {
                String employeeId = columns[0].trim();
                String name = columns[1].trim();
                String title = columns[2].trim();
                int point = Integer.parseInt(columns[3].trim());
                double annualRate = Double.parseDouble(columns[4].trim());
                double hourlyRate = Double.parseDouble(columns[5].trim());
                String employmentType = columns[6].trim();

                if ("FullTime".equalsIgnoreCase(employmentType)) {
                    employees.add(new FullTimeEmployee(employeeId, name, title, point, annualRate));
                } else if ("PartTime".equalsIgnoreCase(employmentType)) {
                    employees.add(new PartTimeEmployee(employeeId, name, title, point, hourlyRate, 160));
                } else {
                    System.err.println("Unknown employment type: " + employmentType + " in line: " + line);
                }
            } catch (NumberFormatException e) {
                System.err.println("Skipping line with invalid number format: " + line);
            }
        }
        reader.close();
        return employees;
    }

    /**
     * Reads a list of deductions from a CSV file.
     *
     * @param fileName The name of the file containing deductions data.
     * @return A list of deductions parsed from the file.
     * @throws IOException If an I/O error occurs during file reading.
     */
    /**
     * Reads a list of deductions from a CSV file.
     *
     * @param fileName The name of the file containing deductions data.
     * @return A list of Deductions objects parsed from the file.
     * @throws IOException If an I/O error occurs during file reading.
     */
    public static List<Deductions> readDeductions(String fileName) throws IOException {
        List<Deductions> deductions = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;

        // Skip the header row
        reader.readLine();

        while ((line = reader.readLine()) != null) {
            String[] columns = line.split(",");

            if (columns.length != 4) {
                System.err.println("Skipping malformed line: " + line);
                continue;
            }

            try {
                String employeeId = columns[0].trim();
                String deductionName = columns[1].trim();
                double amount = Double.parseDouble(columns[2].trim());
                String effectiveDate = columns[3].trim();

                deductions.add(new Deductions(employeeId, deductionName, amount, effectiveDate));
            } catch (NumberFormatException e) {
                System.err.println("Skipping line with invalid number format: " + line);
            }
        }

        reader.close();
        return deductions;
    }


    /**
     * Reads a list of tax records from a CSV file.
     *
     * @param fileName The name of the file containing tax data.
     * @return A list of tax records parsed from the file.
     * @throws IOException If an I/O error occurs during file reading.
     */
    public static List<Tax> readTaxes(String fileName) throws IOException {
        List<Tax> taxes = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        reader.readLine(); // Skip the header row

        while ((line = reader.readLine()) != null) {
            String[] columns = line.split(",");
            String employeeId = columns[0].trim();
            double taxAmount = Double.parseDouble(columns[4].trim());

            taxes.add(new Tax(employeeId, taxAmount));
        }
        reader.close();
        return taxes;
    }

    /**
     * Reads a list of pay scales from a CSV file.
     *
     * @param fileName The name of the file containing pay scale data.
     * @return A list of pay scales parsed from the file.
     * @throws IOException If an I/O error occurs during file reading.
     */
    public static List<PayScale> readPayScales(String fileName) throws IOException {
        List<PayScale> payScales = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        reader.readLine(); // Skip the header row

        while ((line = reader.readLine()) != null) {
            if (line.trim().isEmpty()) continue;

            String[] columns = line.split(",");

            if (columns.length < 4) {
                System.err.println("Skipping malformed line: " + line);
                continue;
            }

            try {
                String title = columns[0].trim();
                int point = Integer.parseInt(columns[1].trim());
                double annualRate = Double.parseDouble(columns[2].trim());
                double hourlyRate = Double.parseDouble(columns[3].trim());

                payScales.add(new PayScale(title, point, annualRate, hourlyRate));
            } catch (NumberFormatException e) {
                System.err.println("Skipping line with invalid number format: " + line);
            }
        }
        reader.close();
        return payScales;
    }

    /**
     * Reads a list of payslips from a CSV file.
     *
     * @param fileName The name of the file containing payslip data.
     * @return A list of payslips parsed from the file.
     * @throws IOException If an I/O error occurs during file reading.
     */
    public static List<Payslip> readPayslips(String fileName) throws IOException {
        List<Payslip> payslips = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        reader.readLine(); // Skip the header row

        while ((line = reader.readLine()) != null) {
            String[] columns = line.split(",");

            if (columns.length < 6) {
                System.err.println("Skipping malformed line: " + line);
                continue;
            }

            try {
                String employeeId = columns[0].trim();
                String payPeriod = columns[1].trim();
                double grossPay = Double.parseDouble(columns[2].trim());
                double netPay = Double.parseDouble(columns[3].trim());
                double taxes = Double.parseDouble(columns[4].trim());
                double hoursWorked = columns[5].trim().isEmpty() ? 0 : Double.parseDouble(columns[5].trim());

                payslips.add(new Payslip(employeeId, payPeriod, grossPay, netPay, taxes, hoursWorked));
            } catch (NumberFormatException e) {
                System.err.println("Skipping line with invalid number format: " + line);
            }
        }
        reader.close();
        return payslips;
    }



}
