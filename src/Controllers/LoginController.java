package Controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    public static Stage dialogStage = new Stage();


    @FXML
    private TextField emailIdField;

    @FXML
    private PasswordField passwordIdField;

    private static String username;

    @FXML
    public void submit(ActionEvent event)  throws Exception{

        username = emailIdField.getText();
        String password = passwordIdField.getText();

        System.out.println(username);

    }


}

