public class Doctor extends Person {
    
    public String speciality, startWorkingHours, endWorkingHours;

    public Doctor(String firstname,String lastname,String phoneNumber, String email, String speciality, String startWorkingHours, String endWorkingHours)
    {
        super(firstname,lastname, phoneNumber,email);
        this.speciality = speciality;
        this.startWorkingHours = startWorkingHours;
        this.endWorkingHours = endWorkingHours;
        

        appointments.push(new Appointment("turki", "basel","10:30","Cardiology"));
        appointments.push(new Appointment("turki", "basel","10:30","Cardiology"));
        appointments.push(new Appointment("turki", "basel","10:30","Cardiology"));
        appointments.push(new Appointment("turki", "basel","10:30","Cardiology"));
        
        
        

    }

}
