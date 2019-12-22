package Objects.loan;

public class loan {
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

    public loan() {

    }

    public loan(String customerId, String branchId, Double amount, String employeeId, Double grossSalary, Double netSalary, String employmentSector, String employmentType, String profession) {
        CustomerId = customerId;
        BranchId = branchId;
        Amount = amount;
        EmployeeId = employeeId;
        GrossSalary = grossSalary;
        NetSalary = netSalary;
        EmploymentSector = employmentSector;
        EmploymentType = employmentType;
        Profession = profession;
    }

    public loan(String approvedStatus) {
        ApprovedStatus = approvedStatus;
    }

    public loan(String requestId, String customerId, String branchId, Double amount, String approvedStatus, String employeeId, Double grossSalary, Double netSalary, String loanId, String employmentSector, String employmentType, String profession) {
        RequestId = requestId;
        CustomerId = customerId;
        BranchId = branchId;
        Amount = amount;
        ApprovedStatus = approvedStatus;
        EmployeeId = employeeId;
        GrossSalary = grossSalary;
        NetSalary = netSalary;
        LoanId = loanId;
        EmploymentSector = employmentSector;
        EmploymentType = employmentType;
        Profession = profession;
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public Double getAmount() {
        return Amount;
    }

    public String getApprovedStatus() {
        return ApprovedStatus;
    }

    public String getEmployeeId() {
        return EmployeeId;
    }

    public Double getGrossSalary() {
        return GrossSalary;
    }

    public Double getNetSalary() {
        return NetSalary;
    }

    public String getEmploymentSector() {
        return EmploymentSector;
    }

    public String getEmploymentType() {
        return EmploymentType;
    }

    public String getProfession() {
        return Profession;
    }

    public String getRequestId() {
        return RequestId;
    }

    public String getBranchId() {
        return BranchId;
    }

    public String getLoanId() {
        return LoanId;
    }

    public void setRequestId(String requestId) {
        RequestId = requestId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

    public void setBranchId(String branchId) {
        BranchId = branchId;
    }

    public void setAmount(Double amount) {
        Amount = amount;
    }

    public void setApprovedStatus(String approvedStatus) {
        ApprovedStatus = approvedStatus;
    }

    public void setEmployeeId(String employeeId) {
        EmployeeId = employeeId;
    }

    public void setGrossSalary(Double grossSalary) {
        GrossSalary = grossSalary;
    }

    public void setNetSalary(Double netSalary) {
        NetSalary = netSalary;
    }

    public void setLoanId(String loanId) {
        LoanId = loanId;
    }

    public void setEmploymentSector(String employmentSector) {
        EmploymentSector = employmentSector;
    }

    public void setEmploymentType(String employmentType) {
        EmploymentType = employmentType;
    }

    public void setProfession(String profession) {
        Profession = profession;
    }


}
