package Controllers.Employee.OnlineCustomer;

import Controllers.Employee.EmployeeHome;
import Helpers.Helpers;
import Models.OnlineCustomerModel;
import Objects.OnlineCustomerAccount.OnlineCustomer;
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

public class onlineCustomerRegistrationForm {
    private EmployeeHome parent;
    private String gender = "";

    public onlineCustomerRegistrationForm(EmployeeHome parent){
        this.parent =parent;
    }

    public GridPane onlineCustomerRegistrationFormUI(BorderPane pane){
        GridPane pane1 = new GridPane();
        pane.setCenter(pane1);
        pane1.setHgap(8);
        pane1.setVgap(8);
        pane1.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

        //Main Title
        Label mainTitle = new Label("Online Account Register");
        mainTitle.setFont(Font.font("System", FontWeight.BOLD, 35));
        pane1.add(mainTitle,1,0,4,1);


        //Nic Label
        Label nicNoLabel = new Label("NIC No");
        nicNoLabel.setFont(Font.font("System",15));
        pane1.add(nicNoLabel,3,3);


        //NIC Number Input Field
        TextField nicNoText = new TextField();
        pane1.add(nicNoText,3,4);

        nicNoText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,9}([\\.]\\d{0,2})*[V]?")) {
                    nicNoText.setText(oldValue);
                }
            }
        });

        //Cancel Button
        Button cancelButton = new Button("Cancel");
        cancelButton.setDefaultButton(true);
        cancelButton.setPrefSize(73,35);
        pane1.add(cancelButton,2,17);

        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                parent.enablePane();
                pane.getChildren().remove(pane1);
            }
        });

        //submit button
        Button submitButton = new Button("Submit");
        submitButton.setDefaultButton(true);
        submitButton.setPrefWidth(73);
        submitButton.setPrefHeight(35);
        pane1.add(submitButton,3,17);


        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if( nicNoText.getText().isEmpty()){
                    Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Please enter All the Fields");
                    return;
                }
                if(!FormValidator.nicNumberValidate(nicNoText.getText())){
                    Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Please enter the Correct NIC Number");
                    return;
                }

                OnlineCustomer customer = new OnlineCustomer();
                customer.setUsername(nicNoText.getText());

                OnlineCustomerModel model=new OnlineCustomerModel();
                String result = model.addCustomer(customer);

                if(result=="success"){
                    Helpers.showAlert(Alert.AlertType.INFORMATION,pane.getScene().getWindow(), "OK!",result);
                    parent.enablePane();
                    pane.getChildren().remove(pane1);
                }else{
                    Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "System Error!", result);
                }

            }
        });

        return pane1;
    }

}
