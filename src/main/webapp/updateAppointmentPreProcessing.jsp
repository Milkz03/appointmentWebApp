<%--
  Created by IntelliJ IDEA.
  User: Lianne
  Date: 02/04/2024
  Time: 11:43 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Make an Appointment with a Doctor</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="p-5">
<h1 class="text-center">WebAppointments</h1>
<br/>
<h2 class="text-center">Update an Appointment with a Doctor</h2>
<form action="updateAppointmentProcessing.jsp">
    <jsp:useBean id="UpdateB" class="appointmentManagement.updateAppointment" scope="session"/>
    <% UpdateB.startTransaction();
       UpdateB.appointmentID = request.getParameter("appointmentID");
       UpdateB.infoAppointments();
       session.setAttribute("appointmentID", UpdateB.appointmentID);%>
    <div class="row">
        <div class="col">Details</div>
    </div>
    <div class="row">
        <div class="col">
            <div class="form-floating mb-3">
                <input type="text" class="form-control" name="patientName" id="floatingInput" value="<%= UpdateB.patientID%>" placeholder="name@example.com">
                <label for="floatingInput">Patient Name</label>
            </div>
        </div>
        <div class="col">
            <div class="form-floating">
                <select class="form-select" name="doctorName" id="floatingSelect" aria-label="Floating label select example">
                    <option selected> <%=UpdateB.doctorID%> </option>
                    <option value="1">One</option>
                    <option value="2">Two</option>
                    <option value="3">Three</option>
                </select>
                <label for="floatingSelect">Doctor Name</label>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col">
            <div class="row">

                <div class="col">
                    Date and Time Queued
                </div>
            </div>
            <div class="row">
                <div class="col">
                    <input type="datetime-local" class="p-2 form-control" name="timeQueued" value="<%=UpdateB.timeQueued%>" step="1">
                </div>
            </div>
        </div>
        <div class="col">
            <div class="row">

                <div class="col">
                    Start Time
                </div>
            </div>
            <div class="row">
                <div class="col">
                    <input type="datetime-local" class="p-2 form-control" name="startTime" value="<%=UpdateB.startTime%>" step="1">
                </div>
            </div>
        </div>
        <div class="col">
            <div class="row">
                <div class="col">
                    End Time
                </div>
            </div>
            <div class="row">
                <div class="col">
                    <input type="datetime-local" class="p-2 form-control" name="endTime" value="<%=UpdateB.endTime%>" step="1">
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col">
            Consultation Details
        </div>
    </div>
    <div class="row">
        <div class="col">
            <div class="form-floating">
                <select class="form-select" name="apptStatus" id="floatingSelect" aria-label="Floating label select example">
                    <option value="<%=UpdateB.apptStatus%>"selected><%=UpdateB.apptStatus%></option>
                    <option value="Queued">Queued</option>
                    <option value="No Show">No Show</option>
                    <option value="Cancel">Cancel</option>
                    <option value="Serving">Serving</option>
                    <option value="Skip">Skip</option>
                </select>
                <label for="floatingSelect">Status</label>
            </div>
        </div>
        <div class="col">
            <div class="form-floating">
                <select class="form-select" name="consultationType" id="floatingSelect" aria-label="Floating label select example">
                    <option value="<%=UpdateB.consultationType%>"selected><%=UpdateB.consultationType%></option>
                    <option value="InPatient">InPatient</option>
                </select>
                <label for="floatingSelect">Type</label>
            </div>
        </div>
        <div class="col form-check">
            <input class="form-check-input" type="checkbox" name="virtualConsultation" id="flexCheckDefault" <%=UpdateB.virtualState%>>
            <label class="form-check-label" for="flexCheckDefault">
                Virtual
            </label>
        </div>
    </div>
    <div class="row">
        <button type="submit" class="btn btn-primary mx-auto my-2 w-100">Update Appointment</button>
    </div>
</form>

</body>
</html>
