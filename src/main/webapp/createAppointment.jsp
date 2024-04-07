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
<h2 class="text-center">Make an Appointment with a Doctor</h2>
<form>
    <div class="row">
        <div class="col">Details</div>
    </div>
    <div class="row">
        <div class="col">
            <div class="form-floating mb-3">
                <input type="text" class="form-control" id="floatingInput" placeholder="name@example.com">
                <label for="floatingInput">Appointment ID</label>
            </div>
        </div>
        <div class="col">
            <div class="form-floating mb-3">
                <input type="text" class="form-control" id="floatingInput" placeholder="name@example.com">
                <label for="floatingInput">Patient ID</label>
            </div>
        </div>
        <div class="col">
            <div class="form-floating">
                <select class="form-select" id="floatingSelect" aria-label="Floating label select example">
                    <option selected>Doctor ID</option>
                    <option value="1">One</option>
                    <option value="2">Two</option>
                    <option value="3">Three</option>
                </select>
                <label for="floatingSelect">Doctor ID</label>
            </div>
        </div>
        <div class="col">
            <div class="form-floating">
                <select class="form-select" id="floatingSelect" aria-label="Floating label select example">
                    <option selected>Clinic ID</option>
                    <option value="1">One</option>
                    <option value="2">Two</option>
                    <option value="3">Three</option>
                </select>
                <label for="floatingSelect">Clinic ID</label>
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
                    <input type="datetime-local" class="p-2 form-control">
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
                    <input type="datetime-local" class="p-2 form-control">
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
                    <input disabled type="datetime-local" class="p-2 form-control">
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
                <select disable class="form-select" id="floatingSelect" aria-label="Floating label select example">
                    <option value="Consultation">Complete</option>
                    <option value="InPatient" selected>Queued</option>
                    <option value="InPatient">No Show</option>
                    <option value="InPatient">Cancel</option>
                    <option value="InPatient">Serving</option>
                    <option value="InPatient">Skip</option>
                </select>
                <label for="floatingSelect">Status</label>
            </div>
        </div>
        <div class="col">
            <div class="form-floating">
                <select class="form-select" id="floatingSelect" aria-label="Floating label select example">
                    <option value="Consultation"selected>Consultation</option>
                    <option value="InPatient">InPatient</option>
                </select>
                <label for="floatingSelect">Type</label>
            </div>
        </div>
        <div class="col form-check">
            <input class="form-check-input" type="checkbox" value="" id="flexCheckDefault">
            <label class="form-check-label" for="flexCheckDefault">
                Virtual
            </label>
        </div>
    </div>
    <div class="row">
        <button type="submit" class="btn btn-primary mx-auto my-2 w-100">Create Appointment</button>
    </div>
</form>

</body>
</html>