package Controllers.Employee;


import Controllers.LoginForm;
import Models.EmployeeModel;
import Objects.Employee;
import Validator.FormValidator;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.Window;
import Helpers.Helpers;


import java.sql.SQLException;
import java.time.LocalDate;


public class EmployeeRegisterController extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Employee Registration Form");

        // Create the registration form grid pane
        Pane pane = new Pane();
        // Add UI controls to the registration form grid pane
        addUIControls(pane, primaryStage);
        // Create a scene with registration form grid pane as the root node
        Scene scene = new Scene(pane, 800, 800);
        // Set the scene in primary stage
        primaryStage.setScene(scene);

        primaryStage.show();
    }


    private void addUIControls(Pane pane, Stage primaryStage) {
        // Add Header
        Label headerLabel = new Label("Employee Registration Form");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 25));
        headerLabel.setTextFill(Paint.valueOf("#ff7a00"));
        pane.getChildren().add(headerLabel);
        headerLabel.relocate(212,35);

        // Add Name Label
        Label nameLabel = new Label("First Name : ");
        pane.getChildren().add(nameLabel);
        nameLabel.relocate(45,146);

        // Add Name Text Field
        TextField firstName = new TextField();
        firstName.setPrefWidth(399);
        firstName.setPrefHeight(32);
        pane.getChildren().add(firstName);
        firstName.relocate(234,140);

        // Add Name Label
        Label nameLabel2 = new Label("Last Name : ");
        pane.getChildren().add(nameLabel2);
        nameLabel2.relocate(45,216);

        // Add Name Text Field
        TextField lastName = new TextField();
        lastName.setPrefWidth(399);
        lastName.setPrefHeight(32);
        pane.getChildren().add(lastName);
        lastName.relocate(234,210);

       // Add NIC Label
        Label nicLabel = new Label("National ID Number : ");
        pane.getChildren().add(nicLabel);
        nicLabel.relocate(45,289);

        // Add NIC Text Field
        TextField nic = new TextField();
        nic.setPrefHeight(32);
        nic.setPrefWidth(399);
        pane.getChildren().add(nic);
        nic.relocate(234,276);


        // Add Username Label
        Label usernameLabel = new Label("Username : ");
        pane.getChildren().add(usernameLabel);
        usernameLabel.relocate(45, 347);

        // Add Username Text Field
        TextField username = new TextField();
        username.setPrefHeight(32);
        username.setPrefWidth(399);
        pane.getChildren().add(username);
        username.relocate(234, 341);

        // Add Password Label
        Label passwordLabel = new Label("Password : ");
        pane.getChildren().add(passwordLabel);
        passwordLabel.relocate(45,417);

        // Add Password Field
        PasswordField password = new PasswordField();
        password.setPrefHeight(32);
        password.setPrefWidth(399);
        pane.getChildren().add(password);
        password.relocate(234, 411);

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
        pane.getChildren().add(dob);
        dob.relocate(234,500);

        // Add Submit Button
        Button submitButton = new Button("Submit");
        submitButton.setDefaultButton(true);
        submitButton.setPrefWidth(100);
        submitButton.setPrefHeight(42);
        submitButton.setTextFill(Paint.valueOf("#ff7a00"));
        pane.getChildren().add(submitButton);
        submitButton.relocate(318,720);


        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(firstName.getText().isEmpty()) {
                    Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Please enter your First Name");
                    return;
                }
                
                if(lastName.getText().isEmpty()) {
                    Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Please enter your Last Name");
                    return;
                }
                
                if(!nic.getText().isEmpty()) {
                   if(!FormValidator.nicNumberValidate(nic.getText())){
                       Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Please enter your Correct NIC Number");
                       return;
                   }
                }else{
                    Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Please enter your NIC Number");
                    return;
                }
                
                if(username.getText().isEmpty()) {
                    Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Please enter a Username");
                    return;
                }
                
                if(!password.getText().isEmpty()) {
                    if(!FormValidator.passwordValidate(password.getText())){
                        Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Please enter a Strong password");
                        return;
                    }
                }else{
                    Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Please enter a password");
                    return;
                }

//                Employee employee = new Employee(username.getText());
//                EmployeeModel model = new EmployeeModel();
//                Boolean result = false;
//                try {
//                    result = model.addNewEmployee(employee);
//                }catch (SQLException e){
//                    e.printStackTrace();
//                }if(result){
//                    LoginForm form = new LoginForm();
//                    form.start(primaryStage);
//                }else{
//                    showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Username exist");
//                }
            }



        });
   }
}