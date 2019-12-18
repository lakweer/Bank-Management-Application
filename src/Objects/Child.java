package Objects;

import Objects.Customer;

import java.time.LocalDate;

public class Child extends Customer {

    private String fullName;
    private LocalDate dob;
    private String guardianId;

    public Child(String fullName, LocalDate dob, String guardianId) {
        this.fullName = fullName;
        this.dob = dob;
        this.guardianId = guardianId;
    }

    public Child(String customerType, String fullName, LocalDate dob, String guardianId) {
        super(customerType);
        this.fullName = fullName;
        this.dob = dob;
        this.guardianId = guardianId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getGuardianId() {
        return guardianId;
    }

    public void setGuardianId(String guardianId) {
        this.guardianId = guardianId;
    }
}
