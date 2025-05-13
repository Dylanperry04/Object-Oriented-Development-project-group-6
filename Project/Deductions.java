import java.util.*;

/**
 * Represents a deduction for an employee, including the deduction name, amount, and effective date.
 */
public class Deductions {
    private String employeeId;
    private String deductionName;
    private double amount;
    private String effectiveDate;

    /**
     * Constructs a new Deductions object with the specified details.
     *
     * @param employeeId    The ID of the employee associated with this deduction.
     * @param deductionName The name of the deduction.
     * @param amount        The monetary amount of the deduction.
     * @param effectiveDate The date when the deduction becomes effective.
     */
    public Deductions(String employeeId, String deductionName, double amount, String effectiveDate) {
        this.employeeId = employeeId;
        this.deductionName = deductionName;
        this.amount = amount;
        this.effectiveDate = effectiveDate;
    }

    /**
     * Gets the employee ID associated with this deduction.
     *
     * @return The employee ID.
     */
    public String getEmployeeId() {
        return employeeId;
    }

    /**
     * Gets the name of the deduction.
     *
     * @return The name of the deduction.
     */
    public String getDeductionName() {
        return deductionName;
    }

    /**
     * Gets the monetary amount of the deduction.
     *
     * @return The amount of the deduction.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Gets the effective date of the deduction.
     *
     * @return The effective date as a String.
     */
    public String getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * Calculates the total deductions for a specific employee from a list of deductions.
     *
     * @param deductionsList A list of all deductions.
     * @param employeeId     The ID of the employee for whom the total deductions are calculated.
     * @return The total amount of deductions for the specified employee.
     */
    public static double calculateTotalDeductions(List<Deductions> deductionsList, String employeeId) {
        double total = 0;
        for (Deductions deduction : deductionsList) {
            if (deduction.getEmployeeId().equals(employeeId)) {
                total += deduction.getAmount();
            }
        }
        return total;
    }
}