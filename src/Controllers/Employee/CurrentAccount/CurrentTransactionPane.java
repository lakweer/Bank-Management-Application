package Controllers.Employee.CurrentAccount;

import Controllers.Employee.EmployeeHome;
import Helpers.Helpers;
import Models.CurrentAccountModel;
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


public class CurrentTransactionPane {

    private EmployeeHome parent;
    private String transactionType = "";
    private String transactionMode = "";
    private CurrentAccountModel currentAccountModel;

    private Label ChequeNumberLabel =new Label("Cheque Number : ");
    private TextField chequeNumberText = new TextField();

    private String chequeNumber = "";

    GridPane pane1 = new GridPane();

    public CurrentTransactionPane(EmployeeHome parent){
        this.parent =parent;
        this.currentAccountModel = new CurrentAccountModel();
    }

    public GridPane TransactionCurrentPane(BorderPane pane) {

        pane.setCenter(pane1);
        pane1.setHgap(10);
        pane1.setVgap(10);
        pane1.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

        //Title
        Label headerLabel = new Label("Current Account Transaction");
        headerLabel.setFont(Font.font("System", FontWeight.BOLD, 35));
        pane1.add(headerLabel, 2, 1, 4, 1);

        //Employee username Label
        Label employeeLabel = new Label("Employee : " + parent.getUserName());
        pane1.add(employeeLabel, 1, 2, 2, 1);

        //Today Date Label
        Label dateLabel = new Label("Date : " + LocalDate.now().toString());
        pane1.setHalignment(dateLabel, HPos.RIGHT);
        pane1.add(dateLabel, 4, 2);

        //Account Number Label
        Label accNumberLabel = new Label("Account Number : ");
        pane1.add(accNumberLabel, 1, 4, 2, 1);
        pane1.setHalignment(accNumberLabel, HPos.LEFT);

        //Account Number Text Field
        TextField accountNumberText = new TextField();
        pane1.add(accountNumberText, 3, 4, 4, 1);
        pane1.setHalignment(accountNumberText, HPos.CENTER);

        //Transaction Type Label
        Label transactionTypeLabel = new Label("Transaction Type:");
        pane1.add(transactionTypeLabel, 1, 5, 2, 1);
        pane1.setHalignment(transactionTypeLabel, HPos.LEFT);

        //Transaction type Menu
        MenuItem withdrawalType = new MenuItem("Withdrawal");
        MenuItem depositType = new MenuItem("Deposit");

        MenuButton transactionTypeMenu = new MenuButton("Type", null, withdrawalType, depositType);
        transactionTypeMenu.setFont(Font.font("System", 15));
        transactionTypeMenu.setPrefSize(140, 32);
        transactionTypeMenu.setPopupSide(Side.BOTTOM);
        pane1.add(transactionTypeMenu, 3, 5);

        //Transaction Mode Label
        Label transactionModeLabel = new Label("Transaction Mode:");
        pane1.add(transactionModeLabel, 1, 6, 2, 1);
        pane1.setHalignment(transactionModeLabel, HPos.LEFT);

        //Transaction Mode Menu
        MenuItem chequeMode = new MenuItem("Cheque");
        MenuItem cashMode = new MenuItem("Cash");

        MenuButton transactionModeMenu = new MenuButton("Mode", null, chequeMode, cashMode);
        transactionModeMenu.setFont(Font.font("System", 15));
        transactionModeMenu.setPrefSize(140, 32);
        transactionModeMenu.setPopupSide(Side.BOTTOM);
        pane1.add(transactionModeMenu, 3, 6);

        //Amount Label
        Label amountLabel = new Label("Amount(Rs) :");
        pane1.add(amountLabel, 1, 7);
        pane1.setHalignment(amountLabel, HPos.LEFT);

        //Amount Text Field
        TextField amountText = new TextField();
        pane1.add(amountText, 3, 7);
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

        chequeMode.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                transactionMode = "Cheque";
                transactionModeMenu.setText(transactionMode);
                addChequeNumber();
            }
        });

        cashMode.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                transactionMode = "Cash";
                transactionModeMenu.setText(transactionMode);
                removeChequeSection();
            }
        });


        Button cancelButton = new Button("Back");
        cancelButton.setDefaultButton(true);
        cancelButton.setPrefSize(73, 35);
        pane1.add(cancelButton, 1, 10);
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
        submitButton.setPrefSize(73, 35);
        pane1.add(submitButton, 4, 10);
        pane1.setHalignment(submitButton, HPos.CENTER);


        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String chequeNumber;
                if(transactionMode == "Cheque") {
                    chequeNumber = chequeNumberText.getText();
                }
                else {
                    chequeNumber = null;
                }
                System.out.println("submit");
                /*to check whether all the required fields are filled*/
                if(!accountNumberText.getText().isEmpty() && !transactionType.isEmpty() && !transactionMode.isEmpty()
                && !amountText.getText().isEmpty()){
                    /*check whether cheque number is filled if transaction mode is cheque*/
                    if(transactionMode == "Cheque" && chequeNumberText.getText().isEmpty()) {
                        Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Fill All the Fields");
                        return;

                    }

                    else {
                        System.out.println("inside");
                        String result = currentAccountModel.currentAccountTransaction(accountNumberText.getText(), LocalDate.now().toString(), parent,
                                amountText.getText(), chequeNumber, transactionType, transactionMode);
                        if (result.equals("Success")) {
                            Helpers.showAlert(Alert.AlertType.CONFIRMATION, pane.getScene().getWindow(), "Success!", result);
                            parent.enablePane();
                            pane.getChildren().remove(pane1);
                        } else {
                            Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", result);
                            return;
                        }
                    }
                }else{
                    Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Fill All the Fields");
                    return;
                }
            }
        });
        return pane1;

    }
        private void addChequeNumber() {
            if(!pane1.getChildren().contains(ChequeNumberLabel)) {
                pane1.add(ChequeNumberLabel, 1, 8, 2, 1);
                pane1.add(chequeNumberText, 3, 8);
            }
        }

        private void removeChequeSection(){
            if(pane1.getChildren().contains(ChequeNumberLabel)){
                pane1.getChildren().removeAll(ChequeNumberLabel,chequeNumberText);
            }
        }

}
