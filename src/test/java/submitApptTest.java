import org.junit.jupiter.api.Test;

import java.sql.*;

import static net.sourceforge.jwebunit.junit.JWebUnit.*;
import static org.junit.jupiter.api.Assertions.*;

import net.sourceforge.jwebunit.util.TestingEngineRegistry;

public class submitApptTest {
    @Test
    void central_node_unavailable_during_transaction_but_eventually_comes_online() {
        try{
            // given an appointment with details
            String patient_id = "516B8BDD2B9DD8C0374075A5EAF63A3D";
            String clinic_id = "BBDB6BAC289A5524F2CD9440C4AC90DD";
            String doctor_id = "DA0F2C7272AC7F5274AC6D9E93699241";
            String appt_id = "405F0FA8443937FD85B85AFA01DE2831";
            String start_time = "2024-04-06T15:30";
            String appt_type = "Consultation";

            // and the central node is shut off
            // shut down central node


            // when it is posted to node 2 (change the link in CreateAppointment.java)
            setTestingEngineKey(TestingEngineRegistry.TESTING_ENGINE_HTMLUNIT);    // use HtmlUnit
            beginAt("appointmentWebApp_war_exploded/createAppointment.jsp");
            assertTitleEquals("Create New Appointment");
            setTextField("patientID", patient_id);
            setTextField("clinicID", clinic_id);
            setTextField("doctorID", doctor_id);
            setTextField("appointmentID", appt_id);
            setTextField("StartTime", start_time);
            selectOptionByValue("consultationType", appt_type);
            clickButton("create-appt-btn");

            // and central node comes back online
            // turn central node on again
            System.out.println("Open central node now");
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conncentral;
            conncentral = DriverManager.getConnection("jdbc:mysql://ccscloud.dlsu.edu.ph:20183/apptMCO2?user=advdb&connectTimeout=200000&socketTimeout=200000"); // 200 seconds waiting to turn on

            // The data must also be in the central node too
            PreparedStatement pstmt = conncentral.prepareStatement("SELECT * FROM appointments " +
                    "WHERE patientID = ? AND clinicID = ? AND doctorID = ? AND appointmentID = ? AND consultationType = ?" +
                    "ORDER BY TimeQueued DESC");
            pstmt.setString(1, patient_id);
            pstmt.setString(2, clinic_id);
            pstmt.setString(3, doctor_id);
            pstmt.setString(4, appt_id);
            pstmt.setString(5, appt_type);
            ResultSet rst = pstmt.executeQuery();

            assertTrue(rst.next());

            // and node 3 too
            Connection connnode3;
            connnode3 = DriverManager.getConnection("jdbc:mysql://ccscloud.dlsu.edu.ph:20185/apptMCO2?user=advdb&connectTimeout=200000&socketTimeout=200000"); // 200 seconds waiting to turn on
            pstmt = connnode3.prepareStatement("SELECT * FROM appointments " +
                    "WHERE patientID = ? AND clinicID = ? AND doctorID = ? AND appointmentID = ? AND consultationType = ?" +
                    "ORDER BY TimeQueued DESC");
            pstmt.setString(1, patient_id);
            pstmt.setString(2, clinic_id);
            pstmt.setString(3, doctor_id);
            pstmt.setString(4, appt_id);
            pstmt.setString(5, appt_type);
            rst = pstmt.executeQuery();

            assertTrue(rst.next());

        } catch (Exception e) {
            System.out.println(e);
            fail();
        }
    }

    @Test
    void node_2_unavailable_during_transaction_but_eventually_comes_online() {
        try{
            // given an appointment with details
            String patient_id = "516B8BDD2B9DD8C0374075A5EAF63A3D";
            String clinic_id = "BBDB6BAC289A5524F2CD9440C4AC90DD";
            String doctor_id = "DA0F2C7272AC7F5274AC6D9E93699241";
            String appt_id = "405F0FA8443937FD85B85AFA01DE2831";
            String start_time = "2024-04-06T15:30";
            String appt_type = "Consultation";

            // and node 2 is shut off
            // shut down node 2

            // when it is posted to central node (change the link in CreateAppointment.java)
            setTestingEngineKey(TestingEngineRegistry.TESTING_ENGINE_HTMLUNIT);    // use HtmlUnit
            beginAt("appointmentWebApp_war_exploded/createAppointment.jsp");
            assertTitleEquals("Create New Appointment");
            setTextField("patientID", patient_id);
            setTextField("clinicID", clinic_id);
            setTextField("doctorID", doctor_id);
            setTextField("appointmentID", appt_id);
            setTextField("StartTime", start_time);
            selectOptionByValue("consultationType", appt_type);
            clickButton("create-appt-btn");

            // and node 2 comes back online
            // turn node 2 on again
            System.out.println("Open node 2 now");
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connnode2;
            connnode2 = DriverManager.getConnection("jdbc:mysql://ccscloud.dlsu.edu.ph:20184/apptMCO2?user=advdb&connectTimeout=200000&socketTimeout=200000"); // 200 seconds waiting to turn on

            // The data must also be in the node 2 too
            PreparedStatement pstmt = connnode2.prepareStatement("SELECT * FROM appointments " +
                    "WHERE patientID = ? AND clinicID = ? AND doctorID = ? AND appointmentID = ? AND consultationType = ?" +
                    "ORDER BY TimeQueued DESC");
            pstmt.setString(1, patient_id);
            pstmt.setString(2, clinic_id);
            pstmt.setString(3, doctor_id);
            pstmt.setString(4, appt_id);
            pstmt.setString(5, appt_type);
            ResultSet rst = pstmt.executeQuery();

            assertTrue(rst.next());

            // and node 3 too
            Connection connnode3;
            connnode3 = DriverManager.getConnection("jdbc:mysql://ccscloud.dlsu.edu.ph:20185/apptMCO2?user=advdb&connectTimeout=200000&socketTimeout=200000"); // 200 seconds waiting to turn on
            pstmt = connnode3.prepareStatement("SELECT * FROM appointments " +
                    "WHERE patientID = ? AND clinicID = ? AND doctorID = ? AND appointmentID = ? AND consultationType = ?" +
                    "ORDER BY TimeQueued DESC");
            pstmt.setString(1, patient_id);
            pstmt.setString(2, clinic_id);
            pstmt.setString(3, doctor_id);
            pstmt.setString(4, appt_id);
            pstmt.setString(5, appt_type);
            rst = pstmt.executeQuery();

            assertTrue(rst.next());

        } catch (Exception e) {
            System.out.println(e);
            fail();
        }
    }

}
