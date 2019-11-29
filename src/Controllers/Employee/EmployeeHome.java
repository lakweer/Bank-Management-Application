package Controllers.Employee;

import Controllers.LoginForm;
import Helpers.Helpers;
import Validator.FormValidator;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import Objects.Employee;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import Models.EmployeeModel;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class EmployeeHome extends Application {

    private String userName;
    private String employeeID;
    private String BranchID;
    private AnchorPane panes[] =new AnchorPane[2];
    private EmployeeModel model = new EmployeeModel();

    public EmployeeHome(String userName, String employeeID, String BranchID){
        this.userName=userName;
        this.employeeID=employeeID;
        this.BranchID = BranchID;
        model.setEmployeeID(employeeID);
        model.setBranchId(BranchID);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Employee Home");

        // Create the registration form grid pane
        Pane pane = new Pane();
        // Add UI controls to the registration form grid pane
        addUIControls(pane, primaryStage);
        leftSidePane(pane, primaryStage);
        // Create a scene with registration form grid pane as the root node
        Scene scene = new Scene(pane, 1196, 892);
        // Set the scene in primary stage
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void addUIControls(Pane pane, Stage primaryStage) throws Exception {

        //Top pane
        AnchorPane topAnchorPane = new AnchorPane();
        topAnchorPane.setPrefSize(1196,88);
        pane.getChildren().add(topAnchorPane);
        topAnchorPane.relocate(0,0);
        panes[0] = topAnchorPane;

        //Home Title
        Label homeLabel = new Label("Bank Of Sri Lanka");
        homeLabel.setFont(Font.font("Cambria", FontWeight.BOLD, 35));
        topAnchorPane.getChildren().add(homeLabel);
        homeLabel.relocate(24,33);

        FileInputStream input = new FileInputStream("E:\\Bank Management Application\\src\\Views\\profile.jpg");
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(25.0);
        imageView.setFitWidth(25.0);

        //Set Settings Menu Item
        MenuItem editProfile = new MenuItem("Settings");
        MenuItem logout = new MenuItem("Logout");

        //User Menu
        MenuButton userMenu = new MenuButton(userName, imageView, editProfile, logout);
        userMenu.setFont(Font.font("System",15));
        userMenu.setPrefSize(170,30);
        topAnchorPane.getChildren().add(userMenu);
        userMenu.relocate(1000,30);


        editProfile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                disablePane();
                changeAccountSettings(pane, primaryStage);
            }
        });


        logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                LoginForm form = new LoginForm();
                form.start(primaryStage);
            }
        });
    }

    private void leftSidePane(Pane pane,Stage primaryStage){
        //Side Anchor Pane
        AnchorPane sideAnchorPane = new AnchorPane();
        sideAnchorPane.setPrefSize(207,804);
        pane.getChildren().add(sideAnchorPane);
        sideAnchorPane.relocate(0,88);
        panes[1] = sideAnchorPane;

        //Savings Account Button
        MenuItem personalSavingsOpenItem = new MenuItem("Open Savings Account");
        MenuItem closeSA = new MenuItem("Close Savings Account");
        MenuItem transactionSA = new MenuItem("Transaction");

        MenuButton savingsMenu = new MenuButton("Savings Account", null,personalSavingsOpenItem, closeSA, transactionSA);
        savingsMenu.setFont(Font.font("System",15));
        savingsMenu.setPrefSize(170,42);
        savingsMenu.setPopupSide(Side.RIGHT);
        sideAnchorPane.getChildren().add(savingsMenu);
        savingsMenu.relocate(14,40);

        //FD Account
        MenuItem openFD = new MenuItem("Open Fixed Deposit");
        MenuItem closeFD = new MenuItem("Close Fixed Deposit");

        MenuButton fdMenu = new MenuButton("Fixed Deposit", null,openFD, closeFD);
        fdMenu.setFont(Font.font("System",15));
        fdMenu.setPrefSize(170,42);
        fdMenu.setPopupSide(Side.RIGHT);
        sideAnchorPane.getChildren().add(fdMenu);
        fdMenu.relocate(14,82);

        //Current Account
        MenuItem openCA = new MenuItem("Open Current Account");
        MenuItem closeCA = new MenuItem("Close Current Account");
        MenuItem transactionCA = new MenuItem("Check Deposit");

        MenuButton caMenu = new MenuButton("Current Account", null, openCA, closeCA, transactionCA);
        caMenu.setFont(Font.font("System",15));
        caMenu.setPrefSize(170,42);
        caMenu.setPopupSide(Side.RIGHT);
        sideAnchorPane.getChildren().add(caMenu);
        caMenu.relocate(14,122);

        //Bank Loans
        MenuItem requestBL = new MenuItem("Request Bank Loan");
        MenuItem checkStatus = new MenuItem("Check Status");

        MenuButton blMenu = new MenuButton("Bank Loans", null, requestBL, checkStatus);
        blMenu.setFont(Font.font("System",15));
        blMenu.setPrefSize(170,42);
        blMenu.setPopupSide(Side.RIGHT);
        sideAnchorPane.getChildren().add(blMenu);
        blMenu.relocate(14,162);

        //Debit Card
        MenuItem issueDC = new MenuItem("Issue new card");
        MenuItem cancelDC = new MenuItem("Cancel card");

        MenuButton dcMenu = new MenuButton("Debit Card", null, issueDC, cancelDC);
        dcMenu.setFont(Font.font("System",15));
        dcMenu.setPrefSize(170,42);
        dcMenu.setPopupSide(Side.RIGHT);
        sideAnchorPane.getChildren().add(dcMenu);
        dcMenu.relocate(14,202);

        MenuItem addNewEmployee = new MenuItem("New Employee");

        MenuButton employeeSettingsMenu = new MenuButton("Branch Employee", null, addNewEmployee);
        employeeSettingsMenu.setFont(Font.font("System",15));
        employeeSettingsMenu.setPrefSize(170,42);
        employeeSettingsMenu.setPopupSide(Side.RIGHT);
        sideAnchorPane.getChildren().add(employeeSettingsMenu);
        employeeSettingsMenu.relocate(14,582);

        personalSavingsOpenItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                disablePane();
                personalSavingsOpenPane(pane);
            }
        });

        addNewEmployee.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                disablePane();
                addNewEmployeePane(pane);
            }
        });
    }

    private void personalSavingsOpenPane(Pane pane){

        Pane pane1 = new Pane();
        pane1.setPrefSize(800,750);
        pane.getChildren().add(pane1);
        pane1.relocate(269,128);

        //Main Title
        Label mainTitle = new Label("APPLICATION");
        mainTitle.setFont(Font.font("System", FontWeight.BOLD, 35));
        pane1.getChildren().add(mainTitle);
        mainTitle.relocate(281,14);

        //Sub Title
        Label subTitle = new Label("Personal Account Opening");
        subTitle.setFont(Font.font("System",20));
        pane1.getChildren().add(subTitle);
        subTitle.relocate(281,55);


        //Account Type
        Label acType = new Label("Account Type");
        acType.setFont(Font.font("System",15));
        pane1.getChildren().add(acType);
        acType.relocate(61,114);

        MenuItem acTypeOne = new MenuItem("Children's Account");
        MenuItem acTypeTwo = new MenuItem("18+ Account");

        MenuButton acTypeMenu = new MenuButton("Account Type", null, acTypeOne, acTypeTwo);
        acTypeMenu.setCache(true);
        acTypeMenu.setFont(Font.font("System",15));
        acTypeMenu.setPrefSize(144,27);
        acTypeMenu.setPopupSide(Side.BOTTOM);
        pane1.getChildren().add(acTypeMenu);
        acTypeMenu.relocate(61,162);

        //Full Name Label
        Label fullNameLabel = new Label("Full Name");
        fullNameLabel.setFont(Font.font("System",15));
        pane1.getChildren().add(fullNameLabel);
        fullNameLabel.relocate(61,204);

        //Full name Input Field
        TextField fullNameText = new TextField();
        fullNameText.setPrefSize(632,35);
        pane1.getChildren().add(fullNameText);
        fullNameText.relocate(61, 234);

        //House Name Label
        Label houseNameLabel = new Label("House Name");
        houseNameLabel.setFont(Font.font("System",15));
        pane1.getChildren().add(houseNameLabel);
        houseNameLabel.relocate(61,288);

        //House Name Input Field
        TextField houseNameText = new TextField();
        houseNameText.setPrefSize(192,35);
        pane1.getChildren().add(houseNameText);
        houseNameText.relocate(61, 318);

        //Street One Label
        Label streetOneLabel = new Label("Street One");
        streetOneLabel.setFont(Font.font("System",15));
        pane1.getChildren().add(streetOneLabel);
        streetOneLabel.relocate(281,288);

        //Street One Input Field
        TextField streetOneText = new TextField();
        streetOneText.setPrefSize(192,35);
        pane1.getChildren().add(streetOneText);
        streetOneText.relocate(281, 318);


        //Street Two Label
        Label streetTwoLabel = new Label("Street Two");
        streetTwoLabel.setFont(Font.font("System",15));
        pane1.getChildren().add(streetTwoLabel);
        streetTwoLabel.relocate(497,288);

        //Street Two Input Field
        TextField streetTwoText = new TextField();
        streetTwoText.setPrefSize(192,35);
        pane1.getChildren().add(streetTwoText);
        streetTwoText.relocate(497, 318);


        //town Label
        Label townLabel = new Label("Town");
        townLabel.setFont(Font.font("System",15));
        pane1.getChildren().add(townLabel);
        townLabel.relocate(61,365);

        //Town Input Field
        TextField townNameText = new TextField();
        townNameText.setPrefSize(192,35);
        pane1.getChildren().add(townNameText);
        townNameText.relocate(61, 395);

        //District Label
        Label districtLabel = new Label("District");
        districtLabel.setFont(Font.font("System",15));
        pane1.getChildren().add(districtLabel);
        districtLabel.relocate(281,365);

        //District Input Field
        TextField districtNameText = new TextField();
        districtNameText.setPrefSize(192,35);
        pane1.getChildren().add(districtNameText);
        districtNameText.relocate(281, 395);

        //Postal Code Label
        Label postalCodeLabel = new Label("Postal Code");
        postalCodeLabel.setFont(Font.font("System",15));
        pane1.getChildren().add(postalCodeLabel);
        postalCodeLabel.relocate(497,365);

        //Postal Code Input Field
        TextField postalCodeText = new TextField();
        postalCodeText.setPrefSize(192,35);
        pane1.getChildren().add(postalCodeText);
        postalCodeText.relocate(497, 395);


        //Gender Label
        Label genderLabel = new Label("Gender");
        genderLabel.setFont(Font.font("System",15));
        pane1.getChildren().add(genderLabel);
        genderLabel.relocate(61,456);

        //Gender Menu
        MenuItem maleType = new MenuItem("Male");
        MenuItem femaleType = new MenuItem("Female");

        MenuButton genderTypeMenu = new MenuButton("Gender", null, maleType, femaleType);
        genderTypeMenu.setFont(Font.font("System",15));
        genderTypeMenu.setPrefSize(102,35);
        acTypeMenu.setPopupSide(Side.BOTTOM);
        pane1.getChildren().add(genderTypeMenu);
        genderTypeMenu.relocate(61,485);

        //Postal Code Label
        Label nicNoLabel = new Label("NIC No");
        nicNoLabel.setFont(Font.font("System",15));
        pane1.getChildren().add(nicNoLabel);
        nicNoLabel.relocate(281,456);

        //NIC Number Input Field
        TextField nicNoText = new TextField();
        nicNoText.setPrefSize(192,35);
        pane1.getChildren().add(nicNoText);
        nicNoText.relocate(281, 485);

        //Buttons
        cancelButton(pane, pane1);
        //Submit Button
        Button submitButton = new Button("Submit");
        submitButton.setDefaultButton(true);
        submitButton.setPrefWidth(73);
        submitButton.setPrefHeight(35);
        pane1.getChildren().add(submitButton);
        submitButton.relocate(616,658);

        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                enablePane();
                pane.getChildren().remove(pane1);
            }
        });


    }

    private void changeAccountSettings(Pane pane, Stage primaryStage){
        Pane pane1 = new Pane();
        pane1.setPrefSize(800,750);
        pane.getChildren().add(pane1);
        pane1.relocate(269,128);

        //Main Title
        Label mainTitle = new Label("GENERAL ACCOUNT SETTINGS");
        mainTitle.setFont(Font.font("System", FontWeight.BOLD, 35));
        pane1.getChildren().add(mainTitle);
        mainTitle.relocate(139,38);

        //UserName Label
        Label UserNameLabel = new Label("Username");
        UserNameLabel.setFont(Font.font("System",FontWeight.BOLD,15));
        pane1.getChildren().add(UserNameLabel);
        UserNameLabel.relocate(61,189);

        //Full name Input Field
        Label employeeUsername = new Label(userName);
        employeeUsername.setFont(Font.font("System",15));
        employeeUsername.setOpacity(0.75);
        pane1.getChildren().add(employeeUsername);
        employeeUsername.relocate(179,187);

        Hyperlink userNameEdit = new Hyperlink("Edit");
        userNameEdit.setUnderline(true);
        pane1.getChildren().add(userNameEdit);
        userNameEdit.relocate(728,203);

        Hyperlink changePassword = new Hyperlink("Change Password");
        changePassword.setFont(Font.font("System",FontWeight.BOLD,20));
        pane1.getChildren().add(changePassword);
        changePassword.relocate(61,260);

        TextField userNameText = new TextField();
        userNameText.setPrefSize(399,32);
        userNameText.setText(userName);

        Button saveUsername = new Button("Save");
        saveUsername.setTextFill(Paint.valueOf("#0c0099"));

        Button cancelUsername = new Button("Cancel");

        Button cancelButton = new Button("Back");
        cancelButton.setDefaultButton(true);
        cancelButton.setPrefSize(73,35);
        pane1.getChildren().add(cancelButton);
        cancelButton.relocate(343,658);


        //Password Pane
        Pane passwordPane = new Pane();
        passwordPane.setPrefSize(365,247);

        Label changePasswordLabel = new Label("Change Password");
        changePasswordLabel.setFont(Font.font("System",FontWeight.BOLD,15));
        passwordPane.getChildren().add(changePasswordLabel);
        changePasswordLabel.relocate(14,14);

        Button passwordSaveButton = new Button("Save");
        passwordSaveButton.setTextFill(Paint.valueOf("#0c0099"));
        passwordPane.getChildren().add(passwordSaveButton);
        passwordSaveButton.relocate(14,206);

        Button passwordCancelButton = new Button("Cancel");
        passwordPane.getChildren().add(passwordCancelButton);
        passwordCancelButton.relocate(294,15);

        Label currentPasswordLabel = new Label("Current");
        passwordPane.getChildren().add(currentPasswordLabel);
        currentPasswordLabel.relocate(51,69);

        PasswordField currentPasswordField = new PasswordField();
        currentPasswordField.setPrefSize(217,27);
        passwordPane.getChildren().add(currentPasswordField);
        currentPasswordField.relocate(126,62);

        Label newPasswordLabel = new Label("New");
        passwordPane.getChildren().add(newPasswordLabel);
        newPasswordLabel.relocate(71,104);

        PasswordField newPasswordField = new PasswordField();
        newPasswordField.setPrefSize(217,27);
        passwordPane.getChildren().add(newPasswordField);
        newPasswordField.relocate(126,101);

        Label reTypePasswordLabel = new Label("Re-type new");
        passwordPane.getChildren().add(reTypePasswordLabel);
        reTypePasswordLabel.relocate(17,146);

        PasswordField reTypePasswordField = new PasswordField();
        reTypePasswordField.setPrefSize(217,27);
        passwordPane.getChildren().add(reTypePasswordField);
        reTypePasswordField.relocate(126,143);

        passwordCancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pane1.getChildren().add(changePassword);
                pane1.getChildren().remove(passwordPane);
            }
        });


        changePassword.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pane1.getChildren().remove(changePassword);
                pane1.getChildren().add(passwordPane);
                passwordPane.relocate(61,261);
            }
        });

        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                enablePane();
                pane.getChildren().remove(pane1);
            }
        });

        userNameEdit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pane1.getChildren().remove(userNameEdit);
                pane1.getChildren().remove(employeeUsername);
                pane1.getChildren().add(userNameText);
                userNameText.relocate(180,187);
                pane1.getChildren().add(cancelUsername);
                cancelUsername.relocate(706,190);
                pane1.getChildren().add(saveUsername);
                saveUsername.relocate(626,192);
            }
        });

        cancelUsername.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                userNameText.setText(userName);
                pane1.getChildren().remove(userNameText);
                pane1.getChildren().remove(saveUsername);
                pane1.getChildren().remove(cancelUsername);
                pane1.getChildren().add(userNameEdit);
                pane1.getChildren().add(employeeUsername);
            }
        });

        passwordSaveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               if(currentPasswordField.getText().isEmpty() && newPasswordField.getText().isEmpty() && reTypePasswordField.getText().isEmpty()){
                   Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Please enter All Password Fields");
                   return;
               }else{
                   String password = model.getEmployeePassword();
                   if(currentPasswordField.getText().equals(password)){
                       if(FormValidator.passwordValidate(newPasswordField.getText())){
                           if(newPasswordField.getText().equals(reTypePasswordField.getText())){
                                if(model.updateEmployeePassword(newPasswordField.getText())){
                                    pane1.getChildren().add(changePassword);
                                    pane1.getChildren().remove(passwordPane);
                                }else{
                                    Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Password Didn't Update!", "Error Occur!");
                                    return;
                                }

                           }else{
                               Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Passwords Doesn't Match");
                               return;
                           }
                       }else{
                           Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Please enter a Strong Password");
                           return;
                       }

                   }else{
                       Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Current Password Doesn't Match");
                       return;
                   }
               }
            }
        });

        saveUsername.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(userNameText.getText().isEmpty()) {
                    Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Please enter a User Name");
                    return;
                }
                if(model.isUniqueUsername(userNameText.getText())){
                    if(model.updateEmployeeUsername(userNameText.getText())){
                        userName=userNameText.getText();
                        pane1.getChildren().remove(userNameText);
                        pane1.getChildren().remove(saveUsername);
                        pane1.getChildren().remove(cancelUsername);
                        pane1.getChildren().add(userNameEdit);
                        pane1.getChildren().add(employeeUsername);
                        userNameText.setText(userName);
                        employeeUsername.setText(userName);
                    }
                }else{
                    Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Username exist!", "Please enter a new Username");
                    return;
                }
            }
        });

        }

    private void addNewEmployeePane(Pane pane){
        Pane pane1 = new Pane();
        pane1.setPrefSize(800,750);
        pane.getChildren().add(pane1);
        pane1.relocate(269,128);

        //Title
        Label headerLabel = new Label("Employee Registration Form");
        headerLabel.setFont(Font.font("System", FontWeight.BOLD, 35));
        pane1.getChildren().add(headerLabel);
        headerLabel.relocate(167,20);

        //Branch Label
        Label branchLabel = new Label("Branch :");
        pane1.getChildren().add(branchLabel);
        branchLabel.relocate(47,79);

        Label branchIDLabel = new Label(BranchID);
        pane1.getChildren().add(branchIDLabel);
        branchIDLabel.relocate(112,79);

        //Today Date Label
        Label dateLabel = new Label("Date :");
        pane1.getChildren().add(dateLabel);
        dateLabel.relocate(549,79);

        Label todayDateLabel = new Label(LocalDate.now().toString());
        pane1.getChildren().add(todayDateLabel);
        todayDateLabel.relocate(596,79);

        // First Name Label
        Label firstNameLabel = new Label("First Name");
        pane1.getChildren().add(firstNameLabel);
        firstNameLabel.relocate(47,148);

        // First Name Text Field
        TextField firstNameText = new TextField();
        firstNameText.setPrefSize(399,32);
        pane1.getChildren().add(firstNameText);
        firstNameText.relocate(201,142);

        // Last Name Label
        Label lastNameLabel = new Label("Last Name");
        pane1.getChildren().add(lastNameLabel);
        lastNameLabel.relocate(47,210);

        // Add Name Text Field
        TextField lastNameText = new TextField();
        lastNameText.setPrefSize(399,32);
        pane1.getChildren().add(lastNameText);
        lastNameText.relocate(201,204);

        // Add NIC Label
        Label nicLabel = new Label("NIC Number");
        pane1.getChildren().add(nicLabel);
        nicLabel.relocate(47,279);

        // Add NIC Text Field
        TextField nicText = new TextField();
        nicText.setPrefSize(399,32);
        pane1.getChildren().add(nicText);
        nicText.relocate(201,273);

        // Email Label
        Label emailLabel = new Label("Email");
        pane1.getChildren().add(emailLabel);
        emailLabel.relocate(47, 337);

        // Email Text Field
        TextField emailText = new TextField();
        emailText.setPrefSize(399,32);
        pane1.getChildren().add(emailText);
        emailText.relocate(201, 331);

        // Date Of Birth Label
        Label dobLabel = new Label("Date Of Birth");
        pane1.getChildren().add(dobLabel);
        dobLabel.relocate(47, 407);

        //ADD birthday selector
        DatePicker dob = new DatePicker(LocalDate.now());
        dob.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) > 0);
            }
        });
        dob.setPrefSize(150,40);
        pane1.getChildren().add(dob);
        dob.relocate(201,403);

        //Buttons
        cancelButton(pane, pane1);

        //Submit Button
        Button submitButton = new Button("Submit");
        submitButton.setDefaultButton(true);
        submitButton.setPrefSize(73,35);
        pane1.getChildren().add(submitButton);
        submitButton.relocate(616,658);


        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(firstNameText.getText().isEmpty()) {
                    Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Please enter the First Name");
                    return;
                }

                if(lastNameText.getText().isEmpty()) {
                    Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Please enter the Last Name");
                    return;
                }

                if(!nicText.getText().isEmpty()) {
                    if(!FormValidator.nicNumberValidate(nicText.getText())){
                        Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Please enter the Correct NIC Number");
                        return;
                    }
                }else{
                    Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Please enter the NIC Number");
                    return;
                }

                if(!emailText.getText().isEmpty()) {
                    if(!FormValidator.emailValidate(emailText.getText())){
                        Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Please enter the Valid Email");
                        return;
                    }
                }else{
                    Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Please enter a Email");
                    return;
                }

                if(dob.getValue().toString().isEmpty()) {
                    Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Please enter the Birth Date");
                    return;
                }

                Employee employee = new Employee(nicText.getText());
                employee.setFirstName(firstNameText.getText());
                employee.setLastName(lastNameText.getText());
                employee.setNic(nicText.getText());
                employee.setEmail(emailText.getText());
                employee.setDateOfBirth(LocalDate.parse(dob.getValue().toString()));
                model.setEmployeeID(employeeID);
                Boolean result = false;
                try {
                    result = model.addNewEmployee(employee);
                }catch (SQLException e){
                    e.printStackTrace();
                }if(result){
                    pane.getChildren().remove(pane1);
                    enablePane();
                }else{
                    Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Error When Creating the Form");
                }

    }   });}

    private void cancelButton(Pane pane,Pane pane1 ){
        //Cancel Button
        Button cancelButton = new Button("Cancel");
        cancelButton.setDefaultButton(true);
        cancelButton.setPrefSize(73,35);
        pane1.getChildren().add(cancelButton);
        cancelButton.relocate(61,658);

        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                enablePane();
                pane.getChildren().remove(pane1);
            }
        });
    }

    private void disablePane(){
        for (Pane p: panes) {
            p.setDisable(true);
        }
    }

    private void enablePane(){
        for (Pane p: panes) {
            p.setDisable(false);
        }
    }


}
