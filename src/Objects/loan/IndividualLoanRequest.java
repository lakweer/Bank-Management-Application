package Objects.loan;

import java.time.LocalDate;

public class IndividualLoanRequest {
    private String RequestId;
    private String CustomerId;
    private String BranchId;
    private Double Amount;
    private String ApprovedStatus;
    private String EmployeeId;
    private Double GrossSalary;
    private Double NetSalary;
    private String LoanId;
    private String EmploymentSector;
    private String EmploymentType;
    private String Profession;
    private String LoanTypeId;
    private LocalDate RequestDate;
    private Integer SettlementPeriod;
    private Integer NoOfSettlements;

//    public IndividualLoanRequest(String requestId, String customerId, String branchId, Double amount, String approvedStatus, String employeeId, Double grossSalary, Double netSalary, String loanId, String employmentSector, String employmentType, String profession,String loanTypeId,LocalDate requestDate) {
//        RequestId = requestId;
//        CustomerId = customerId;
//        BranchId = branchId;
//        Amount = amount;
//        ApprovedStatus = approvedStatus;
//        EmployeeId = employeeId;
//        GrossSalary = grossSalary;
//        NetSalary = netSalary;
//        LoanId = loanId;
//        EmploymentSector = employmentSector;
//        EmploymentType = employmentType;
//        Profession = profession;
//        LoanTypeId = loanTypeId;
//        RequestDate = requestDate;
//    }

    public IndividualLoanRequest(String customerId, String branchId, Double amount, String employeeId, Double grossSalary, Double netSalary, String employmentSector, String employmentType, String profession,String loanTypeId,Integer settlementPeriod, Integer noOfSettlements, LocalDate requestDate) {

        CustomerId = customerId;
        BranchId = branchId;
        Amount = amount;

        EmployeeId = employeeId;
        GrossSalary = grossSalary;
        NetSalary = netSalary;

        EmploymentSector = employmentSector;
        EmploymentType = employmentType;
        Profession = profession;
        LoanTypeId = loanTypeId;
        SettlementPeriod = settlementPeriod;
        NoOfSettlements = noOfSettlements;
        RequestDate = requestDate;
    }

    public String getRequestId() {
        return RequestId;
    }

    public void setRequestId(String requestId) {
        RequestId = requestId;
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
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

    public Double getGrossSalary() {
        return GrossSalary;
    }

    public void setGrossSalary(Double grossSalary) {
        GrossSalary = grossSalary;
    }

    public Double getNetSalary() {
        return NetSalary;
    }

    public void setNetSalary(Double netSalary) {
        NetSalary = netSalary;
    }

    public String getLoanId() {
        return LoanId;
    }

    public void setLoanId(String loanId) {
        LoanId = loanId;
    }

    public String getEmploymentSector() {
        return EmploymentSector;
    }

    public void setEmploymentSector(String employmentSector) {
        EmploymentSector = employmentSector;
    }

    public String getEmploymentType() {
        return EmploymentType;
    }

    public void setEmploymentType(String employmentType) {
        EmploymentType = employmentType;
    }

    public String getProfession() {
        return Profession;
    }

    public void setProfession(String profession) {
        Profession = profession;
    }

    public String getLoanTypeId() {
        return LoanTypeId;
    }

    public Integer getSettlementPeriod() {
        return SettlementPeriod;
    }

    public Integer getNoOfSettlements() {
        return NoOfSettlements;
    }

    public void setNoOfSettlements(Integer noOfSettlements) {
        NoOfSettlements = noOfSettlements;
    }

    public void setSettlementPeriod(Integer settlementPeriod) {
        SettlementPeriod = settlementPeriod;
    }

    public void setLoanTypeId(String loanTypeId) {
        LoanTypeId = loanTypeId;
    }

    public LocalDate getRequestDate() {
        return RequestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        RequestDate = requestDate;
    }
}
