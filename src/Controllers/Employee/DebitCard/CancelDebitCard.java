package Controllers.Employee.DebitCard;

import Controllers.Employee.EmployeeHome;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.time.LocalDate;

public class CancelDebitCard {

    private EmployeeHome parent;

    /*
    Card Number, Pin Number, Account Number, Issued Date Expiry Date
     */

    public CancelDebitCard(EmployeeHome parent){
        this.parent = parent;
    }

    public GridPane openDebitCardPane(BorderPane pane) {
        GridPane pane1 = new GridPane();
        pane.setCenter(pane1);
        pane1.setHgap(10);
        pane1.setVgap(10);
        pane1.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

        //Title
        Label headerLabel = new Label("Cancel Debit Card");
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
