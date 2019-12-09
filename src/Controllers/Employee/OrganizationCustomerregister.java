package Controllers.Employee;

import Helpers.Helpers;
import Models.CustomerModel;
import Objects.OrganizationCustomer;
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

public class OrganizationCustomerregister {
    private EmployeeHome parent;

    protected OrganizationCustomerregister(EmployeeHome parent){
        this.parent = parent;
    }

    protected GridPane organizationCustomerRegisterPane(BorderPane pane){

        GridPane pane1 = new GridPane();
        pane.setCenter(pane1);
        pane1.setHgap(8);
        pane1.setVgap(8);

        //Main Title
        Label mainTitle = new Label("Organization Customer Register");
        mainTitle.setFont(Font.font("System", FontWeight.BOLD, 35));
        pane1.add(mainTitle,1,0,4,1);

        //Organization Name Label
        Label organizationNameLabel = new Label("Organization Name");
        pane1.add(organizationNameLabel,1,3);

        //organization name Input Field
        TextField organizationNameText = new TextField();
        pane1.add(organizationNameText,1,4);

        //Register Number Label
        Label registerNumberLabel = new Label("Register Number");
        pane1.add(registerNumberLabel,2,3);

        //Register Number Input Field
        TextField registerNumberText = new TextField();
        pane1.add(registerNumberText,2,4);

        //Authorized Person  Label
        Label authorizedPersonLabel = new Label("Authorized Person");
        pane1.add(authorizedPersonLabel,1,5);

        //Authorized Person Input Field
        TextField authorizedPersonText = new TextField();
        pane1.add(authorizedPersonText,1,6);

        //Authorized Person  Label
        Label authorizedNICLabel = new Label("Authorized Person Nic");
        pane1.add(authorizedNICLabel,2,5);


        //Authorized Person NIC Input Field
        TextField authorizedPersonNICText = new TextField();
        pane1.add(authorizedPersonNICText,2,6);
        authorizedPersonNICText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,9}([\\.]\\d{0,2})*[V]?")) {
                    authorizedPersonNICText.setText(oldValue);
                }
            }
        });

        //Email Label
        Label emailLabel = new Label("Email");
        pane1.add(emailLabel,1,7);

        //Email Input Field
        TextField emailText = new TextField();
        pane1.add(emailText,1,8,2,1);

        //Building Number Label
        Label buildingNumberLabel = new Label("Building Number");
        pane1.add(buildingNumberLabel,1,9);

        //Building Number Input Field
        TextField buildingNumberText = new TextField();
        pane1.add(buildingNumberText,1,10);

        //Street One Label
        Label streetOneLabel = new Label("Street One");
        pane1.add(streetOneLabel,2,9);

        //Street One Input Field
        TextField streetOneText = new TextField();
        pane1.add(streetOneText,2,10);

        //Street Two Label
        Label streetTwoLabel = new Label("Street Two");
        pane1.add(streetTwoLabel,1,11);

        //Street Two Input Field
        TextField streetTwoText = new TextField();
        pane1.add(streetTwoText,1,12);

        //town Label
        Label townLabel = new Label("Town");
        pane1.add(townLabel,2,11);

        //Town Input Field
        TextField townNameText = new TextField();
        pane1.add(townNameText,2,12);

        //Postal Code Label
        Label postalCodeLabel = new Label("Postal Code");
        pane1.add(postalCodeLabel,1,13);

        //Postal Code Input Field
        TextField postalCodeText = new TextField();
        pane1.add(postalCodeText,1,14);
        postalCodeText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,5}?")) {
                    postalCodeText.setText(oldValue);
                }
            }
        });

        //Telephone Number Label
        Label phoneNumberLabel = new Label("Telephone Number");
        pane1.add(phoneNumberLabel,2,13);

        //Phone Number Input Field
        TextField phoneNumberText = new TextField();
        pane1.add(phoneNumberText,2,14);
        phoneNumberText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,10}?")) {
                    phoneNumberText.setText(oldValue);
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
        submitButton.setPrefSize(73,35);
        pane1.add(submitButton,2,17);

        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(organizationNameText.getText().isEmpty() && registerNumberText.getText().isEmpty()&& authorizedPersonNICText.getText().isEmpty() && authorizedPersonText.getText().isEmpty() && emailText.getText().isEmpty()
                        && buildingNumberText.getText().isEmpty() && streetOneText.getText().isEmpty() && phoneNumberText.getText().isEmpty() &&
                        streetTwoText.getText().isEmpty() && townNameText.getText().isEmpty() && postalCodeText.getText().isEmpty()
                ){
                    Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Please enter All the Fields");
                    return;
                }
                if(!FormValidator.nicNumberValidate(authorizedPersonNICText.getText())){
                    Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Please enter the Correct NIC Number");
                    return;
                }if(!FormValidator.emailValidate(emailText.getText())){
                    Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Please enter the Correct Email");
                    return;
                }
                if(!FormValidator.postalCodeValidate(postalCodeText.getText())){
                    Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Please enter the Correct Postal Code");
                    return;
                }
                if(!FormValidator.phoneValidate(phoneNumberText.getText())){
                    Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Please enter the Correct Phone Number");
                    return;
                }
                OrganizationCustomer customer = new OrganizationCustomer();
                customer.setCustomerType("Organization");
                customer.setEmail(emailText.getText());
                customer.setOrganizationName(organizationNameText.getText());
                customer.setRegisterNumber(registerNumberText.getText());
                customer.setAuthorizedPerson(authorizedPersonText.getText());
                customer.setAuthorizedNicNumber(authorizedPersonNICText.getText());
                customer.setBuildingNumber(buildingNumberText.getText());
                customer.setStreetOne(streetOneText.getText());
                customer.setStreetTwo(streetTwoText.getText());
                customer.setTown(townNameText.getText());
                customer.setPostalCode(postalCodeText.getText());
                customer.setTelephoneNumber(phoneNumberText.getText());
                CustomerModel model = new CustomerModel();
                if(model.addOrganizationCustomer(customer)){
                    parent.enablePane();
                    pane.getChildren().remove(pane1);
                }else{
                    Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "System Error!", "Error Occur while performing");
                }

            }
        });
        return pane1;
    }
}
