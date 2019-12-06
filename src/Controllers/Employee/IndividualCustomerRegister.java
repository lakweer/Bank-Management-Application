package Controllers.Employee;

import Helpers.Helpers;
import Models.CustomerModel;
import Validator.FormValidator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
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

    protected GridPane IndividualCustomerRegisterUI(BorderPane pane){
        GridPane pane1 = new GridPane();
//        pane1.setPrefSize(800,750);
        pane.setCenter(pane1);
        pane1.setHgap(8);
        pane1.setVgap(8);
//        pane1.setPadding(new Insets(10, 30, 0, 30));

        //set Border
        pane1.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;");


        //Main Title
        Label mainTitle = new Label("Individual Customer Register");
        mainTitle.setFont(Font.font("System", FontWeight.BOLD, 35));
        pane1.add(mainTitle,1,0,2,1);



        //First Name Label
        Label firstNameLabel = new Label("First Name");
        firstNameLabel.setFont(Font.font("System",15));
        pane1.add(firstNameLabel, 1,3);


        //Last name Input Field
        TextField firstNameText = new TextField();
        firstNameText.setPrefSize(256,32);
        pane1.add(firstNameText,1,4);

        firstNameText.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-Z*")) {
                firstNameText.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
            }
        });

        //Last Name Label
        Label lastNameLabel = new Label("Last Name");
        lastNameLabel.setFont(Font.font("System",15));
        pane1.add(lastNameLabel,2,3);


        //Last name Input Field
        TextField lastNameText = new TextField();
        lastNameText.setPrefSize(256,32);
        pane1.add(lastNameText,2,4);

        lastNameText.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-Z*")) {
                lastNameText.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
            }
        });

        //Nic Label
        Label nicNoLabel = new Label("NIC No");
        nicNoLabel.setFont(Font.font("System",15));
        pane1.add(nicNoLabel,1,5);


        //NIC Number Input Field
        TextField nicNoText = new TextField();
        nicNoText.setPrefSize(379,32);
        pane1.add(nicNoText,1,6,2,1);

        nicNoText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,9}([\\.]\\d{0,2})*[V]?")) {
                    nicNoText.setText(oldValue);
                }
            }
        });

        //Email Label
        Label emailLabel = new Label("Email");
        emailLabel.setFont(Font.font("System",15));
        pane1.add(emailLabel,1,7);


        //Email Input Field
        TextField emailText = new TextField();
        emailText.setPrefSize(379,32);
        pane1.add(emailText,1,8,2,1);


        //House Number Label
        Label houseNumberLabel = new Label("House Number");
        houseNumberLabel.setFont(Font.font("System",15));
        pane1.add(houseNumberLabel,1,9);


        //House Number Input Field
        TextField houseNumberText = new TextField();
        houseNumberText.setPrefSize(150,32);
        pane1.add(houseNumberText,1,10);


        //Street One Label
        Label streetOneLabel = new Label("Street One");
        streetOneLabel.setFont(Font.font("System",15));
        pane1.add(streetOneLabel,2,9);


        //Street One Input Field
        TextField streetOneText = new TextField();
        streetOneText.setPrefSize(173,32);
        pane1.add(streetOneText,2,10);



        //Street Two Label
        Label streetTwoLabel = new Label("Street Two");
        streetTwoLabel.setFont(Font.font("System",15));
        pane1.add(streetTwoLabel,1,11);


        //Street Two Input Field
        TextField streetTwoText = new TextField();
        streetTwoText.setPrefSize(173,32);
        pane1.add(streetTwoText,1,12);



        //town Label
        Label townLabel = new Label("Town");
        townLabel.setFont(Font.font("System",15));
        pane1.add(townLabel,2,11);


        //Town Input Field
        TextField townNameText = new TextField();
        townNameText.setPrefSize(173,32);
        pane1.add(townNameText,2,12);


        //District Label
        Label districtLabel = new Label("District");
        districtLabel.setFont(Font.font("System",15));
        pane1.add(districtLabel,1,13);


        //District Input Field
        TextField districtNameText = new TextField();
        districtNameText.setPrefSize(173,32);
        pane1.add(districtNameText,1,14);


        //Postal Code Label
        Label postalCodeLabel = new Label("Postal Code");
        postalCodeLabel.setFont(Font.font("System",15));
        pane1.add(postalCodeLabel,2,13);


        //Postal Code Input Field
        TextField postalCodeText = new TextField();
        postalCodeText.setPrefSize(150,32);
        pane1.add(postalCodeText,2,14);

        postalCodeText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,5}?")) {
                    postalCodeText.setText(oldValue);
                }
            }
        });


        //Gender Label
        Label genderLabel = new Label("Gender");
        genderLabel.setFont(Font.font("System",15));
        pane1.add(genderLabel,1,15);


        //Gender Menu
        MenuItem maleType = new MenuItem("Male");
        MenuItem femaleType = new MenuItem("Female");

        MenuButton genderTypeMenu = new MenuButton("Gender", null, maleType, femaleType);
        genderTypeMenu.setFont(Font.font("System",15));
        genderTypeMenu.setPrefSize(102,35);
        genderTypeMenu.setPopupSide(Side.BOTTOM);
        pane1.add(genderTypeMenu,1,16);


        // Date Of Birth Label
        Label dobLabel = new Label("Date Of Birth");
        pane1.add(dobLabel,2,15);


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
        pane1.add(dob,2,16);



        //Cancel Button
        Button cancelButton = new Button("Cancel");
        cancelButton.setDefaultButton(true);
        cancelButton.setPrefSize(73,35);
        pane1.add(cancelButton,1,19);

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
        pane1.add(submitButton,2,19);


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
