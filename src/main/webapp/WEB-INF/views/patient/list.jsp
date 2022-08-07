<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  * @author amimul.ehsan
  * @since 03/08/2022
--%>
<html>
<head>
    <title>Patients</title>

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
            <th scope="col">New Prescription</th>
        </tr>
        </thead>

        <tbody>
        <c:forEach var="patient" items="${patients}" varStatus="loop">
            <tr>
                <th scope="row">${loop.index + 1}</th>
                <td><c:out value="${patient.person.name}"/></td>
                <td>very old</td>
                <td><c:out value="${patient.person.gender}"/></td>
                <c:url var="historyViewPage" value="${pageContext.request.contextPath}/patient/history">
                    <c:param name="id" value="${patient.id}"/>
                </c:url>
                <td><a href="${historyViewPage}">View</a></td>
                <c:url var="prescriptionForm" value="/prescription/create">
                    <c:param name="patientId" value="${patient.id}"/>
                    <c:param name="doctorId" value="${doctorId}"/>
                </c:url>
                <td><a href="<c:url value="${prescriptionForm}"/>">Add</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<jsp:include page="../footer.jsp"/>
</body>
</html>

