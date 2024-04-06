<%--
  Created by IntelliJ IDEA.
  User: mukbookpro
  Date: 4/7/24
  Time: 12:28 am
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
<form action="updateAppointmentPreProcessing.jsp">
    <jsp:useBean id="UpdateA" class="appointmentManagement.updateAppointment" scope="session"/>
    <div class="row">
        <div class="col">
            Choose Appointment ID
        </div>
    </div>
    <div class="row">
        <div class="col">
            <div class="form-floating">
                <select class="form-select" name="apptid" id="floatingSelect" aria-label="Floating label select example">
                    <option selected></option>
                    <% UpdateA.availableAppointments();
                        for (int i = 0; i < UpdateA.appointmentsIDs.size(); i++) { %>
                    <option value="<%=UpdateA.appointmentsIDs.get(i)%>"><%=UpdateA.appointmentsIDs.get(i)%></option>
                    <%  } %>
                </select>
                <label for="floatingSelect">Appointment ID</label>
            </div>
        </div>
    </div>
    <button type="submit" class="btn btn-primary mx-auto my-2 w-100">Check Details</button>
</form>
<form>
    <button type="submit" class="btn btn-primary mx-auto my-2 w-100">See All Appointments</button>
</form>

</body>
</html>
