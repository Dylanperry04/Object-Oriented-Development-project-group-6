public class PartTimeEmployee extends Employee {
    private double hourlyRate;
    private int hoursWorked;

    public PartTimeEmployee(String employeeId, String name, String title, int point, double hourlyRate, int hoursWorked) {
        super(employeeId, name, title, point);
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
    }


    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    @Override
    public double calculateGrossPay() {
        grossPay = hourlyRate * hoursWorked;
        return grossPay;
    }

    @Override
    public String toCSV() {
        return String.format("%s,%s,%s,%d,%.2f,%d,PartTime",
                getEmployeeId(), getName(), getTitle(), getPoint(), hourlyRate, hoursWorked);
    }

    @Override
    public String toString() {
        return String.format("EmployeeID: %s, Name: %s, Title: %s, Point: %d, HourlyRate: %.2f, HoursWorked: %d",
                getEmployeeId(), getName(), getTitle(), getPoint(), hourlyRate, hoursWorked);
    }
}
