<%--
  Created by IntelliJ IDEA.
  User: mukbookpro
  Date: 4/7/24
  Time: 2:22 am
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Make an Appointment with a Doctor</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>
<form action="index.jsp">
    <jsp:useBean id="UpdateC" class="appointmentManagement.updateAppointment" scope="session"/>

    <%
        String v_patientName            = request.getParameter("patientName");
        String v_doctorName             = request.getParameter("doctorName");
        String v_timeQueued             = request.getParameter("timeQueued");
        String v_startTime              = request.getParameter("startTime");
        String v_endTime                = request.getParameter("endTime");
        String v_apptStatus             = request.getParameter("apptStatus");
        String v_consultationType       = request.getParameter("consultationType");
        String v_virtualConsultation    = request.getParameter("virtualConsultation");

        UpdateC.patientID               = v_patientName;
        UpdateC.doctorID                = v_doctorName;
        UpdateC.timeQueued              = v_timeQueued;
        UpdateC.startTime               = v_startTime;
        UpdateC.endTime                 = v_endTime;
        UpdateC.apptStatus              = v_apptStatus;
        UpdateC.consultationType        = v_consultationType;
        UpdateC.virtualConsultation     = UpdateC.virtualUpdate(v_virtualConsultation);
        UpdateC.appointmentID           = (String) session.getAttribute("appointmentID");


        int result = UpdateC.updateAppointments();
        if (result == 0) { %>
        <h1>Fail</h1>
        <% }
        else { %>
        <h1>Pass</h1>
        <% } %>

</form>
</body>
</html>
