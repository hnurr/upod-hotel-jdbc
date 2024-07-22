package model;

import java.util.List;

public class Customer {

    private int costumerId;
    private String costumerFirstName;
    private String customerLastName;
    private String costumerEmail;
    private String costumerAddress;
    private String costumerPhone;

    public Customer(int custumerId, String costumerFirstName, String customerLastName, String costumerEmail, String costumerAddress, String costumerPhone) {
        this.costumerId = custumerId;
        this.costumerFirstName = costumerFirstName;
        this.customerLastName = customerLastName;
        this.costumerEmail = costumerEmail;
        this.costumerAddress = costumerAddress;
        this.costumerPhone = costumerPhone;

    }

    public int getCostumerId() {
        return costumerId;
    }

    public void setCostumerId(int costumerId) {
        this.costumerId = costumerId;
    }

    public String getCostumerFirstName() {
        return costumerFirstName;
    }

    public void setCostumerFirstName(String costumerFirstName) {
        this.costumerFirstName = costumerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public String getCostumerEmail() {
        return costumerEmail;
    }

    public void setCostumerEmail(String costumerEmail) {
        this.costumerEmail = costumerEmail;
    }

    public String getCostumerAddress() {
        return costumerAddress;
    }

    public void setCostumerAddress(String costumerAddress) {
        this.costumerAddress = costumerAddress;
    }

    public String getCostumerPhone() {
        return costumerPhone;
    }

    public void setCostumerPhone(String costumerPhone) {
        this.costumerPhone = costumerPhone;
    }


}
