package appointmentManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class CreateAppointment {
    public int connectionNumber = 1;

    public boolean createNewAppointment(Appointment appointment) {
        // Database connection string
        // Uncomment if you want to insert at a particular node
        appointment.connectionNumber = connectionNumber;
        String dbUrl = appointment.connectionString();
//        String dbUrl = "jdbc:mysql://ccscloud.dlsu.edu.ph:20183/apptMCO2?user=advdb&connectTimeout=3000"; // central node
//        String dbUrl = "jdbc:mysql://ccscloud.dlsu.edu.ph:20184/apptMCO2?user=advdb&connectTimeout=3000"; // node 2
//        String dbUrl = "jdbc:mysql://ccscloud.dlsu.edu.ph:20185/apptMCO2?user=advdb&connectTimeout=3000"; // node 3

        // Attempt to load the JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        // Try-with-resources statement to ensure proper closure of resources
        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO appointments (patientID, clinicID, doctorID, appointmentID, apptStatus, TimeQueued, QueueDate, StartTime, consultationType, virtualConsultation, EndTime) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

            // Set parameters directly from the Appointment object's fields
            pstmt.setString(1, appointment.patientID);
            pstmt.setString(2, appointment.clinicID);
            pstmt.setString(3, appointment.doctorID);
            pstmt.setString(4, appointment.appointmentID);
            pstmt.setString(5, appointment.apptStatus);

            if (appointment.TimeQueued != null) {
                pstmt.setTimestamp(6, new Timestamp(appointment.TimeQueued.getTime()));
            } else {
                pstmt.setNull(6, java.sql.Types.TIMESTAMP);
            }

            if (appointment.QueueDate != null) {
                pstmt.setTimestamp(7, new Timestamp(appointment.QueueDate.getTime()));
            } else {
                pstmt.setNull(7, java.sql.Types.TIMESTAMP);
            }

            // Assuming StartTime is mandatory and always provided
            pstmt.setTimestamp(8, new Timestamp(appointment.StartTime.getTime()));

            pstmt.setString(9, appointment.consultationType);
            pstmt.setInt(10, appointment.virtualConsultation);

            // Check for null EndTime
            if (appointment.EndTime != null) {
                pstmt.setTimestamp(11, new Timestamp(appointment.EndTime.getTime()));
            } else {
                pstmt.setNull(11, java.sql.Types.TIMESTAMP);
            }

            // Execute the update and check if an appointment was added
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0; // Return true if at least one row was affected
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if any SQLException occurs
        }
    }
}
