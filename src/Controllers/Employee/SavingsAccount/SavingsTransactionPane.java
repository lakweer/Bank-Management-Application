package Controllers.Employee.SavingsAccount;

import Controllers.Employee.EmployeeHome;
import Helpers.Helpers;
import Models.SavingsAccountModel;
import Objects.SavingsAccounts.SavingsTransaction;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

import java.time.LocalDate;

public class SavingsTransactionPane {

    private EmployeeHome parent;
    private String transactionType = "";
    private SavingsAccountModel savingsAccountModel;

    public SavingsTransactionPane(EmployeeHome parent){
        this.parent =parent;
        this.savingsAccountModel = new SavingsAccountModel();
    }

    public GridPane savingsTransactionPane(BorderPane pane) {
        GridPane pane1 =new GridPane();
        pane.setCenter(pane1);
        pane1.setHgap(10);
        pane1.setVgap(10);
        pane1.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

        //Title
        Label headerLabel = new Label("Savings Account Transaction");
        headerLabel.setFont(Font.font("System", FontWeight.BOLD, 35));
        pane1.add(headerLabel, 2, 1, 4, 1);

        //Employee username Label
        Label employeeLabel = new Label("Employee : " + parent.getUserName());
        pane1.add(employeeLabel, 1, 2, 2, 1);

        //Today Date Label
        Label dateLabel = new Label("Date : " + LocalDate.now().toString());
        pane1.setHalignment(dateLabel, HPos.RIGHT);
        pane1.add(dateLabel, 4, 2);

        Label accNumberLabel = new Label("Account Number : ");
        pane1.add(accNumberLabel,1,4,2,1);
        pane1.setHalignment(accNumberLabel, HPos.RIGHT);

        TextField accountNumberText = new TextField();
        pane1.add(accountNumberText,3,4,4,1);
        pane1.setHalignment(accountNumberText, HPos.CENTER);

        //Transaction Type Label
        Label transactionTypeLabel = new Label("Transaction Type:");
        pane1.add(transactionTypeLabel,1,5,2,1);
        pane1.setHalignment(transactionTypeLabel, HPos.RIGHT);

        //Account type Menu
        MenuItem withdrawalType = new MenuItem("Withdrawal");
        MenuItem depositType = new MenuItem("Deposit");

        MenuButton transactionTypeMenu = new MenuButton("Transaction Type", null, withdrawalType, depositType);
        transactionTypeMenu.setFont(Font.font("System",15));
        transactionTypeMenu.setPrefSize(140,32);
        transactionTypeMenu.setPopupSide(Side.BOTTOM);
        pane1.add(transactionTypeMenu,3,5);


        //Amount Label
        Label amountLabel = new Label("Amount(Rs) :");
        pane1.add(amountLabel,2,6);
        pane1.setHalignment(amountLabel, HPos.RIGHT);

        // Deposit Amount Text Field
        TextField amountText = new TextField();
        pane1.add(amountText,3,6);
        amountText.setAlignment(Pos.CENTER_RIGHT);
        amountText.setText("0.00");
        amountText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,2})?")) {
                    amountText.setText(oldValue);
                }
            }
        });

        withdrawalType.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                transactionType = "Withdrawal";
                transactionTypeMenu.setText(transactionType);
            }
        });

        depositType.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                transactionType = "Deposit";
                transactionTypeMenu.setText(transactionType);
            }
        });


        Button cancelButton = new Button("Back");
        cancelButton.setDefaultButton(true);
        cancelButton.setPrefSize(73,35);
        pane1.add(cancelButton,1,10);
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
        pane1.add(submitButton,4,10);
        pane1.setHalignment(submitButton, HPos.CENTER);


        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if(!(transactionType.equals("") && !(accountNumberText.getText().trim().equals("")) && !(amountText.equals("0.00")))){

                    SavingsTransaction transaction = new SavingsTransaction(accountNumberText.getText(), LocalDate.now(),
                            Double.valueOf(amountText.getText()),"EMP", transactionType );
                    String result ="";
                    if(transactionType.equals("Withdrawal")){
                        result = savingsAccountModel.withdrawal(transaction, parent.getEmployeeID());
                    }if(transactionType.equals("Deposit")){
                        result = savingsAccountModel.deposit(transaction, parent.getEmployeeID());
                    }
                    if(result.equals("Success")){
                        Helpers.showAlert(Alert.AlertType.CONFIRMATION, pane.getScene().getWindow(), "Success!", "Transaction Proceed!");
                        parent.enablePane();
                        pane.getChildren().remove(pane1);
                    }else{
                        Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", result);
                        return;
                    }
                }
                else{
                    Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Fill all the fields!");
                    return;
                }
            }
        });

        return pane1;
    }
}
