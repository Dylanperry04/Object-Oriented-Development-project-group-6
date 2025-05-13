/**
 * Represents a payroll claim for an employee.
 * A payroll claim includes details such as the employee's ID, claim date, hours worked, and hourly rate.
 */
public class PayrollClaim {
    private String employeeId;
    private String claimDate;
    private double hoursWorked;
    private double hourlyRate;

    /**
     * Constructs a new PayrollClaim with the specified details.
     *
     * @param employeeId  The unique identifier for the employee.
     * @param claimDate   The date of the claim.
     * @param hoursWorked The number of hours worked for the claim.
     * @param hourlyRate  The hourly rate for the claim.
     */
    public PayrollClaim(String employeeId, String claimDate, double hoursWorked, double hourlyRate) {
        this.employeeId = employeeId;
        this.claimDate = claimDate;
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }

    /**
     * Gets the employee ID associated with this payroll claim.
     *
     * @return The employee ID.
     */
    public String getEmployeeId() {
        return employeeId;
    }

    /**
     * Gets the date of the payroll claim.
     *
     * @return The claim date.
     */
    public String getClaimDate() {
        return claimDate;
    }

    /**
     * Gets the number of hours worked for this payroll claim.
     *
     * @return The hours worked.
     */
    public double getHoursWorked() {
        return hoursWorked;
    }

    /**
     * Gets the hourly rate for this payroll claim.
     *
     * @return The hourly rate.
     */
    public double getHourlyRate() {
        return hourlyRate;
    }

    /**
     * Sets the employee ID associated with this payroll claim.
     *
     * @param employeeId The employee ID to set.
     */
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * Sets the date of the payroll claim.
     *
     * @param claimDate The claim date to set.
     */
    public void setClaimDate(String claimDate) {
        this.claimDate = claimDate;
    }

    /**
     * Sets the number of hours worked for this payroll claim.
     *
     * @param hoursWorked The hours worked to set.
     */
    public void setHoursWorked(double hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    /**
     * Sets the hourly rate for this payroll claim.
     *
     * @param hourlyRate The hourly rate to set.
     */
    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }
}