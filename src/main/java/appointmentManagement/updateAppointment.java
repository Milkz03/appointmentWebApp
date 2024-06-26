package appointmentManagement;

import java.sql.*;
import java.text.*;
import java.util.*;

public class updateAppointment {

    public String appointmentID;
    public String patientID;
    public String doctorID;
    public String apptStatus;
    public String timeQueued;
    public String startTime;
    public String endTime;
    public String consultationType;
    public int virtualConsultation;
    public int editAppointment;
    public String virtualState;
    public Appointment appointment = new Appointment(1);
    public ArrayList<String> appointmentsIDs = new ArrayList<>();

    public int availableAppointments(){
        try {
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
            Connection conn;
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(appointment.connectionString());

            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM appointments WHERE appointmentID=? FOR UPDATE");
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

            PreparedStatement pstmtEdit = conn.prepareStatement("UPDATE appointments SET editAppointment=? WHERE appointmentID=?");
            pstmtEdit.setInt(1, 1);
            pstmtEdit.setString(2, appointmentID);
            pstmtEdit.executeUpdate();

            pstmt.close();
            pstmtEdit.close();
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
            conn = DriverManager.getConnection(appointment.connectionString());

            PreparedStatement pstmt = conn.prepareStatement("UPDATE appointments SET patientID=?, doctorID=?, apptStatus=?, TimeQueued=?, StartTime=?, EndTime=?, consultationType=?, virtualConsultation=? WHERE appointmentID=?");
            pstmt.setString(1, patientID);
            pstmt.setString(2, doctorID);
            pstmt.setString(3, apptStatus);
            pstmt.setTimestamp(4, fixDateFormat(timeQueued));
            pstmt.setTimestamp(5, fixDateFormat(startTime));
            pstmt.setTimestamp(6, fixDateFormat(endTime));
            pstmt.setString(7, consultationType);
            pstmt.setInt(8, virtualConsultation);
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

    public int virtualUpdate(String v_virtual){
        if (v_virtual != null) {
            return 1;
        } else {
            return 0;
        }
    }

    public Timestamp fixDateFormat(String date) throws ParseException {
        SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        if (date.length() == 16) {
            date += ":00";
        }
        if (!date.isEmpty()) {
            java.util.Date parsedDate = sdfInput.parse(date);
            return new Timestamp(parsedDate.getTime());
        } else {
            return null;
        }
    }

    public int startTransaction(){
        try {
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

    public int commitTransaction(){
        try {
            Connection conn;
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(appointment.connectionString());

            PreparedStatement pstmt = conn.prepareStatement("COMMIT");
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

    public int rollbackTransaction(){
        try {
            Connection conn;
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(appointment.connectionString());

            PreparedStatement pstmt = conn.prepareStatement("ROLLBACK");
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

    public int checkTransaction(){
        try {
            Connection conn;
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(appointment.connectionString());

            PreparedStatement pstmt = conn.prepareStatement("SELECT editAppointment FROM appointments WHERE appointmentID=?");
            pstmt.setString(1, appointmentID);
            ResultSet rst = pstmt.executeQuery();

            while(rst.next()) {
                if(rst.getInt("editAppointment") == 1){
                    System.out.println("No conflicting transaction");
                    pstmt.close();
                    conn.close();
                    return 1;
                }
                else{
                    this.rollbackTransaction();
                    System.out.println("Conflicting transaction");
                    pstmt.close();
                    conn.close();
                    return 0;
                }
            }

            // error no resulting set
            return 0;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int resetEditTransaction(){
        try {
            Connection conn;
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(appointment.connectionString());

            PreparedStatement pstmt = conn.prepareStatement("UPDATE appointments SET editAppointment=? WHERE appointmentID=?");

            pstmt.setInt(1, 0);
            pstmt.setString(2, appointmentID);

            pstmt.executeUpdate();

            pstmt.close();
            conn.close();

            System.out.println("ApptID: " + appointmentID);
            System.out.println("Edit Appointment Reset.");
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
//        System.out.println(update.apptStatus);
//        System.out.println(update.timeQueued);
//        System.out.println(update.startTime);
//        System.out.println(update.endTime);
//        System.out.println(update.consultationType);
//        System.out.println(update.virtualConsultation);

//        update.appointmentID = "00119BFBA0499C5574632FA796ABE466";
//        update.patientID = "79047D3DFD5057BB70B8517736C36DCE";
//        update.doctorID = "1E8C391ABFDE9ABEA82D75A2D60278D4";
//        update.apptStatus = "Complete";
//        update.timeQueued = "2009-04-25 05:49:00";
//        update.startTime = "2009-04-25 05:49:00";
//        update.endTime = "";
//        update.consultationType = "Consultation";
//        update.virtualConsultation = 0;
//
//        update.updateAppointments();
    }
}
