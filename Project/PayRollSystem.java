import java.util.ArrayList;
import java.util.List;

/**
 * Represents the payroll system for managing employee payments, including deductions, taxes, and net pay.
 * This class provides functionality for calculating net pay and generating payslips.
 */
public class PayRollSystem {
    private Employee employee;
    private List<Deductions> deductionsList;
    private double taxes;
    private double netPay;
    private String payDate;

    /**
     * Constructs a PayRollSystem instance for a specific employee.
     *
     * @param employee       The employee for whom the payroll is being calculated.
     * @param deductionsList The list of deductions applicable to the employee.
     * @param taxes          The amount of taxes applicable to the employee.
     * @param payDate        The date of the payment.
     */
    public PayRollSystem(Employee employee, List<Deductions> deductionsList, double taxes, String payDate) {
        this.employee = employee;
        this.deductionsList = deductionsList;
        this.taxes = taxes;
        this.payDate = payDate;
        this.netPay = calculateNetPay();
    }

    /**
     * Calculates the net pay for the employee by subtracting deductions and taxes from gross pay.
     *
     * @return The calculated net pay.
     */
    private double calculateNetPay() {
        double totalDeductions = Deductions.calculateTotalDeductions(deductionsList, employee.getEmployeeId());
        double grossPay = employee.calculateGrossPay();
        return grossPay - totalDeductions - taxes;
    }

    /**
     * Gets the total amount of taxes applied to the employee.
     *
     * @return The total taxes.
     */
    public double getTaxes() {
        return taxes;
    }

    /**
     * Gets the net pay for the employee after deductions and taxes.
     *
     * @return The net pay.
     */
    public double getNetPay() {
        return netPay;
    }

    /**
     * Gets the date of the payment.
     *
     * @return The pay date.
     */
    public String getPayDate() {
        return payDate;
    }

    /**
     * Gets the employee associated with this payroll.
     *
     * @return The employee.
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * Generates and displays a payslip for the employee.
     */
    public void generatePayslip() {
        System.out.println("\n=== Pay Slip ===");
        System.out.println("Employee ID: " + employee.getEmployeeId());
        System.out.println("Name: " + employee.getName());
        System.out.println("Title: " + employee.getTitle());
        System.out.println("Gross Pay: " + employee.calculateGrossPay());
        System.out.println("Total Deductions: " + Deductions.calculateTotalDeductions(deductionsList, employee.getEmployeeId()));
        System.out.println("Taxes: " + taxes);
        System.out.println("Net Pay: " + netPay);
        System.out.println("Pay Date: " + payDate);
        System.out.println("-----------------------------");
    }


}