import java.util.*;

public class Tax {
    private String employeeId;
    private double taxAmount;

    public Tax(String employeeId, double taxAmount) {
        this.employeeId = employeeId;
        this.taxAmount = taxAmount;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public static double getTaxAmount(List<Tax> taxes, String employeeId) {
        return taxes.stream()
                .filter(t -> t.getEmployeeId().equals(employeeId))
                .mapToDouble(Tax::getTaxAmount)
                .sum();
    }
}
