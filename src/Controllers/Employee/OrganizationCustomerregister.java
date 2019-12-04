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
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class OrganizationCustomerregister {
    private EmployeeHome parent;

    protected OrganizationCustomerregister(EmployeeHome parent){
        this.parent = parent;
    }

    protected Pane organizationCustomerRegisterPane(Pane pane){
        Pane pane1 = new Pane();
        pane1.setPrefSize(800,750);
        pane.getChildren().add(pane1);
        pane1.relocate(269,128);

        //Main Title
        Label mainTitle = new Label("Organization Customer Register");
        mainTitle.setFont(Font.font("System", FontWeight.BOLD, 35));
        pane1.getChildren().add(mainTitle);
        mainTitle.relocate(37,14);


        //Organization Name Label
        Label organizationNameLabel = new Label("Organization Name");
        organizationNameLabel.setFont(Font.font("System",15));
        pane1.getChildren().add(organizationNameLabel);
        organizationNameLabel.relocate(37,109);

        //organization name Input Field
        TextField organizationNameText = new TextField();
        organizationNameText.setPrefSize(256,32);
        pane1.getChildren().add(organizationNameText);
        organizationNameText.relocate(37, 129);

        //Register Number Label
        Label registerNumberLabel = new Label("Register Number");
        registerNumberLabel.setFont(Font.font("System",15));
        pane1.getChildren().add(registerNumberLabel);
        registerNumberLabel.relocate(374,109);

        //Register Number Input Field
        TextField registerNumberText = new TextField();
        registerNumberText.setPrefSize(256,32);
        pane1.getChildren().add(registerNumberText);
        registerNumberText.relocate(374, 129);

        //Authorized Person  Label
        Label authorizedPersonLabel = new Label("Authorized Person");
        authorizedPersonLabel.setFont(Font.font("System",15));
        pane1.getChildren().add(authorizedPersonLabel);
        authorizedPersonLabel.relocate(37,176);

        //Authorized Person Input Field
        TextField authorizedPersonText = new TextField();
        authorizedPersonText.setPrefSize(256,32);
        pane1.getChildren().add(authorizedPersonText);
        authorizedPersonText.relocate(37, 196);

        //Authorized Person  Label
        Label authorizedNICLabel = new Label("Authorized Person Nic");
        authorizedNICLabel.setFont(Font.font("System",15));
        pane1.getChildren().add(authorizedNICLabel);
        authorizedNICLabel.relocate(374,176);

        //Authorized Person NIC Input Field
        TextField authorizedPersonNICText = new TextField();
        authorizedPersonNICText.setPrefSize(256,32);
        pane1.getChildren().add(authorizedPersonNICText);
        authorizedPersonNICText.relocate(374, 196);
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
        emailLabel.setFont(Font.font("System",15));
        pane1.getChildren().add(emailLabel);
        emailLabel.relocate(37,241);

        //Email Input Field
        TextField emailText = new TextField();
        emailText.setPrefSize(379,32);
        pane1.getChildren().add(emailText);
        emailText.relocate(37, 265);

        //Building Number Label
        Label buildingNumberLabel = new Label("Building Number");
        buildingNumberLabel.setFont(Font.font("System",15));
        pane1.getChildren().add(buildingNumberLabel);
        buildingNumberLabel.relocate(37,313);

        //Building Number Input Field
        TextField buildingNumberText = new TextField();
        buildingNumberText.setPrefSize(150,32);
        pane1.getChildren().add(buildingNumberText);
        buildingNumberText.relocate(37, 333);

        //Street One Label
        Label streetOneLabel = new Label("Street One");
        streetOneLabel.setFont(Font.font("System",15));
        pane1.getChildren().add(streetOneLabel);
        streetOneLabel.relocate(243,313);

        //Street One Input Field
        TextField streetOneText = new TextField();
        streetOneText.setPrefSize(173,32);
        pane1.getChildren().add(streetOneText);
        streetOneText.relocate(243, 333);


        //Street Two Label
        Label streetTwoLabel = new Label("Street Two");
        streetTwoLabel.setFont(Font.font("System",15));
        pane1.getChildren().add(streetTwoLabel);
        streetTwoLabel.relocate(455,313);

        //Street Two Input Field
        TextField streetTwoText = new TextField();
        streetTwoText.setPrefSize(173,32);
        pane1.getChildren().add(streetTwoText);
        streetTwoText.relocate(455, 333);


        //town Label
        Label townLabel = new Label("Town");
        townLabel.setFont(Font.font("System",15));
        pane1.getChildren().add(townLabel);
        townLabel.relocate(37,381);

        //Town Input Field
        TextField townNameText = new TextField();
        townNameText.setPrefSize(173,32);
        pane1.getChildren().add(townNameText);
        townNameText.relocate(37, 401);


        //Postal Code Label
        Label postalCodeLabel = new Label("Postal Code");
        postalCodeLabel.setFont(Font.font("System",15));
        pane1.getChildren().add(postalCodeLabel);
        postalCodeLabel.relocate(243,381);

        //Postal Code Input Field
        TextField postalCodeText = new TextField();
        postalCodeText.setPrefSize(150,32);
        pane1.getChildren().add(postalCodeText);
        postalCodeText.relocate(243, 401);
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
        phoneNumberLabel.setFont(Font.font("System",15));
        pane1.getChildren().add(phoneNumberLabel);
        phoneNumberLabel.relocate(37,459);

        //Phone Number Input Field
        TextField phoneNumberText = new TextField();
        phoneNumberText.setPrefSize(216,32);
        pane1.getChildren().add(phoneNumberText);
        phoneNumberText.relocate(37, 486);
        phoneNumberText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,10}?")) {
                    phoneNumberText.setText(oldValue);
                }
            }
        });


        parent.cancelButton(pane, pane1);

        Button submitButton = new Button("Submit");
        submitButton.setDefaultButton(true);
        submitButton.setPrefWidth(73);
        submitButton.setPrefHeight(35);
        pane1.getChildren().add(submitButton);
        submitButton.relocate(616,658);

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
