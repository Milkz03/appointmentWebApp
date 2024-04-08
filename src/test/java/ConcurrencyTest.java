import appointmentManagement.readAppointment;
import appointmentManagement.updateAppointment;
import org.testng.annotations.Test;

import java.sql.*;
import java.util.*;
import java.util.concurrent.*;
import static org.junit.jupiter.api.Assertions.*;

public class ConcurrencyTest {
    final updateAppointment updateAppointment = new updateAppointment();
    @Test // Case #3
    public void two_concurrent_updates_same_row() throws InterruptedException, SQLException {
        updateAppointment.appointmentID = "test1";

        // Given two concurrent updates on the same appointment
        Thread thread1 = new Thread(() -> {
            updateAppointment.appointment.connectionNumber = 1;
            updateAppointment.patientID = "Patient1";
            updateAppointment.doctorID = "Doctor1";
            updateAppointment.apptStatus = "Status1";
            updateAppointment.timeQueued = "2024-04-08T10:00:00";
            updateAppointment.startTime = "2024-04-08T10:30:00";
            updateAppointment.endTime = "2024-04-08T11:00:00";
            updateAppointment.consultationType = "ConsultationType1";
            updateAppointment.virtualConsultation = 1;
            updateAppointment.updateAppointments();
        });

        Thread thread2 = new Thread(() -> {
            updateAppointment.appointment.connectionNumber = 0;
            updateAppointment.patientID = "Patient2";
            updateAppointment.doctorID = "Doctor2";
            updateAppointment.apptStatus = "Status2";
            updateAppointment.timeQueued = "2024-04-08T11:00:00";
            updateAppointment.startTime = "2024-04-08T11:30:00";
            updateAppointment.endTime = "2024-04-08T12:00:00";
            updateAppointment.consultationType = "ConsultationType2";
            updateAppointment.virtualConsultation = 0;
            updateAppointment.updateAppointments();
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        // thread1 updates the appointment -> once done, thread2 updates the appointment
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://ccscloud.dlsu.edu.ph:20183/apptMCO2?user=advdb");

            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM appointments WHERE appointmentID=?");
            pstmt.setString(1, "test1");
            ResultSet resultSet = pstmt.executeQuery();

            // Then the appointment information would have the information from the second thread
            if (resultSet.next()) {
                assertEquals("Patient2", resultSet.getString("patientID"));
                assertEquals("Doctor2", resultSet.getString("doctorID"));
                assertEquals("Status2", resultSet.getString("apptStatus"));

            } else {
                fail("Appointment not found in the database");
            }

            pstmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            fail("An exception occurred while querying the database");
            if (conn != null) {
                conn.close();
            }
        }
    }

}
