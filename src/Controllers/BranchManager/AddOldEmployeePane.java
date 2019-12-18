package Controllers.BranchManager;

import Helpers.Helpers;
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

public class AddOldEmployeePane {
    private BranchMangerHome parent;

    public   AddOldEmployeePane(BranchMangerHome parent){
        this.parent =parent;
    }
    public GridPane addOldEmployeePane(BorderPane pane){
        GridPane pane1 = new GridPane();
        pane.setCenter(pane1);
        pane1.setHgap(10);
        pane1.setVgap(10);
        pane1.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

        //Title
        Label headerLabel = new Label("Add Existing Employee");
        headerLabel.setFont(Font.font("System", FontWeight.BOLD, 35));
        pane1.add(headerLabel,1,1,3,1);

        //Branch Label
        Label branchLabel = new Label("Branch : " + parent.getBranchID());
        pane1.add(branchLabel,1,2,2,1);

        //Today Date Label
        Label dateLabel = new Label("Date : " + LocalDate.now().toString());
        pane1.add(dateLabel,3,2);

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

                if(!nicText.getText().isEmpty()) {
                    if(!FormValidator.nicNumberValidate(nicText.getText())){
                        Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Please enter the Correct NIC Number");
                        return;
                    }
                }else{
                    Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Please enter the NIC Number");
                    return;
                }
                String result = "";
                result = parent.getModel().addExistingEmployeeToBranch(nicText.getText());

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
