package project3;

/**
 * Parent Employee class holds the common information between all employees
 * 
 * @author Peter Dawoud
 * @author Maharshi Patel
 */
public class Employee {
    public static final int NO_ERR = -1;
    public static final int NAME_ERR = 0;
    public static final int DEP_ERR = 1;
    public static final int DATE_ERR = 2;
    public static final int ANNUAL_SAL_ERR = 3;
    public static final int MANG_ERR = 4;
    public static final int RATE_ERR = 5;
    public static final int HOURS_ERR_NEG = 6;
    public static final int HOURS_ERR_EXCEED = 7;

    private Profile employeeProfile;
    private int errNo;
    private double payment;

    /**
     * Employee Constructor
     * 
     * @param name The name of the employee
     * @param department The department of the employee
     * @param dateHired The date hired of the employee
     */
    public Employee(String name, String department, Date dateHired) {
        this.employeeProfile = new Profile(name, department, dateHired);
        errNo = NO_ERR;
    }

    /**
     * Getter method for employee profile
     * 
     * @return employeeProfile The profile of an employee
     */
    public Profile getEmployeeProfile() {
        return employeeProfile;
    }

    /**
     * Getter method for error number
     * 
     * @return errNo The error number
     */
    public int getErrNo() {
        return errNo;
    }

    /**
     * Getter method for department
     * 
     * @return department The department the employee works for
     */
    public String getDepartment() {
        return this.employeeProfile.getDepartment();
    }

    /**
     * Getter method for the date hired
     * 
     * @return date hired The date the employee was hired
     */
    public Date getDateHired() {
        return this.employeeProfile.getDateHired();
    }

    /**
     * At method to set the error number
     * 
     * @param errNo The errNo to set
     */
    public void setErrNo(int errNo) {
        this.errNo = errNo;
    }

    /**
     * Getter method for payment
     * 
     * @return payment the payment
     */
    public double getPayment() {
        return payment;
    }

    /**
     * Setter method for payment
     * 
     * @param payment the payment to set
     */
    public void setPayment(double payment) {
        this.payment = payment;
    }

    /**
     * Validates the name of an employee
     * 
     * @return It returns true if the name is valid, otherwise false
     */
    private boolean validateName() {
        return employeeProfile.getName() != null;
    }

    /**
     * Validates the department of an employee
     * 
     * @return It returns true if the department is valid, otherwise false
     */
    private boolean validateDep() {
        return (employeeProfile.getDepartment().equals("CS") || employeeProfile.getDepartment().equals("IT")
                || employeeProfile.getDepartment().equals("ECE"));
    }

    /**
     * Validates the hired date of an employee
     * 
     * @return It returns true if the date is valid, otherwise false
     */
    private boolean validateDate() {
        return employeeProfile.getDateHired().isValid();
    }

    /**
     * Validates the employee and sets errorNo accordingly
     * 
     * @return It returns true if there is an error, otherwise false
     */
    public boolean validate() {
        
        //Check if the name is valid
        if (!this.validateName()) {
            errNo = NAME_ERR;
            return false;
        }
        
        // Check if the department is valid
        else if (!this.validateDep()) {
            errNo = DEP_ERR;
            return false;
        } 
        
        //Check if the date is valid
        else if (!this.validateDate()) {
            errNo = DATE_ERR;
            return false;
        } 
        else {
            return true;
        }
    }

    /**
     * Calculate the payment based on the given data
     * 
     * @return payment The payment calculated
     */
    public double calculatePayment() {
        return payment;
    }

    /**
     * Equals method that checks if two objects are equal
     * 
     * @return It return true if two objects are equal, otherwise false
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Employee) {
            Employee input = (Employee) obj;
            return (this.employeeProfile.equals(input.employeeProfile));
        }
        return false;
    }

    /**
     * toString method
     * 
     * @return String String representing the employee
     */
    @Override
    public String toString() {
        return this.employeeProfile.toString();
    }

    /**
     * To String method UI representing an employee
     *
     * @return String The representing parttime employee
     */
    public String toStringUI() {
        return "";
    }
}
