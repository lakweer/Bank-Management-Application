package Controllers.Employee.Loans;

import Controllers.Employee.EmployeeHome;
import Helpers.Helpers;
import Models.LoanModel;
import Objects.loan.OrgLoanRequest;
import Validator.FormValidator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.time.LocalDate;

public class RequestOrgLoan {

    /*
    String requestId, String registerId, String branchId, Double amount, String approvedStatus,
     String employeeId, Double projectGrossValue, String loanId, String loanReason,
      String organizationType,LocalDate requestDate

     */

    private EmployeeHome parent;
    private String organizationType = "";
    private String loanReason = "";
    private LoanModel omodel= new LoanModel();

    public RequestOrgLoan(EmployeeHome parent){
        this.parent = parent;
    }

    public GridPane requestOrgLoanPane(BorderPane pane) {
        GridPane pane1 = new GridPane();
        pane.setCenter(pane1);
        pane1.setHgap(10);
        pane1.setVgap(10);
        pane1.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

        //Title
        Label headerLabel = new Label("  Organization Loan Request  ");
        headerLabel.setFont(Font.font("System", FontWeight.BOLD, 35));
        pane1.add(headerLabel, 1, 1, 5, 1);

        //Employee Label
        Label branchLabel = new Label("Employee : " + parent.getUserName());
        pane1.add(branchLabel, 1, 2, 2, 1);

        //Today Date Label
        Label dateLabel = new Label("Date : " + LocalDate.now().toString());
        pane1.setHalignment(dateLabel, HPos.RIGHT);
        pane1.add(dateLabel, 4, 2);

/*
        //menu for loan type
        Label loanTypeLabel = new Label("Loan type : ");
        pane1.add(loanTypeLabel, 1, 3, 2, 1);

        MenuItem educationalType = new MenuItem("Educational");
        MenuItem housingType = new MenuItem("Housing");
        MenuItem personalType = new MenuItem("Personal");
        MenuItem vehicleType = new MenuItem("Vehicle");

        MenuButton loanTypeMenu = new MenuButton("LOAN TYPE", null, educationalType, housingType, personalType, vehicleType);
        pane1.add(loanTypeMenu, 3, 3,2,1);

 */

        //Registration number of organization label
        Label registerIdLabel = new Label("Registration Number : ");
        pane1.add(registerIdLabel, 1, 3, 2, 1);

        TextField registerIdText = new TextField();
        pane1.add(registerIdText, 3, 3, 2, 1);
        pane1.setHalignment(registerIdLabel, HPos.LEFT);


        //request amount label
        Label requestIdLabel = new Label("Request Amount (Rs) : ");
        pane1.add(requestIdLabel, 1, 4, 2, 1);

        TextField requestAmountText = new TextField();
        pane1.add(requestAmountText, 3, 4, 2, 1);
        pane1.setHalignment(requestAmountText, HPos.LEFT);

        requestAmountText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,8}([\\.]\\d{0,2})?")) {
                    requestAmountText.setText(oldValue);
                }
            }
        });


        //Organization type label
        Label organizationTypeLabel = new Label("Organization Type : ");
        pane1.add(organizationTypeLabel, 1, 5, 2, 1);

        MenuItem individualType = new MenuItem("Individual");
        MenuItem jointType = new MenuItem("Joint");
        MenuItem pvtType = new MenuItem("Pvt Ltd Co");
        MenuItem limitedCoType = new MenuItem("Limited Co");
        MenuItem trustType = new MenuItem("Trust");
        MenuItem otherType = new MenuItem("Other");

        MenuButton organizationTypeTypeMenu = new MenuButton("Organization Type", null, individualType, jointType,pvtType,limitedCoType,trustType,otherType);
        pane1.add(organizationTypeTypeMenu, 3, 5,2,1);


        //Project Gross Value  label
        Label projectGrossValueLabel = new Label("Project Gross Value (Rs) : ");
        pane1.add(projectGrossValueLabel, 1, 6, 2, 1);

        TextField projectGrossValuText = new TextField();
        pane1.add(projectGrossValuText, 3, 6, 2, 1);
        pane1.setHalignment(projectGrossValuText, HPos.LEFT);

        projectGrossValuText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,10}([\\.]\\d{0,2})?")) {
                    projectGrossValuText.setText(oldValue);
                }
            }
        });

        //loan reasonlabel
        Label loanReasonLabel = new Label("Loan Reason : ");
        pane1.add(loanReasonLabel, 1, 7, 2, 1);

        MenuItem constructionType = new MenuItem("Construction");
        MenuItem assetPurchaseType = new MenuItem("Asset Purchase");
        MenuItem refinancingType = new MenuItem("Refinancing");
        MenuItem otherReasonType = new MenuItem("Other Reason");

        MenuButton loanReasonTypeMenu = new MenuButton("Loan Reason", null, constructionType, assetPurchaseType,refinancingType,otherReasonType);
        pane1.add(loanReasonTypeMenu, 3, 7,2,1);


        constructionType.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loanReason = "Construction";
                loanReasonTypeMenu.setText(constructionType.getText());
            }
        });

        assetPurchaseType.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loanReason = "Asset_Purchase";
                loanReasonTypeMenu.setText(assetPurchaseType.getText());
            }
        });

        refinancingType.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loanReason = "Refinancing";
                loanReasonTypeMenu.setText(refinancingType.getText());
            }
        });

        otherReasonType.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loanReason = "Other_Reason";
                loanReasonTypeMenu.setText(otherReasonType.getText());
            }
        });

        individualType.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                organizationType = "Individual";
                organizationTypeTypeMenu.setText(individualType.getText());
            }
        });

        jointType.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                organizationType = "Joint";
                organizationTypeTypeMenu.setText(jointType.getText());
            }
        });

        pvtType.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                organizationType = "Pvt_Ltd_Co";
                organizationTypeTypeMenu.setText(pvtType.getText());
            }
        });

        limitedCoType.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                organizationType = "Limited_Co";
                organizationTypeTypeMenu.setText(limitedCoType.getText());
            }
        });

        trustType.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                organizationType = "Trust";
                organizationTypeTypeMenu.setText(trustType.getText());
            }
        });

        otherType.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                organizationType = "Other";
                organizationTypeTypeMenu.setText(otherType.getText());
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
                if (registerIdLabel.getText().isEmpty()) {
                    Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Please enter the Registration Number");
                    return;
                }

                if (requestAmountText.getText().isEmpty()) {
                    Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Please enter the Request Amount");
                    return;
                }

                if (projectGrossValuText.getText().isEmpty()) {
                    Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Please enter the Project Gross Value");
                    return;
                }


                OrgLoanRequest newOrgLoan = new OrgLoanRequest(registerIdText.getText(),parent.getBranchID(), Double.valueOf(requestAmountText.getText()), parent.getEmployeeID(),
                        Double.valueOf(projectGrossValuText.getText()), loanReason, organizationType, LocalDate.now());

                String result = "";
                result = omodel.createOrgLoanRequest(newOrgLoan);
                if (result.equals("Success")) {
                    pane.getChildren().remove(pane1);
                    parent.enablePane();
                    Helpers.showAlert(Alert.AlertType.CONFIRMATION, pane.getScene().getWindow(), "Success", "Successfully Requested");
                } else {
                    Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", result);
                }


            }

        });

        return pane1;
    }
}
