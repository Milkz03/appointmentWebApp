<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<!DOCTYPE html>
<html>
<head>
    <title>WebAppointments</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="p-5">
<h1 class="text-center">WebAppointments</h1>
<br/>
<h2 class="text-center">What do you want to do today?</h2>
<div class="container fluid d-flex flex-column my-3">
    <!-- Button to go to page which CREATEs an appointment -->
    <button type="button" class="btn btn-primary mx-auto my-2 w-50">Make an Appointment with a Doctor</button>
    <!-- Button to go to page which READs an appointment -->
    <button type="button" class="btn btn-primary mx-auto my-2 w-50">Check the Appointments of a Doctor</button>
    <!-- Button to go to page which UPDATEs an appointment -->
    <button type="button" class="btn btn-primary mx-auto my-2 w-50">Update an Existing Appointment with a Doctor</button>
    <!-- Button to go to page which DELETEs an appointment -->
    <button type="button" class="btn btn-primary mx-auto my-2 w-50">Cancel an Existing Appointment with a Doctor</button>
</div>
</body>
</html>