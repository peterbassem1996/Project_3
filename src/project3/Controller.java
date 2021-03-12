package project3;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.Node;

import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;

import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * A class that handles the execution of the GUI controls
 * @author Maharshi Patel
 * @author Peter Dawoud
 */

public class Controller {

    private Company ourCompany = new Company();
    private File file;
    private static final int MAX_NUM_OF_TOKENS = 6;

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
    private TextField salar_wage;

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
    private TextArea textArea;

    @FXML
    private MenuItem importBtn;

    @FXML
    private MenuItem printBtn;

    @FXML
    private MenuItem printByDepBtn;

    @FXML
    private MenuItem printByDateBtn;

    @FXML
    private MenuItem exportBtn;

    @FXML
    private MenuItem paymentBtn;

    /**
     * A method to clear the text area and the data fields
     */
    private void clearInput(){
        employeeName.clear();
        salar_wage.clear();
        hoursWorked.clear();
        datePicker.getEditor().clear();
        datePicker.setValue(null);
    }

    /**
     * A method to validate the name of an employee
     *
     * @return true if the name is valid, otherwise false
     */
    private boolean validateName() {

        //If the employee name is empty
        if(employeeName.getText().isEmpty()){
            textArea.appendText("Name cannot be empty!\n");
            return false;
        }
        return true;
    }

    /**
     * A method to validate the annual salary of an employee
     *
     * @return annualSalary the annual salary of an employee
     */
    private double validateSalary() {
        double returnedVal;

        //If the salary is not empty
        if(!salar_wage.getText().isEmpty()){
            try{
                returnedVal = Double.parseDouble(salar_wage.getText());
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

        //The salary is empty
        else{
            textArea.appendText("Wages and salaries cannot be empty!\n");
            return -1;
        }
        return returnedVal;
    }

    /**
     * A method to vaidate the hours of an employee
     *
     * @return true if the hours are valid, otherwise false
     */
    private double validateHours() {
        double returnedVal;

        //If the hours are not empty
        if(!hoursWorked.getText().isEmpty()){
            try{
                returnedVal = Double.parseDouble(hoursWorked.getText());
            }
            catch (NumberFormatException e){
                textArea.appendText("Hours Worked must be a numerical value.\n");
                return -1;
            }

            //The hours are less than 0
            if(returnedVal < 0){
                textArea.appendText("Hours Worked cannot be less than ZERO!\n");
                return -1;
            }

            //The hours are greater than 100
            if(returnedVal > 100){
                textArea.appendText("Hours Worked cannot be more than 100!\n");
                return -1;
            }
        }

        //The hours are empty
        else{
            textArea.appendText("Hours Worked cannot be empty!\n");
            return -1;
        }
        return returnedVal;
    }

    /**
     * A method to validate management
     *
     * @return true if the management is valid, otherwise false
     */
    private boolean validateManagement(){
        if(!managerRadioBtn.isSelected() && !departmentHeadRadioBtn.isSelected() && !directorRadioBtn.isSelected()){
            textArea.appendText("Please select a management Level!\n");
        }
        return (managerRadioBtn.isSelected() || departmentHeadRadioBtn.isSelected() || directorRadioBtn.isSelected());
    }

    /**
     * A method to validate the date
     * @param input The date that is entered
     * @return true if the date is valid, otherwise false
     */
    private boolean validateDate(Date input){
        if(!input.isValid()){
            textArea.appendText("Date Entered is Invalid\n");
        }
        return input.isValid();
    }

    /**
     * A controller method to add an employee
     * @param event The event that is triggered
     */
    @FXML
    void addEmployee(ActionEvent event) {

        //Checking the date
        Date inputDate;
        try{
            inputDate = new Date(datePicker.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        }
        catch (Exception e){
            textArea.appendText("Please, Select a date from the date picker!\n");
            return;
        }

        //Different validations
        double payRate = validateSalary();
        if (!validateName()) return;
        if (payRate == -1) return;
        if (!validateDate(inputDate)) return;

        String department = "";

        if(csRadioBtn.isSelected()) department = "CS";
        if(itRadioBtn.isSelected()) department = "IT";
        if(eceRadioBtn.isSelected()) department = "ECE";

        //If parttime is selected
        if(parttimeRadioBtn.isSelected()){
            if (!ourCompany.add(new Parttime(employeeName.getText(), department, inputDate, payRate))){
                textArea.appendText("Employee is already in the list.\n");
            }
            else {
                textArea.appendText("Employee added.\n");
            }
        }

        //If fulltime is selected
        if(fulltimeRadioBtn.isSelected()){
            if (!ourCompany.add(new Fulltime(employeeName.getText(), department, inputDate, payRate))){
                textArea.appendText("Employee is already in the list.\n");
            }
            else {
                textArea.appendText("Employee added.\n");
            }
        }

        //If management is selected
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
        clearInput();
    }

    /**
     * A controller method to clear the data fields and the text area
     * @param event The event that is triggered
     */
    @FXML
    void clear(ActionEvent event) {

        textArea.clear();
        clearInput();
    }

    /**
     * A controller method to set the changes after management is selected
     * @param event The event that is triggered
     */
    @FXML
    void enableManagement(ActionEvent event) {
        managerRadioBtn.setDisable(false);
        departmentHeadRadioBtn.setDisable(false);
        directorRadioBtn.setDisable(false);
        hoursWorked.setDisable(true);
        setHours.setDisable(true);

    }

    /**
     * A controller method to set the changes after management is deselected
     */
    private void disableManagement(){
        managerRadioBtn.setDisable(true);
        departmentHeadRadioBtn.setDisable(true);
        directorRadioBtn.setDisable(true);
    }

    /**
     * A controller method to set the changes after fulltime is selected
     * @param event The event that is triggered
     */
    @FXML
    void fulltime(ActionEvent event) {
        disableManagement();
        hoursWorked.setDisable(true);
        setHours.setDisable(true);
    }

    /**
     * A controller method to set the changes after parttime is selected
     * @param event The event that is triggered
     */
    @FXML
    void parttime(ActionEvent event) {
        hoursWorked.setDisable(false);
        disableManagement();
        setHours.setDisable(false);
    }

    /**
     * A controller method to remove an employee
     * @param event The event that is triggered
     */
    @FXML
    void removeEmp(ActionEvent event) {

        //If the database is empty
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

            //Check if the data is valid
            if (!validateName()) return;
            if (!validateDate(inputDate)) return;

            String department = "";
            if(csRadioBtn.isSelected()) department = "CS";
            if(itRadioBtn.isSelected()) department = "IT";
            if(eceRadioBtn.isSelected()) department = "ECE";

            //removing the employee
            if (!ourCompany.remove(new Employee(employeeName.getText(), department, inputDate))) {
                textArea.appendText("Employee cannot be found!\n");
            } else {
                textArea.appendText("Employee removed.\n");
            }
        }
        clearInput();
    }

    /**
     * The controller method to set the hours of a parttime employee
     *
     * @param event The event that is triggered
     */
    @FXML
    void setHours(ActionEvent event) {

        //If the database is empty
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

            //Check if the data is valid
            if (!validateName()) return;
            if (!validateDate(inputDate)) return;

            String department = "";
            if(csRadioBtn.isSelected()) department = "CS";
            if(itRadioBtn.isSelected()) department = "IT";
            if(eceRadioBtn.isSelected()) department = "ECE";

            double hours = validateHours();
            if(hours == -1) return;

            //Set the hours of a parttime employee
            if (!ourCompany.setHours(new Employee(employeeName.getText(), department, inputDate), hours)) {
                textArea.appendText("Employee cannot be found!\n");
            } else {
                textArea.appendText("Working hours set.\n");
            }
        }
        clearInput();
    }

    /**
     * A controller method to import file from the system
     * @param event The event that is triggered
     * @throws FileNotFoundException FileNotFoundException will be thrown if the file does not exists
     */
    @FXML
    void importFromFile(ActionEvent event) throws FileNotFoundException {

        Node node = (Node) textArea;
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Open File");
        fileChooser.setInitialDirectory(new File("./Data"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );
        file = fileChooser.showOpenDialog(node.getScene().getWindow());

        //If the file is not null
        if(file != null){
            Scanner input = new Scanner(file);
            String line;
            String tokensArr[] = new String[MAX_NUM_OF_TOKENS];
            int fileCounter = 1;

            //Reading the file
            while (input.hasNext()) {
                line = input.nextLine();
                StringTokenizer tokens = new StringTokenizer(line, ",");
                int counter = 0;
                while (tokens.hasMoreTokens()) {
                    tokensArr[counter] = tokens.nextToken();
                    counter++;
                }

                int tokensI = 0;
                if (tokensArr[tokensI++] != null) {

                    //Check if the employee is a parttime
                    if (tokensArr[0].equals("P")) {
                        try {
                            ourCompany.add(new Parttime(tokensArr[tokensI++], tokensArr[tokensI++],
                                    new Date(tokensArr[tokensI++]), Double.parseDouble(tokensArr[tokensI++])));
                        } catch (Exception e) {
                            textArea.appendText("Line " + fileCounter + " can't be read!\n");
                        }
                    }

                    //Check if the employee is a fulltime
                    else if (tokensArr[0].equals("F")) {
                        try {
                            ourCompany.add(new Fulltime(tokensArr[tokensI++], tokensArr[tokensI++],
                                    new Date(tokensArr[tokensI++]), Double.parseDouble(tokensArr[tokensI++])));
                        } catch (Exception e) {
                            textArea.appendText("Line " + fileCounter + " can't be read!\n");
                        }
                    }

                    //Check if the employee is a management
                    else if (tokensArr[0].equals("M")) {
                        try {
                            ourCompany.add(new Management(tokensArr[tokensI++], tokensArr[tokensI++],
                                    new Date(tokensArr[tokensI++]), Double.parseDouble(tokensArr[tokensI++]),
                                    Integer.parseInt(tokensArr[tokensI++])));
                        } catch (Exception e) {
                            textArea.appendText("Line " + fileCounter + " can't be read!\n");
                        }
                    }

                    else{
                        textArea.appendText("Line " + fileCounter + " can't be read!\n");
                    }
                }

                else {
                    textArea.appendText("Line " + fileCounter + " can't be read!\n");
                }


                fileCounter++;
            }
            textArea.appendText("File imported!\n");
        }
        else{
            textArea.appendText("No file selected!\n");
        }
    }

    /**
     * A controller method to print the data
     * @param event The event that is triggered
     */
    @FXML
    void printFromFile(ActionEvent event) {
        textArea.appendText(ourCompany.printToUI());
    }

    /**
     * A controller method to print the data by department
     * @param event The event that is triggered
     */
    @FXML
    void printByDep(ActionEvent event) {
        textArea.appendText(ourCompany.printByDepartmentUI());
    }

    /**
     * A controller method to print the data by date hired
     * @param event The event that is triggered
     */
    @FXML
    void printByDate(ActionEvent event) {
        textArea.appendText(ourCompany.printByDateUI());
    }

    /**
     * A controller method to export data to a file
     * @param event The event that is triggered
     */
    @FXML
    void exportToFile(ActionEvent event) {
        Node node = (Node) textArea;;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        fileChooser.setInitialDirectory(new File("./Data"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );
        file = fileChooser.showSaveDialog(node.getScene().getWindow());

        //If the file is not null
        if (file != null){
            try {
                FileWriter f2 = new FileWriter(file, false);
                f2.write(ourCompany.exportDatabase());
                f2.close();
                textArea.appendText("File exported!\n");
            }
            catch (IOException e) {
                textArea.appendText("Cannot write to the file specified.\n");
            }
        }
    }

    /**
     * A controller method to process the payments of the employees
     * @param event The event that is triggered
     */
    @FXML
    void processPayment(ActionEvent event) {

        //Check if the database is empty
        if(ourCompany.getNumEmployee() == 0){
            textArea.appendText("Employee database is empty. \n");
        }
        else {
            ourCompany.processPayments();
            textArea.appendText("Payments have been processed successfully!\n");
        }
    }

}