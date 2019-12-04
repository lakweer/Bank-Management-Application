package Controllers.Employee;

import Controllers.Employee.SavingsAccount.SavingsAccountOpen;
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
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.io.FileInputStream;
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
        MenuItem savingsOpenItem = new MenuItem("Open Savings Account");
        MenuItem closeSA = new MenuItem("Close Savings Account");
        MenuItem transactionSA = new MenuItem("Transaction");

        MenuButton savingsMenu = new MenuButton("Savings Account", null,savingsOpenItem, closeSA, transactionSA);
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

        MenuItem addIndividualCustomer = new MenuItem("Create Individual");
        MenuItem addOrganizationCustomer = new MenuItem("Create Organization");

        MenuButton customerManagement = new MenuButton("Customer Management", null, addIndividualCustomer, addOrganizationCustomer);
        customerManagement.setFont(Font.font("System",15));
        customerManagement.setPrefSize(170,42);
        customerManagement.setPopupSide(Side.RIGHT);
        sideAnchorPane.getChildren().add(customerManagement);
        customerManagement.relocate(14,242);

        MenuItem addNewEmployee = new MenuItem("New Employee");

        MenuButton employeeSettingsMenu = new MenuButton("Branch Employee", null, addNewEmployee);
        employeeSettingsMenu.setFont(Font.font("System",15));
        employeeSettingsMenu.setPrefSize(170,42);
        employeeSettingsMenu.setPopupSide(Side.RIGHT);
        sideAnchorPane.getChildren().add(employeeSettingsMenu);
        employeeSettingsMenu.relocate(14,582);

        addIndividualCustomer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                disablePane();
                individualCustomerCreatePane(pane);
            }
        });

        addOrganizationCustomer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                disablePane();
                organizationCustomerCreatePane(pane);
            }
        });

        addNewEmployee.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                disablePane();
                addNewEmployeePane(pane);
            }
        });

        savingsOpenItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                disablePane();
                openSavingsPane(pane);
            }
        });
    }

    private void individualCustomerCreatePane(Pane pane){
        IndividualCustomerRegister c =new  IndividualCustomerRegister(this);
        c.IndividualCustomerRegisterUI(pane);
    }

    private void openSavingsPane(Pane pane){
        SavingsAccountOpen p = new SavingsAccountOpen(this);
        p.openSavingsPane(pane);
    }

    private void organizationCustomerCreatePane(Pane pane){
        OrganizationCustomerregister c = new OrganizationCustomerregister(this);
        c.organizationCustomerRegisterPane(pane);
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
        AddNewEmplyeePane addNewEmployee = new AddNewEmplyeePane(this);
        addNewEmployee.registerNewEmployeePane(pane);
       }

    public void cancelButton(Pane pane,Pane pane1 ){
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

    protected void disablePane(){
        for (Pane p: panes) {
            p.setDisable(true);
        }
    }

    protected void enablePane(){
        for (Pane p: panes) {
            p.setDisable(false);
        }
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getBranchID() {
        return BranchID;
    }

    public void setBranchID(String branchID) {
        BranchID = branchID;
    }

    public AnchorPane[] getPanes() {
        return panes;
    }

    public void setPanes(AnchorPane[] panes) {
        this.panes = panes;
    }

    public EmployeeModel getModel() {
        return model;
    }

    public void setModel(EmployeeModel model) {
        this.model = model;
    }
}
