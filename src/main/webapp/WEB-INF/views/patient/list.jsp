<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  * @author amimul.ehsan
  * @since 03/08/2022
--%>
<html>
<head>
    <title>Prescription</title>

    <link type="text/css" href="<c:url value="../../../assets/css/bootstrap.min.css"/>" rel="stylesheet"/>
    <link type="text/css" href="<c:url value="../../../assets/css/style.css"/>" rel="stylesheet"/>
    <script type="text/javascript" src="<c:url value="../../../assets/js/jquery-3.6.0.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="../../../assets/js/bootstrap.bundle.min.js"/>"></script>
</head>
<body>

<div class="container-fluid bg-primary-custom h-100 w-75">

    <h2 class="text-center py-3"> List of patients </h2>

    <table class="table text-center">
        <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col">Name</th>
            <th scope="col">Age</th>
            <th scope="col">Sex</th>
            <th scope="col">History</th>
            <th scope="col">Appointments with me</th>
        </tr>
        </thead>

        <tbody>
        <c:forEach var="patient" items="${patients}" varStatus="loop">
            <tr>
                <th scope="row">${loop.index + 1}</th>
                <td><c:out value="${patient.person.name}"/></td>
                <td>very old</td>
                <td><c:out value="${patient.person.gender}"/></td>
                <td><a href="<c:url value="/patient/list"/>">View</a></td>
                <td><a href="<c:url value="/patient/list"/>">Edit</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>

