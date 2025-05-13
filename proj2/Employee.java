public abstract class Employee {
    protected String employeeId;
    protected String name;
    protected String title;
    protected int point;
    protected double grossPay;

    public Employee(String employeeId, String name, String title, int point) {
        this.employeeId = employeeId;
        this.name = name;
        this.title = title;
        this.point = point;
    }

    public abstract double calculateGrossPay();

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

    public String toCSV() {
        return String.format("%s,%s,%s,%d,%.2f", employeeId, name, title, point, grossPay);
    }
}
