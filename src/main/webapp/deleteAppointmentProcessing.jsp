<%--
  Created by IntelliJ IDEA.
  User: mukbookpro
  Date: 4/7/24
  Time: 3:47 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Make an Appointment with a Doctor</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>
<form>
    <jsp:useBean id="DeleteB" class="appointmentManagement.deleteAppointment" scope="session"/>
    <%
        DeleteB.appointmentID = request.getParameter("appointmentID");
        int result = DeleteB.deleteAppointment();
        if (result == 0) { %>
    <h1>Fail</h1>
    <% }
    else { %>
    <h1>Pass</h1>
    <% } %>
</form>
</body>
</html>
