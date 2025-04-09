package com.example;

import java.util.LinkedList;

public class Person {
    
    public String firstname, lastname, phoneNumber, email;
    public LinkedList<Appointment> appointments;
    public Person(String firstname,String lastname,String phoneNumber, String email)
    {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        appointments = new LinkedList<Appointment>();
    }
}
