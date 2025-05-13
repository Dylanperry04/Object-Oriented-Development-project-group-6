public class Payslip {
    private String employeeId;
    private String payDate;
    private double grossPay;
    private double netPay;
    private double taxes;
    private double hoursWorked;

    public Payslip(String employeeId, String payDate, double grossPay, double netPay, double taxes, double hoursWorked) {
        this.employeeId = employeeId;
        this.payDate = payDate;
        this.grossPay = grossPay;
        this.netPay = netPay;
        this.taxes = taxes;
        this.hoursWorked = hoursWorked;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    @Override
    public String toString() {
        return String.format("EmployeeID: %s, PayDate: %s, GrossPay: %.2f, NetPay: %.2f, Taxes: %.2f, HoursWorked: %.2f",
                employeeId, payDate, grossPay, netPay, taxes, hoursWorked);
    }
}
