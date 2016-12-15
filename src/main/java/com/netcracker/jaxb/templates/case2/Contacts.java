package com.netcracker.jaxb.templates.case2;

public class Contacts {

    private String mobile_number;
    private String email;

    public String getMobile_number() {
        return mobile_number;
    }

    public Contacts setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Contacts setEmail(String email) {
        this.email = email;
        return this;
    }
}
