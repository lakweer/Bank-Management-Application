package Controllers.BranchManager.Reports;

import Controllers.BranchManager.BranchMangerHome;
import Models.RepotsModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class SavingsTransactionReport {
    private BranchMangerHome parent;
    private RepotsModel model;

    public SavingsTransactionReport(BranchMangerHome parent){
        this.parent = parent;
        this.model = new RepotsModel();
    }

    public GridPane getReport(BorderPane pane){
        GridPane pane1 = new GridPane();
        pane.setCenter(pane1);
        pane1.setHgap(10);
        pane1.setVgap(10);
        pane1.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

        HashMap<String, ArrayList<String>> result = model.getMonthlyTransactionReport(parent.getBranchID(), "2020-01-01");

        HashMap<String, String > acs = model.getNumberOfActiveAccountsInBranch(parent.getBranchID());

        //Title
        Label headerLabel = new Label("Savings Transaction Report");
        headerLabel.setFont(Font.font("System", FontWeight.BOLD, 35));
        pane1.add(headerLabel, 2, 1, 4, 1);


        Label TypeLabel = new Label("Transaction Type");
        pane1.add(TypeLabel, 1, 2);

        Label countLabel = new Label("No. Transactions");
        pane1.add(countLabel, 2, 2);

        Label amountLabel = new Label("Total Amount (Rs.)");
        pane1.add(amountLabel, 3, 2);

        Label withdrawTypeLabel = new Label(result.get("Withdrawal").get(0));
        pane1.add(withdrawTypeLabel, 1, 3);

        Label withdrawcountLabel = new Label(result.get("Withdrawal").get(1));
        pane1.add(withdrawcountLabel, 2, 3);

        Label withdrawamountLabel = new Label(result.get("Withdrawal").get(2));
        pane1.add(withdrawamountLabel, 3, 3);

        Label depositLabel = new Label(result.get("Deposits").get(0));
        pane1.add(depositLabel, 1, 4);

        Label depositcountLabel = new Label(result.get("Deposits").get(1));
        pane1.add(depositcountLabel, 2, 4);

        Label depositamountLabel = new Label(result.get("Deposits").get(2));
        pane1.add(depositamountLabel, 3, 4);

        //Title
        Label acNoLabel = new Label("Number Of Accounts");
        acNoLabel.setFont(Font.font("System", FontWeight.BOLD, 35));
        pane1.add(acNoLabel, 2, 5, 4, 1);

        Label acTypeLabel = new Label("AccountType   ");
        pane1.add(acTypeLabel, 1, 6);

        Label acCountLabel = new Label("Count");
        pane1.add(acCountLabel, 2, 6);

        Label childrenLabel = new Label("Children");
        pane1.add(childrenLabel, 1, 7);

        Label childrenCountLabel = new Label(acs.get("Children"));
        pane1.add(childrenCountLabel, 2, 7);

        Label teenLabel = new Label("Teen");
        pane1.add(teenLabel, 1, 8);

        Label teenCountLabel = new Label(acs.get("Teen"));
        pane1.add(teenCountLabel, 2, 8);


        Label adultLabel = new Label("Adult");
        pane1.add(adultLabel, 1, 9);

        Label adultCountLabel = new Label(acs.get("18+"));
        pane1.add(adultCountLabel, 2, 9);


        Label seniorLabel = new Label("Senior Citizen");
        pane1.add(seniorLabel, 1, 10);

        Label seniorCountLabel = new Label(acs.get("Senior Citizen"));
        pane1.add(seniorCountLabel, 2, 10);


        //Cancel Button
        Button cancelButton = new Button("Back");
        cancelButton.setDefaultButton(true);
        cancelButton.setPrefSize(73,35);
        pane1.add(cancelButton,1,12);

        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                parent.enablePane();
                pane.getChildren().remove(pane1);
            }
        });

        return pane1;
    }
}
