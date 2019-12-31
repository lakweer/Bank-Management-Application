package Controllers.BranchManager;

import Models.LoanModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewOrganizationLoansPane {

    private Pagination pagination;
    private BranchMangerHome parent;
    private LoanModel loanModel;
    private ArrayList<HashMap<String, String>> loans;
    private int count =0;
    private int ar_size;

    public   ViewOrganizationLoansPane(BranchMangerHome parent){
        this.parent =parent;
        this.loanModel = new LoanModel();
        this.loans = loanModel.viewIndividulaLoanRequests(parent.getBranchID());
        this.ar_size=loans.size();
    }


    public int itemsPerPage() {
        return 1;
    }


    public VBox createPage(int pageIndex) {
        if(ar_size > 0){
            VBox box = new VBox(25);
            box.setPadding(new Insets(10, 10, 10, 10));
            int page = pageIndex * itemsPerPage();
            for (int i = page; i < page + itemsPerPage(); i++) {
                VBox element = new VBox();
                element.setPadding(new Insets(10, 10, 10, 10));
                element.setSpacing(5);

                Label loanDetails = new Label("Loan Details");
                loanDetails.setUnderline(true);
                loanDetails.setPadding(new Insets(10, 10, 10, 10));

                Label link = new Label("RequestId : " + (loans.get(pageIndex).get("RequestId")));
                Label Amount = new Label("Amount : " + loans.get(pageIndex).get("Amount"));
                Label requestedDate = new Label("Project Gross Value : " + loans.get(pageIndex).get("ProjectGrossValue"));
                Label loanReason = new Label("Reason For Loan : " + loans.get(pageIndex).get("LoanReason"));
                Label requestedBy = new Label("Requested Employee : " + loans.get(pageIndex).get("EmployeeId"));
                Label settlementPeriod = new Label("Settlement Period : " + 5);
                Label numOfSettlements = new Label("Number of Settlements : " + 5);

                Label customerDetails = new Label("Organization Details");
                customerDetails.setUnderline(true);
                customerDetails.setPadding(new Insets(10, 10, 10, 10));

                Label organizationType = new Label("Customer Employment Sector : " + loans.get(pageIndex).get("OrganizationType"));
                organizationType.setPadding(new Insets(5, 5, 15, 5));


                GridPane gp = new GridPane();
                gp.setHgap(3);
                Button approveButton = new Button("Approve");
                approveButton.setDefaultButton(true);
                gp.add(approveButton,1,1,2,1);



                Button rejectButton = new Button("Reject");
                rejectButton.setDefaultButton(true);
                gp.add(rejectButton,3,1,2,1);


                element.getChildren().addAll(loanDetails, link, Amount, requestedDate, loanReason, requestedBy, settlementPeriod, numOfSettlements,
                        customerDetails);
                box.getChildren().add(element);

                if(loans.get(pageIndex).get("ApprovedStatus").equals("PENDING")){
                    element.getChildren().addAll(gp);
                }

                approveButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        String r = loanModel.approveLoanRequest(loans.get(pageIndex).get("RequestId"), loans.get(pageIndex).get("Amount"),
                                loans.get(pageIndex).get("LoanTypeId"), "5","5");
                        if(r.equals("Success")){
                            loans.get(pageIndex).replace("ApprovedStatus","REJECTED");
                            element.getChildren().removeAll(gp);
                        }
                    }
                });

                rejectButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Boolean r = loanModel.rejectIndividualLoanRequest(loans.get(pageIndex).get("RequestId"));
                        if(r){
                            loans.get(pageIndex).replace("ApprovedStatus","");
                            element.getChildren().removeAll(gp);
                        }
                    }
                });

            }
            return box;
        }
        else {
            Label l = new Label("No Loan Requests");
            VBox v = new VBox();
            v.getChildren().addAll(l);
            return v;
        }
    }

    public GridPane viewOrganizationLoans(BorderPane pane){
        GridPane pane1 = new GridPane();
        pane.setCenter(pane1);
        pane1.setHgap(10);
        pane1.setVgap(10);
        pane1.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

        if(ar_size > 0){
            pagination = new Pagination(ar_size, 0);
        }else {
            pagination = new Pagination(1, 0);
        }

        pagination.setStyle("-fx-border-color:red;");
        pagination.setPageFactory((Integer pageIndex) -> createPage(pageIndex));

        AnchorPane anchor = new AnchorPane();
        AnchorPane.setTopAnchor(pagination, 10.0);
        AnchorPane.setRightAnchor(pagination, 10.0);
        AnchorPane.setBottomAnchor(pagination, 10.0);
        AnchorPane.setLeftAnchor(pagination, 10.0);
        anchor.getChildren().addAll(pagination);

        pane1.add(anchor,1,1);

        //Cancel Button
        Button cancelButton = new Button("Back");
        cancelButton.setDefaultButton(true);
        cancelButton.setPrefSize(73,35);
        pane1.add(cancelButton,1,10);

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
