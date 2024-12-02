/**
 * Abstract class representing a generic employee. Provides common properties and behaviors
 * for all employee types, such as employee ID, name, title, point, and gross pay calculation.
 */
public abstract class Employee {
    protected String employeeId;
    protected String name;
    protected String title;
    protected int point;
    protected double grossPay;

    /**
     * Constructs a new Employee with the specified details.
     *
     * @param employeeId The unique identifier for the employee.
     * @param name       The name of the employee.
     * @param title      The job title of the employee.
     * @param point      The point on the pay scale associated with the employee.
     */
    public Employee(String employeeId, String name, String title, int point) {
        this.employeeId = employeeId;
        this.name = name;
        this.title = title;
        this.point = point;
    }

    /**
     * Abstract method to calculate the gross pay for the employee.
     * This method must be implemented by subclasses.
     *
     * @return The calculated gross pay for the employee.
     */
    public abstract double calculateGrossPay();

    /**
     * Gets the employee's unique identifier.
     *
     * @return The employee ID.
     */
    public String getEmployeeId() {
        return employeeId;
    }

    /**
     * Gets the employee's name.
     *
     * @return The name of the employee.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the employee's job title.
     *
     * @return The job title of the employee.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the employee's point on the pay scale.
     *
     * @return The pay scale point associated with the employee.
     */
    public int getPoint() {
        return point;
    }

    /**
     * Sets the employee's job title.
     *
     * @param title The new job title for the employee.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the employee's point on the pay scale.
     *
     * @param point The new pay scale point for the employee.
     */
    public void setPoint(int point) {
        this.point = point;
    }

    /**
     * Converts the employee's details into a CSV-formatted string.
     *
     * @return A string containing the employee's details in CSV format.
     */
    public String toCSV() {
        return String.format("%s,%s,%s,%d,%.2f", employeeId, name, title, point, grossPay);
    }
}
