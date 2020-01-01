package Controllers.Employee.DebitCard;

import Controllers.Employee.EmployeeHome;
import Helpers.Helpers;
import Models.CurrentAccountModel;
import Models.DebitCardModel;
import Objects.CurrentAccount.CurrentAccount;
import Validator.FormValidator;
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
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class OpenDebitCard {

    private EmployeeHome parent;
    private LocalDate todayDate;
    private LocalDate expirayDate;
    private DebitCardModel debitModel;

    /*
    Card Number, Pin Number, Account Number, Issued Date Expiry Date
     */

    public OpenDebitCard(EmployeeHome parent){
        this.parent = parent;
        this.todayDate =  LocalDate.now();
        this.expirayDate = todayDate.plusYears(4);
        this.debitModel = new DebitCardModel();
    }

    public GridPane openDebitCardPane(BorderPane pane) {
        GridPane pane1 = new GridPane();
        pane.setCenter(pane1);
        pane1.setHgap(10);
        pane1.setVgap(10);
        pane1.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

        //Title
        Label headerLabel = new Label("Open Debit Card");
        headerLabel.setFont(Font.font("System", FontWeight.BOLD, 35));
        pane1.add(headerLabel, 2, 1, 4, 1);

        //Employee Label
        Label branchLabel = new Label("Employee : " + parent.getUserName());
        pane1.add(branchLabel, 1, 2, 2, 1);

        //Today Date Label
        Label dateLabel = new Label("Date : " + LocalDate.now().toString());
        pane1.setHalignment(dateLabel, HPos.RIGHT);
        pane1.add(dateLabel, 4, 2);

        Label fdNumberLabel = new Label("Debit Card Number : ");
        pane1.add(fdNumberLabel, 1, 4, 2, 1);

        Label savingsNumberLabel = new Label("Savings Account Number : ");
        pane1.add(savingsNumberLabel, 1, 5, 2, 1);

        TextField savingsAccNumberText = new TextField();
        pane1.add(savingsAccNumberText, 3, 5, 2, 1);
        pane1.setHalignment(savingsAccNumberText, HPos.LEFT);

        Label expiaryDateLabel = new Label("Expiry Date : " + expirayDate);
        pane1.add(expiaryDateLabel, 1, 6, 3, 1);


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
        submitButton.relocate(616,658);

        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if(!savingsAccNumberText.getText().isEmpty()){

                    long number = ThreadLocalRandom.current().nextLong(9999999999999999L);
                    String card_number = String.valueOf(number);

                    int pin =  new Random().nextInt(9999);
                    String pin_number = String.valueOf(pin);
                    //String card_number = "1111222255554444";
                    //String pin_number = "4564";
                    String result = debitModel.issueNewDebitCard(card_number,pin_number,savingsAccNumberText.getText(), LocalDate.now().toString(), expirayDate.toString());

                    if(result.equals("Success")){
                        Helpers.showAlert(Alert.AlertType.CONFIRMATION, pane.getScene().getWindow(), "Success!", "Issued new debit card");
                        parent.enablePane();
                        pane.getChildren().remove(pane1);
                    }
                    else{
                        Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", result);
                        return;
                    }

                }
                else {
                    Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Fill All the Fields");
                    return;
                }

            }


        });

        return pane1;
    }
}
