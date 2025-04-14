package com.example.Models;

public class Appointment {
    
    public String patient, doctor, time, clinicName;

    public Appointment(String patient, String doctor, String time, String clinicName)
    {
        this.patient = patient;
        this.doctor = doctor;
        this.time = time;
        this.clinicName = clinicName;
    }
}
