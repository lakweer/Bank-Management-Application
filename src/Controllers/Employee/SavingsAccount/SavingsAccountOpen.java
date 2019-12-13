package Controllers.Employee.SavingsAccount;

import Controllers.Employee.EmployeeHome;
import Helpers.Helpers;
import Objects.SavingsAccounts.SavingsAccount;
import Validator.FormValidator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;

import Models.SavingsAccountModel;
import Models.CustomerModel;

public class SavingsAccountOpen {

    private EmployeeHome parent;
    private String accountType = "";
    private int noOfAcctHolds = 1;
    private ResultSet accountDetails;
    Label firstHolderLabel = new Label("First Holder:");
    TextField firstText = new TextField();
    Label secondHolderLabel = new Label("Second Holder:");
    TextField secondText = new TextField();
    Label thirdHolderLabel = new Label("Third Holder:");
    TextField thirdText = new TextField();
    GridPane pane1 = new GridPane();
    Label guardianNicLabel = new Label("Guardian NIC :");
    Label fullNameLabel = new Label("Child Full Name :");
    Label dobLabel = new Label(" Child DOB :");
    TextField guardianText = new TextField();
    TextField fullNameText = new TextField();
    DatePicker dob = new DatePicker(LocalDate.now());
    private Label accountHolderLabel =new Label("Account Holder NIC : ");
    private TextField accountHolderText = new TextField();
    private TextField adultAccountHolders[] = {firstText, secondText, thirdText};
    private SavingsAccountModel savingsModel;
    private CustomerModel customerModel;
    private HashMap<String,String[]> map = new HashMap<String, String[]>();


    public SavingsAccountOpen(EmployeeHome parent){
        this.parent = parent;
        this.savingsModel = new SavingsAccountModel();
        this.customerModel = new CustomerModel();
        this.accountDetails = savingsModel.getAccountDetails();
        setAccountDetailsGenerate();
    }

    public GridPane openSavingsPane(BorderPane pane){
        pane.setCenter(pane1);
        pane1.setHgap(10);
        pane1.setVgap(10);
        pane1.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

        //Title
        Label headerLabel = new Label("Savings Account Open");
        headerLabel.setFont(Font.font("System", FontWeight.BOLD, 35));
        pane1.add(headerLabel,2,1,4,1);

        //Branch Label
        Label branchLabel = new Label("Branch : " + parent.getBranchID());
        pane1.add(branchLabel,1,2,2,1);

        //Today Date Label
        Label dateLabel = new Label("Date : " + LocalDate.now().toString());
        pane1.add(dateLabel,4,2);

        //Account type menu label
        Label accountTypeLabel = new Label("Account type : ");
        pane1.add(accountTypeLabel,1,3);

        //Account type Menu
        MenuItem childrenType = new MenuItem("Children");
        MenuItem teenType = new MenuItem("Teen");
        MenuItem adultType = new MenuItem("18+");
        MenuItem seniorType = new MenuItem("Senior Citizen");

        MenuButton accountTypeMenu = new MenuButton("Account Type", null, childrenType, teenType, adultType, seniorType);
        accountTypeMenu.setFont(Font.font("System",15));
        accountTypeMenu.setPrefSize(140,32);
        accountTypeMenu.setPopupSide(Side.BOTTOM);
        pane1.add(accountTypeMenu,2,3);

        // Deposit Amount Label
        Label amountLabel = new Label("Deposit Amount(Rs) :");
        pane1.add(amountLabel,4,3);
        pane1.setHalignment(amountLabel, HPos.RIGHT);

        // Deposit Amount Text Field
        TextField depositAmountText = new TextField();
        pane1.add(depositAmountText,5,3);
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

        Label accNumberLabel = new Label("Account Number : ");
        pane1.add(accNumberLabel,1,4,3,1);

        TextField accnumberText = new TextField();
        pane1.add(accnumberText,3,4,3,1);
        pane1.setHalignment(accnumberText, HPos.CENTER);

        Separator separator1 = new Separator();
        pane1.add(separator1,1,5,10,1);

        //Number of account holders
        Label numAccountHoldersLabel = new Label("No. Account Holders :");

        MenuItem one = new MenuItem("One");
        MenuItem two = new MenuItem("Two");
        MenuItem three = new MenuItem("Three");

        MenuButton numCusMenu = new  MenuButton("Select", null , one, two, three);

        guardianText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,9}([\\.]\\d{0,2})*[V]?")) {
                    guardianText.setText(oldValue);
                }
            }
        });

        accountHolderText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,9}([\\.]\\d{0,2})*[V]?")) {
                    accountHolderText.setText(oldValue);
                }
            }
        });

        dob.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) > 0);
            }
        });
        dob.getEditor().setDisable(true);
        dob.setPrefSize(150,40);

        //Cancel Button
        Button cancelButton = new Button("Cancel");
        cancelButton.setDefaultButton(true);
        cancelButton.setPrefSize(73,35);
        pane1.add(cancelButton,1,19);
        pane1.setHalignment(cancelButton, HPos.CENTER);
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
        pane1.add(submitButton,5,19);
        pane1.setHalignment(submitButton, HPos.CENTER);
        submitButton.relocate(616,658);

        childrenType.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                accountType = "Children";
                accountTypeMenu.setText(accountType);
                removeNumberOfCustomers(pane1,numAccountHoldersLabel,numCusMenu);
                removeAccountHolderNIC();
                addChildrenSection();
            }
        });

        teenType.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                accountType = "Teen";
                accountTypeMenu.setText(accountType);
                removeNumberOfCustomers(pane1,numAccountHoldersLabel,numCusMenu);
                removeAccountHolderNIC();
                addChildrenSection();
            }
        });

        adultType.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                accountType = "18+";
                accountTypeMenu.setText(accountType);
                removeChildrenSection();
                addAccountHolderNIC();
            }
        });


        seniorType.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                accountType = "Senior Citizen";
                accountTypeMenu.setText(accountType);
                removeChildrenSection();
                addAccountHolderNIC();
            }
        });
        one.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                noOfAcctHolds = 1;
                numCusMenu.setText(one.getText());
                removeNICFields();
                addCustomersNic();
            }
        });

        two.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                noOfAcctHolds = 2;
                numCusMenu.setText(two.getText());
                removeNICFields();
                addCustomersNic();
            }
        });

        three.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                noOfAcctHolds = 3;
                numCusMenu.setText(three.getText());
                removeNICFields();
                addCustomersNic();
            }
        });

        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String result[];
                if(accountType.equals("")){

                }else if(accountType.equals("18+")){
                    if(FormValidator.nicNumberValidate(accountHolderText.getText())){
                        if(checkAmount("18+", depositAmountText.getText())){
                            result = getIndividualDetails();
                            if(!result[0].equals("")){
                                SavingsAccount sa = new SavingsAccount(accnumberText.getText(),parent.getBranchID(), map.get("18+")[0], result[0], Double.valueOf(depositAmountText.getText()),"1");
                                if(savingsModel.openIndividualSavingsAccount(sa,parent.getEmployeeID())){
                                    parent.enablePane();
                                    pane.getChildren().remove(pane1);
                                }else {
                                    Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Some Error Occur During Execution.");
                                    return;
                                }
                            }else{
                                Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Customer NIC Doesn't Exist.");
                                return;
                            }

                        }else {
                            Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Need More money.");
                            return;
                        }
                    }else {
                        Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Wrong NIC");
                        return;
                    }
                }
                else if(accountType.equals("Senior Citizen")){
                    if(FormValidator.nicNumberValidate(accountHolderText.getText())){

                        if(checkAmount("Senior Citizen", depositAmountText.getText())){
                            //customer details id and birthdate
                            result = getIndividualDetails();

                            if(!result[0].equals("")){

                                if(checkAge("Senior Citizen", result[1])){

                                    SavingsAccount sa = new SavingsAccount(accnumberText.getText(),parent.getBranchID(), map.get("Senior Citizen")[0], result[0], Double.valueOf(depositAmountText.getText()),"1");

                                    if( savingsModel.openIndividualSavingsAccount( sa, parent.getEmployeeID() ) ){
                                        parent.enablePane();
                                        pane.getChildren().remove(pane1);
                                    }
                                    else {
                                        Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Some Error Occur During Execution.");
                                        return;
                                    }
                                }else{
                                    Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Candidate is Under Age.");
                                    return;
                                }
                            }

                            else{
                                Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Customer NIC Doesn't Exist.");
                                return;
                            }

                        }else {
                            Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Need More money.");
                            return;
                        }
                    }else {
                        Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Wrong NIC");
                        return;
                    }
                }
            }
        });
        return pane1;
    }

    private boolean checkAmount(String type,String amount){
        if(Double.valueOf(amount) >= Double.valueOf(map.get(type)[5])){
            return true;
        }
        return false;
    }

    private boolean checkAge(String type, String dob){

        if(type.equals("Children") && (Integer.valueOf(map.get("Children")[4]) > Period.between(LocalDate.parse(dob), LocalDate.now()).getYears())){
            return true;
        }else if((type.equals("Teen") && (Integer.valueOf(map.get("Teen")[4]) > Period.between(LocalDate.parse(dob), LocalDate.now()).getYears()) && (Period.between(LocalDate.parse(dob), LocalDate.now()).getYears() > Integer.valueOf(map.get("Teen")[3])))){
                return true;
        }
        else if(type.equals("Senior Citizen") && (Integer.valueOf(map.get("Senior Citizen")[3]) <= Period.between(LocalDate.parse(dob), LocalDate.now()).getYears())){
            return true;
        }
        return false;
    }

    private void addNumberOfCustomers(GridPane pane,Label label,MenuButton button){
        if(!(pane.getChildren().contains(label) && pane.getChildren().contains(button))){
            pane.add(label,1,6,2,1);
            pane.add(button,2,6);
            pane.setHalignment(button, HPos.CENTER);
        }
    }

    private void removeNumberOfCustomers(GridPane pane,Label label,MenuButton button) {
        if ((pane.getChildren().contains(label) && pane.getChildren().contains(button))) {
            removeNICFields();
            pane.getChildren().removeAll(label, button);
        }
    }

    private void addAccountHolderNIC(){
        if(!pane1.getChildren().contains(accountHolderLabel)) {
            pane1.add(accountHolderLabel, 1, 8, 2, 1);
            pane1.add(accountHolderText, 3, 8);
        }
    }

    private void addCustomersNic(){
        if(noOfAcctHolds==1){
            firstNICAdd();
        }else if(noOfAcctHolds==2){
            firstNICAdd();
            secondNICAdd();
        }else if(noOfAcctHolds==3){
            firstNICAdd();
            secondNICAdd();
            thirdNICAdd();
        }
    }

    private void removeAccountHolderNIC(){
        if(pane1.getChildren().contains(accountHolderLabel)) {
            pane1.getChildren().removeAll(accountHolderLabel,accountHolderText);
        }
    }

    private void firstNICAdd(){
        pane1.add(firstHolderLabel,2,7);
        pane1.setHalignment(firstHolderLabel, HPos.CENTER);
        pane1.add(firstText,2,8);
        pane1.setHalignment(firstText, HPos.CENTER);
    }

    private void secondNICAdd(){
        pane1.add(secondHolderLabel,4,7);
        pane1.setHalignment(secondHolderLabel, HPos.CENTER);
        pane1.add(secondText,4,8);
        pane1.setHalignment(secondText, HPos.CENTER);
    }

    private void thirdNICAdd(){
        pane1.add(thirdHolderLabel,5,7);
        pane1.setHalignment(thirdHolderLabel, HPos.CENTER);
        pane1.add(thirdText,5,8);
        pane1.setHalignment(thirdText, HPos.CENTER);
    }

    private void removeNICFields(){
        if(pane1.getChildren().contains(thirdHolderLabel)){
            pane1.getChildren().removeAll(firstHolderLabel,firstText,secondHolderLabel,secondText,thirdHolderLabel,thirdText);
        }else if(pane1.getChildren().contains(secondHolderLabel)){
            pane1.getChildren().removeAll(firstHolderLabel,firstText,secondHolderLabel,secondText);
        }else{
            pane1.getChildren().removeAll(firstHolderLabel,firstText);
        }
    }

    private void addChildrenSection(){
        if(!pane1.getChildren().contains(guardianNicLabel)){
            pane1.add(guardianNicLabel,1,7);
            pane1.add(guardianText,2,7);
            pane1.add(fullNameLabel,1,6);
            pane1.add(fullNameText,2,6,3,1);
            pane1.add(dobLabel,1,8);
            pane1.add(dob,2,8);
        }
    }

    private void removeChildrenSection(){
        if(pane1.getChildren().contains(guardianNicLabel)){
            pane1.getChildren().removeAll(guardianNicLabel,guardianText,fullNameLabel,fullNameText,dobLabel,dob);
        }
    }

    private void setAccountDetailsGenerate(){
        try {
            while (accountDetails.next()){
                String[] details = new String[6];
                for (int i = 1; i < 7 ; i++) {
                    details[i-1] = accountDetails.getString(i);
                }
                map.put(accountDetails.getString(2),details);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private String[] getIndividualDetails(){
        // 0: CustomerID
        // 1: BirthDate
        String result[] = {"", "" };
        try {
            ResultSet rs = customerModel.getCustomerDetails(accountHolderText.getText());
            if(rs.next()){
                result[0] = rs.getString(1);
                result[1] = rs.getString(2);

            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

}
