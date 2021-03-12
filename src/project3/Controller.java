package project3;

/**
 * Sample Skeleton for 'sample.fxml' Controller Class
 */

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Controller {

    private Company ourCompany = new Company();
    private File file;
    private static final int MAX_NUM_OF_TOKENS = 6;

    @FXML // fx:id="emplyeeName"
    private TextField emplyeeName; // Value injected by FXMLLoader

    @FXML // fx:id="datePicker"
    private DatePicker datePicker; // Value injected by FXMLLoader

    @FXML // fx:id="csRadioBtn"
    private RadioButton csRadioBtn; // Value injected by FXMLLoader

    @FXML // fx:id="job_category"
    private ToggleGroup job_category; // Value injected by FXMLLoader

    @FXML // fx:id="itRadioBtn"
    private RadioButton itRadioBtn; // Value injected by FXMLLoader

    @FXML // fx:id="eceRadioBtn"
    private RadioButton eceRadioBtn; // Value injected by FXMLLoader

    @FXML // fx:id="fulltimeRadioBtn"
    private RadioButton fulltimeRadioBtn; // Value injected by FXMLLoader

    @FXML // fx:id="Employment_Type"
    private ToggleGroup Employment_Type; // Value injected by FXMLLoader

    @FXML // fx:id="parttimeRadioBtn"
    private RadioButton parttimeRadioBtn; // Value injected by FXMLLoader

    @FXML // fx:id="managementRadioBtn"
    private RadioButton managementRadioBtn; // Value injected by FXMLLoader

    @FXML // fx:id="salar_wage"
    private TextField salar_wage; // Value injected by FXMLLoader

    @FXML // fx:id="hoursWorked"
    private TextField hoursWorked; // Value injected by FXMLLoader

    @FXML // fx:id="managerRadioBtn"
    private RadioButton managerRadioBtn; // Value injected by FXMLLoader

    @FXML // fx:id="mng_level"
    private ToggleGroup mng_level; // Value injected by FXMLLoader

    @FXML // fx:id="departmentHeadRadioBtn"
    private RadioButton departmentHeadRadioBtn; // Value injected by FXMLLoader

    @FXML // fx:id="directorRadioBtn"
    private RadioButton directorRadioBtn; // Value injected by FXMLLoader

    @FXML // fx:id="clear"
    private Button clear; // Value injected by FXMLLoader

    @FXML // fx:id="addEmployee"
    private Button addEmployee; // Value injected by FXMLLoader

    @FXML // fx:id="removeEmp"
    private Button removeEmp; // Value injected by FXMLLoader

    @FXML // fx:id="setHours"
    private Button setHours; // Value injected by FXMLLoader

    @FXML // fx:id="textArea"
    private TextArea textArea; // Value injected by FXMLLoader

    @FXML // fx:id="importBtn"
    private Button importBtn; // Value injected by FXMLLoader

    @FXML // fx:id="printBtn"
    private Button printBtn; // Value injected by FXMLLoader

    @FXML // fx:id="exportBtn"
    private Button exportBtn; // Value injected by FXMLLoader

    @FXML // fx:id="paymentBtn"
    private Button paymentBtn; // Value injected by FXMLLoader

    private boolean validateName() {
        if(emplyeeName.getText().isEmpty()){
            textArea.appendText("Name cannot be empty!\n");
            return false;
        }
        return true;
    }

    private double validateSalary() {
        double returnedVal;
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
    void addEmployee(ActionEvent event) {

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
            if (!ourCompany.add(new Parttime(emplyeeName.getText(), department, inputDate, payRate))){
                textArea.appendText("Employee is already in the list.\n");
            }
            else {
                textArea.appendText("Employee added.\n");
            }
        }

        if(fulltimeRadioBtn.isSelected()){
            if (!ourCompany.add(new Fulltime(emplyeeName.getText(), department, inputDate, payRate))){
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

            if (!ourCompany.add(new Management(emplyeeName.getText(), department, inputDate, payRate, management))){
                textArea.appendText("Employee is already in the list.\n");
            }
            else {
                textArea.appendText("Employee added.\n");
            }
        }
        //ourCompany.print();
    }

    @FXML
    void clear(ActionEvent event) {
        textArea.clear();
    }

    @FXML
    void enableManagement(ActionEvent event) {
        managerRadioBtn.setDisable(false);
        departmentHeadRadioBtn.setDisable(false);
        directorRadioBtn.setDisable(false);
        hoursWorked.setDisable(true);
        setHours.setDisable(true);

    }

    private void disableManagement(){
        managerRadioBtn.setDisable(true);
        departmentHeadRadioBtn.setDisable(true);
        directorRadioBtn.setDisable(true);
    }

    @FXML
    void fulltime(ActionEvent event) {
        disableManagement();
        hoursWorked.setDisable(true);
        setHours.setDisable(true);
    }

    @FXML
    void parttime(ActionEvent event) {
        hoursWorked.setDisable(false);
        disableManagement();
        setHours.setDisable(false);
    }

    @FXML
    void removeEmp(ActionEvent event) {
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

            if (!ourCompany.remove(new Employee(emplyeeName.getText(), department, inputDate))) {
                textArea.appendText("Employee cannot be found!\n");
            } else {
                textArea.appendText("Employee removed.\n");
            }
        }
        //ourCompany.print();
    }

    @FXML
    void setHours(ActionEvent event) {
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

            if (!ourCompany.setHours(new Employee(emplyeeName.getText(), department, inputDate), hours)) {
                textArea.appendText("Employee cannot be found!\n");
            } else {
                textArea.appendText("Working hours set.\n");
            }
        }
        //ourCompany.print();
    }

    @FXML
    void importFromFile(ActionEvent event) throws FileNotFoundException {
        Node node = (Node) event.getSource();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        fileChooser.setInitialDirectory(new File("./Data"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );
        file = fileChooser.showOpenDialog(node.getScene().getWindow());
        //System.out.println(file == null);
        if(file != null){
            Scanner input = new Scanner(file);
            String line;
            String tokensArr[] = new String[MAX_NUM_OF_TOKENS];
            int fileCounter = 1;
            while (input.hasNext()) {
                line = input.nextLine();
                StringTokenizer tokens = new StringTokenizer(line, ",");
                int counter = 0;
                while (tokens.hasMoreTokens()) {
                    tokensArr[counter] = tokens.nextToken();
                    counter++;
                }
//                for (int i = 0; i < tokensArr.length; i++) {
//                    System.out.print(tokensArr[i] + "\\");
//                }
//                System.out.print("\n");
                int tokensI = 0;
                if (tokensArr[tokensI++] != null) {
                    if (tokensArr[0].equals("P")) {
                        try {
                            ourCompany.add(new Parttime(tokensArr[tokensI++], tokensArr[tokensI++],
                                    new Date(tokensArr[tokensI++]), Double.parseDouble(tokensArr[tokensI++])));
                        } catch (Exception e) {
                            textArea.appendText("Line " + fileCounter + " can't be read!\n");
                        }
                    } else if (tokensArr[0].equals("F")) {
                        try {
                            ourCompany.add(new Fulltime(tokensArr[tokensI++], tokensArr[tokensI++],
                                    new Date(tokensArr[tokensI++]), Double.parseDouble(tokensArr[tokensI++])));
                        } catch (Exception e) {
                            textArea.appendText("Line " + fileCounter + " can't be read!\n");
                        }
                    } else if (tokensArr[0].equals("M")) {
                        try {
                            ourCompany.add(new Management(tokensArr[tokensI++], tokensArr[tokensI++],
                                    new Date(tokensArr[tokensI++]), Double.parseDouble(tokensArr[tokensI++]),
                                    Integer.parseInt(tokensArr[tokensI++])));
                        } catch (Exception e) {
                            textArea.appendText("Line " + fileCounter + " can't be read!\n");
                        }
                    }
                } else {
                    textArea.appendText("Line " + fileCounter + " can't be read!\n");
                }
                fileCounter++;
            }
        }
        else{
            textArea.appendText("No file selected!");
        }
        //ourCompany.print();
    }

    @FXML
    void printFromFile(ActionEvent event) {
        textArea.appendText(ourCompany.printToUI());
    }

    @FXML
    void exportToFile(ActionEvent event) {
        Node node = (Node) event.getSource();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        fileChooser.setInitialDirectory(new File("./Data"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );
        file = fileChooser.showSaveDialog(node.getScene().getWindow());
        if (file != null){
            try {
                FileWriter f2 = new FileWriter(file, false);
                f2.write(ourCompany.exportDatabase());
                f2.close();

            } catch (IOException e) {
                textArea.appendText("Cannot write to the file specified.\n");
            }
        }
    }

    @FXML
    void processPayment(ActionEvent event) {
        ourCompany.processPayments();
        textArea.appendText("Payments have been processed successfully!\n");
    }

}