<%--
  Created by IntelliJ IDEA.
  User: Lianne
  Date: 04/04/2024
  Time: 12:22 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All Appointments</title>
</head>
<body>
<h1>Appointments</h1>
<table border="1">
    <tr>
        <th>AppointmentID</th>
        <th>PatientID</th>
        <th>DoctorID</th>
        <th>Status</th>
        <th>TimeQueued</th>
        <th>QueueDate</th>
        <th>StartTime</th>
        <th>EndTime</th>
        <th>Consultation Type</th>
        <th>Virtual Consultation</th>
    </tr>
    <jsp:useBean id="AllAppointsments" class="appointmentManagement.DisplayAllAppointments"
                 scope="session"/>
    <% AllAppointsments.displayAppointments();
        for (int i = 0; i < AllAppointsments.appointments.size(); i++) { %>
    <tr>
        <td><%=AllAppointsments.appointments.get(i).appointmentID%></td>
        <td><%=AllAppointsments.appointments.get(i).patientID%></td>
        <td><%=AllAppointsments.appointments.get(i).doctorID%></td>
        <td><%=AllAppointsments.appointments.get(i).apptStatus%></td>
        <td><%=AllAppointsments.appointments.get(i).TimeQueued%></td>
        <td><%=AllAppointsments.appointments.get(i).QueueDate%></td>
        <td><%=AllAppointsments.appointments.get(i).StartTime%></td>
        <td><%=AllAppointsments.appointments.get(i).EndTime%></td>
        <td><%=AllAppointsments.appointments.get(i).consultationType%></td>
        <td><%=AllAppointsments.appointments.get(i).virtualConsultation%></td>
    </tr>
    <%  } %>
</table>
</body>
</html>
