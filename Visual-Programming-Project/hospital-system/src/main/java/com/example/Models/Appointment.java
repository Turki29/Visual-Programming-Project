package com.example.Models;

public class Appointment {
    private int id, patientId;
    private String department, doctor, day, time;

    public Appointment(int id, int patientId, String department, String doctor, String day, String time) {
        this.id = id;
        this.patientId = patientId;
        this.department = department;
        this.doctor = doctor;
        this.day = day;
        this.time = time;
    }

    public int getId() { return id; }
    public int getPatientId() { return patientId; }
    public String getDepartment() { return department; }
    public String getDoctor() { return doctor; }
    public String getDay() { return day; }
    public String getTime() { return time; }
}
