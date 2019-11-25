package Controllers;

import Models.EmploeeModel;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.Window;
import Controllers.Employee.EmployeeHome;
import java.sql.SQLException;

public class LoginForm extends Application {

    private void addUIControls(Pane pane, Stage primaryStage){
        //Add Header
        Label titleLabel = new Label("ACCOUNT LOGIN");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        pane.getChildren().add(titleLabel);
        titleLabel.relocate(111,75);

        //User Name Label
        Label userNameLabel = new Label("username");
        userNameLabel.setFont(Font.font("System",15));
        pane.getChildren().add(userNameLabel);
        userNameLabel.relocate(111,122);

        //User Name Input Field
        TextField username = new TextField();
        username.setPrefHeight(34);
        username.setPrefWidth(360);
        pane.getChildren().add(username);
        username.relocate(111, 157);

        //Password Label
        Label passwordLabel = new Label("password");
        passwordLabel.setFont(Font.font("System",15));
        pane.getChildren().add(passwordLabel);
        passwordLabel.relocate(111,216);

        //Password Input Field
        PasswordField password = new PasswordField();
        password.setPrefWidth(360);
        password.setPrefHeight(34);
        pane.getChildren().add(password);
        password.relocate(111,259);

        //Forgot Password Link
        Hyperlink forGotPassword = new Hyperlink("Forgot Password?");
        forGotPassword.setFont(Font.font("System",15));
        forGotPassword.setUnderline(true);
        pane.getChildren().add(forGotPassword);
        forGotPassword.relocate(345,308);

        //Sign In Link
        Hyperlink signIn = new Hyperlink("Sign In");
        signIn.setFont(Font.font("System",15));
        signIn.setUnderline(true);
        pane.getChildren().add(signIn);
        signIn.relocate(416,349);

        //Login Button

        Button loginButton = new Button("Login");
        loginButton.setPrefHeight(34);
        loginButton.setPrefWidth(75);
        pane.getChildren().add(loginButton);
        loginButton.relocate(111,409);


        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(username.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Please enter your Username");
                    return;
                }

                if(password.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Please enter your password");
                    return;
                }
                EmploeeModel model =new  EmploeeModel();
                String[] params = {username.getText(),password.getText()};
                boolean result = false;
                try {
                   result= model.login(params);
                }catch (SQLException e){
                    e.printStackTrace();
                }
                if(result){
                    EmployeeHome home = new EmployeeHome(username.getText(),model.getEmployeeID());
                    try {
                        home.start(primaryStage);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Invalid Username Password");
                    return;
                }
            }



        });

        signIn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                EmployeeRegisterController signupForm = new EmployeeRegisterController();
                try {
                    signupForm.start(primaryStage);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login Form");
        Pane pane = new Pane();
        addUIControls(pane, primaryStage);
        Scene scene = new Scene(pane, 570, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
