public class FullTimeEmployee extends Employee {
    private double annualRate;

    public FullTimeEmployee(String employeeId, String name, String title, int point, double annualRate) {
        super(employeeId, name, title, point);
        this.annualRate = annualRate;
    }

    public double getAnnualRate() {
        return annualRate;
    }

    public void setAnnualRate(double annualRate) {
        this.annualRate = annualRate;
    }

    @Override
    public double calculateGrossPay() {
        return annualRate / 12; // Monthly salary calculation
    }

    @Override
    public String toString() {
        return String.format("EmployeeID: %s, Name: %s, Title: %s, Point: %d, AnnualRate: %.2f",
                getEmployeeId(), getName(), getTitle(), getPoint(), annualRate);
    }
}
