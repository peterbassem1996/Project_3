package project3;

/**
 * Company class that holds the list of all employees
 * 
 * @author Peter Dawoud
 * @author Maharshi Patel
 */
public class Company {
    public static final int NOT_FOUND = -1;
    private static final int INTIAL_CAPACITY = 4;
    private static final int ADDITIONAL_CAPACITY = 4;
    private Employee[] empList;
    private int numEmployee;

    /**
     * Company default constructor
     */
    public Company() {
        empList = new Employee[INTIAL_CAPACITY];
        numEmployee = 0;
    }

    /**
     * Getter method for number of employees
     * 
     * @return numEmployee The number of employees
     */
    public int getNumEmployee() {
        return numEmployee;
    }

    /**
     * Find method to find the employee in the list
     * 
     * @param employee The employee that is needed to be find
     * @return index of the employee, -1 if not found
     */
    private int find(Employee employee) {
        for (int i = 0; i < numEmployee; i++) {
            if (empList[i].equals(employee)) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Method to increase the size of the employee list
     */
    private void grow() {
        Employee[] newlist = new Employee[empList.length + ADDITIONAL_CAPACITY];
        for (int i = 0; i < numEmployee; i++) {
            newlist[i] = empList[i];
        }
        empList = newlist;
    }

    /**
     * Adding a new employee
     * 
     * @param employee The employee that is needed to be added
     * @return true if the employee is added, otherwise false
     */
    public boolean add(Employee employee) {
        //Employee not found
        if (find(employee) != -1) {
            employee.setErrNo(Employee.NO_ERR);
            return false;
        }
        //Validate employee and add
        else if (employee.validate()) {
            if (numEmployee == empList.length) {
                this.grow();
            }
            empList[numEmployee] = employee;
            numEmployee++;
            return true;
        }
        //Else false
        else {
            return false;
        }
    }

    /**
     * Remove an existing employee
     * 
     * @param employee The employee that is needed to be removed
     * @return true if the employee is added, otherwise false
     */
    public boolean remove(Employee employee) {

        int employeeLocation = find(employee);
        
        //If not found return
        if (employeeLocation == -1) {
            return false;
        }
        
        //Else remove the employee from the list
        else {

            empList[employeeLocation] = null;

            for (int i = employeeLocation; i < numEmployee - 1; i++) {
                empList[i] = empList[i + 1];
            }

            empList[numEmployee - 1] = null;
            numEmployee--;

            return true;
        }
    }

    /**
     * Set hours for a part time
     * 
     * @param employee The employee that is needed to be removed
     * @param hours    The hours that are need to be set
     * @return true if the hours are set, otherwise false
     */
    public boolean setHours(Employee employee, double hours) {
        //If employee is valid
        if (employee.validate()) {
            int index = find(employee);
            
            //If employee is found
            if (index > NOT_FOUND) {
                
                //If employee instance of Parttime
                if (empList[index] instanceof Parttime) {
                    Parttime entry = (Parttime) empList[index];
                    entry.setHours(hours);
                    return true;
                }
                else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Updates payments for all employees
     */
    public void processPayments() {
        for (int i = 0; i < numEmployee; i++) {
            empList[i].calculatePayment();
        }
    }

    /**
     * Prints the employees in the list
     */
    public void print() {

        //If the list is empty
        if (this.numEmployee <= 0) {
            System.out.println("Emplyee Database is empty.");
            return;
        }

        System.out.println("--Printing earning statements for all employees--");

        for (int i = 0; i < numEmployee; i++) {
            System.out.println(empList[i].toString());
        }
    }

    /**
     * Prints the employees in the list to UI
     */
    public String printToUI() {
        String returnedVal = "";
        //If the list is empty
        if (this.numEmployee <= 0) {
            returnedVal = "Emplyee Database is empty.\n";
        }
        else {
            returnedVal += "--Printing earning statements for all employees--\n";

            for (int i = 0; i < numEmployee; i++) {
                returnedVal += empList[i].toString() + "\n";
            }
        }
        return returnedVal;
    }

    /**
     * Prints the employees in the list to file
     */
    public String exportDatabase() {
        String returnedVal = "";
        //If the list is empty
        if (this.numEmployee <= 0) {
            returnedVal = "Emplyee Database is empty.\n";
        }
        else {
            for (int i = 0; i < numEmployee; i++) {
                returnedVal += empList[i].toStringUI() + "\n";
            }
        }
        return returnedVal;
    }

    /**
     * Prints the employees in the list sorted by department
     */
    public void printByDepartment() {
        
        //If the list is empty
        if (this.numEmployee <= 0) {
            System.out.println("Emplyee Database is empty.");
            return;
        }

        System.out.println("--Printing earning statements by department--");
        
        //Sorting them by department
        for (int i = 0; i < numEmployee; i++) {
            int minIndex = i;

            for (int j = i + 1; j < numEmployee; j++) {
                if (empList[j].getDepartment().compareTo(empList[minIndex].getDepartment()) < 0) {
                    minIndex = j;
                }
            }
            Employee tempEmployee = empList[minIndex];
            empList[minIndex] = empList[i];
            empList[i] = tempEmployee;
        }
        
        
        //Printing the employees
        for (int i = 0; i < numEmployee; i++) {
            System.out.println(empList[i].toString());
        }
    }

    /**
     * Prints the employees in the list sorted by date
     */
    public void printByDate() {
        
        //If the list is empty
        if (this.numEmployee <= 0) {
            System.out.println("Emplyee Database is empty.");
            return;
        }

        System.out.println("--Printing earning statements by date hired--");
        
        //Sorting them according to the dates
        for (int i = 0; i < numEmployee; i++) {
            int minIndex = i;

            for (int j = i + 1; j < numEmployee; j++) {
                if (empList[j].getDateHired().compareTo(empList[minIndex].getDateHired()) < 0) {
                    minIndex = j;
                }
            }
            Employee tempEmployee = empList[minIndex];
            empList[minIndex] = empList[i];
            empList[i] = tempEmployee;
        }
        
        //Printing the employees
        for (int i = 0; i < numEmployee; i++) {
            System.out.println(empList[i].toString());
        }
    }
}
