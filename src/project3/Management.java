package project3;

import java.text.DecimalFormat;

/**
 * Management employee class
 * 
 * @author Peter Dawoud
 * @author Maharshi Patel
 */
public class Management extends Fulltime {
    private static final int MANAGER = 1;
    private static final int DEP_HEAD = 2;
    private static final int DIR = 3;
    private static final double MANAGER_COMP = 5000;
    private static final double DEP_HEAD_COMP = 9500;
    private static final double DIR_COMP = 12000;
    private int managerLevel;

    /**
     * Constructor to initialize management
     * 
     * @param name         The name of the employee
     * @param department   The department of the employee
     * @param dateHired    The date hired of the employee
     * @param annualSalary The annual salary of the employee
     * @param managerLevel The manager level of the employee
     */
    public Management(String name, String department, Date dateHired, double annualSalary, int managerLevel) {
        super(name, department, dateHired, annualSalary);
        this.managerLevel = managerLevel;
    }

    /**
     * Method to check the manager level
     * 
     * @return true if the level is valid, otherwise false
     */
    private boolean ValidateManagerLevel() {
        return this.managerLevel == MANAGER || this.managerLevel == DEP_HEAD || this.managerLevel == DIR;
    }

    /**
     * Validate method for the Management check the management code.
     * 
     * @return true if there is no error, otherwise false
     */
    @Override
    public boolean validate() {
        //Check the management level
        if (!this.ValidateManagerLevel()) {
            this.setErrNo(Employee.MANG_ERR);
            return false;
        } 
        else {
            return super.validate();
        }
    }

    /**
     * Calculate payment of the employee
     * 
     * @return payment The payment of an employee
     */
    @Override
    public double calculatePayment() {
        double additionalComp = 0;
        //If the employee is a manager
        if (this.managerLevel == MANAGER) {
            additionalComp = MANAGER_COMP / Fulltime.NUBMER_OF_PAY_PERIODS;
        }
        
        //If the employee is a department head
        else if (this.managerLevel == DEP_HEAD) {
            additionalComp = DEP_HEAD_COMP / Fulltime.NUBMER_OF_PAY_PERIODS;
        }
        
        //If the employee is a director
        else if (this.managerLevel == DIR) {
            additionalComp = DIR_COMP / Fulltime.NUBMER_OF_PAY_PERIODS;
        }
        this.setPayment(super.calculatePayment() + additionalComp);
        return this.getPayment();
    }

    /**
     * Equals method to check two objects
     * 
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
     * @return String The string representing the employee
     */
    @Override
    public String toString() {
        DecimalFormat decimalFormat = new DecimalFormat("###,##0.00");
        String additionalStr = "";
        
        //If the employee is a manager
        if (this.managerLevel == MANAGER) {
            additionalStr = "::Manager Compensation$"
                    + decimalFormat.format(MANAGER_COMP / Fulltime.NUBMER_OF_PAY_PERIODS);
        }
        
        //If the employee is a department head
        else if (this.managerLevel == DEP_HEAD) {
            additionalStr = "::Department Head Compensation$"
                    + decimalFormat.format(DEP_HEAD_COMP / Fulltime.NUBMER_OF_PAY_PERIODS);
        }
        
        //If the employee is a director
        else if (this.managerLevel == DIR) {
            additionalStr = "::Director Compensation$"
                    + decimalFormat.format(DIR_COMP / Fulltime.NUBMER_OF_PAY_PERIODS);
        }
        return super.toString() + additionalStr;
    }
}
