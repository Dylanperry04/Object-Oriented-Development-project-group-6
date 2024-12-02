/**
 * Represents a full-time employee. This class extends the {@link Employee} class
 * and includes additional properties and behaviors specific to full-time employees.
 */
public class FullTimeEmployee extends Employee {
    private double annualRate;

    /**
     * Constructs a new FullTimeEmployee with the specified details.
     *
     * @param employeeId The unique identifier for the employee.
     * @param name       The name of the employee.
     * @param title      The job title of the employee.
     * @param point      The pay scale point associated with the employee.
     * @param annualRate The annual salary rate for the employee.
     */
    public FullTimeEmployee(String employeeId, String name, String title, int point, double annualRate) {
        super(employeeId, name, title, point);
        this.annualRate = annualRate;
    }

    /**
     * Gets the annual salary rate for the employee.
     *
     * @return The annual salary rate.
     */
    public double getAnnualRate() {
        return annualRate;
    }

    /**
     * Sets the annual salary rate for the employee.
     *
     * @param annualRate The new annual salary rate for the employee.
     */
    public void setAnnualRate(double annualRate) {
        this.annualRate = annualRate;
    }

    /**
     * Calculates the gross pay for the full-time employee.
     * Gross pay is calculated as the annual rate divided by 12 to determine the monthly salary.
     *
     * @return The gross pay (monthly salary) for the employee.
     */
    @Override
    public double calculateGrossPay() {
        grossPay = annualRate / 12;
        return grossPay;
    }

    /**
     * Converts the full-time employee's details into a CSV-formatted string.
     *
     * @return A string containing the employee's details in CSV format.
     */
    @Override
    public String toCSV() {
        return String.format("%s,%s,%s,%d,%.2f,FullTime",
                getEmployeeId(), getName(), getTitle(), getPoint(), annualRate);
    }

    /**
     * Returns a string representation of the full-time employee.
     *
     * @return A string containing the employee's details in a human-readable format.
     */
    @Override
    public String toString() {
        return String.format("EmployeeID: %s, Name: %s, Title: %s, Point: %d, AnnualRate: %.2f",
                getEmployeeId(), getName(), getTitle(), getPoint(), annualRate);
    }
}
