package Controllers.Employee.Loans;


import Controllers.Employee.EmployeeHome;
import Helpers.Helpers;
import Models.LoanModel;
import Objects.Employee;
import Objects.loan.IndividualLoanRequest;
import Validator.FormValidator;
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

public class CheckStatus {
    private EmployeeHome parent;
    private String customerLoanType = "";
    private LoanModel lmodel= new LoanModel();

    public CheckStatus(EmployeeHome parent){
        this.parent = parent;
    }

    public GridPane checkStatusPane(BorderPane pane) {
        GridPane pane1 = new GridPane();
        pane.setCenter(pane1);
        pane1.setHgap(10);
        pane1.setVgap(10);
        pane1.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

        //Title
        Label headerLabel = new Label("    Check Loan Status  ");
        headerLabel.setFont(Font.font("System", FontWeight.BOLD, 25));
        pane1.add(headerLabel, 1, 1, 4, 1);

        //Employee Label
        Label branchLabel = new Label("Employee : " + parent.getUserName());
        pane1.add(branchLabel, 1, 2, 2, 1);

        //Today Date Label
        Label dateLabel = new Label("Date : " + LocalDate.now().toString());
        pane1.setHalignment(dateLabel, HPos.RIGHT);
        pane1.add(dateLabel, 4, 2);


        //menu for CustomerLoan type
        Label loanTypeLabel = new Label("Loan type : ");
        pane1.add(loanTypeLabel, 1, 4, 2, 1);

        MenuItem individualType = new MenuItem("Individual Loan");
        MenuItem organizationalType = new MenuItem("Organizational loan");

        MenuButton loanTypeMenu = new MenuButton("LOAN TYPE", null, individualType, organizationalType);
        pane1.add(loanTypeMenu, 3, 4,3,1);


        //request id label
        Label requestIdLabel = new Label("Request ID : ");
        pane1.add(requestIdLabel, 1, 5, 2, 1);

        TextField requestIDText = new TextField();
        pane1.add(requestIDText, 3, 5, 2, 1);
        pane1.setHalignment(requestIDText, HPos.LEFT);


        individualType.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                customerLoanType = "Individual";
                loanTypeMenu.setText(individualType.getText());
            }
        });

        organizationalType.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                customerLoanType = "Organizational";
                loanTypeMenu.setText(organizationalType.getText());
            }
        });


        Button cancelButton = new Button("Back");
        cancelButton.setDefaultButton(true);
        cancelButton.setPrefSize(73, 35);
        pane1.add(cancelButton, 1, 12);
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
        pane1.add(submitButton, 3, 12);
        pane1.setHalignment(submitButton, HPos.CENTER);

        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (requestIDText.getText().isEmpty()) {
                    Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Please enter the Profession");
                    return;
                }

                String result = "";
                result = lmodel.checkLoanStatus(customerLoanType,Integer.valueOf(requestIDText.getText()));

                if (result.equals("APPROVED")) {
                    pane.getChildren().remove(pane1);
                    parent.enablePane();
                    Helpers.showAlert(Alert.AlertType.CONFIRMATION, pane.getScene().getWindow(), "Loan Status", "Your loan request has been APPROVED");
                }
                else if(result.equals("PENDING")) {
                    pane.getChildren().remove(pane1);
                    parent.enablePane();
                    Helpers.showAlert(Alert.AlertType.CONFIRMATION, pane.getScene().getWindow(), "Loan Status", "Your loan request is still PENDING");
                }
                else if(result.equals("REJECTED")) {
                    pane.getChildren().remove(pane1);
                    parent.enablePane();
                    Helpers.showAlert(Alert.AlertType.CONFIRMATION, pane.getScene().getWindow(), "Loan Status", "Your loan request hes been REJECTED");
                }
                else {
                    Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", result);
                }


           }

        });

        return pane1;
    }




}
