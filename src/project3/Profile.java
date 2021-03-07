package project3;

/**
 * 
 * Profile Class that holds personal info about the employee
 *
 * @author Peter Dawoud
 * @author Maharshi Patel
 */
public class Profile {
    private String name;
    private String department;
    private Date dateHired;

    /**
     * Profile Constructor
     * 
     * @param name       The name of the employee
     * @param department The department of the employee
     * @param dateHired  The date hired of the employee
     */
    public Profile(String name, String department, Date dateHired) {
        this.name = name;
        this.department = department;
        this.dateHired = dateHired;
    }

    /**
     * Getter method for employee
     * 
     * @return department The Department the employee is working for
     */
    public String getDepartment() {
        return department;
    }

    /**
     * Getter method for name
     * 
     * @return name The name of the employee
     */
    public String getName() {
        return name;
    }

    /**
     * Getter method for the date hired
     * 
     * @return date The date the employee was hired
     */
    public Date getDateHired() {
        return dateHired;
    }

    /**
     * Equals method to check if two objects are equal
     * 
     * @return true if the objects are equal, otherwise false
     */
    @Override
    public boolean equals(Object object) {
        if (object instanceof Profile) {
            Profile input = (Profile) object;
            return (this.name.equals(input.name) && this.department.equals(input.department)
                    && this.dateHired.equals(input.dateHired));
        }
        return false;
    }

    /**
     * To String method
     * 
     * @return String The string that represents the profile
     */
    @Override
    public String toString() {
        return (this.name + "::" + this.department + "::" + this.dateHired.toString());
    }
}
