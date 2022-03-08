package com.example.myfirstproject;

public class Customers {
    private Integer salesRepEmployeeNumber;
    private Integer customerNumber;
    private String companyName;
    private String contactFirstName;
    private String contactLastName;
    private String city;
    private String phone;

    public Customers(Integer salesRepEmployeeNumber, Integer customerNumber, String companyName,
                     String contactFirstName, String contactLastName,  String city,String phone) {
        this.salesRepEmployeeNumber = salesRepEmployeeNumber;
        this.customerNumber = customerNumber;
        this.companyName = companyName;
        this.contactFirstName = contactFirstName;
        this.contactLastName = contactLastName;
        this.city = city;
        this.phone=phone;
    }

    public Integer getSalesRepEmployeeNumber() {
        return salesRepEmployeeNumber;
    }

    public void setSalesRepEmployeeNumber(Integer salesRepEmployeeNumber) {
        this.salesRepEmployeeNumber = salesRepEmployeeNumber;
    }

    public Integer getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(Integer customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactFirstName() {
        return contactFirstName;
    }

    public void setContactFirstName(String contactFirstName) {
        this.contactFirstName = contactFirstName;
    }

    public String getContactLastName() {
        return contactLastName;
    }

    public void setContactLastName(String contactLastName) {
        this.contactLastName = contactLastName;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Customers{" +
                "salesRepEmployeeNumber=" + salesRepEmployeeNumber +
                ", customerNumber=" + customerNumber +
                ", companyName='" + companyName + '\'' +
                ", contactFirstName='" + contactFirstName + '\'' +
                ", contactLastName='" + contactLastName + '\'' +
                ", city='" + city + '\'' +phone+
                '}';
    }
}
