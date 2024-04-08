package appointmentManagement;

import java.util.Date;

public class Appointment {
    public String patientID;
    public String clinicID;
    public String doctorID;
    public String appointmentID;
    public String apptStatus;
    public Date TimeQueued;
    public Date QueueDate;
    public Date StartTime;
    public Date EndTime;
    public String consultationType;
    public Integer virtualConsultation;

    public Appointment(String patientID, String clinicID, String doctorID, String appointmentID, String apptStatus, Date timeQueued, Date queueDate, Date startTime, Date endTime, String consultationType, Integer virtualConsultation) {
        this.patientID = patientID;
        this.clinicID = clinicID;
        this.doctorID = doctorID;
        this.appointmentID = appointmentID;
        this.apptStatus = apptStatus;
        TimeQueued = timeQueued;
        QueueDate = queueDate;
        StartTime = startTime;
        EndTime = endTime;
        this.consultationType = consultationType;
        this.virtualConsultation = virtualConsultation;
    }

    public Appointment(){}
}
