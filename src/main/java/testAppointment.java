import java.sql.*;

public class testAppointment {
    public int testAppt(){
        try {
            Connection conn;
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://ccscloud.dlsu.edu.ph:20183/apptMCO2?user=advdb");

            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM appointments LIMIT 1");
            ResultSet rst = pstmt.executeQuery();

            while(rst.next()){
                System.out.println(rst.getString(1));
                System.out.println(rst.getString(2));
                System.out.println(rst.getString(3));
                System.out.println(rst.getString(4));
                System.out.println(rst.getString(5));
                System.out.println(rst.getString(6));
                System.out.println(rst.getString(7));
                System.out.println(rst.getString(8));
                System.out.println(rst.getString(9));
                System.out.println(rst.getString(10));
                System.out.println(rst.getString(11));
            }

            System.out.println("Connection Successful");
            return 1;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Nutted");
            return 0;
        }
    }
    public static void main(String[] args) {
        testAppointment app = new testAppointment();
        System.out.println(app.testAppt());
    }
}
