package Objects;


public class Employee {
    private String first_name;
    private  String last_name;
    private String nic;
    private String username;
    private String password;


    public Employee(String first_name, String last_name, String nic, String username, String password){
        this.first_name=first_name;
        this.last_name=last_name;
        this.nic=nic;
        this.username=username;
        this.password=password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String get_nic() {
        return nic;
    }

}
