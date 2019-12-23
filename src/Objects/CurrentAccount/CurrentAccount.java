package Objects.CurrentAccount;

public class CurrentAccount {
    private String accountNumber;
    private String branchId;
    private String customerId;
    private Double balance;
    private String status;

    public CurrentAccount(){

    }

    public CurrentAccount(String accountNumber,String branchId,String customerId, Double amount, String status){
        this.accountNumber = accountNumber;
        this.branchId = branchId;
        this.customerId = customerId;
        this.balance = amount;
        this.status = status;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
