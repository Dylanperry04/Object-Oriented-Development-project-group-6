/**
 * Represents a pay scale, defining the salary or hourly rate associated with a specific job title and point on the pay scale.
 */
public class PayScale {
    private String title;
    private int point;
    private double annualRate;
    private double hourlyRate;

    /**
     * Constructs a new PayScale with the specified details.
     *
     * @param title      The job title associated with this pay scale.
     * @param point      The point on the pay scale.
     * @param annualRate The annual salary rate for this pay scale.
     * @param hourlyRate The hourly rate for this pay scale.
     */
    public PayScale(String title, int point, double annualRate, double hourlyRate) {
        this.title = title;
        this.point = point;
        this.annualRate = annualRate;
        this.hourlyRate = hourlyRate;
    }

    /**
     * Gets the job title associated with this pay scale.
     *
     * @return The job title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the point on the pay scale.
     *
     * @return The point on the pay scale.
     */
    public int getPoint() {
        return point;
    }

    /**
     * Gets the annual salary rate associated with this pay scale.
     *
     * @return The annual salary rate.
     */
    public double getAnnualRate() {
        return annualRate;
    }

    /**
     * Gets the hourly rate associated with this pay scale.
     *
     * @return The hourly rate.
     */
    public double getHourlyRate() {
        return hourlyRate;
    }
}