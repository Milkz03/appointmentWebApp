<%--
  Created by IntelliJ IDEA.
  User: [Your Name or User's Name]
  Date: [Current Date]
  Time: [Current Time]
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create New Appointment</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="p-5">
<h1 class="text-center">Appointment Booking System</h1>
<br/>
<h2 class="text-center">Create New Appointment</h2>
<form action="CreateAppointmentServlet" method="post">
    <div class="mb-3">
        <label for="patientID" class="form-label">Patient ID</label>
        <input type="text" class="form-control" id="patientID" name="patientID" placeholder="Enter Patient ID" required>
    </div>
    <div class="mb-3">
        <label for="clinicID" class="form-label">Clinic ID</label>
        <input type="text" class="form-control" id="clinicID" name="clinicID" placeholder="Enter Clinic ID" required>
    </div>
    <div class="mb-3">
        <label for="doctorID" class="form-label">Doctor ID</label>
        <input type="text" class="form-control" id="doctorID" name="doctorID" placeholder="Enter Doctor ID" required>
    </div>
    <div class="mb-3">
        <label for="appointmentID" class="form-label">Appointment ID</label>
        <input type="text" class="form-control" id="appointmentID" name="appointmentID" placeholder="Enter Appointment ID" required>
    </div>
    <div class="mb-3">
        <label for="startTime" class="form-label">Start Time</label>
        <input type="datetime-local" class="form-control" id="startTime" name="StartTime" required>
    </div>
    <div class="mb-3">
        <label for="consultationType" class="form-label">Consultation Type</label>
        <select class="form-select" id="consultationType" name="consultationType">
            <option value="Consultation">Consultation</option>
            <option value="InPatient">InPatient</option>
        </select>
    </div>
    <div class="mb-3 form-check">
        <input type="checkbox" class="form-check-input" id="virtualConsultation" name="virtualConsultation" value="1">
        <label class="form-check-label" for="virtualConsultation">Virtual Consultation</label>
    </div>
    <button type="submit" class="btn btn-primary" id="create-appt-btn">Create Appointment</button>
</form>

<%--<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kQtW33rZJAHj6NBK8rDnBy3v5a1Hgf5DIPzlC8ghKBUQaDX3H4yvWowZ2kz37HDN" crossorigin="anonymous"></script>--%>
</body>
</html>
