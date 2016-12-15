package com.netcracker.jaxb.templates.case2;

public class Rector {
    private String name;
    private String surname;
    private Contacts contacts;

    public String getName() {
        return name;
    }

    public Rector setName(String name) {
        this.name = name;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public Rector setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public Contacts getContacts() {
        return contacts;
    }

    public Rector setContacts(Contacts contacts) {
        this.contacts = contacts;
        return this;
    }
}
