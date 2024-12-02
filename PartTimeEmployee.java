/**
 * Represents a part-time employee. This class extends the {@link Employee} class
 * and includes additional properties and behaviors specific to part-time employees.
 */
public class PartTimeEmployee extends Employee {
    private double hourlyRate;
    private int hoursWorked;

    /**
     * Constructs a new PartTimeEmployee with the specified details.
     *
     * @param employeeId  The unique identifier for the employee.
     * @param name        The name of the employee.
     * @param title       The job title of the employee.
     * @param point       The pay scale point associated with the employee.
     * @param hourlyRate  The hourly rate of pay for the employee.
     * @param hoursWorked The number of hours worked by the employee.
     */
    public PartTimeEmployee(String employeeId, String name, String title, int point, double hourlyRate, int hoursWorked) {
        super(employeeId, name, title, point);
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
    }

    /**
     * Gets the hourly rate of the part-time employee.
     *
     * @return The hourly rate.
     */
    public double getHourlyRate() {
        return hourlyRate;
    }

    /**
     * Sets the hourly rate of pay for the part-time employee.
     *
     * @param hourlyRate The new hourly rate of pay.
     */
    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    /**
     * Gets the hours worked by the part-time employee.
     *
     * @return The number of hours worked.
     */
    public int getHoursWorked() {
        return hoursWorked;
    }

    /**
     * Sets the hours worked by the part-time employee.
     *
     * @param hoursWorked The new number of hours worked.
     */
    public void setHoursWorked(int hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    /**
     * Calculates the gross pay for the part-time employee.
     * Gross pay is calculated as the product of the hourly rate and the hours worked.
     *
     * @return The gross pay for the employee.
     */
    @Override
    public double calculateGrossPay() {
        grossPay = hourlyRate * hoursWorked;
        return grossPay;
    }

    /**
     * Converts the part-time employee's details into a CSV-formatted string.
     *
     * @return A string containing the employee's details in CSV format.
     */
    @Override
    public String toCSV() {
        return String.format("%s,%s,%s,%d,%.2f,%d,PartTime",
                getEmployeeId(), getName(), getTitle(), getPoint(), hourlyRate, hoursWorked);
    }

    /**
     * Returns a string representation of the part-time employee.
     *
     * @return A string containing the employee's details in a human-readable format.
     */
    @Override
    public String toString() {
        return String.format("EmployeeID: %s, Name: %s, Title: %s, Point: %d, HourlyRate: %.2f, HoursWorked: %d",
                getEmployeeId(), getName(), getTitle(), getPoint(), hourlyRate, hoursWorked);
    }
}
