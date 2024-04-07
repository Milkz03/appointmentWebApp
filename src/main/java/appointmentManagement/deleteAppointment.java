package appointmentManagement;

import java.sql.*;
import java.util.*;

public class deleteAppointment {
    public String appointmentID;

    public ArrayList<String> appointmentsIDs = new ArrayList<>();

    public int availableAppointments(){
        try {
            Connection conn;
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://ccscloud.dlsu.edu.ph:20183/apptMCO2?user=advdb");

            PreparedStatement pstmt = conn.prepareStatement("SELECT appointmentID FROM appointments WHERE appointmentID LIKE 'test%'");
            ResultSet rst = pstmt.executeQuery();

            appointmentsIDs.clear();

            while(rst.next()){
                appointmentID = rst.getString("appointmentID");
                appointmentsIDs.add(appointmentID);
            }

            pstmt.close();
            conn.close();

            System.out.println("Connection Successful");
            return 1;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int deleteAppointment(){
        try {
            Connection conn;
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://ccscloud.dlsu.edu.ph:20183/apptMCO2?user=advdb");

            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM appointments WHERE appointmentID=?");
            pstmt.setString(1, appointmentID);
            pstmt.executeUpdate();

            pstmt.close();
            conn.close();

            System.out.println("Connection Successful");
            return 1;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
