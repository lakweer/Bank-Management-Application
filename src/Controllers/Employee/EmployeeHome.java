package Controllers.Employee;

import Controllers.LoginForm;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.io.FileInputStream;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class EmployeeHome extends Application {

    private String userName;
    private String employeeID;
    private AnchorPane panes[] =new AnchorPane[2];

    public EmployeeHome(String userName, String employeeID){
        this.userName=userName;
        this.employeeID=employeeID;
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

        personalSavingsOpenItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                disablePane();
                personalSavingsOpenPane(pane);
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

    private void cancelButton(Pane pane,Pane pane1 ){
        //Cancel Button
        Button cancelButton = new Button("Cancel");
        cancelButton.setDefaultButton(true);
        cancelButton.setPrefWidth(73);
        cancelButton.setPrefHeight(35);
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
