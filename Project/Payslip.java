
public class Payslip {
    private String employeeId;
    private String payPeriod;
    private double grossPay;
    private double netPay;
    private double taxes;
    private double hoursWorked; // Only applicable for part-time employees

    /**
     * Constructs a Payslip object with the specified details.
     *
     * @param employeeId  The ID of the employee.
     * @param payPeriod   The pay period for this payslip.
     * @param grossPay    The gross pay for the employee.
     * @param netPay      The net pay for the employee.
     * @param taxes       The taxes applied to the employee's pay.
     * @param hoursWorked The number of hours worked (for part-time employees).
     */
    public Payslip(String employeeId, String payPeriod, double grossPay, double netPay, double taxes, double hoursWorked) {
        this.employeeId = employeeId;
        this.payPeriod = payPeriod;
        this.grossPay = grossPay;
        this.netPay = netPay;
        this.taxes = taxes;
        this.hoursWorked = hoursWorked;
    }

    // Getter methods
    public String getEmployeeId() {
        return employeeId;
    }

    public String getPayPeriod() {
        return payPeriod;
    }

    public double getGrossPay() {
        return grossPay;
    }

    public double getNetPay() {
        return netPay;
    }

    public double getTaxes() {
        return taxes;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    @Override
    public String toString() {
        return "Payslip{" +
                "employeeId='" + employeeId + '\'' +
                ", payPeriod='" + payPeriod + '\'' +
                ", grossPay=" + grossPay +
                ", netPay=" + netPay +
                ", taxes=" + taxes +
                ", hoursWorked=" + hoursWorked +
                '}';
    }
}

