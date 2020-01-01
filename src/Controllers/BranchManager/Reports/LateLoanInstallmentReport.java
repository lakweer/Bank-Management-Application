package Controllers.BranchManager.Reports;

import Controllers.BranchManager.BranchMangerHome;
import Models.RepotsModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.HashMap;

public class LateLoanInstallmentReport {

    private BranchMangerHome parent;
    private RepotsModel model;

    public LateLoanInstallmentReport(BranchMangerHome parent){
        this.parent = parent;
        this.model = new RepotsModel();
    }

    public GridPane getLateLoanReport(BorderPane pane){
        GridPane pane1 = new GridPane();
        pane.setCenter(pane1);
        pane1.setHgap(12);
        pane1.setVgap(12);
        pane1.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

        String[] result = model.getLateLoanReports(parent.getBranchID());

        //Title
        Label headerLabel = new Label("Late Loan Installment Report");
        headerLabel.setFont(Font.font("System", FontWeight.BOLD, 35));
        pane1.add(headerLabel, 1, 1, 4, 1);


        Label TypeLabel = new Label("Expected Late Charges From Org Loans :");
        pane1.add(TypeLabel, 1, 2);

        Label countLabel = new Label("Rs " + result[0]);
        pane1.add(countLabel, 2, 2);

        Label amountLabel = new Label("Received Late Charges From Org Loans :");
        pane1.add(amountLabel, 1, 3);

        Label withdrawTypeLabel = new Label("Rs " + result[1]);
        pane1.add(withdrawTypeLabel, 2, 3);

        Label withdrawcountLabel = new Label("Expected Late Charges From Indi Loans :");
        pane1.add(withdrawcountLabel, 1, 4);

        Label withdrawamountLabel = new Label("Rs " + result[2]);
        pane1.add(withdrawamountLabel, 2, 4);

        Label depositLabel = new Label("Received Late Charges From Org Loans :");
        pane1.add(depositLabel, 1, 5);

        Label depositcountLabel = new Label("Rs "+ result[3]);
        pane1.add(depositcountLabel, 2, 5);


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
