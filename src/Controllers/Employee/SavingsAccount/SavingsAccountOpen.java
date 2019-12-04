package Controllers.Employee.SavingsAccount;

import Controllers.Employee.EmployeeHome;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;

import java.time.LocalDate;

public class SavingsAccountOpen {

    private EmployeeHome parent;
    private String gender = "";
    private String accountType = "";

    public SavingsAccountOpen(EmployeeHome parent){
        this.parent = parent;
    }

    public Pane openSavingsPane(Pane pane){
        Pane pane1 = new Pane();
        pane1.setPrefSize(800,750);
        pane.getChildren().add(pane1);
        pane1.relocate(269,128);

        //Title
        Label headerLabel = new Label("Savings Account Open");
        headerLabel.setFont(Font.font("System", FontWeight.BOLD, 35));
        pane1.getChildren().add(headerLabel);
        headerLabel.relocate(47,16);

        //Branch Label
        Label branchLabel = new Label("Branch :");
        pane1.getChildren().add(branchLabel);
        branchLabel.relocate(47,86);

        Label branchIDLabel = new Label(parent.getBranchID());
        pane1.getChildren().add(branchIDLabel);
        branchIDLabel.relocate(122,86);

        //Today Date Label
        Label dateLabel = new Label("Date :");
        pane1.getChildren().add(dateLabel);
        dateLabel.relocate(568,82);

        Label todayDateLabel = new Label(LocalDate.now().toString());
        pane1.getChildren().add(todayDateLabel);
        todayDateLabel.relocate(621,82);

        //First Holder Details

        Separator separator1 = new Separator();
        separator1.setPrefSize(800,5);
        pane1.getChildren().add(separator1);
        separator1.relocate(0,109);

        Label fstHlderDetlLabel = new Label("First Holder Details");
        fstHlderDetlLabel.setOpacity(0.75);
        pane1.getChildren().add(fstHlderDetlLabel);
        fstHlderDetlLabel.relocate(47,114);

        //NIC Number Label
        Label FirstNICNumber = new Label("NIC No :");
        pane1.getChildren().add(FirstNICNumber);
        FirstNICNumber.relocate(47,156);

        // Nic Number Text Field Text Field
        TextField firstHolderNicText = new TextField();
        firstHolderNicText.setPrefSize(277,32);
        pane1.getChildren().add(firstHolderNicText);
        firstHolderNicText.relocate(123,150);
        firstHolderNicText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,9}([\\.]\\d{0,2})*[V]?")) {
                    firstHolderNicText.setText(oldValue);
                }
            }
        });

        //Second Holder Details
        Separator separator2 = new Separator();
        separator2.setPrefSize(800,5);
        pane1.getChildren().add(separator2);
        separator2.relocate(0,215);

        Label scndHlderDetailsLabel = new Label("Second Holder Details");
        scndHlderDetailsLabel.setOpacity(0.75);
        pane1.getChildren().add(scndHlderDetailsLabel);
        scndHlderDetailsLabel.relocate(47,231);

        // Full Name Label
        Label fullNameLabel = new Label("Full Name :");
        pane1.getChildren().add(fullNameLabel);
        fullNameLabel.relocate(47,274);

        // Full Name Text Field
        TextField fullNameText = new TextField();
        fullNameText.setPrefSize(564,32);
        pane1.getChildren().add(fullNameText);
        fullNameText.relocate(153,267);
        fullNameText.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-Z*")) {
                fullNameText.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
            }
        });

        // Add NIC Label
        Label nicLabel = new Label("NIC No :");
        pane1.getChildren().add(nicLabel);
        nicLabel.relocate(47,333);

        // Add NIC Text Field
        TextField nicSecondText = new TextField();
        nicSecondText.setPrefSize(399,32);
        pane1.getChildren().add(nicSecondText);
        nicSecondText.relocate(153,326);
        nicSecondText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,9}([\\.]\\d{0,2})*[V]?")) {
                    nicSecondText.setText(oldValue);
                }
            }
        });

        //Gender
        Label genderLabel =new  Label("Gender :");
        pane1.getChildren().add(genderLabel);
        genderLabel.relocate(47,390);

        //Gender Menu
        MenuItem maleType = new MenuItem("Male");
        MenuItem femaleType = new MenuItem("Female");

        MenuButton genderTypeMenu = new MenuButton("Gender", null, maleType, femaleType);
        genderTypeMenu.setFont(Font.font("System",15));
        genderTypeMenu.setPrefSize(102,35);
        genderTypeMenu.setPopupSide(Side.BOTTOM);
        pane1.getChildren().add(genderTypeMenu);
        genderTypeMenu.relocate(157,383);


        // Date Of Birth Label
        Label dobLabel = new Label("Date Of Birth");
        pane1.getChildren().add(dobLabel);
        dobLabel.relocate(415, 391);

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
        dob.getEditor().setDisable(true);
        pane1.getChildren().add(dob);
        dob.relocate(527,380);

        //Account Details
        Separator separator3 = new Separator();
        separator3.setPrefSize(800,5);
        pane1.getChildren().add(separator3);
        separator3.relocate(0,426);

        Label accountDetailsLabel = new Label("Account Details");
        accountDetailsLabel.setOpacity(0.75);
        pane1.getChildren().add(accountDetailsLabel);
        accountDetailsLabel.relocate(47,447);

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

        //Gender
        Label accountTypeLabel =new  Label("Account Type :");
        pane1.getChildren().add(accountTypeLabel);
        accountTypeLabel.relocate(47,486);

        //Gender Menu
        MenuItem childrenType = new MenuItem("Children");
        MenuItem teenType = new MenuItem("Teen");
        MenuItem adultType = new MenuItem("18+");
        MenuItem seniorType = new MenuItem("Senior Citizen");

        MenuButton accountTypeMenu = new MenuButton("Account Type", null, childrenType, teenType, adultType, seniorType);
        accountTypeMenu.setFont(Font.font("System",15));
        accountTypeMenu.setPrefSize(145,35);
        accountTypeMenu.setPopupSide(Side.BOTTOM);
        pane1.getChildren().add(accountTypeMenu);
        accountTypeMenu.relocate(158,483);

        // Deposit Amount Label
        Label amountLabel = new Label("Deposit Amount(Rs) :");
        pane1.getChildren().add(amountLabel);
        amountLabel.relocate(407,487);

        // Deposit Amount Text Field
        TextField depositAmountText = new TextField();
        depositAmountText.setPrefSize(192,32);
        pane1.getChildren().add(depositAmountText);
        depositAmountText.relocate(550,479);
        depositAmountText.setAlignment(Pos.CENTER_RIGHT);
        depositAmountText.setText("0.00");
        depositAmountText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,2})?")) {
                    depositAmountText.setText(oldValue);
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


        childrenType.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                accountType = "Children";
                accountTypeMenu.setText(accountType);
                depositAmountText.setText("0.00");
            }
        });

        teenType.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                accountType = "Teen";
                accountTypeMenu.setText(accountType);
                depositAmountText.setText("500.00");
            }
        });

        adultType.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                accountType = "18+";
                accountTypeMenu.setText(accountType);
                depositAmountText.setText("1000.00");
            }
        });


        seniorType.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                accountType = "Senior Citizen";
                accountTypeMenu.setText(accountType);
                depositAmountText.setText("1000.00");
            }
        });

        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(accountType.equals("")){

                }
            }
        });

        return pane1;
    }
}
