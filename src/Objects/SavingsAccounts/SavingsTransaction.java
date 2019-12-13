package Objects.SavingsAccounts;

import java.time.LocalDate;

public class SavingsTransaction {

    private String accountNumber;
    private LocalDate transactionDate;
    private Double amount;
    private String tellerType;
    private String trasactionType;

    public SavingsTransaction(String accountNumber, LocalDate transactionDate, Double amount, String tellerType, String trasactionType) {
        this.accountNumber = accountNumber;
        this.transactionDate = transactionDate;
        this.amount = amount;
        this.tellerType = tellerType;
        this.trasactionType = trasactionType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getTellerType() {
        return tellerType;
    }

    public void setTellerType(String tellerType) {
        this.tellerType = tellerType;
    }

    public String getTrasactionType() {
        return trasactionType;
    }

    public void setTrasactionType(String trasactionType) {
        this.trasactionType = trasactionType;
    }
}
