package appointmentManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class CreateAppointment {

    public boolean createNewAppointment(Appointment appointment) {
        String dbUrl = "jdbc:mysql://ccscloud.dlsu.edu.ph:20183/apptMCO2?user=advdb&connectTimeout=3000";

        // Using try-with-resources for automatic resource management
        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO appointments (patientID, clinicID, doctorID, apptStatus, TimeQueued, QueueDate, StartTime, consultationType, virtualConsultation, EndTime) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

            Class.forName("com.mysql.cj.jdbc.Driver");

            pstmt.setString(1, appointment.patientID);
            pstmt.setString(2, appointment.clinicID);
            pstmt.setString(3, appointment.doctorID);
            // Set defaults or use provided values
            pstmt.setString(4, appointment.apptStatus != null ? appointment.apptStatus : "Queued");
            pstmt.setTimestamp(5, appointment.TimeQueued != null ? new Timestamp(appointment.TimeQueued.getTime()) : new Timestamp(System.currentTimeMillis()));
            pstmt.setTimestamp(6, appointment.QueueDate != null ? new Timestamp(appointment.QueueDate.getTime()) : new Timestamp(System.currentTimeMillis()));
            pstmt.setTimestamp(7, new Timestamp(appointment.StartTime.getTime()));
            pstmt.setString(8, appointment.consultationType);
            pstmt.setInt(9, appointment.virtualConsultation);
            pstmt.setTimestamp(10, appointment.EndTime != null ? new Timestamp(appointment.EndTime.getTime()) : null);

            pstmt.executeUpdate();
            return true; // Successfully created the appointment
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false; // Indicate failure
        }
    }
}
