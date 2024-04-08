package appointmentManagement;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DisplayAllAppointments {

    public ArrayList<Appointment> appointments = new ArrayList<>();
    public void displayAppointments() throws SQLException {
        Connection conn = null;
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e){
            System.out.println(e);
        }

        try {
            Appointment appt = new Appointment();
            conn = DriverManager.getConnection(appt.connectionString());

            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM appointments LIMIT 50");
            ResultSet rst = pstmt.executeQuery();

            while (rst.next()) {
                String  patientID           = rst.getString("patientID");
                String  clinicID            = rst.getString("clinicID");
                String  doctorID            = rst.getString("doctorID");
                String  appointmentID       = rst.getString("appointmentID");
                String  apptStatus          = rst.getString("apptStatus");
                String  consultationType    = rst.getString("consultationType");
                Integer virtualConsultation = rst.getInt("virtualConsultation");

                Timestamp timeQueued        = rst.getTimestamp("TimeQueued");
                Timestamp queueDate         = rst.getTimestamp("QueueDate");
                Timestamp startTime         = rst.getTimestamp("StartTime");
                Timestamp endTime           = rst.getTimestamp("EndTime");

                Appointment appointment     = new Appointment(  patientID, clinicID, doctorID, appointmentID,
                                                            apptStatus, timeQueued, queueDate,
                                                            startTime, endTime, consultationType, virtualConsultation);
                appointments.add(appointment);
            }

            pstmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);
        } finally {
            if (conn != null){
                conn.close();
            }
        }
    }
}
