package mx.cetys.jorgepayan.whatsonsale.Models;

/**
 * Created by jorge.payan on 11/17/17.
 */

public class Customer {
    private String customerId;
    private String userEmail;
    private String name;
    private int age;
    private String gender;

    public Customer(String customerId, String userEmail, String name, int age, String gender) {
        this.customerId = customerId;
        this.userEmail = userEmail;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
