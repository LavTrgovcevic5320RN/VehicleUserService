package com.example.dto;

import java.sql.Date;

public class ClientQueueDto {
    private Long userId;
    private String email;
    private String firstName;
    private String lastName;
    private Date birthday;
    private Boolean increment;
    private Long rentingId;
    private String companyName;
    private String city;
    private Long managerId;
    private String managerEmail;

    @Override
    public String toString() {
        return "ClientQueueDto{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthday=" + birthday +
                ", increment=" + increment +
                ", rentingId=" + rentingId +
                ", companyName='" + companyName + '\'' +
                ", city='" + city + '\'' +
                ", managerId=" + managerId +
                ", managerEmail='" + managerEmail + '\'' +
                '}';
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Boolean getIncrement() {
        return increment;
    }

    public void setIncrement(Boolean increment) {
        this.increment = increment;
    }

    public Long getRentingId() {
        return rentingId;
    }

    public void setRentingId(Long rentingId) {
        this.rentingId = rentingId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public String getManagerEmail() {
        return managerEmail;
    }

    public void setManagerEmail(String managerEmail) {
        this.managerEmail = managerEmail;
    }
}
