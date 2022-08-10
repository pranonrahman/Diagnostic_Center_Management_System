<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  * @author amimul.ehsan
  * @since 03/08/2022
--%>
<html>
<head>
    <title><fmt:message key="title.patients"/> </title>

    <link type="text/css" href="<c:url value="../../../assets/css/bootstrap.min.css"/>" rel="stylesheet"/>
    <link type="text/css" href="<c:url value="../../../assets/css/style.css"/>" rel="stylesheet"/>
    <script type="text/javascript" src="<c:url value="../../../assets/js/jquery-3.6.0.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="../../../assets/js/bootstrap.bundle.min.js"/>"></script>
    <jsp:include page="../header.jsp"/>
</head>
<body>

<div class="container-fluid bg-primary-custom h-100 w-75">

    <h2 class="text-center py-3"> <fmt:message key="text.listOfPatients"/> </h2>

    <table class="table text-center">
        <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col"><fmt:message key="text.name"/> </th>
            <th scope="col"><fmt:message key="text.age"/></th>
            <th scope="col"><fmt:message key="text.sex"/></th>
            <th scope="col"><fmt:message key="text.history"/></th>
        </tr>
        </thead>

        <tbody>
        <c:forEach var="patientViewModel" items="${patients}" varStatus="loop">
            <tr>
                <th scope="row">${loop.index + 1}</th>
                <td><c:out value="${patientViewModel.patient.user.name}"/></td>
                <td><c:out value="${patientViewModel.getAge()}"/></td>
                <td><c:out value="${patientViewModel.patient.user.gender}"/></td>
                <c:url var="historyViewPage" value="${pageContext.request.contextPath}/patient/history">
                    <c:param name="id" value="${patientViewModel.patient.id}"/>
                </c:url>
                <td><a href="${historyViewPage}"><fmt:message key="text.view"/></a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<jsp:include page="../footer.jsp"/>
</body>
</html>

