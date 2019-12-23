package Controllers.Employee.CurrentAccount;


import Controllers.Employee.EmployeeHome;
import Helpers.Helpers;
import Models.CurrentAccountModel;
import Objects.CurrentAccount.CurrentAccount;
import Validator.FormValidator;
import javafx.event.ActionEvent;
        import javafx.event.EventHandler;
        import javafx.geometry.HPos;
        import javafx.geometry.Side;
        import javafx.scene.control.*;
        import javafx.scene.layout.BorderPane;
        import javafx.scene.layout.GridPane;
        import javafx.scene.layout.Region;
        import javafx.scene.text.Font;
        import javafx.scene.text.FontWeight;
        import java.time.LocalDate;

public class CurrentAccountOpen {

    private EmployeeHome parent;
    private CurrentAccountModel currentModel;
    private String accountType = "";
    GridPane pane1 = new GridPane();

    private Label IndividualNICLabel =new Label("Individual NIC : ");
    private Label OrganizationRegNumberLabel =new Label("Organization Reg Number : ");
    private TextField organizationText = new TextField();
    private TextField individualText = new TextField();



    public CurrentAccountOpen(EmployeeHome parent){
        this.parent = parent;
        this.currentModel = new CurrentAccountModel();
    }

    public GridPane openCurrentPane(BorderPane pane) {
        pane.setCenter(pane1);
        pane1.setHgap(10);
        pane1.setVgap(10);
        pane1.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

        //Title
        Label headerLabel = new Label("Current Account Open");
        headerLabel.setFont(Font.font("System", FontWeight.BOLD, 35));
        pane1.add(headerLabel,1,1,4,1);

        //Branch Label
        Label branchLabel = new Label("Branch : " + parent.getBranchID());
        pane1.add(branchLabel,1,2,2,1);

        //Today Date Label
        Label dateLabel = new Label("Date : " + LocalDate.now().toString());
        pane1.add(dateLabel,4,2);

        //Account type menu label
        Label accountTypeLabel = new Label("Account type : ");
        pane1.add(accountTypeLabel,1,3);

        //Account type Menu
        MenuItem individualType = new MenuItem("Individual");
        MenuItem organizationType = new MenuItem("Organization");

        MenuButton accountTypeMenu = new MenuButton("Account Type", null, individualType, organizationType);
        accountTypeMenu.setFont(Font.font("System",15));
        accountTypeMenu.setPrefSize(140,32);
        accountTypeMenu.setPopupSide(Side.BOTTOM);
        pane1.add(accountTypeMenu,2,3);

        //Account Number
        Label accNumberLabel = new Label("Account Number : ");
        pane1.add(accNumberLabel,1,4,3,1);

        TextField accnumberText = new TextField();
        pane1.add(accnumberText,3,4,3,1);
        pane1.setHalignment(accnumberText, HPos.CENTER);

        Separator separator1 = new Separator();
        pane1.add(separator1,1,5,10,1);


        //Cancel Button
        Button cancelButton = new Button("Cancel");
        cancelButton.setDefaultButton(true);
        cancelButton.setPrefSize(73,35);
        pane1.add(cancelButton,1,19);
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
        submitButton.setPrefSize(73,35);
        pane1.add(submitButton,5,19);
        pane1.setHalignment(submitButton, HPos.CENTER);
        submitButton.relocate(616,658);

        individualType.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                accountType = "Individual";
                accountTypeMenu.setText(accountType);
                removeOrganizationSection();
                addIndividualNIC();
            }
        });

        organizationType.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                accountType = "Organization";
                accountTypeMenu.setText(accountType);
                removeIndividualSection();
                addOrganizationRegNumber();
            }
        });

        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String tempId;
                if(!accnumberText.getText().isEmpty() && (!organizationText.getText().isEmpty() || !individualText.getText().isEmpty())){
                    if(accountType=="Individual"){
                        tempId = individualText.getText();
                        System.out.println("Individual");
                    }
                    else{
                        tempId = organizationText.getText();
                        System.out.println("organization");
                    }
                    Boolean value = true;
                    if(accountType=="Individual") {
                        if (FormValidator.nicNumberValidate(individualText.getText())) {
                            value = true;
                        } else {
                            value = false;
                        }
                    }

                    if(value){
                        CurrentAccount account = new CurrentAccount(accnumberText.getText(), parent.getBranchID(),
                                tempId, 0.00, "1" );

                        String result = currentModel.openCurrentAccount(account,LocalDate.now().toString(), accountType,  parent);
                        if(result.equals("Success")){
                            Helpers.showAlert(Alert.AlertType.CONFIRMATION, pane.getScene().getWindow(), "Success!", "Account opened!");
                            parent.enablePane();
                            pane.getChildren().remove(pane1);
                        }
                        else{
                            Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", result);
                            return;
                        }

                    }else{
                        Helpers.showAlert(Alert.AlertType.ERROR, pane.getScene().getWindow(), "Form Error!", "Wrong NIC");
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


    private void addIndividualNIC(){
        if(!pane1.getChildren().contains(IndividualNICLabel)) {
            pane1.add(IndividualNICLabel, 1, 8, 2, 1);
            pane1.add(individualText, 3, 8);
        }
    }

    private void addOrganizationRegNumber(){
        if(!pane1.getChildren().contains(OrganizationRegNumberLabel)) {
            pane1.add(OrganizationRegNumberLabel, 1, 8, 2, 1);
            pane1.add(organizationText, 3, 8);
        }
    }

    private void removeOrganizationSection(){
        if(pane1.getChildren().contains(OrganizationRegNumberLabel)){
            pane1.getChildren().removeAll(OrganizationRegNumberLabel,organizationText);
        }
    }

    private void removeIndividualSection(){
        if(pane1.getChildren().contains(IndividualNICLabel)){
            pane1.getChildren().removeAll(IndividualNICLabel,individualText);
        }
    }



}

