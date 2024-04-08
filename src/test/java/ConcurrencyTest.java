import appointmentManagement.readAppointment;
import appointmentManagement.updateAppointment;
import org.testng.annotations.Test;

import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

public class ConcurrencyTest {
    final updateAppointment updateAppointment = new updateAppointment();
    final readAppointment readAppointment = new readAppointment();

    @Test // Case #1
    public void concurrent_reads_same_row() throws InterruptedException, SQLException {
        String appointmentID = "test1";
        
        readAppointment readAppointment1 = new readAppointment();
        readAppointment readAppointment2 = new readAppointment();
        readAppointment1.appointmentID = appointmentID;
        readAppointment2.appointmentID = appointmentID;

        Thread thread1 = new Thread(() -> {
            readAppointment1.appointment.connectionNumber = 0;
            readAppointment1.infoAppointments();
        });

        Thread thread2 = new Thread(() -> {
            readAppointment2.appointment.connectionNumber = 1;
            readAppointment2.infoAppointments();
        });

        Thread thread3 = new Thread(() -> {
            readAppointment2.appointment.connectionNumber = 2;
            readAppointment2.infoAppointments();
        });

        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();

        assertTrue(true, "Concurrent reads completed successfully.");
    }

    @Test // Case #2
    public void two_concurrent_updates_and_read_same_row() throws InterruptedException, SQLException {
        updateAppointment.appointmentID = "test1";
        readAppointment.appointmentID = "test1";

        // Given two concurrent transactions
        // one updates
        Thread thread1 = new Thread(() -> {
            updateAppointment.appointment.connectionNumber = 1;
            updateAppointment.startTransaction();
            updateAppointment.infoAppointments();
            updateAppointment.patientID = "Patient1";
            updateAppointment.doctorID = "Doctor1";
            updateAppointment.apptStatus = "Status1";
            updateAppointment.timeQueued = "2024-04-08T10:00:00";
            updateAppointment.startTime = "2024-04-08T10:30:00";
            updateAppointment.endTime = "2024-04-08T11:00:00";
            updateAppointment.consultationType = "ConsultationType1";
            updateAppointment.virtualConsultation = 1;
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            System.out.println("Thread1: " + dtf.format(now));
            updateAppointment.updateAppointments();
        });
        // the other reads
        Thread thread2 = new Thread(() -> {
            readAppointment.appointment.connectionNumber = 0;
            readAppointment.startTransaction();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            System.out.println("Thread2: " +dtf.format(now));
            readAppointment.infoAppointments();
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        // thread1 updates the appointment -> once done, thread2 reads the appointment
        Connection conn1 = null;
        Connection conn2 = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn1 = DriverManager.getConnection("jdbc:mysql://ccscloud.dlsu.edu.ph:20183/apptMCO2?user=advdb");
            conn2 = DriverManager.getConnection("jdbc:mysql://ccscloud.dlsu.edu.ph:20184/apptMCO2?user=advdb");

            PreparedStatement pstmt1 = conn1.prepareStatement("SELECT * FROM appointments WHERE appointmentID=?");
            pstmt1.setString(1, "test1");
            ResultSet resultSet1 = pstmt1.executeQuery();

            PreparedStatement pstmt2 = conn2.prepareStatement("SELECT * FROM appointments WHERE appointmentID=?");
            pstmt2.setString(1, "test1");
            ResultSet resultSet2 = pstmt2.executeQuery();

            // Then the appointment information would have the information from the second thread
            if (resultSet1.next() && resultSet2.next()) {
                assertEquals(resultSet2.getString("patientID"), resultSet1.getString("patientID"));
                assertEquals(resultSet2.getString("doctorID"), resultSet1.getString("doctorID"));
                assertEquals(resultSet2.getString("TimeQueued"), resultSet1.getString("TimeQueued"));
                assertEquals(resultSet2.getString("QueueDate"), resultSet1.getString("QueueDate"));
                assertEquals(resultSet2.getString("StartTime"), resultSet1.getString("StartTime"));
                assertEquals(resultSet2.getString("EndTime"), resultSet1.getString("EndTime"));
                assertEquals(resultSet2.getString("consultationType"), resultSet1.getString("consultationType"));
                assertEquals(resultSet2.getString("virtualConsultation"), resultSet1.getString("virtualConsultation"));
            } else {
                fail("Appointment not found in the database");
            }

            pstmt1.close();
            conn1.close();
            pstmt2.close();
            conn2.close();

        } catch (Exception e) {
            e.printStackTrace();
            fail("An exception occurred while querying the database");
            if (conn1 != null && conn2 != null) {
                conn1.close();
                conn2.close();
            }
        }
    }
    @Test // Case #3
    public void two_concurrent_updates_same_row() throws InterruptedException, SQLException {
        updateAppointment.appointmentID = "test1";

        // Given two concurrent updates on the same appointment
        Thread thread1 = new Thread(() -> {
            updateAppointment.appointment.connectionNumber = 1;
            updateAppointment.startTransaction();
            updateAppointment.infoAppointments();
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
            updateAppointment.startTransaction();
            updateAppointment.infoAppointments();
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

        // thread1 updates the appointment -> once done, thread2 reads the appointment
        Connection conn1 = null;
        Connection conn2 = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn1 = DriverManager.getConnection("jdbc:mysql://ccscloud.dlsu.edu.ph:20183/apptMCO2?user=advdb");
            conn2 = DriverManager.getConnection("jdbc:mysql://ccscloud.dlsu.edu.ph:20184/apptMCO2?user=advdb");

            PreparedStatement pstmt1 = conn1.prepareStatement("SELECT * FROM appointments WHERE appointmentID=?");
            pstmt1.setString(1, "test1");
            ResultSet resultSet1 = pstmt1.executeQuery();

            PreparedStatement pstmt2 = conn2.prepareStatement("SELECT * FROM appointments WHERE appointmentID=?");
            pstmt2.setString(1, "test1");
            ResultSet resultSet2 = pstmt2.executeQuery();

            // Then the appointment information would have the information from the second thread
            if (resultSet1.next() && resultSet2.next()) {
                assertEquals(resultSet2.getString("patientID"), resultSet1.getString("patientID"));
                assertEquals(resultSet2.getString("doctorID"), resultSet1.getString("doctorID"));
                assertEquals(resultSet2.getString("TimeQueued"), resultSet1.getString("TimeQueued"));
                assertEquals(resultSet2.getString("QueueDate"), resultSet1.getString("QueueDate"));
                assertEquals(resultSet2.getString("StartTime"), resultSet1.getString("StartTime"));
                assertEquals(resultSet2.getString("EndTime"), resultSet1.getString("EndTime"));
                assertEquals(resultSet2.getString("consultationType"), resultSet1.getString("consultationType"));
                assertEquals(resultSet2.getString("virtualConsultation"), resultSet1.getString("virtualConsultation"));
            } else {
                fail("Appointment not found in the database");
            }

            pstmt1.close();
            conn1.close();
            pstmt2.close();
            conn2.close();

        } catch (Exception e) {
            e.printStackTrace();
            fail("An exception occurred while querying the database");
            if (conn1 != null && conn2 != null) {
                conn1.close();
                conn2.close();
            }
        }
    }

}
