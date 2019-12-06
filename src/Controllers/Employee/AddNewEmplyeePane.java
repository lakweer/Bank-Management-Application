package Controllers.Employee;

import Helpers.Helpers;
import Objects.Employee;
import Validator.FormValidator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.sql.SQLException;
import java.time.LocalDate;

public class AddNewEmplyeePane {
    private EmployeeHome parent;

    protected  AddNewEmplyeePane(EmployeeHome parent){
        this.parent =parent;
    }
    protected Pane registerNewEmployeePane(Pane pane){
        Pane pane1 = new Pane();
        pane1.setPrefSize(800,750);
        pane1.setStyle(
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: black;");
        pane.getChildren().add(pane1);
        pane1.relocate(269,128);

        //Title
        Label headerLabel = new Label("Employee Registration Form");
        headerLabel.setFont(Font.font("System", FontWeight.BOLD, 35));
        pane1.getChildren().add(headerLabel);
        headerLabel.relocate(167,20);

        //Branch Label
        Label branchLabel = new Label("Branch :");
        pane1.getChildren().add(branchLabel);
        branchLabel.relocate(47,79);

        Label branchIDLabel = new Label(parent.getBranchID());
        pane1.getChildren().add(branchIDLabel);
        branchIDLabel.relocate(112,79);

        //Today Date Label
        Label dateLabel = new Label("Date :");
        pane1.getChildren().add(dateLabel);
        dateLabel.relocate(549,79);

        Label todayDateLabel = new Label(LocalDate.now().toString());
        pane1.getChildren().add(todayDateLabel);
        todayDateLabel.relocate(596,79);

        // First Name Label
        Label firstNameLabel = new Label("First Name");
        pane1.getChildren().add(firstNameLabel);
        firstNameLabel.relocate(47,148);

        // First Name Text Field
        TextField firstNameText = new TextField();
        firstNameText.setPrefSize(399,32);
        pane1.getChildren().add(firstNameText);
        firstNameText.relocate(201,142);
        firstNameText.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-Z*")) {
                firstNameText.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
            }
        });

        // Last Name Label
        Label lastNameLabel = new Label("Last Name");
        pane1.getChildren().add(lastNameLabel);
        lastNameLabel.relocate(47,210);

        // Add Name Text Field
        TextField lastNameText = new TextField();
        lastNameText.setPrefSize(399,32);
        pane1.getChildren().add(lastNameText);
        lastNameText.relocate(201,204);
        lastNameText.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-Z*")) {
                lastNameText.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
            }
        });

        // Add NIC Label
        Label nicLabel = new Label("NIC Number");
        pane1.getChildren().add(nicLabel);
        nicLabel.relocate(47,279);

        // Add NIC Text Field
        TextField nicText = new TextField();
        nicText.setPrefSize(399,32);
        pane1.getChildren().add(nicText);
        nicText.relocate(201,273);
        nicText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,9}([\\.]\\d{0,2})*[V]?")) {
                    nicText.setText(oldValue);
                }
            }
        });

        // Email Label
        Label emailLabel = new Label("Email");
        pane1.getChildren().add(emailLabel);
        emailLabel.relocate(47, 337);

        // Email Text Field
        TextField emailText = new TextField();
        emailText.setPrefSize(399,32);
        pane1.getChildren().add(emailText);
        emailText.relocate(201, 331);

        // Date Of Birth Label
        Label dobLabel = new Label("Date Of Birth");
        pane1.getChildren().add(dobLabel);
        dobLabel.relocate(47, 407);

        //ADD birthday selector
        DatePicker dob = new DatePicker(LocalDate.now());
        dob.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) > 0);
            }
        });
        dob.setPrefSize(150,40);
        dob.getEditor().setDisable(true);
        pane1.getChildren().add(dob);
        dob.relocate(201,403);

        //Buttons
        parent.cancelButton(pane, pane1);

        //Submit Button
        Button submitButton = new Button("Submit");
        submitButton.setDefaultButton(true);
        submitButton.setPrefSize(73,35);
        pane1.getChildren().add(submitButton);
        submitButton.relocate(616,658);


        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(firstNameText.getText().isEmpty()) {
                    Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Please enter the First Name");
                    return;
                }

                if(lastNameText.getText().isEmpty()) {
                    Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Please enter the Last Name");
                    return;
                }

                if(!nicText.getText().isEmpty()) {
                    if(!FormValidator.nicNumberValidate(nicText.getText())){
                        Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Please enter the Correct NIC Number");
                        return;
                    }
                }else{
                    Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Please enter the NIC Number");
                    return;
                }

                if(!emailText.getText().isEmpty()) {
                    if(!FormValidator.emailValidate(emailText.getText())){
                        Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Please enter the Valid Email");
                        return;
                    }
                }else{
                    Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Please enter a Email");
                    return;
                }

                if(dob.getValue().toString().isEmpty()) {
                    Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Please enter the Birth Date");
                    return;
                }

                Employee employee = new Employee(nicText.getText(), parent.getBranchID());
                employee.setFirstName(firstNameText.getText());
                employee.setLastName(lastNameText.getText());
                employee.setNic(nicText.getText());
                employee.setEmail(emailText.getText());
                employee.setDateOfBirth(LocalDate.parse(dob.getValue().toString()));
                parent.getModel().setEmployeeID(parent.getEmployeeID());
                Boolean result = false;
                try {
                    result = parent.getModel().addNewEmployee(employee);
                }catch (SQLException e){
                    e.printStackTrace();
                }if(result){
                    pane.getChildren().remove(pane1);
                    parent.enablePane();
                }else{
                    Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Error When Creating the Form");
                }

            }   });
        return pane1;
    }
}
