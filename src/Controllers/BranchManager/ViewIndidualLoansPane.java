package Controllers.BranchManager;

import Models.LoanModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import java.util.ArrayList;
import java.util.HashMap;


public class ViewIndidualLoansPane {

    private Pagination pagination;
    private BranchMangerHome parent;
    private LoanModel loanModel;
    private ArrayList<HashMap<String, String>> loans;
    private int count =0;
    private int ar_size;


    //TABLE VIEW AND DATA
    private ObservableList<ObservableList> data  = FXCollections.observableArrayList();
    private TableView tableview =new TableView();

    public   ViewIndidualLoansPane(BranchMangerHome parent){
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
                Label LoanType = new Label("Loan Type : " + loans.get(pageIndex).get("LoanTypeName"));
                Label Amount = new Label("Amount : " + loans.get(pageIndex).get("Amount"));
                Label requestedDate = new Label("Date Requested : " + loans.get(pageIndex).get("requestDate"));
                Label requestedBy = new Label("Requested Employee : " + loans.get(pageIndex).get("EmployeeId"));
                Label settlementPeriod = new Label("Settlement Period : " + loans.get(pageIndex).get("SettlementPeriod"));
                Label numOfSettlements = new Label("Number of Settlements : " + loans.get(pageIndex).get("NoOfSettlements"));

                Label customerDetails = new Label("Customer Details");
                customerDetails.setUnderline(true);
                customerDetails.setPadding(new Insets(10, 10, 10, 10));

                Label fullName = new Label("Full Name : " + loans.get(0).get("FirstName")+ " " + loans.get(pageIndex).get("LastName"));
                Label nic = new Label("Nic :" + loans.get(pageIndex).get("Nic"));
                Label employeementSector = new Label("Customer Employment Sector : " + loans.get(pageIndex).get("EmploymentSector"));
                Label employementType = new Label("Employment Type : " + loans.get(pageIndex).get("EmploymentType"));
                Label proffesion = new Label("Profession : " + loans.get(pageIndex).get("Profession"));
                Label grossSalary = new Label("grossSalary : " + loans.get(pageIndex).get("GrossSalary"));
                Label netSalary = new Label("Net Salary : " + loans.get(pageIndex).get("NetSalary"));
                netSalary.setPadding(new Insets(5, 5, 15, 5));


                GridPane gp = new GridPane();
                gp.setHgap(3);
                Button approveButton = new Button("Approve");
                approveButton.setDefaultButton(true);
                gp.add(approveButton,1,1,2,1);



                Button rejectButton = new Button("Reject");
                rejectButton.setDefaultButton(true);
                gp.add(rejectButton,3,1,2,1);


                element.getChildren().addAll(loanDetails, link, LoanType, Amount, requestedDate, requestedBy, settlementPeriod, numOfSettlements,
                        customerDetails, fullName, nic, employeementSector, employementType, proffesion, grossSalary, netSalary);
                box.getChildren().add(element);

                if(loans.get(pageIndex).get("ApprovedStatus").equals("PENDING")){
                    element.getChildren().addAll(gp);
                }

                approveButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        String r = loanModel.approveLoanRequest(loans.get(pageIndex).get("RequestId"), loans.get(pageIndex).get("Amount"),
                                loans.get(pageIndex).get("LoanTypeId"), loans.get(pageIndex).get("SettlementPeriod"),loans.get(pageIndex).get("NoOfSettlements"));
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

    public GridPane viewIndividualLoans(BorderPane pane){
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
