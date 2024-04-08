package appointmentManagement;

import java.sql.*;
import java.util.ArrayList;

public class readAppointment {
    public String appointmentID;
    public String patientID;
    public String doctorID;
    public String apptStatus;
    public String timeQueued;
    public String startTime;
    public String endTime;
    public String consultationType;
    public int virtualConsultation;
    public String virtualState;

    public ArrayList<String> appointmentsIDs = new ArrayList<>();

    public int availableAppointments(){
        try {
            Appointment appointment = new Appointment();
            Connection conn;
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(appointment.connectionString());

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

    public int infoAppointments() {
        try {
            Appointment appointment = new Appointment();
            Connection conn;
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(appointment.connectionString());

            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM appointments WHERE appointmentID=? LOCK IN SHARE MODE");
            pstmt.setString(1, appointmentID);
            ResultSet rst = pstmt.executeQuery();

            while(rst.next()) {
                patientID = rst.getString("patientID");
                doctorID = rst.getString("doctorID");
                apptStatus = rst.getString("apptStatus");
                timeQueued = rst.getString("TimeQueued");
                startTime = rst.getString("StartTime");
                endTime = rst.getString("EndTime");
                consultationType = rst.getString("consultationType");
                virtualConsultation = rst.getInt("virtualConsultation");
                if (virtualConsultation==1){
                    virtualState = "checked";
                } else {
                    virtualState = "";
                }
            }

            PreparedStatement pstmtCom = conn.prepareStatement("COMMIT");
            pstmtCom.executeUpdate();

            pstmt.close();
            pstmtCom.close();
            conn.close();

            System.out.println("Connection Successful");
            return 1;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int startTransaction(){
        try {
            Appointment appointment = new Appointment();
            Connection conn;
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(appointment.connectionString());

            PreparedStatement pstmt = conn.prepareStatement("START TRANSACTION");
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
