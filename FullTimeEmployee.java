public class FullTimeEmployee extends Employee {
    private double annualRate;

    public FullTimeEmployee(String employeeId, String name, String title, int point, double annualRate) {
        super(employeeId, name, title, point);
        this.annualRate = annualRate;
    }

    public void setAnnualRate(double annualRate) {
        this.annualRate = annualRate;
    }

    @Override
    public double calculateGrossPay() {
        grossPay = annualRate / 12; // Example calculation: Monthly gross pay
        return grossPay;
    }

    @Override
    public String toCSV() {
        return String.format("%s,%s,%s,%d,%.2f,FullTime",
                getEmployeeId(), getName(), getTitle(), getPoint(), annualRate);
    }

    @Override
    public String toString() {
        return String.format("EmployeeID: %s, Name: %s, Title: %s, Point: %d, AnnualRate: %.2f",
                getEmployeeId(), getName(), getTitle(), getPoint(), annualRate);
    }
}
