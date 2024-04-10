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
    updateAppointment updateAppointment = new updateAppointment();
    readAppointment readAppointment = new readAppointment();

    @Test // #Case 1
    public void two_concurrent_reads_same_row() throws InterruptedException {
        readAppointment.appointmentID = "test1";
        readAppointment readAppointment1 = new readAppointment();
        readAppointment1.appointmentID = "test1";

        Thread thread1 = new Thread(() -> {
            readAppointment.appointment.connectionNumber = 0;
            readAppointment.startTransaction();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            System.out.println("Thread1: " +dtf.format(now));
            readAppointment.infoAppointments();
        });

        Thread thread2 = new Thread(() -> {
            readAppointment1.appointment.connectionNumber = 1;
            readAppointment1.startTransaction();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            System.out.println("Thread2: " +dtf.format(now));
            readAppointment1.infoAppointments();
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        // thread1 and thread2 reads the appointment concurrently
        Connection conn1;
        Connection conn2;
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
                fail("Appointment not found in the database");}

            String patientID = resultSet2.getString("patientID");
            String doctorID = resultSet2.getString("doctorID");
            String apptStatus = resultSet2.getString("apptStatus");
            String timeQueued = resultSet2.getString("TimeQueued");
            String startTime = resultSet2.getString("StartTime");
            String endTime = resultSet2.getString("EndTime");
            String consultationType = resultSet2.getString("consultationType");
            int virtualConsultation = resultSet2.getInt("virtualConsultation");

            System.out.println("test1"
                    + "patientID: " + patientID + " "
                    + "doctorID: " + doctorID + " "
                    + "apptStatus: " + apptStatus + " "
                    + "timeQueued: " + timeQueued+ " "
                    + "startTime: " + startTime+ " "
                    + "endTime: " + endTime+ " "
                    + "consultationType: " + consultationType+ " "
                    + "virtualConsultation: " + virtualConsultation
            );

            pstmt1.close();
            conn1.close();
            pstmt2.close();
            conn2.close();

        } catch (Exception e) {
            e.printStackTrace();
            fail("An exception occurred while querying the database");
        }}

    @Test // Case #2
    public void two_concurrent_updates_and_read_same_row() throws InterruptedException {
        updateAppointment.appointmentID = "test1";
        readAppointment.appointmentID = "test1";

        String test_forThread1 = "Patient1Case2";

        // Given two concurrent transactions
        // one updates
        Thread thread1 = new Thread(() -> {
            updateAppointment.appointment.connectionNumber = 1;
            updateAppointment.startTransaction();
            updateAppointment.infoAppointments();
            updateAppointment.patientID = test_forThread1;
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
        Connection conn1;
        Connection conn2;
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

                assertEquals( test_forThread1, resultSet2.getString("patientID"));
            } else {
                fail("Appointment not found in the database");
            }

            String patientID = resultSet2.getString("patientID");
            String doctorID = resultSet2.getString("doctorID");
            String apptStatus = resultSet2.getString("apptStatus");
            String timeQueued = resultSet2.getString("TimeQueued");
            String startTime = resultSet2.getString("StartTime");
            String endTime = resultSet2.getString("EndTime");
            String consultationType = resultSet2.getString("consultationType");
            int virtualConsultation = resultSet2.getInt("virtualConsultation");

            System.out.println("test1"
                    + "patientID: " + patientID + " "
                    + "doctorID: " + doctorID + " "
                    + "apptStatus: " + apptStatus + " "
                    + "timeQueued: " + timeQueued+ " "
                    + "startTime: " + startTime+ " "
                    + "endTime: " + endTime+ " "
                    + "consultationType: " + consultationType+ " "
                    + "virtualConsultation: " + virtualConsultation
            );

            pstmt1.close();
            conn1.close();
            pstmt2.close();
            conn2.close();

        } catch (Exception e) {
            e.printStackTrace();
            fail("An exception occurred while querying the database");
        }
    }

    @Test // Case #3
    public void two_concurrent_updates_same_row() throws InterruptedException {
        updateAppointment updateAppointment1 = new updateAppointment();

        String test_ForThread1 = "PatientA2";
        String test_ForThread2 = "PatientB2";

        updateAppointment.appointmentID = "test1";
        updateAppointment1.appointmentID ="test1";

        // Given two concurrent updates on the same appointment
        Thread thread1 = new Thread(() -> {
            updateAppointment.appointment.connectionNumber = 1;
            updateAppointment.startTransaction();
            updateAppointment.infoAppointments();
            updateAppointment.patientID = test_ForThread1;
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

        Thread thread2 = new Thread(() -> {
            updateAppointment1.appointment.connectionNumber = 0;
            updateAppointment1.startTransaction();
            updateAppointment1.infoAppointments();
            updateAppointment1.patientID = test_ForThread2;
            updateAppointment1.doctorID = "Doctor2";
            updateAppointment1.apptStatus = "Status2";
            updateAppointment1.timeQueued = "2024-04-08T11:00:00";
            updateAppointment1.startTime = "2024-04-08T11:30:00";
            updateAppointment1.endTime = "2024-04-08T12:00:00";
            updateAppointment1.consultationType = "ConsultationType2";
            updateAppointment1.virtualConsultation = 0;
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            System.out.println("Thread2: " + dtf.format(now));
            updateAppointment.updateAppointments();
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        // thread1 updates the appointment -> once done, thread2 reads the appointment
        Connection conn1;
        Connection conn2;
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

                boolean samePatientID = resultSet2.getString("patientID").equals(test_ForThread1) ||
                        resultSet2.getString("patientID").equals(test_ForThread2);

                assertTrue(samePatientID, "Update is successful.");
            } else {
                fail("Appointment not found in the database");
            }

            String patientID = resultSet2.getString("patientID");
            String doctorID = resultSet2.getString("doctorID");
            String apptStatus = resultSet2.getString("apptStatus");
            String timeQueued = resultSet2.getString("TimeQueued");
            String startTime = resultSet2.getString("StartTime");
            String endTime = resultSet2.getString("EndTime");
            String consultationType = resultSet2.getString("consultationType");
            int virtualConsultation = resultSet2.getInt("virtualConsultation");

            System.out.println("test1"
                    + "patientID: " + patientID + " "
                    + "doctorID: " + doctorID + " "
                    + "apptStatus: " + apptStatus + " "
                    + "timeQueued: " + timeQueued+ " "
                    + "startTime: " + startTime+ " "
                    + "endTime: " + endTime+ " "
                    + "consultationType: " + consultationType+ " "
                    + "virtualConsultation: " + virtualConsultation
            );

            pstmt1.close();
            conn1.close();
            pstmt2.close();
            conn2.close();

        } catch (Exception e) {
            e.printStackTrace();
            fail("An exception occurred while querying the database");
        }
    }

    @Test // #Case N
    public void nonrepeatableread() throws InterruptedException {
        readAppointment.appointmentID = "test2";
        updateAppointment.appointmentID = "test2";

        final int[] virtualConsultationT1R1 = new int[1];
        final int[] virtualConsultationT2R1 = new int[1];
        final int[] virtualConsultationT2R2 = new int[1];

        Thread thread1 = new Thread(() -> {
            updateAppointment.appointment.connectionNumber = 0;
            updateAppointment.startTransaction();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            System.out.println("Thread1: " +dtf.format(now));
            updateAppointment.infoAppointments();
            virtualConsultationT1R1[0] = updateAppointment.virtualConsultation;
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            updateAppointment.patientID = "test2";
            updateAppointment.doctorID = "test2";
            updateAppointment.apptStatus = "Queued";
            updateAppointment.timeQueued = "2009-04-25T05:49:00";
            updateAppointment.startTime = "2009-04-25T05:49:00";
            updateAppointment.endTime = "";
            updateAppointment.consultationType = "Consultation";
            updateAppointment.virtualConsultation = 222;
            updateAppointment.updateAppointments();
        });

        Thread thread2 = new Thread(() -> {
            readAppointment.appointment.connectionNumber = 1;
            readAppointment.startTransaction();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            System.out.println("Thread2: " +dtf.format(now));
            readAppointment.infoAppointments();
            virtualConsultationT2R1[0] = readAppointment.virtualConsultation;
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            readAppointment.infoAppointments();
            virtualConsultationT2R2[0] = readAppointment.virtualConsultation;
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        assertEquals(virtualConsultationT1R1[0], virtualConsultationT2R1[0]);
        assertEquals(virtualConsultationT2R1[0], virtualConsultationT2R2[0]);
        assertEquals(virtualConsultationT1R1[0], virtualConsultationT2R2[0]);

//        System.out.println("T1R1: " + virtualConsultationT1R1[0]);
//        System.out.println("T2R1: " + virtualConsultationT2R1[0]);
//        System.out.println("T2R2: " + virtualConsultationT2R2[0]);

    }

}
