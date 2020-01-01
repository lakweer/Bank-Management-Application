package Objects.loan;

import java.time.LocalDate;

public class OrgLoanRequest {

        private String RequestId;
        private String RegisterId;
        private String BranchId;
        private Double Amount;
        private String ApprovedStatus;
        private String EmployeeId;
        private Double ProjectGrossValue;
        private String LoanId;
        private String LoanReason;
        private String OrganizationType;
        private LocalDate RequestDate;
        private Integer SettlementPeriod;
        private Integer NoOfSettlements;

//    public OrgLoanRequest(String requestId, String registerId, String branchId, Double amount, String approvedStatus, String employeeId, Double projectGrossValue, String loanId, String loanReason, String organizationType,LocalDate requestDate) {
//
//        RequestId = requestId;
//        RegisterId = registerId;
//        BranchId = branchId;
//        Amount = amount;
//        ApprovedStatus = approvedStatus;
//        EmployeeId = employeeId;
//        ProjectGrossValue = projectGrossValue;
//        LoanId = loanId;
//        LoanReason = loanReason;
//        OrganizationType = organizationType;
//        RequestDate = requestDate;
//
//    }


    public OrgLoanRequest(String registerId, String branchId, Double amount, String employeeId, Double projectGrossValue, String loanReason, String organizationType, Integer settlementPeriod, Integer noOfSettlements, LocalDate requestDate) {

        RegisterId = registerId;
        BranchId = branchId;
        Amount = amount;

        EmployeeId = employeeId;
        ProjectGrossValue = projectGrossValue;

        LoanReason = loanReason;
        OrganizationType = organizationType;
        SettlementPeriod = settlementPeriod;
        NoOfSettlements = noOfSettlements;
        RequestDate = requestDate;

    }

    public String getRequestId() {
        return RequestId;
    }

    public String getRegisterId() {
        return RegisterId;
    }

    public void setRegisterId(String registerId) {
        RegisterId = registerId;
    }

    public String getBranchId() {
        return BranchId;
    }

    public void setBranchId(String branchId) {
        BranchId = branchId;
    }

    public Double getAmount() {
        return Amount;
    }

    public void setAmount(Double amount) {
        Amount = amount;
    }

    public String getApprovedStatus() {
        return ApprovedStatus;
    }

    public void setApprovedStatus(String approvedStatus) {
        ApprovedStatus = approvedStatus;
    }

    public String getEmployeeId() {
        return EmployeeId;
    }

    public void setEmployeeId(String employeeId) {
        EmployeeId = employeeId;
    }

    public Double getProjectGrossValue() {
        return ProjectGrossValue;
    }

    public void setProjectGrossValue(Double projectGrossValue) {
        ProjectGrossValue = projectGrossValue;
    }

    public String getLoanId() {
        return LoanId;
    }

    public void setLoanId(String loanId) {
        LoanId = loanId;
    }

    public String getLoanReason() {
        return LoanReason;
    }

    public void setLoanReason(String loanReason) {
        LoanReason = loanReason;
    }

    public String getOrganizationType() {
        return OrganizationType;
    }

    public void setOrganizationType(String organizationType) {
        OrganizationType = organizationType;
    }

    public LocalDate getRequestDate() {
        return RequestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        RequestDate = requestDate;
    }

    public void setRequestId(String requestId) {
        RequestId = requestId;
    }

    public Integer getSettlementPeriod() {
        return SettlementPeriod;
    }

    public void setSettlementPeriod(Integer settlementPeriod) {
        SettlementPeriod = settlementPeriod;
    }

    public Integer getNoOfSettlements() {
        return NoOfSettlements;
    }

    public void setNoOfSettlements(Integer noOfSettlements) {
        NoOfSettlements = noOfSettlements;
    }
}
