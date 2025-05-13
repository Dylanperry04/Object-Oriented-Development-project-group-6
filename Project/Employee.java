public abstract class Employee {
    protected String employeeId;
    protected String name;
    protected String title;
    protected int point;

    public Employee(String employeeId, String name, String title, int point) {
        this.employeeId = employeeId;
        this.name = name;
        this.title = title;
        this.point = point;
    }

    // Abstract method for subclasses to implement gross pay calculation
    public abstract double calculateGrossPay();

    // Dynamic method to get gross pay
    public double getGrossPay() {
        return calculateGrossPay();
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public int getPoint() {
        return point;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
