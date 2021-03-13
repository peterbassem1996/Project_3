package project3;

import java.text.DecimalFormat;

/**
 * Parttime employee class that extends Employee
 * 
 * @author Peter Dawoud
 * @author Maharshi Patel
 */
public class Parttime extends Employee {
    private double payRate;
    private double hours;
    private static final int MAX_HOURS = 100;
    private static final int OVERTIME_LIMIT = 80;
    private static final double OVERTIME_RATE = 1.5;
    private static final int MIN_HOURS = 0;

    /**
     * Part Time employee constructor
     * 
     * @param name       The name of the employee
     * @param department The department name of the employee
     * @param dateHired  The date hired of the employee
     * @param payRate    The pay rate of the employee
     */
    public Parttime(String name, String department, Date dateHired, double payRate) {
        super(name, department, dateHired);
        this.hours = 0;
        this.payRate = payRate;
    }

    /**
     * Setter method for hours
     * 
     * @param hours The hours to set
     */
    public void setHours(double hours) {
        if (hours > MAX_HOURS) {
            this.hours = MAX_HOURS;
        } else {
            this.hours = hours;
        }
    }

    /**
     * Method to check the hours
     * 
     * @return true if the hours are not negative, otherwise false
     */
    private boolean validateHoursNotNeg() {
        return this.hours >= MIN_HOURS;
    }

    /**
     * Method to check the maximum hours
     * 
     * @return true if the hours are valid, otherwise false
     */
    private boolean validateHoursNotExec() {
        return this.hours <= MAX_HOURS;
    }

    /**
     * Method to check the pay rate
     * 
     * @return true if the pay rate is valid, otherwise false
     */
    private boolean validatePayRate() {
        return this.payRate >= MIN_HOURS;
    }

    /**
     * Part Time validation checks hours and pay rate
     * 
     * @return true if the employee is valid, otherwise false
     */
    @Override
    public boolean validate() {
        //Check if the hours are not negative
        if (!this.validateHoursNotNeg()) {
            this.setErrNo(Employee.HOURS_ERR_NEG);
            return false;
        }
        
        //Check if the hours are not exceded
        else if (!this.validateHoursNotExec()) {
            this.setErrNo(Employee.HOURS_ERR_EXCEED);
            return false;
        }
        
        //Check the pay rate
        else if (!this.validatePayRate()) {
            this.setErrNo(Employee.RATE_ERR);
            return false;
        }

        else {
            return super.validate();
        }
    }

    /**
     * Method to calculate the payment
     * 
     * @return payment The payment calculated
     */
    @Override
    public double calculatePayment() {
        double returnedVal = 0;
        
        //Check overtime
        if (hours > OVERTIME_LIMIT) {
            returnedVal = (((hours - OVERTIME_LIMIT) * OVERTIME_RATE) + OVERTIME_LIMIT) * payRate;
        } else {
            returnedVal = hours * payRate;
        }
        this.setPayment(returnedVal);
        return this.getPayment();
    }

    /**
     * Equals method to check if two objects are equal
     * 
     * @return true if equals, otherwise false
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Employee) {
            Employee input = (Employee) obj;
            return this.getEmployeeProfile().equals(input.getEmployeeProfile());
        }
        return false;
    }

    /**
     * To String method
     * 
     * @return String The representing parttime employee
     */
    @Override
    public String toString() {
        DecimalFormat decimalFormat = new DecimalFormat("###,##0.00");
        return super.toString() + "::Payment $" + decimalFormat.format(this.getPayment()) + "::PART TIME::HourlyRate $"
                + decimalFormat.format(this.payRate) + "::Hours worked this period: "
                + String.format("%.2f", this.hours);
    }

    /**
     * To String method UI representing a parttime employee
     *
     * @return String The representing parttime employee
     */
    public String toStringUI() {
        DecimalFormat decimalFormat = new DecimalFormat("#####0.00");
        return "P," + this.getEmployeeProfile().getName() + "," + this.getEmployeeProfile().getDepartment()+ "," +
                this.getEmployeeProfile().getDateHired().toString()+ "," + decimalFormat.format(this.payRate);
    }
}
