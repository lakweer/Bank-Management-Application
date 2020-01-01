package Controllers.BranchManager;


import Hashing.GFG;
import Helpers.Helpers;
import Objects.Employee;
import Validator.FormValidator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.time.LocalDate;

public class AddNewEmplyeePane {
    private BranchMangerHome parent;

    public   AddNewEmplyeePane(BranchMangerHome parent){
        this.parent =parent;
    }
    public GridPane registerNewEmployeePane(BorderPane pane){
        GridPane pane1 = new GridPane();
        pane.setCenter(pane1);
        pane1.setHgap(10);
        pane1.setVgap(10);
        pane1.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

        //Title
        Label headerLabel = new Label("Employee Registration Form");
        headerLabel.setFont(Font.font("System", FontWeight.BOLD, 35));
        pane1.add(headerLabel,1,1,3,1);

        //Branch Label
        Label branchLabel = new Label("Branch : " + parent.getBranchID());
        pane1.add(branchLabel,1,2,2,1);

        //Today Date Label
        Label dateLabel = new Label("Date : " + LocalDate.now().toString());
        pane1.add(dateLabel,3,2);

        // First Name Label
        Label firstNameLabel = new Label("First Name");
        pane1.add(firstNameLabel,1,3);

        // First Name Text Field
        TextField firstNameText = new TextField();
        pane1.add(firstNameText,2,3);
        firstNameText.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-Z*")) {
                firstNameText.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
            }
        });

        // Last Name Label
        Label lastNameLabel = new Label("Last Name");
        pane1.add(lastNameLabel,1,4);

        // Add Name Text Field
        TextField lastNameText = new TextField();
        pane1.add(lastNameText,2,4);
        lastNameText.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-Z*")) {
                lastNameText.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
            }
        });

        // Add NIC Label
        Label nicLabel = new Label("NIC Number");
        pane1.add(nicLabel,1,5);

        // Add NIC Text Field
        TextField nicText = new TextField();
        pane1.add(nicText,2,5);
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
        pane1.add(emailLabel,1,6);

        // Email Text Field
        TextField emailText = new TextField();
        pane1.add(emailText,2,6);

        // Date Of Birth Label
        Label dobLabel = new Label("Date Of Birth");
        pane1.add(dobLabel,1,7);

        //ADD birthday selector
        DatePicker dob = new DatePicker(LocalDate.now());
        dob.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) > 0);
            }
        });
        dob.getEditor().setDisable(true);
        pane1.add(dob,2,7);

        //Buttons

        //Cancel Button
        Button cancelButton = new Button("Cancel");
        cancelButton.setDefaultButton(true);
        cancelButton.setPrefSize(73,35);
        pane1.add(cancelButton,2,10);

        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                parent.enablePane();
                pane.getChildren().remove(pane1);
            }
        });

        //Submit Button
        Button submitButton = new Button("Submit");
        submitButton.setDefaultButton(true);
        submitButton.setPrefSize(73,35);
        pane1.add(submitButton,3,10);

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

                Employee newEmployee = new Employee(firstNameText.getText(), lastNameText.getText(), nicText.getText(),
                        nicText.getText(), GFG.encryptThisString("BOSL"), emailText.getText(), parent.getBranchID(), dob.getValue());
                String result = "";
                    result = parent.getModel().addNewEmployee(newEmployee, "Normal");

                if(result.equals("Success")){
                    pane.getChildren().remove(pane1);
                    parent.enablePane();
                }else{
                    Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", result);
                }

            }   });
        return pane1;
    }
}
