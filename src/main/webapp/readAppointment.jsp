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
<h2 class="text-center">Read an Appointment with a Doctor</h2>
<form>
    <div class="row">
        <div class="col">
            Choose Appointment ID
        </div>
    </div>
    <div class="row">
        <div class="col">
            <div class="form-floating">
                <select class="form-select" id="floatingSelect" aria-label="Floating label select example">
                    <option selected></option>
                    <option value="1">One</option>
                    <option value="2">Two</option>
                    <option value="3">Three</option>
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
