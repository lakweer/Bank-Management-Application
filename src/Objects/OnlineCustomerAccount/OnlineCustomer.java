package Objects.OnlineCustomerAccount;

public class OnlineCustomer {
    /*
    CustomerId	Username	Password	Status
     */

    private String CustomerId;
    private String Username;
    private  String password;
    private String Status;

    public OnlineCustomer() {
    }


    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
