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

public class RequestLoan {

    /*
    RequestId, Customer Id, Manager Id, Amount, Approved Status('APPROVED'PENDING','REJECTED"), ,
    Employee Id, Gross Salary, Net Salary, LoanId, EmployeementSector('PRIVATE','PUBLIC','SELF'),
    'EmployeementType('PER','TEMP'), Profession
     */

    private EmployeeHome parent;
    private String loanType = "";
    private String employmentSector = "";
    private String employmentType = "";
    private LoanModel lmodel= new LoanModel();

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
        Label headerLabel = new Label("  Individual Loan Request  ");
        headerLabel.setFont(Font.font("System", FontWeight.BOLD, 35));
        pane1.add(headerLabel, 1, 1, 5, 1);

        //Employee Label
        Label branchLabel = new Label("Employee : " + parent.getUserName());
        pane1.add(branchLabel, 1, 2, 2, 1);

        //Today Date Label
        Label dateLabel = new Label("Date : " + LocalDate.now().toString());
        pane1.setHalignment(dateLabel, HPos.RIGHT);
        pane1.add(dateLabel, 4, 2);

//        //menu for customer type
//        MenuItem individualType = new MenuItem("Individual");
//        MenuItem organaizationType = new MenuItem("Organization");
//
//        MenuButton customerTypeMenu = new MenuButton("CUSTOMER TYPE", null, individualType, organaizationType);
//        pane1.add(customerTypeMenu, 1, 3,2,1);

        //menu for loan type
        Label loanTypeLabel = new Label("Loan type : ");
        pane1.add(loanTypeLabel, 1, 3, 2, 1);

        MenuItem educationalType = new MenuItem("Educational");
        MenuItem housingType = new MenuItem("Housing");
        MenuItem personalType = new MenuItem("Personal");
        MenuItem vehicleType = new MenuItem("Vehicle");

        MenuButton loanTypeMenu = new MenuButton("LOAN TYPE", null, educationalType, housingType, personalType, vehicleType);
        pane1.add(loanTypeMenu, 3, 3,2,1);

        //customer id label
        Label customerIDLabel = new Label("NIC : ");
        pane1.add(customerIDLabel, 1, 4, 2, 1);

        TextField customerNICNumberText = new TextField();
        pane1.add(customerNICNumberText, 3, 4, 2, 1);
        pane1.setHalignment(customerNICNumberText, HPos.LEFT);

        customerNICNumberText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,9}([\\.]\\d{0,2})*[V]?")) {
                    customerNICNumberText.setText(oldValue);
                }
            }
        });

        //request amount label
        Label requestAmountLabel = new Label("Request Amount (Rs) : ");
        pane1.add(requestAmountLabel, 1, 5, 2, 1);

        TextField requestAmountText = new TextField();
        pane1.add(requestAmountText, 3, 5, 2, 1);
        pane1.setHalignment(requestAmountText, HPos.LEFT);

        requestAmountText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,8}([\\.]\\d{0,2})?")) {
                    requestAmountText.setText(oldValue);
                }
            }
        });

        //employment sector label
        Label employmentSectorLabel = new Label("Employment Sector : ");
        pane1.add(employmentSectorLabel, 1, 6, 2, 1);

        MenuItem publicType = new MenuItem("Public");
        MenuItem privateType = new MenuItem("Private");
        MenuItem selfType = new MenuItem("Self");

        MenuButton employmentSectorTypeMenu = new MenuButton("Employment Sector", null, publicType, privateType, selfType);
        pane1.add(employmentSectorTypeMenu, 3, 6,2,1);

        //employment type label
        Label employmentTypeLabel = new Label("Employment Type : ");
        pane1.add(employmentTypeLabel, 1, 7, 2, 1);

        MenuItem permanentType = new MenuItem("Permanent");
        MenuItem temporaryType = new MenuItem("Temporary");

        MenuButton employmentTypeTypeMenu = new MenuButton("Employment Type", null, permanentType, temporaryType);
        pane1.add(employmentTypeTypeMenu, 3, 7,2,1);

        //profession label
        Label professionLabel = new Label("Profession : ");
        pane1.add(professionLabel, 1, 8, 2, 1);

        TextField professionText = new TextField();
        pane1.add(professionText, 3, 8, 2, 1);
        pane1.setHalignment(professionText, HPos.LEFT);

        //Gross Salary label
        Label grossSalaryLabel = new Label("Gross Salary (Rs) : ");
        pane1.add(grossSalaryLabel, 1, 9, 2, 1);

        TextField grossSalaryText = new TextField();
        pane1.add(grossSalaryText, 3, 9, 2, 1);
        pane1.setHalignment(grossSalaryText, HPos.LEFT);

        grossSalaryText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,10}([\\.]\\d{0,2})?")) {
                    grossSalaryText.setText(oldValue);
                }
            }
        });

        //Net Salary label
        Label netSalaryLabel = new Label("Net Salary (Rs) : ");
        pane1.add(netSalaryLabel, 1, 10, 2, 1);

        TextField netSalaryText = new TextField();
        pane1.add(netSalaryText, 3, 10, 2, 1);
        pane1.setHalignment(netSalaryText, HPos.LEFT);

        netSalaryText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,10}([\\.]\\d{0,2})?")) {
                    netSalaryText.setText(oldValue);
                }
            }
        });

        educationalType.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loanType = "Educational";
                loanTypeMenu.setText(educationalType.getText());
            }
        });

        personalType.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loanType = "Personal";
                loanTypeMenu.setText(personalType.getText());
            }
        });

        housingType.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loanType = "Housing";
                loanTypeMenu.setText(housingType.getText());
            }
        });

        vehicleType.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loanType = "Vehicle";
                loanTypeMenu.setText(vehicleType.getText());
            }
        });

        publicType.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                employmentSector = "GOV";
                employmentSectorTypeMenu.setText(publicType.getText());
            }
        });

        privateType.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                employmentSector = "PRIVATE";
                employmentSectorTypeMenu.setText(privateType.getText());
            }
        });

        selfType.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                employmentSector = "SELF";
                employmentSectorTypeMenu.setText(selfType.getText());
            }
        });

        permanentType.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                employmentType = "PER";
                employmentTypeTypeMenu.setText(permanentType.getText());
            }
        });

        temporaryType.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                employmentType = "TEMP";
                employmentTypeTypeMenu.setText(temporaryType.getText());
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
                if (!customerNICNumberText.getText().isEmpty()) {
                    if (!FormValidator.nicNumberValidate(customerNICNumberText.getText())) {
                        Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Please enter the Correct NIC Number");
                        return;
                    }
                } else {
                    Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Please enter the NIC Number");
                    return;
                }

                if (professionText.getText().isEmpty()) {
                    Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Please enter the Profession");
                    return;
                }

                if (requestAmountText.getText().isEmpty()) {
                    Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Please enter the Request Amount");
                    return;
                }

                if (grossSalaryText.getText().isEmpty()) {
                    Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Please enter the Gross salary");
                    return;
                }

                if (netSalaryText.getText().isEmpty()) {
                    Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Please enter the Net salary");
                    return;
                }


                IndividualLoanRequest newLoan = new IndividualLoanRequest(customerNICNumberText.getText(),parent.getBranchID(), Double.valueOf(requestAmountText.getText()), parent.getEmployeeID(),
                        Double.valueOf(grossSalaryText.getText()), Double.valueOf(netSalaryText.getText()),employmentSector,employmentType, professionText.getText(),loanType, LocalDate.now());

                String result = "";
                result = lmodel.createIndividualLoanRequest(newLoan);

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
