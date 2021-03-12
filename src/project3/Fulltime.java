package project3;

import java.text.DecimalFormat;

/**
 * Full Time employee class that extends Employee
 * 
 * @author Peter Dawoud
 * @author Maharshi Patel
 */
public class Fulltime extends Employee {
    public static final int NUBMER_OF_PAY_PERIODS = 26;
    private double annualSalary;

    /**
     * Constructor to initialize a Fulltime employee
     * 
     * @param name         The name of the employee
     * @param department   The department of the employee
     * @param dateHired    The hired date of the employee
     * @param annualSalary The annual salary of the employee
     */
    public Fulltime(String name, String department, Date dateHired, double annualSalary) {
        super(name, department, dateHired);
        this.annualSalary = annualSalary;
    }

    /**
     * Method to get the annual salary
     *
     * @return annualSalary The annual salary of the employee
     */
    public double getAnnualSalary() {
        return annualSalary;
    }

    /**
     * Method to validate the annual salary
     * 
     * @return true if the salary is valid, otherwise false
     */
    private boolean validateAnnualSalary() {
        return annualSalary >= 0;
    }

    /**
     * Validating for Full time employee checks that the salary is not a negative
     * value
     * 
     * @return true if the annual salary is valid, otherwise false
     */
    @Override
    public boolean validate() {
        
        //Check the annual salary
        if (!this.validateAnnualSalary()) {
            this.setErrNo(Employee.ANNUAL_SAL_ERR);
            return false;
        } else {
            return super.validate();
        }
    }

    /**
     * Calculating the payment of a fulltime
     * 
     * @return payment The payment of the employee
     */
    @Override
    public double calculatePayment() {
        this.setPayment(this.annualSalary / NUBMER_OF_PAY_PERIODS);
        return this.getPayment();
    }

    /**
     * Equals method to check if two objects are equal
     * 
     * @param obj The object that is to be compared
     * @return true if the two objects are equal, otherwise false
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
     * @return String The string representing a fulltime
     */
    @Override
    public String toString() {
        DecimalFormat decimalFormat = new DecimalFormat("###,##0.00");
        return super.toString() + "::Payment $" + decimalFormat.format(this.getPayment())
                + "::FULL TIME::Annual Salary $" + decimalFormat.format(this.annualSalary);
    }

    /**
     * To String method UI representing fulltime employee
     *
     * @return String The representing fulltime employee
     */
    public String toStringUI() {
        DecimalFormat decimalFormat = new DecimalFormat("#####0.00");
        return "F," + this.getEmployeeProfile().getName() + "," + this.getEmployeeProfile().getDepartment()+ "," +
                this.getEmployeeProfile().getDateHired().toString()+ "," + decimalFormat.format(this.annualSalary);
    }
}
