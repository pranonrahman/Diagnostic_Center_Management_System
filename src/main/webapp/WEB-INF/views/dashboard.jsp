<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="net.therap.dms.entity.RoleEnum" %>
<%@ page import="net.therap.dms.util.RoleUtil" %>
<%--
  * @author raian.rahman
  * @since 11/08/2022
--%>
<html>
<head>
    <title>
        <fmt:message key="dashboard.title"/>
    </title>

    <link type="text/css" href="<c:url value="../../assets/css/bootstrap.min.css"/>" rel="stylesheet"/>
    <link type="text/css" href="<c:url value="../../assets/css/style.css"/>" rel="stylesheet"/>
    <script type="text/javascript" src="<c:url value="../../assets/js/jquery-3.6.0.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="../../assets/js/bootstrap.bundle.min.js"/>"></script>
</head>
<body>
<jsp:include page="header.jsp">
    <jsp:param name="linksStatus" value="hidden"/>
</jsp:include>

<div class="container-fluid bg-primary-custom h-100">

    <h2 class="text-center pt-3 pb-5">
        <fmt:message key="dashboard.head"/>
    </h2>

    <div class="w-50 mx-auto text-center">
        <c:if test="${RoleUtil.userContains(user, RoleEnum.DOCTOR)}">
            <h4 class="mt-5 text-center">DOCTOR</h4>
            <a href="/patient/list" class="btn btn-outline-dark w-25">
                <fmt:message key="navbar.link.yourPatients"/>
            </a>
        </c:if>

        <c:if test="${RoleUtil.userContains(user, RoleEnum.PATIENT)}">
            <h4 class="mt-5 text-center">PATIENT</h4>
            <a href="/prescription/list" class="btn btn-outline-dark w-25">
                <fmt:message key="navbar.link.yourPrescriptions"/>
            </a>
            <c:url var="patientInvoiceList" value="/invoice/list">
                <c:param name="patientId" value="${user.patient.id}"/>
            </c:url>
            <a href="${patientInvoiceList}" class="btn btn-outline-dark w-25">
                <fmt:message key="navbar.link.yourInvoices"/>
            </a>
        </c:if>

        <c:if test="${RoleUtil.userContains(user, RoleEnum.RECEPTIONIST)}">
            <h4 class="mt-5 text-center">RECEPTIONIST</h4>
            <a href="/invoice/doctor"
               class="btn btn-outline-dark w-25">

                <fmt:message key="navbar.link.createInvoice"/>
            </a>
            <a href="/invoice/list"
               class="btn btn-outline-dark w-25">

                <fmt:message key="navbar.link.viewAllInvoices"/>
            </a>
        </c:if>

        <c:if test="${RoleUtil.userContains(user, RoleEnum.ADMIN)}">
            <h4 class="mt-5 text-center">ADMIN</h4>

            <a href="<c:url value="/user/list"/>"
               class="btn btn-outline-dark w-25">

                <fmt:message key="button.label.viewAllUsers"/>
            </a>

            <a href="/user/" class="btn btn-outline-dark w-25">
                <fmt:message key="navbar.link.createUser"/>
            </a>
        </c:if>
    </div>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>