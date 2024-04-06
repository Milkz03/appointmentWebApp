package appointmentManagement;

import java.sql.*;
import java.util.ArrayList;

public class updateAppointment {

    public String appointmentID;
    public String patientID;
    public String doctorID;
    public String status;
    public String timeQueued;
    public String startTime;
    public String endTime;
    public String type;
    public int virtual;
    public String virtualState;

    public ArrayList<String> appointmentsIDs = new ArrayList<>();

    public int availableAppointments(){
        try {
            Connection conn;
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://ccscloud.dlsu.edu.ph:20183/apptMCO2?user=advdb");

            PreparedStatement pstmt = conn.prepareStatement("SELECT apptid FROM appointments");
            ResultSet rst = pstmt.executeQuery();

            appointmentsIDs.clear();

            while(rst.next()){
                appointmentID = rst.getString("apptid");
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

    public int infoAppointments() {
        try {
            Connection conn;
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://ccscloud.dlsu.edu.ph:20183/apptMCO2?user=advdb");

            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM appointments WHERE apptid=?");
            pstmt.setString(1, appointmentID);
            ResultSet rst = pstmt.executeQuery();

            while(rst.next()) {
                patientID = rst.getString("pxid");
                doctorID = rst.getString("doctorid");
                status = rst.getString("status");
                timeQueued = rst.getString("TimeQueued");
                startTime = rst.getString("StartTime");
                endTime = rst.getString("EndTime");
                type = rst.getString("type");
                virtual = rst.getInt("virtual");
                if (virtual==1){
                    virtualState = "checked";
                } else {
                    virtualState = "";
                }
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

    public int updateAppointments() {
        try {
            Connection conn;
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://ccscloud.dlsu.edu.ph:20183/apptMCO2?user=advdb");

            PreparedStatement pstmt = conn.prepareStatement("UPDATE appointments SET pxid=?, doctorid=?, status=?, TimeQueued=?, StartTime=?, EndTime=?, type=?, appointments.virtual=? WHERE apptid=?");
            pstmt.setString(1, patientID);
            pstmt.setString(2, doctorID);
            pstmt.setString(3, status);
            pstmt.setString(4, timeQueued);
            pstmt.setString(5, startTime);
            pstmt.setString(6, endTime);
            pstmt.setString(7, type);
            pstmt.setInt(8, virtual);
            pstmt.setString(9, appointmentID);

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

    public static void main(String[] args) {
        updateAppointment update = new updateAppointment();
//        update.availableAppointments();
//        for (int i = 0; i < update.appointmentsIDs.size(); i++) {
//            System.out.println(update.appointmentsIDs.get(i));
//        }

//        update.appointmentID = "0038D088E672E0B794960650DCAFF3AE";
//        update.infoAppointments();
//        System.out.println(update.patientID);
//        System.out.println(update.doctorID);
//        System.out.println(update.status);
//        System.out.println(update.timeQueued);
//        System.out.println(update.startTime);
//        System.out.println(update.endTime);
//        System.out.println(update.type);
//        System.out.println(update.virtual);

//        update.appointmentID = "00119BFBA0499C5574632FA796ABE466";
//        update.patientID = "79047D3DFD5057BB70B8517736C36DCE";
//        update.doctorID = "1E8C391ABFDE9ABEA82D75A2D60278D4";
//        update.status = "Complete";
//        update.timeQueued = "2009-04-25 05:49:00";
//        update.startTime = "2009-04-25 05:49:00";
//        update.endTime = "";
//        update.type = "Consultation";
//        update.virtual = 0;
//
//        update.updateAppointments();
    }
}
