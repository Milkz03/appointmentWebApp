import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class submitApptTest {
    void central_node_unavailable_during_transaction_but_eventually_comes_online() {
        try{
            // given an appointment with details and connection to node 1
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conncentral;
            conncentral = DriverManager.getConnection("jdbc:mysql://ccscloud.dlsu.edu.ph:20184/apptMCO2?user=advdb");

            String patient_id = "516B8BDD2B9DD8C0374075A5EAF63A3D";
            String doctor_id = "DA0F2C7272AC7F5274AC6D9E93699241";
            String date_time_queue = "2024-04-06T15:30";
            String start_time = "2024-04-06T15:30";
            String end_time = "2024-04-07T15:30";
            String consult_details = "Queued";
            String appt_type = "Consultation";

            // and the central node is shut off
            // shut down central node


            // when it is posted to node 1
            // im not yet sure on how we would post stuff
//            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO appointments");

            // and central node comes back online
            // turn central node on again
            Connection connnode;
            connnode = DriverManager.getConnection("jdbc:mysql://ccscloud.dlsu.edu.ph:20183/apptMCO2?user=advdb&connectTimeout=20000&socketTimeout=20000");

            // The data must also be in the central node too
            PreparedStatement pstmt = connnode.prepareStatement("SELECT * FROM appointments " +
                    "WHERE pxid = ? AND doctorid = ? " +
                    "ORDER BY apptid DESC" +
                    "LIMIT 1");
            pstmt.setString(1, patient_id);
            pstmt.setString(1, doctor_id);
            ResultSet rst = pstmt.executeQuery();

            while(rst.next()) {
                assertEquals(rst.getString("pxID"), patient_id);
            }


        } catch (Exception e) {
            System.out.println(e);
        }


    }


}
