import java.io.*;
import java.util.*;

public class CsvReader {

    public static List<Employee> readEmployees(String fileName) throws IOException {
        List<Employee> employees = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        reader.readLine();

        while ((line = reader.readLine()) != null) {
            String[] columns = line.split(",");

            String employeeId = columns[0].trim();
            String name = columns[1].trim();
            String title = columns[2].trim();
            int point = Integer.parseInt(columns[3].trim());
            double annualRate = Double.parseDouble(columns[4].trim());
            double hourlyRate = Double.parseDouble(columns[5].trim());
            String employmentType = columns[6].trim();

            if ("fulltime".equalsIgnoreCase(employmentType)) {
                employees.add(new FullTimeEmployee(employeeId, name, title, point, annualRate));
            } else {
                employees.add(new PartTimeEmployee(employeeId, name, title, point, hourlyRate, 160));
            }
        }
        reader.close();
        return employees;
    }

    public static List<Deductions> readDeductions(String fileName) throws IOException {
        List<Deductions> deductions = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        reader.readLine();

        while ((line = reader.readLine()) != null) {
            String[] columns = line.split(",");
            String employeeId = columns[0].trim();
            String deductionName = columns[1].trim();
            double amount = Double.parseDouble(columns[2].trim());
            String effectiveDate = columns[3].trim();

            deductions.add(new Deductions(employeeId, deductionName, amount, effectiveDate));
        }
        reader.close();
        return deductions;
    }

    public static List<Tax> readTaxes(String fileName) throws IOException {
        List<Tax> taxes = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        reader.readLine();

        while ((line = reader.readLine()) != null) {
            String[] columns = line.split(",");
            String employeeId = columns[0].trim();
            double taxAmount = Double.parseDouble(columns[4].trim());

            taxes.add(new Tax(employeeId, taxAmount));
        }
        reader.close();
        return taxes;
    }

    public static List<PayScale> readPayScales(String fileName) throws IOException {
        List<PayScale> payScales = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        reader.readLine();

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

    public static List<Payslip> readPayslips(String fileName) throws IOException {
        List<Payslip> payslips = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        reader.readLine();

        while ((line = reader.readLine()) != null) {
            String[] columns = line.split(",");


            if (columns.length < 6) {
                System.err.println("Skipping malformed line: " + line);
                continue;
            }

            try {
                String employeeId = columns[0].trim();
                String payDate = columns[1].trim();


                double grossPay = Double.parseDouble(columns[2].replace(",", "").replace("\"", "").trim());
                double netPay = Double.parseDouble(columns[3].replace(",", "").replace("\"", "").trim());
                double taxes = Double.parseDouble(columns[4].replace(",", "").replace("\"", "").trim());
                double hoursWorked = columns[5].trim().isEmpty() ? 0 : Double.parseDouble(columns[5].replace(",", "").replace("\"", "").trim());

                payslips.add(new Payslip(employeeId, payDate, grossPay, netPay, taxes, hoursWorked));
            } catch (NumberFormatException e) {
                System.err.println("Skipping line with invalid number format: " + line);
            }
        }
        reader.close();
        return payslips;
    }



}
