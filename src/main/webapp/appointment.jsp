<%--
  Created by IntelliJ IDEA.
  User: Lianne
  Date: 04/04/2024
  Time: 9:37 am
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
            <div class="row">
                <div class="col">Patient Name</div>
            </div>
            <div class="row">
                <div class="col">
                    <input class="form-control" type="text" value="" aria-label="readonly input example" readonly>
                </div>
            </div>
        </div>
        <div class="col">
            <div class="row">
                <div class="col">Doctor Name</div>
            </div>
            <div class="row">
                <input class="form-control" type="text" value="" aria-label="readonly input example" readonly>
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
                    <input disabled type="datetime-local" class="p-2 form-control">
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
                    <input disabled type="datetime-local" class="p-2 form-control">
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
            Type
        </div>
    </div>
    <div class="row">
        <div class="col">
            <div class="form-floating">
                <select disabled class="form-select" id="floatingSelect" aria-label="Floating label select example">
                    <option value="Complete"selected>Complete</option>
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
                <select disabled class="form-select" id="floatingSelect" aria-label="Floating label select example">
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

    </div>
</form>

</body>
</html>
