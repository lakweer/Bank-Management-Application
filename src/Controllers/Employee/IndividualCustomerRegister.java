package Controllers.Employee;

import Helpers.Helpers;
import Models.CustomerModel;
import Validator.FormValidator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import Objects.IndividualCustomer;
import java.time.LocalDate;

public class IndividualCustomerRegister {

    private EmployeeHome parent;
    private String gender = "";

    protected IndividualCustomerRegister(EmployeeHome parent){
        this.parent =parent;
    }

    protected Pane IndividualCustomerRegisterUI(Pane pane){
        Pane pane1 = new Pane();
        pane1.setPrefSize(800,750);
        pane.getChildren().add(pane1);
        pane1.relocate(269,128);

        //Main Title
        Label mainTitle = new Label("Individual Customer Register");
        mainTitle.setFont(Font.font("System", FontWeight.BOLD, 35));
        pane1.getChildren().add(mainTitle);
        mainTitle.relocate(37,14);


        //First Name Label
        Label firstNameLabel = new Label("First Name");
        firstNameLabel.setFont(Font.font("System",15));
        pane1.getChildren().add(firstNameLabel);
        firstNameLabel.relocate(37,109);

        //Last name Input Field
        TextField firstNameText = new TextField();
        firstNameText.setPrefSize(256,32);
        pane1.getChildren().add(firstNameText);
        firstNameText.relocate(37, 129);

        //Last Name Label
        Label lastNameLabel = new Label("First Name");
        lastNameLabel.setFont(Font.font("System",15));
        pane1.getChildren().add(lastNameLabel);
        lastNameLabel.relocate(374,109);

        //Last name Input Field
        TextField lastNameText = new TextField();
        lastNameText.setPrefSize(256,32);
        pane1.getChildren().add(lastNameText);
        lastNameText.relocate(374, 129);

        //Nic Label
        Label nicNoLabel = new Label("NIC No");
        nicNoLabel.setFont(Font.font("System",15));
        pane1.getChildren().add(nicNoLabel);
        nicNoLabel.relocate(37,176);

        //NIC Number Input Field
        TextField nicNoText = new TextField();
        nicNoText.setPrefSize(379,32);
        pane1.getChildren().add(nicNoText);
        nicNoText.relocate(37, 196);

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

        //House Number Label
        Label houseNumberLabel = new Label("House Number");
        houseNumberLabel.setFont(Font.font("System",15));
        pane1.getChildren().add(houseNumberLabel);
        houseNumberLabel.relocate(37,313);

        //House Number Input Field
        TextField houseNumberText = new TextField();
        houseNumberText.setPrefSize(150,32);
        pane1.getChildren().add(houseNumberText);
        houseNumberText.relocate(37, 333);

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

        //District Label
        Label districtLabel = new Label("District");
        districtLabel.setFont(Font.font("System",15));
        pane1.getChildren().add(districtLabel);
        districtLabel.relocate(243,381);

        //District Input Field
        TextField districtNameText = new TextField();
        districtNameText.setPrefSize(173,32);
        pane1.getChildren().add(districtNameText);
        districtNameText.relocate(243, 401);

        //Postal Code Label
        Label postalCodeLabel = new Label("Postal Code");
        postalCodeLabel.setFont(Font.font("System",15));
        pane1.getChildren().add(postalCodeLabel);
        postalCodeLabel.relocate(37,459);

        //Postal Code Input Field
        TextField postalCodeText = new TextField();
        postalCodeText.setPrefSize(150,32);
        pane1.getChildren().add(postalCodeText);
        postalCodeText.relocate(37, 486);


        //Gender Label
        Label genderLabel = new Label("Gender");
        genderLabel.setFont(Font.font("System",15));
        pane1.getChildren().add(genderLabel);
        genderLabel.relocate(243,459);

        //Gender Menu
        MenuItem maleType = new MenuItem("Male");
        MenuItem femaleType = new MenuItem("Female");

        MenuButton genderTypeMenu = new MenuButton("Gender", null, maleType, femaleType);
        genderTypeMenu.setFont(Font.font("System",15));
        genderTypeMenu.setPrefSize(102,35);
        genderTypeMenu.setPopupSide(Side.BOTTOM);
        pane1.getChildren().add(genderTypeMenu);
        genderTypeMenu.relocate(243,486);

        // Date Of Birth Label
        Label dobLabel = new Label("Date Of Birth");
        pane1.getChildren().add(dobLabel);
        dobLabel.relocate(37, 541);

        //ADD birthday selector
        DatePicker dob = new DatePicker(LocalDate.now());
        dob.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) > 0);
            }
        });
        dob.getEditor().setDisable(true);
        dob.setPrefSize(150,40);
        pane1.getChildren().add(dob);
        dob.relocate(37,561);


        parent.cancelButton(pane, pane1);

        Button submitButton = new Button("Submit");
        submitButton.setDefaultButton(true);
        submitButton.setPrefWidth(73);
        submitButton.setPrefHeight(35);
        pane1.getChildren().add(submitButton);
        submitButton.relocate(616,658);

        maleType.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gender = "Male";
                genderTypeMenu.setText(gender);
            }
        });

        femaleType.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gender = "Female";
                genderTypeMenu.setText(gender);
            }
        });

        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(firstNameText.getText().isEmpty() && lastNameText.getText().isEmpty() && nicNoText.getText().isEmpty() && emailText.getText().isEmpty() && houseNumberText.getText().isEmpty() && streetOneText.getText().isEmpty() &&
                    streetTwoText.getText().isEmpty() && townNameText.getText().isEmpty() && districtNameText.getText().isEmpty() && postalCodeText.getText().isEmpty()
                ){
                    Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Please enter All the Fields");
                    return;
                }
                if(!FormValidator.nicNumberValidate(nicNoText.getText())){
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
                if(gender.equals("")){
                    Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Please chose gender");
                    return;
                }
                IndividualCustomer customer = new IndividualCustomer();
                customer.setDistrict(districtNameText.getText());
                customer.setDob(dob.getValue());
                customer.setFirstName(firstNameText.getText());
                customer.setLastName(lastNameText.getText());
                customer.setGender(gender);
                customer.setPostalCode(postalCodeText.getText());
                customer.setStreetOne(streetOneText.getText());
                customer.setStreetTwo(streetTwoText.getText());
                customer.setTown(townNameText.getText());
                customer.setHouseNumber(houseNumberText.getText());
                customer.setEmail(emailText.getText());
                customer.setCustomerType("Individual");
                customer.setNic(nicNoText.getText());
                CustomerModel model = new CustomerModel();
                if(model.addIndividualCustomer(customer)){
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
