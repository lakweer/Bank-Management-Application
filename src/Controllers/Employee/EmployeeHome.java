package Controllers.Employee;

import Controllers.BranchManager.AddNewEmplyeePane;
import Controllers.Employee.CurrentAccount.CurrentAccountClose;
import Controllers.Employee.CurrentAccount.CurrentAccountOpen;
import Controllers.Employee.CurrentAccount.CurrentTransactionPane;
import Controllers.Employee.DebitCard.CancelDebitCard;
import Controllers.Employee.DebitCard.OpenDebitCard;
import Controllers.Employee.FixedDeposit.FixedDepositOpen;
import Controllers.Employee.Loans.CheckStatus;
import Controllers.Employee.Loans.InstallmentSettlement;
import Controllers.Employee.Loans.RequestLoan;
import Controllers.Employee.Loans.RequestOrgLoan;
import Controllers.Employee.SavingsAccount.SavingsAccountClose;
import Controllers.Employee.SavingsAccount.SavingsAccountOpen;
import Controllers.Employee.SavingsAccount.SavingsTransactionPane;
import Controllers.LoginForm;
import Helpers.Helpers;
import Validator.FormValidator;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.FileInputStream;
import Models.EmployeeModel;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class EmployeeHome extends Application {

    private String userName;
    private String employeeID;
    private String BranchID;
    private Pane panes[] =new Pane[2];
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
        BorderPane pane = new BorderPane();
        // Add UI controls to the registration form grid pane
        addUIControls(pane, primaryStage);
        leftSidePane(pane, primaryStage);
        // Create a scene with registration form grid pane as the root node
        Scene scene = new Scene(pane);
        // Set the scene in primary stage
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setScene(scene);
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());
        primaryStage.setMaxWidth(bounds.getWidth() * 2);
        primaryStage.show();
    }

    private void addUIControls(BorderPane pane, Stage primaryStage) throws Exception {

        //Top pane
        HBox topAnchorPane = new HBox();
        topAnchorPane.setPrefSize(1196,88);
        pane.setTop(topAnchorPane);
        panes[0] = topAnchorPane;
        Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);

        //Home Title
        Label homeLabel = new Label("Bank Of Sri Lanka");
        homeLabel.setFont(Font.font("Cambria", FontWeight.BOLD, 35));

        String localDir = System.getProperty("user.dir");
        FileInputStream input = new FileInputStream(localDir+"//src//Views//profile.jpg");
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

        topAnchorPane.getChildren().addAll(homeLabel,region1,userMenu);

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

    private void leftSidePane(BorderPane pane,Stage primaryStage){
        VBox sideAnchorPane = new VBox();
        pane.setLeft(sideAnchorPane);
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

        //FD Account
        MenuItem openFD = new MenuItem("Open Fixed Deposit");
        MenuItem closeFD = new MenuItem("Close Fixed Deposit");

        MenuButton fdMenu = new MenuButton("Fixed Deposit", null,openFD, closeFD);
        fdMenu.setFont(Font.font("System",15));
        fdMenu.setPrefSize(170,42);
        fdMenu.setPopupSide(Side.RIGHT);
        sideAnchorPane.getChildren().add(fdMenu);

        //Current Account
        MenuItem openCA = new MenuItem("Open Current Account");
        MenuItem closeCA = new MenuItem("Close Current Account");
        MenuItem transactionCA = new MenuItem("Current Transaction");

        MenuButton caMenu = new MenuButton("Current Account", null, openCA, closeCA, transactionCA);
        caMenu.setFont(Font.font("System",15));
        caMenu.setPrefSize(170,42);
        caMenu.setPopupSide(Side.RIGHT);
        sideAnchorPane.getChildren().add(caMenu);


        //Bank Loans
        MenuItem requestIndividualBL = new MenuItem("Request Individual Bank Loan");
        MenuItem requestOrgBL = new MenuItem("Request Organization Bank Loan");
        MenuItem checkStatus = new MenuItem("Check Status");
        MenuItem installmentSettlement = new MenuItem("Installment Settlement");

        MenuButton blMenu = new MenuButton("Bank Loans", null, requestIndividualBL, requestOrgBL, checkStatus,installmentSettlement);
        blMenu.setFont(Font.font("System", 15));
        blMenu.setPrefSize(170, 42);
        blMenu.setPopupSide(Side.RIGHT);
        sideAnchorPane.getChildren().add(blMenu);


        //Debit Card
        MenuItem issueDC = new MenuItem("Issue new card");
        MenuItem cancelDC = new MenuItem("Cancel card");

        MenuButton dcMenu = new MenuButton("Debit Card", null, issueDC, cancelDC);
        dcMenu.setFont(Font.font("System",15));
        dcMenu.setPrefSize(170,42);
        dcMenu.setPopupSide(Side.RIGHT);
        sideAnchorPane.getChildren().add(dcMenu);


        MenuItem addIndividualCustomer = new MenuItem("Create Individual");
        MenuItem addOrganizationCustomer = new MenuItem("Create Organization");
        MenuItem SearchOrganizationCustomer = new MenuItem("Search Organization");
        MenuItem SearchIndividualCustomer = new MenuItem("Search Individual");

        MenuButton customerManagement = new MenuButton("Customer Management", null, addIndividualCustomer, addOrganizationCustomer,SearchOrganizationCustomer,SearchIndividualCustomer);
        customerManagement.setFont(Font.font("System",15));
        customerManagement.setPrefSize(170,42);
        customerManagement.setPopupSide(Side.RIGHT);
        sideAnchorPane.getChildren().add(customerManagement);

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

        SearchOrganizationCustomer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                disablePane();
                SearchOrganizationCustomerPane(pane);
            }
        });

        SearchIndividualCustomer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                disablePane();
                SearchIndividualCustomerPane(pane);
            }
        });

        savingsOpenItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                disablePane();
                openSavingsPane(pane);
            }
        });

        closeSA.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                disablePane();
                closeSavingsPane(pane);
            }
        });

        transactionSA.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                disablePane();
                savingsTransactionPane(pane);
            }
        });

        openFD.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                disablePane();
                fdOpenPane(pane);
            }
        });

        requestIndividualBL.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                disablePane();
                requestLoanPane(pane);
            }
        });
        issueDC.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                disablePane();
                openDebitCardPane(pane);
            }
        });

        cancelDC.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                disablePane();
                cancelDebitCardPane(pane);
            }
        });
        openCA.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                disablePane();
                openCurrentPane(pane);
            }
        });

        closeCA.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                disablePane();
                closeCurrentPane(pane);
            }
        });

        requestOrgBL.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                disablePane();
                requestOrgLoanPane(pane);
            }
        });

        checkStatus.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                disablePane();
                checkStatusPane(pane);
            }
        });

        installmentSettlement.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                disablePane();
                installmentSettlementPane(pane);
            }
        });

        transactionCA.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                disablePane();
                transactionCurrentPane(pane);
            }
        });


    }

    private void transactionCurrentPane(BorderPane pane){
        CurrentTransactionPane ca = new CurrentTransactionPane(this);
        ca.TransactionCurrentPane(pane);
    }

    private  void requestOrgLoanPane(BorderPane pane){
        RequestOrgLoan orgLoan = new RequestOrgLoan(this);
        orgLoan.requestOrgLoanPane(pane);
    }

    private  void checkStatusPane(BorderPane pane){
        CheckStatus check = new CheckStatus(this);
        check.checkStatusPane(pane);
    }

    private  void installmentSettlementPane(BorderPane pane){
        InstallmentSettlement check = new InstallmentSettlement(this);
        check.installmentSettlementPane(pane);
    }


    private void openCurrentPane(BorderPane pane){
        CurrentAccountOpen ca = new CurrentAccountOpen(this);
        ca.openCurrentPane(pane);
    }

    private void closeCurrentPane(BorderPane pane){
        CurrentAccountClose ca = new CurrentAccountClose(this);
        ca.closeCurrentPane(pane);
    }


    private void openDebitCardPane(BorderPane pane){
        OpenDebitCard card = new OpenDebitCard(this);
        card.openDebitCardPane(pane);
    }


    private void cancelDebitCardPane(BorderPane pane){
        CancelDebitCard card = new CancelDebitCard(this);
        card.cancelDebitCardPane(pane);
    }

    private void fdOpenPane(BorderPane pane){
        FixedDepositOpen open = new FixedDepositOpen(this);
        open.fdOpenPane(pane);
    }

    private  void requestLoanPane(BorderPane pane){
        RequestLoan loan = new RequestLoan(this);
        loan.requestLoanPane(pane);
    }
    private void individualCustomerCreatePane(BorderPane pane){
        IndividualCustomerRegister c =new  IndividualCustomerRegister(this);
        c.IndividualCustomerRegisterUI(pane);
    }

    private void openSavingsPane(BorderPane pane){
        SavingsAccountOpen p = new SavingsAccountOpen(this);
        p.openSavingsPane(pane);
    }

    private void closeSavingsPane(BorderPane pane){
        SavingsAccountClose p = new SavingsAccountClose(this);
        p.closeSavingsPane(pane);
    }

    private void savingsTransactionPane(BorderPane pane){
        SavingsTransactionPane ta = new SavingsTransactionPane(this);
        ta.savingsTransactionPane(pane);
    }

    private void organizationCustomerCreatePane(BorderPane pane){
        OrganizationCustomerregister c = new OrganizationCustomerregister(this);
        c.organizationCustomerRegisterPane(pane);
    }

    private void SearchOrganizationCustomerPane(BorderPane pane){
        OrganizationCustomerSearch c= new OrganizationCustomerSearch(this);
        c.OrganizationCustomerSearchUI(pane);
    }

    private void SearchIndividualCustomerPane(BorderPane pane){
        IndividualCustomerSearch c = new IndividualCustomerSearch(this);
        c.IndividualCustomerSearchUI(pane);
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


    public void disablePane(){
        for (Pane p: panes) {
            p.setDisable(true);
        }
    }

    public void enablePane(){
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

    public Pane[] getPanes() {
        return panes;
    }

    public void setPanes(Pane[] panes) {
        this.panes = panes;
    }

    public EmployeeModel getModel() {
        return model;
    }

    public void setModel(EmployeeModel model) {
        this.model = model;
    }
}
