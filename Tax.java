/**
 * Represents a tax record for an employee, including the employee's ID and the tax amount.
 */
public class Tax {
    private String employeeId;
    private double taxAmount;

    /**
     * Constructs a new Tax record with the specified details.
     *
     * @param employeeId The unique identifier for the employee.
     * @param taxAmount  The amount of tax applicable to the employee.
     */
    public Tax(String employeeId, double taxAmount) {
        this.employeeId = employeeId;
        this.taxAmount = taxAmount;
    }

    /**
     * Gets the employee ID associated with this tax record.
     *
     * @return The employee ID.
     */
    public String getEmployeeId() {
        return employeeId;
    }

    /**
     * Gets the tax amount associated with this tax record.
     *
     * @return The tax amount.
     */
    public double getTaxAmount() {
        return taxAmount;
    }
}
