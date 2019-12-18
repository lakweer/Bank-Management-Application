package Controllers.BranchManager;

import Controllers.LoginForm;
import Models.BranchManagerModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class BranchMangerHome {

    private String userName;
    private String employeeID;
    private String BranchID;
    private String managerId;
    private Pane panes[] =new Pane[2];
    private BranchManagerModel model = new BranchManagerModel();

    public BranchMangerHome(String userName, String employeeID, String BranchID){
        this.userName=userName;
        this.employeeID=employeeID;
        this.BranchID = BranchID;
        this.managerId = model.getBranchMangerId();
        model.setEmployeeID(employeeID);
        model.setBranchId(BranchID);
    }

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Branch Manager Home");

        BorderPane pane = new BorderPane();
        addUIControls(pane, primaryStage);
        leftSidePane(pane, primaryStage);
        Scene scene = new Scene(pane);
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

        //Bank Loans
        MenuItem requestBL = new MenuItem("Request Bank Loan");
        MenuItem checkStatus = new MenuItem("Check Status");

        MenuButton blMenu = new MenuButton("Bank Loans", null, requestBL, checkStatus);
        blMenu.setFont(Font.font("System",15));
        blMenu.setPrefSize(170,42);
        blMenu.setPopupSide(Side.RIGHT);
        sideAnchorPane.getChildren().add(blMenu);


        MenuItem addNewEmployee = new MenuItem("Add New Employee");
        MenuItem viewEmployees = new MenuItem("View Employees");
        MenuItem removeEmployee = new MenuItem("Remove Employee");
        MenuItem  addOldEmployee = new MenuItem("Add Old Employee");

        MenuButton employeeSettingsMenu = new MenuButton("Employee Management", null, addNewEmployee, viewEmployees, removeEmployee, addOldEmployee);
        employeeSettingsMenu.setFont(Font.font("System",15));
        employeeSettingsMenu.setPrefSize(170,42);
        employeeSettingsMenu.setPopupSide(Side.RIGHT);
        sideAnchorPane.getChildren().add(employeeSettingsMenu);

        addNewEmployee.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                disablePane();
                addNewEmployeePane(pane);
            }
        });

        viewEmployees.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                disablePane();
                viewCurrentEmployeesPane(pane);
            }
        });

        removeEmployee.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                disablePane();
                removeEmployeePane(pane);
            }
        });

        addOldEmployee.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                disablePane();
                addOldEmployeePane(pane);
            }
        });


    }

    private void viewCurrentEmployeesPane(BorderPane pane){
        ViewCurrentEmployees p = new ViewCurrentEmployees(this);
        p.viewCurrentEmployeesPane(pane);
    }

    private void addOldEmployeePane(BorderPane pane) {
        AddOldEmployeePane p = new AddOldEmployeePane(this);
        p.addOldEmployeePane(pane);
    }

    private void removeEmployeePane(BorderPane pane){
        RemoveEmployeePane p = new RemoveEmployeePane(this);
        p.removeEmployeePane(pane);
    }

    private void addNewEmployeePane(BorderPane pane){
        AddNewEmplyeePane addNewEmployee = new AddNewEmplyeePane(this);
        addNewEmployee.registerNewEmployeePane(pane);
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

    public String getBranchID() {
        return BranchID;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public BranchManagerModel getModel() {
        return model;
    }


    private void changeAccountSettings(Pane pane, Stage primaryStage) {
        Pane pane1 = new Pane();
        pane1.setPrefSize(800, 750);
        pane.getChildren().add(pane1);
        pane1.relocate(269, 128);

        //Main Title
        Label mainTitle = new Label("GENERAL ACCOUNT SETTINGS");
        mainTitle.setFont(Font.font("System", FontWeight.BOLD, 35));
        pane1.getChildren().add(mainTitle);
        mainTitle.relocate(139, 38);

        //UserName Label
        Label UserNameLabel = new Label("Username");
        UserNameLabel.setFont(Font.font("System", FontWeight.BOLD, 15));
        pane1.getChildren().add(UserNameLabel);
        UserNameLabel.relocate(61, 189);

        //Full name Input Field
        Label employeeUsername = new Label(userName);
        employeeUsername.setFont(Font.font("System", 15));
        employeeUsername.setOpacity(0.75);
        pane1.getChildren().add(employeeUsername);
        employeeUsername.relocate(179, 187);

        Hyperlink userNameEdit = new Hyperlink("Edit");
        userNameEdit.setUnderline(true);
        pane1.getChildren().add(userNameEdit);
        userNameEdit.relocate(728, 203);

        Hyperlink changePassword = new Hyperlink("Change Password");
        changePassword.setFont(Font.font("System", FontWeight.BOLD, 20));
        pane1.getChildren().add(changePassword);
        changePassword.relocate(61, 260);

        TextField userNameText = new TextField();
        userNameText.setPrefSize(399, 32);
        userNameText.setText(userName);

        Button saveUsername = new Button("Save");
        saveUsername.setTextFill(Paint.valueOf("#0c0099"));

        Button cancelUsername = new Button("Cancel");

        Button cancelButton = new Button("Back");
        cancelButton.setDefaultButton(true);
        cancelButton.setPrefSize(73, 35);
        pane1.getChildren().add(cancelButton);
        cancelButton.relocate(343, 658);


        //Password Pane
        Pane passwordPane = new Pane();
        passwordPane.setPrefSize(365, 247);

        Label changePasswordLabel = new Label("Change Password");
        changePasswordLabel.setFont(Font.font("System", FontWeight.BOLD, 15));
        passwordPane.getChildren().add(changePasswordLabel);
        changePasswordLabel.relocate(14, 14);

        Button passwordSaveButton = new Button("Save");
        passwordSaveButton.setTextFill(Paint.valueOf("#0c0099"));
        passwordPane.getChildren().add(passwordSaveButton);
        passwordSaveButton.relocate(14, 206);

        Button passwordCancelButton = new Button("Cancel");
        passwordPane.getChildren().add(passwordCancelButton);
        passwordCancelButton.relocate(294, 15);

        Label currentPasswordLabel = new Label("Current");
        passwordPane.getChildren().add(currentPasswordLabel);
        currentPasswordLabel.relocate(51, 69);

        PasswordField currentPasswordField = new PasswordField();
        currentPasswordField.setPrefSize(217, 27);
        passwordPane.getChildren().add(currentPasswordField);
        currentPasswordField.relocate(126, 62);

        Label newPasswordLabel = new Label("New");
        passwordPane.getChildren().add(newPasswordLabel);
        newPasswordLabel.relocate(71, 104);

        PasswordField newPasswordField = new PasswordField();
        newPasswordField.setPrefSize(217, 27);
        passwordPane.getChildren().add(newPasswordField);
        newPasswordField.relocate(126, 101);

        Label reTypePasswordLabel = new Label("Re-type new");
        passwordPane.getChildren().add(reTypePasswordLabel);
        reTypePasswordLabel.relocate(17, 146);

        PasswordField reTypePasswordField = new PasswordField();
        reTypePasswordField.setPrefSize(217, 27);
        passwordPane.getChildren().add(reTypePasswordField);
        reTypePasswordField.relocate(126, 143);

        passwordCancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pane1.getChildren().add(changePassword);
                pane1.getChildren().remove(passwordPane);
            }
        });

    }
}
