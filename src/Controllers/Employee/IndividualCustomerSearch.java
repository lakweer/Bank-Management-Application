package Controllers.Employee;

import Helpers.Helpers;
import Models.CustomerModel;
import Objects.IndividualCustomer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.time.LocalDate;

public class IndividualCustomerSearch {

    private EmployeeHome parent;

    protected IndividualCustomerSearch(EmployeeHome parent){
        this.parent =parent;
    }

    protected GridPane IndividualCustomerSearchUI(BorderPane pane){
        GridPane pane1 = new GridPane();
        pane.setCenter(pane1);
        pane1.setHgap(8);
        pane1.setVgap(8);
        pane1.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

        //Main Title
        Label mainTitle = new Label("Individual Customer Search");
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
        pane1.add(cancelButton,1,17);

        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                parent.enablePane();
                pane.getChildren().remove(pane1);
            }
        });

        Button submitButton = new Button("Submit");
        submitButton.setDefaultButton(true);
        submitButton.setPrefWidth(73);
        submitButton.setPrefHeight(35);
        pane1.add(submitButton,2,17);



        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if( nicNoText.getText().isEmpty() ){
                    Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Please enter the NIC");
                    return;
                }

                IndividualCustomer customer = new IndividualCustomer();
//                customer.setDistrict(districtNameText.getText());
//                customer.setDob(dob.getValue());
//                customer.setFirstName(firstNameText.getText());
//                customer.setLastName(lastNameText.getText());
//                customer.setGender(gender);
//                customer.setPostalCode(postalCodeText.getText());
//                customer.setStreetOne(streetOneText.getText());
//                customer.setStreetTwo(streetTwoText.getText());
//                customer.setTown(townNameText.getText());
//                customer.setHouseNumber(houseNumberText.getText());
//                customer.setEmail(emailText.getText());
//                customer.setCustomerType("Individual");
                customer.setNic(nicNoText.getText());
//                CustomerModel model = new CustomerModel();
//                if(model.addIndividualCustomer(customer)){
//                    parent.enablePane();
//                    pane.getChildren().remove(pane1);
//                }else{
//                    Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "System Error!", "Error Occur while performing");
//                }

            }
        });

        return pane1;
    }
}
