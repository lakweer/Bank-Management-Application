package Objects;


import java.time.LocalDate;

public class Employee {
    private String employeeId;
    private String firstName;
    private  String lastName;
    private String nic;
    private String userName;
    private String password;
    private String email;
    private String branchId;
    private LocalDate dateOfBirth;
    private String empType;
    private String joinedDate;

    public Employee(String employeeId, String firstName, String lastName, String nic, String empType, String joinedDate) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nic = nic;
        this.empType = empType;
        this.joinedDate = joinedDate;
    }

    public Employee(String userName, String branchId){
        this.userName = userName;
        this.branchId = branchId;
    }

    public Employee(String firstName, String lastName, String nic, String userName, String password, String email, String branchId, LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nic = nic;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.branchId = branchId;
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
