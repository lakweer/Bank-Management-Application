package Controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Models.EmploeeModel;


public class LoginController {

    public static Stage dialogStage = new Stage();


    @FXML
    private TextField emailIdField;

    @FXML
    private PasswordField passwordIdField;

    private static String username;

    @FXML
    public void submit(ActionEvent event)  throws Exception{

        EmploeeModel model = new EmploeeModel();
        String params[] = {emailIdField.getText(), passwordIdField.getText()};
        model.login(params);
    }


}

