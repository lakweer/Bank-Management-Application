package Controllers.Employee.CurrentAccount;

import Controllers.Employee.EmployeeHome;
import Helpers.Helpers;
import Models.SavingsAccountModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.time.LocalDate;



public class CurrentAccountClose {
    private EmployeeHome parent;
    private SavingsAccountModel savingsModel;

    public  CurrentAccountClose(EmployeeHome parent){
        this.parent=parent;
        this.savingsModel = new SavingsAccountModel();
    }
    public GridPane closeCurrentPane(BorderPane pane) {
        GridPane pane1 =new GridPane();
        pane.setCenter(pane1);
        pane1.setHgap(10);
        pane1.setVgap(10);
        pane1.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

        //Title
        Label headerLabel = new Label("Savings Account Close");
        headerLabel.setFont(Font.font("System", FontWeight.BOLD, 35));
        pane1.add(headerLabel, 2, 1, 4, 1);

        //Branch Label
        Label branchLabel = new Label("Employee : " + parent.getUserName());
        pane1.add(branchLabel, 1, 2, 2, 1);

        //Today Date Label
        Label dateLabel = new Label("Date : " + LocalDate.now().toString());
        pane1.setHalignment(dateLabel, HPos.RIGHT);
        pane1.add(dateLabel, 4, 2);

        Label accNumberLabel = new Label("Account Number : ");
        pane1.add(accNumberLabel,1,4,2,1);

        TextField accnumberText = new TextField();
        pane1.add(accnumberText,1,5,4,1);
        pane1.setHalignment(accnumberText, HPos.CENTER);

        Button cancelButton = new Button("Back");
        cancelButton.setDefaultButton(true);
        cancelButton.setPrefSize(73,35);
        pane1.add(cancelButton,1,19);
        pane1.setHalignment(cancelButton, HPos.CENTER);
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                parent.enablePane();
                pane.getChildren().remove(pane1);
            }
        });

        Button submitButton = new Button("Submit");
        submitButton.setDefaultButton(true);
        submitButton.setPrefSize(73,35);
        pane1.add(submitButton,5,19);
        pane1.setHalignment(submitButton, HPos.CENTER);

        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if(!(accnumberText.getText().trim().equals(""))){

                    String result = savingsModel.close(accnumberText.getText(), LocalDate.now().toString(), parent.getEmployeeID() );
                    if(result.equals("Success")){
                        Helpers.showAlert(Alert.AlertType.CONFIRMATION, pane.getScene().getWindow(), "Success!", "Account Closed");
                        parent.enablePane();
                        pane.getChildren().remove(pane1);
                    }else{
                        Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", result);
                        return;
                    }
                }
                else{
                    Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Fill all the fields!");
                    return;
                }
            }
        });

        return pane1;
    }

}
