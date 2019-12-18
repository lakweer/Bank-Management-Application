package Controllers.Employee.Loans;


import Controllers.Employee.EmployeeHome;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.time.LocalDate;

public class RequestLoan {

    /*
    RequestId, Customer Id, Manager Id, Amount, Approved Status('APPROVED'PENDING','REJECTED"), ,
    Employee Id, Gross Salary, Net Salary, LoanId, EmployeementSector('PRIVATE','PUBLIC','SELF'),
    'EmployeementType('PER','TEMP'), Profession
     */

    private EmployeeHome parent;

    public RequestLoan(EmployeeHome parent){
        this.parent = parent;
    }

    public GridPane requestLoanPane(BorderPane pane) {
        GridPane pane1 = new GridPane();
        pane.setCenter(pane1);
        pane1.setHgap(10);
        pane1.setVgap(10);
        pane1.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

        //Title
        Label headerLabel = new Label("Request Loan");
        headerLabel.setFont(Font.font("System", FontWeight.BOLD, 35));
        pane1.add(headerLabel, 2, 1, 4, 1);

        //Employee Label
        Label branchLabel = new Label("Employee : " + parent.getUserName());
        pane1.add(branchLabel, 1, 2, 2, 1);

        //Today Date Label
        Label dateLabel = new Label("Date : " + LocalDate.now().toString());
        pane1.setHalignment(dateLabel, HPos.RIGHT);
        pane1.add(dateLabel, 4, 2);


        MenuItem sixMonthsType = new MenuItem("SIX MONTHS");
        MenuItem oneYearType = new MenuItem("ONE YEAR ");
        MenuItem threeYearsType = new MenuItem("THREE YEARS");

        MenuButton fdAccountTypeMenu = new MenuButton("ACCOUNT TYPE", null, sixMonthsType, oneYearType, threeYearsType);
        pane1.add(fdAccountTypeMenu, 1, 3,2,1);

        Label expireDateLabel = new Label("Maturity Date :");
        pane1.add(expireDateLabel, 3, 3, 2, 1);

        Label fdNumberLabel = new Label("FD Account Number : ");
        pane1.add(fdNumberLabel, 1, 4, 2, 1);

        TextField fdAccNumberText = new TextField();
        pane1.add(fdAccNumberText, 3, 4, 2, 1);
        pane1.setHalignment(fdAccNumberText, HPos.LEFT);

        Label savingsNumberLabel = new Label("Savings Account Number : ");
        pane1.add(savingsNumberLabel, 1, 5, 2, 1);

        TextField savingsAccNumberText = new TextField();
        pane1.add(savingsAccNumberText, 3, 5, 2, 1);
        pane1.setHalignment(savingsAccNumberText, HPos.LEFT);

        Label customerNICLabel = new Label("Customer Nic Number : ");
        pane1.add(customerNICLabel, 1, 6, 2, 1);

        TextField customerNICNumberText = new TextField();
        pane1.add(customerNICNumberText, 3, 6, 2, 1);
        pane1.setHalignment(customerNICNumberText, HPos.LEFT);

        customerNICNumberText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,9}([\\.]\\d{0,2})*[V]?")) {
                    customerNICNumberText.setText(oldValue);
                }
            }
        });

        // Deposit Amount Label
        Label amountLabel = new Label("Deposit Amount(Rs) :");
        pane1.add(amountLabel, 1, 7);
        pane1.setHalignment(amountLabel, HPos.RIGHT);

        // Deposit Amount Text Field
        TextField depositAmountText = new TextField();
        pane1.add(depositAmountText, 3, 7);
        depositAmountText.setAlignment(Pos.CENTER_RIGHT);
        depositAmountText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,10}([\\.]\\d{0,2})?")) {
                    depositAmountText.setText(oldValue);
                }
            }
        });



        Button cancelButton = new Button("Back");
        cancelButton.setDefaultButton(true);
        cancelButton.setPrefSize(73, 35);
        pane1.add(cancelButton, 1, 8);
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
        pane1.add(submitButton, 3, 8);
        pane1.setHalignment(submitButton, HPos.CENTER);

        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            }


        });

        return pane1;
    }
}
