package Objects.FixedDepositAccount;

import java.time.LocalDate;

public class FixedDepositAccount {
    private String fdAccountNumber;
    private String linkedSavingsAccountNumber;
    private String customerId;
    private Double depositAmount;
    private LocalDate depositDate;
    private LocalDate matuarityDate;
    private String fdType;

    public FixedDepositAccount(String fdAccountNumber, String linkedSavingsAccountNumber, String customerId, Double depositAmount, LocalDate depositDate, LocalDate matuarityDate, String fdType) {
        this.fdAccountNumber = fdAccountNumber;
        this.linkedSavingsAccountNumber = linkedSavingsAccountNumber;
        this.customerId = customerId;
        this.depositAmount = depositAmount;
        this.depositDate = depositDate;
        this.matuarityDate = matuarityDate;
        this.fdType = fdType;
    }

    public String getFdAccountNumber() {
        return fdAccountNumber;
    }

    public void setFdAccountNumber(String fdAccountNumber) {
        this.fdAccountNumber = fdAccountNumber;
    }

    public String getLinkedSavingsAccountNumber() {
        return linkedSavingsAccountNumber;
    }

    public void setLinkedSavingsAccountNumber(String linkedSavingsAccountNumber) {
        this.linkedSavingsAccountNumber = linkedSavingsAccountNumber;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Double getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(Double depositAmount) {
        this.depositAmount = depositAmount;
    }

    public LocalDate getDepositDate() {
        return depositDate;
    }

    public void setDepositDate(LocalDate depositDate) {
        this.depositDate = depositDate;
    }

    public LocalDate getMatuarityDate() {
        return matuarityDate;
    }

    public void setMatuarityDate(LocalDate matuarityDate) {
        this.matuarityDate = matuarityDate;
    }

    public String getFdType() {
        return fdType;
    }

    public void setFdType(String fdType) {
        this.fdType = fdType;
    }
}
