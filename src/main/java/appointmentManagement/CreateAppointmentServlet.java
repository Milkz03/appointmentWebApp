package appointmentManagement;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.text.SimpleDateFormat;

@WebServlet("/CreateAppointmentServlet")
public class CreateAppointmentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            // Parse string inputs to appropriate types, particularly dates
            // Assuming this code is within a method of a Servlet
            String patientID = request.getParameter("patientID");
            String clinicID = request.getParameter("clinicID");
            String doctorID = request.getParameter("doctorID");
            String appointmentID = request.getParameter("appointmentID");
            String apptStatus = "Queued";
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            Date startTime = null;
            try {
                // Parsing the "StartTime" parameter which should match the "yyyy-MM-dd HH:mm" format
                startTime = dateFormat.parse(request.getParameter("StartTime"));
            } catch (ParseException e) {
                e.printStackTrace(); // Handle parsing error
            }
            Date timeQueued = Date.from(ZonedDateTime.now(ZoneId.systemDefault()).toInstant());
            Date queueDate = timeQueued; // Assuming both represent the same point in time
            Date endTime = null;
            String consultationType = request.getParameter("consultationType");
            Integer virtualConsultation = 0; // Assume unchecked by default

            String virtualConsultationStr = request.getParameter("virtualConsultation");
            if (virtualConsultationStr != null && !virtualConsultationStr.isEmpty()) {
                // If the checkbox is checked, it will have a value (typically "on" if not specified otherwise in HTML)
                // You can assign a different value based on your application's needs
                virtualConsultation = 1; // Assume checked/yes for virtual consultation
            }

            // Create Appointment object
            Appointment appointment = new Appointment(patientID, clinicID, doctorID, appointmentID, apptStatus, timeQueued, queueDate, startTime, endTime, consultationType, virtualConsultation);

            // Attempt to create the new appointment in the database
            CreateAppointment createAppointment = new CreateAppointment();
            boolean success = createAppointment.createNewAppointment(appointment);

            // Redirect based on success of operation
            if (success) {
//                response.sendRedirect("appointmentCreated.jsp"); // Or some success page
                response.sendRedirect("index.jsp");
            } else {
//                response.sendRedirect("appointmentError.jsp"); // Or some error handling page
                response.sendRedirect("index.jsp");
            }
        } catch (Exception e) {
            // Handle parsing or other exceptions
            e.printStackTrace();
            response.sendRedirect("appointmentError.jsp"); // Redirect to an error page
        }
    }
}
