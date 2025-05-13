public class PartTimeEmployee extends Employee {
    private double hourlyRate;
    private int hoursWorked;
    private boolean hasSubmittedClaim;

    public PartTimeEmployee(String employeeId, String name, String title, int point, double hourlyRate, int hoursWorked) {
        super(employeeId, name, title, point);
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
        this.hasSubmittedClaim = false;

    }

    public boolean hasSubmittedClaim() {
        return hasSubmittedClaim;
    }

    public void submitPayClaim() {
        this.hasSubmittedClaim = true;
    }

    public void resetClaimStatus() {
        this.hasSubmittedClaim = false;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public int getHoursWorked() {
        return hoursWorked;
    }



    @Override
    public double calculateGrossPay() {
        return hourlyRate * hoursWorked; // Gross pay for part-time employees
    }

    @Override
    public String toString() {
        return String.format("EmployeeID: %s, Name: %s, Title: %s, Point: %d, HourlyRate: %.2f, HoursWorked: %d",
                getEmployeeId(), getName(), getTitle(), getPoint(), hourlyRate, hoursWorked);
    }
}
