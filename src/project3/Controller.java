package project3;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Controller {

    private static final int MAX_NUM_OF_COMMAND_PARTS = 7;
    private static final int FIRST_ARG_INDEX = 0;
    private static final int SECOND_ARG_INDEX = 1;
    private static final int THIRD_ARG_INDEX = 2;
    private static final int FORTH_ARG_INDEX = 3;
    private static final int FIFTH_ARG_INDEX = 4;
    private static final int SIXTH_ARG_INDEX = 5;
    private Company ourCompany = new Company();

    @FXML
    private TextField employeeName;

    @FXML
    private DatePicker datePicker;

    @FXML
    private RadioButton csRadioBtn;

    @FXML
    private ToggleGroup job_category;

    @FXML
    private RadioButton itRadioBtn;

    @FXML
    private RadioButton eceRadioBtn;

    @FXML
    private RadioButton fulltimeRadioBtn;

    @FXML
    private ToggleGroup Employment_Type;

    @FXML
    private RadioButton parttimeRadioBtn;

    @FXML
    private RadioButton managementRadioBtn;

    @FXML
    private TextField salary_wage;

    @FXML
    private TextField hoursWorked;

    @FXML
    private RadioButton managerRadioBtn;

    @FXML
    private ToggleGroup mng_level;

    @FXML
    private RadioButton departmentHeadRadioBtn;

    @FXML
    private RadioButton directorRadioBtn;

    @FXML
    private Button clear;

    @FXML
    private Button addEmployee;

    @FXML
    private Button removeEmp;

    @FXML
    private Button setHours;

    @FXML
    private MenuBar commandMenu;

    @FXML
    private MenuItem importDatabase;

    @FXML
    private MenuItem exportDatabase;

    @FXML
    private MenuItem printEmployees;

    @FXML
    private MenuItem printByDepartment;

    @FXML
    private MenuItem printByDate;

    @FXML
    private MenuItem compute;

    @FXML
    private TextArea textArea;

    private boolean validateName() {
        if(employeeName.getText().isEmpty()){
            textArea.appendText("Name cannot be empty!\n");
            return false;
        }
        return true;
    }

    private double validateSalary() {
        double returnedVal;
        if(!salary_wage.getText().isEmpty()){
            try{
                returnedVal = Double.parseDouble(salary_wage.getText());
            }
            catch (NumberFormatException e){
                textArea.appendText("Wages and salaries must be a numerical value.\n");
                return -1;
            }
            if(returnedVal < 0){
                textArea.appendText("Wages and salaries cannot be less than ZERO!\n");
                return -1;
            }
        }
        else{
            textArea.appendText("Wages and salaries cannot be empty!\n");
            return -1;
        }
        return returnedVal;
    }

    private double validateHours() {
        double returnedVal;
        if(!hoursWorked.getText().isEmpty()){
            try{
                returnedVal = Double.parseDouble(hoursWorked.getText());
            }
            catch (NumberFormatException e){
                textArea.appendText("Hours Worked must be a numerical value.\n");
                return -1;
            }
            if(returnedVal < 0){
                textArea.appendText("Hours Worked cannot be less than ZERO!\n");
                return -1;
            }
            if(returnedVal > 100){
                textArea.appendText("Hours Worked cannot be more than 100!\n");
                return -1;
            }
        }
        else{
            textArea.appendText("Hours Worked cannot be empty!\n");
            return -1;
        }
        return returnedVal;
    }

    private boolean validateManagement(){
        if(!managerRadioBtn.isSelected() && !departmentHeadRadioBtn.isSelected() && !directorRadioBtn.isSelected()){
            textArea.appendText("Please select a management Level!\n");
        }
        return (managerRadioBtn.isSelected() || departmentHeadRadioBtn.isSelected() || directorRadioBtn.isSelected());
    }

    private boolean validateDate(Date input){
        if(!input.isValid()){
            textArea.appendText("Date Entered is Invalid\n");
        }
        return input.isValid();
    }

    @FXML
    public void addEmployee(ActionEvent event) {

        //date creation and check
        Date inputDate;
        try{
            inputDate = new Date(datePicker.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        }
        catch (Exception e){
            textArea.appendText("Please, Select a date from the date picker!\n");
            return;
        }

        //common Validation
        double payRate = validateSalary();
        if (!validateName()) return;
        if (payRate == -1) return;
        if (!validateDate(inputDate)) return;

        //department
        String department = "";

        if(csRadioBtn.isSelected()) department = "CS";
        if(itRadioBtn.isSelected()) department = "IT";
        if(eceRadioBtn.isSelected()) department = "ECE";

        if(parttimeRadioBtn.isSelected()){
            if (!ourCompany.add(new Parttime(employeeName.getText(), department, inputDate, payRate))){
                textArea.appendText("Employee is already in the list.\n");
            }
            else {
                textArea.appendText("Employee added.\n");
            }
        }

        if(fulltimeRadioBtn.isSelected()){
            if (!ourCompany.add(new Fulltime(employeeName.getText(), department, inputDate, payRate))){
                textArea.appendText("Employee is already in the list.\n");
            }
            else {
                textArea.appendText("Employee added.\n");
            }
        }

        if(managementRadioBtn.isSelected()){
            int management = 0;
            if (!validateManagement()) {return;}
            else {
                if (managerRadioBtn.isSelected()) management = 1;
                if (departmentHeadRadioBtn.isSelected()) management = 2;
                if (directorRadioBtn.isSelected()) management = 3;
            }

            if (!ourCompany.add(new Management(employeeName.getText(), department, inputDate, payRate, management))){
                textArea.appendText("Employee is already in the list.\n");
            }
            else {
                textArea.appendText("Employee added.\n");
            }
        }
    }

    @FXML
    public void clear(ActionEvent event) {
        textArea.clear();
    }

    @FXML
    public void enableManagement(ActionEvent event) {
        managerRadioBtn.setDisable(false);
        departmentHeadRadioBtn.setDisable(false);
        directorRadioBtn.setDisable(false);
        salary_wage.setDisable(false);
        hoursWorked.setDisable(true);
    }

    private void disableManagement(){
        managerRadioBtn.setDisable(true);
        departmentHeadRadioBtn.setDisable(true);
        directorRadioBtn.setDisable(true);
    }

    @FXML
    public void fulltime(ActionEvent event) {
        disableManagement();
        salary_wage.setDisable(false);
        hoursWorked.setDisable(true);
    }

    @FXML
    public void parttime(ActionEvent event) {
        hoursWorked.setDisable(false);
        disableManagement();
        salary_wage.setDisable(true);
    }

    @FXML
    public void removeEmp(ActionEvent event) {
        if (ourCompany.getNumEmployee() == 0) {
            textArea.appendText("Employee database is empty.\n");
            return;
        }
        else{
            Date inputDate;
            try{
                inputDate = new Date(datePicker.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
            }
            catch (Exception e){
                textArea.appendText("Please, Select a date from the date picker!\n");
                return;
            }

            if (!validateName()) return;
            if (!validateDate(inputDate)) return;

            String department = "";
            if(csRadioBtn.isSelected()) department = "CS";
            if(itRadioBtn.isSelected()) department = "IT";
            if(eceRadioBtn.isSelected()) department = "ECE";

            if (!ourCompany.remove(new Employee(employeeName.getText(), department, inputDate))) {
                textArea.appendText("Employee cannot be found!\n");
            } else {
                textArea.appendText("Employee removed.\n");
            }
        }
    }

    @FXML
    public void setHours(ActionEvent event) {
        if (ourCompany.getNumEmployee() == 0) {
            textArea.appendText("Employee database is empty.\n");
            return;
        }
        else{
            Date inputDate;
            try{
                inputDate = new Date(datePicker.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
            }
            catch (Exception e){
                textArea.appendText("Please, Select a date from the date picker!\n");
                return;
            }

            if (!validateName()) return;
            if (!validateDate(inputDate)) return;

            String department = "";
            if(csRadioBtn.isSelected()) department = "CS";
            if(itRadioBtn.isSelected()) department = "IT";
            if(eceRadioBtn.isSelected()) department = "ECE";

            double hours = validateHours();
            if(hours == -1) return;

            if (!ourCompany.setHours(new Employee(employeeName.getText(), department, inputDate), hours)) {
                textArea.appendText("Employee cannot be found!\n");
            }
            else {
                textArea.appendText("Working hours set.\n");
            }
        }
        ourCompany.print();
    }

    @FXML
    public void calculatePayment(ActionEvent event) {

        if (ourCompany.getNumEmployee() == 0) {
            textArea.appendText("Employee database is empty. \n");
        }
        else {
            ourCompany.processPayments();
            textArea.appendText("Calculation of employee payments is done! \n");
        }
    }

    @FXML
    public void exportDatabase(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open Target File for the Export");
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        Stage stage = new Stage();
        File targetFile = chooser.showSaveDialog(stage);

        FileWriter file;

        try{
            file = new FileWriter(targetFile);

            BufferedWriter writer = new BufferedWriter(file);

            for(int i = 0; i < ourCompany.getNumEmployee(); i++){
                writer.write(ourCompany.getEmpList()[i].toString(), 0, ourCompany.getEmpList()[i].toString().length());
                writer.newLine();
            }

            writer.flush();
            writer.close();
            file.close();
        }
        catch(IOException e){
            textArea.appendText("Invalid File \n");
            return;
        }

        textArea.appendText("File exported. \n");
    }

    @FXML
    public void importDatabase(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open Source File for the Import");
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        Stage stage = new Stage();
        File sourceFile = chooser.showOpenDialog(stage);

        Scanner input;

        try {
            input = new Scanner(sourceFile);
        }
        catch(FileNotFoundException e){
            textArea.appendText("File Not Found. \n");
            return;
        }

        do {
            commandExecution(input.nextLine(), ourCompany);
        }
        while (input.hasNext());
    }

    @FXML
    public void print(ActionEvent event) {
        //If the list is empty
        if (ourCompany.getNumEmployee() <= 0) {
            textArea.appendText("Employee Database is empty.\n");
            return;
        }

        textArea.appendText("--Printing earning statements for all employees--\n");

        for (int i = 0; i < ourCompany.getNumEmployee(); i++) {
            textArea.appendText(ourCompany.getEmpList()[i].toString() + "\n");
        }
    }

    @FXML
    public void printByDate(ActionEvent event) {
        //If the list is empty
        if (ourCompany.getNumEmployee() <= 0) {
            textArea.appendText("Employee Database is empty.\n");
            return;
        }

        textArea.appendText("--Printing earning statements by date hired--\n");

        //Sorting them according to the dates
        for (int i = 0; i < ourCompany.getNumEmployee(); i++) {
            int minIndex = i;

            for (int j = i + 1; j < ourCompany.getNumEmployee(); j++) {
                if (ourCompany.getEmpList()[j].getDateHired().compareTo(ourCompany.getEmpList()[minIndex].getDateHired()) < 0) {
                    minIndex = j;
                }
            }
            Employee tempEmployee = ourCompany.getEmpList()[minIndex];
            ourCompany.getEmpList()[minIndex] = ourCompany.getEmpList()[i];
            ourCompany.getEmpList()[i] = tempEmployee;
        }

        //Printing the employees
        for (int i = 0; i < ourCompany.getNumEmployee(); i++) {
            textArea.appendText(ourCompany.getEmpList()[i].toString() + "\n");
        }
    }

    @FXML
    public void printByDepartment(ActionEvent event) {
        //If the list is empty
        if (ourCompany.getNumEmployee() <= 0) {
            textArea.appendText("Employee Database is empty.\n");
            return;
        }

        textArea.appendText("--Printing earning statements by department--\n");

        //Sorting them by department
        for (int i = 0; i < ourCompany.getNumEmployee(); i++) {
            int minIndex = i;

            for (int j = i + 1; j < ourCompany.getNumEmployee(); j++) {
                if (ourCompany.getEmpList()[j].getDepartment().compareTo(ourCompany.getEmpList()[minIndex].getDepartment()) < 0) {
                    minIndex = j;
                }
            }
            Employee tempEmployee = ourCompany.getEmpList()[minIndex];
            ourCompany.getEmpList()[minIndex] = ourCompany.getEmpList()[i];
            ourCompany.getEmpList()[i] = tempEmployee;
        }


        //Printing the employees
        for (int i = 0; i < ourCompany.getNumEmployee(); i++) {
            textArea.appendText(ourCompany.getEmpList()[i].toString() + "\n");
        }
    }

    /**
     * A method to handle the errors
     *
     * @param employee The current employee
     */
    private void errorHandle(Employee employee) {
        int errorCode = employee.getErrNo();
        switch (errorCode) {
            case Employee.NAME_ERR:
                textArea.appendText("Name cannot be empty! \n");
                break;
            case Employee.DEP_ERR:
                textArea.appendText("'" + employee.getDepartment() + "'" + " is not a valid department! \n");
                break;
            case Employee.ANNUAL_SAL_ERR:
                textArea.appendText("Salary cannot be negative. \n");
                break;
            case Employee.MANG_ERR:
                textArea.appendText("Invalid management Code! \n");
                break;
            case Employee.RATE_ERR:
                textArea.appendText("Pay rate cannot be negative. \n");
                break;
            case Employee.DATE_ERR:
                textArea.appendText(employee.getDateHired() + " is not a valid Date! \n");
                break;
            case Employee.HOURS_ERR_NEG:
                textArea.appendText("Working Hours cannot be negative. \n");
                break;
            case Employee.HOURS_ERR_EXCEED:
                textArea.appendText("Invalid Hours: Over 100. \n");
                break;
            case Employee.NO_ERR:
                textArea.appendText("Employee does not exist. \n");
                break;
        }
    }

    /**
     * A method that executes the commands
     *
     * @param command The command entered by the user
     * @param company The list of the employees
     * @return true if we need to continue, otherwise false
     */
    private boolean commandExecution(String command, Company company) {
        // variables used
        String[] commandParts = new String[MAX_NUM_OF_COMMAND_PARTS];
        StringTokenizer tokens = new StringTokenizer(command, ",");
        int counter = 0;

        // tokenizing the command into parts
        while (tokens.hasMoreTokens() && counter < MAX_NUM_OF_COMMAND_PARTS) {
            commandParts[counter] = tokens.nextToken();
            counter++;
        }

        Employee emp;

        if (commandParts[FIRST_ARG_INDEX] == null) {
            return true;
        }

        //Adding a parttime
        else if (commandParts[FIRST_ARG_INDEX].equals("P")) {

            try {
                emp = new Parttime(commandParts[SECOND_ARG_INDEX], commandParts[THIRD_ARG_INDEX],
                        new Date(commandParts[FORTH_ARG_INDEX]), Double.parseDouble(commandParts[FIFTH_ARG_INDEX]));
            } catch (Exception e) {
                textArea.appendText("The employee data dosn't match the form! \n");
                return true;
            }


            if (!emp.validate()) {
                errorHandle(emp);
            }
            else if (!company.add(emp)) {
                textArea.appendText("Employee is already in the list. \n");
                return true;
            }
            else {
                textArea.appendText("Employee added. \n");
                return true;
            }

        }

        //Adding a fulltime
        else if (commandParts[FIRST_ARG_INDEX].equals("F")) {

            try {
                emp = new Fulltime(commandParts[SECOND_ARG_INDEX], commandParts[THIRD_ARG_INDEX],
                        new Date(commandParts[FORTH_ARG_INDEX]), Double.parseDouble(commandParts[FIFTH_ARG_INDEX]));
            }
            catch (Exception e) {
                textArea.appendText("The employee data dosn't match the form! \n");
                return true;
            }
            if (!emp.validate()) {
                errorHandle(emp);
            }
            else if (!company.add(emp)) {
                textArea.appendText("Employee is already in the list.\n");
                return true;
            }
            else {
                textArea.appendText("Employee added. \n");
                return true;
            }
        }

        //Adding a management employee
        else if (commandParts[FIRST_ARG_INDEX].equals("M")) {

            try {
                emp = new Management(commandParts[SECOND_ARG_INDEX], commandParts[THIRD_ARG_INDEX],
                        new Date(commandParts[FORTH_ARG_INDEX]), Double.parseDouble(commandParts[FIFTH_ARG_INDEX]),
                        Integer.parseInt(commandParts[SIXTH_ARG_INDEX]));
            }
            catch (Exception e) {
                textArea.appendText("The employee data dosn't match the form! \n");
                return true;
            }
            if (!emp.validate()) {
                errorHandle(emp);
            }
            else if (!company.add(emp)) {
                textArea.appendText("Employee is already in the list. \n");
                return true;
            }
            else {
                textArea.appendText("Employee added. \n");
                return true;
            }

        }

        //Removing an employee
        else if (commandParts[FIRST_ARG_INDEX].equals("R")) {

            if (company.getNumEmployee() == 0) {
                textArea.appendText("Employee database is empty. \n");
                return true;
            } else {
                try {
                    emp = new Employee(commandParts[SECOND_ARG_INDEX], commandParts[THIRD_ARG_INDEX],
                            new Date(commandParts[FORTH_ARG_INDEX]));
                } catch (Exception e) {
                    textArea.appendText("The employee data dosn't match the form! \n");
                    return true;
                }
                if (!company.remove(emp)) {
                    errorHandle(emp);
                }
                else {
                    textArea.appendText("Employee removed. \n");
                    return true;
                }
            }

        }

        //Calculate the payments
        else if (commandParts[FIRST_ARG_INDEX].equals("C")) {

            if (company.getNumEmployee() == 0) {
                textArea.appendText("Employee database is empty. \n");
                return true;
            } else {
                company.processPayments();
                textArea.appendText("Calculation of employee payments is done! \n");
                return true;
            }

        }

        //Set hours of the parttime employee
        else if (commandParts[FIRST_ARG_INDEX].equals("S")) {

            double hours;

            if (company.getNumEmployee() == 0) {
                textArea.appendText("Employee database is empty. \n");
                return true;
            }
            else {
                try {
                    emp = new Parttime(commandParts[SECOND_ARG_INDEX], commandParts[THIRD_ARG_INDEX],
                            new Date(commandParts[FORTH_ARG_INDEX]), 0);

                    hours = Double.parseDouble(commandParts[FIFTH_ARG_INDEX]);
                }
                catch (Exception e) {
                    textArea.appendText("The command doesn't match the form! \n");
                    return true;
                }

                if (hours < 0) {
                    textArea.appendText("Working Hours cannot be negative! \n");
                    return true;
                }

                else if (hours > 100) {
                    textArea.appendText("Invalid Hours: over 100!");
                    return true;
                }

                if (!company.setHours(emp, hours)) {
                    errorHandle(emp);
                    return true;
                }
                else {
                    textArea.appendText("Working hours set.");
                    return true;
                }
            }

        }

        //Print the employees
        else if (commandParts[FIRST_ARG_INDEX].equals("PA")) {
            company.print();
            return true;
        }

        //Print the employees by date hired
        else if (commandParts[FIRST_ARG_INDEX].equals("PH")) {
            company.printByDate();
            return true;
        }

        //Print the employees by the department
        else if (commandParts[FIRST_ARG_INDEX].equals("PD")) {
            company.printByDepartment();
            return true;
        }

        //Quit the program
        else if (commandParts[FIRST_ARG_INDEX].equals("Q")) {
            textArea.appendText("Payroll Processing copleted. \n");
            return false;
        }
        else {
            textArea.appendText("Command '" + commandParts[FIRST_ARG_INDEX] + "' not supported! \n");
            return true;
        }
        return true;
    }
}