<%@ page import="net.therap.model.Role" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="net.therap.model.RoleEnum" %>
<%--
  @author: khandaker.maruf
  @since: 6/20/22
--%>
<link type="text/css" href="<c:url value="../../assets/css/bootstrap.min.css"/>" rel="stylesheet"/>
<link type="text/css" href="<c:url value="../../assets/css/style.css"/>" rel="stylesheet"/>
<script type="text/javascript" src="<c:url value="../../assets/js/jquery-3.6.0.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="../../assets/js/bootstrap.bundle.min.js"/>"></script>
<header>
    <nav class="px-4 navbar sticky-top navbar-expand-lg">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/">
            <img src="../../assets/logo.png" alt="" width="55" height="55">
        </a>
        <div class="collapse navbar-collapse justify-content-end" id="navbarNavDropdown">
            <ul class="navbar-nav mx-3">
                <c:choose>
                    <c:when test="${role.getName().equals(RoleEnum.DOCTOR)}">
                        <li class="nav-item">
                            <a href="${pageContext.request.contextPath}/patient/list" class="nav-link active">
                                <fmt:message key="navbar.link.viewPatients"/>
                            </a>
                        </li>
                    </c:when>

                    <c:when test="${role.getName().equals(RoleEnum.PATIENT)}">
                        <li class="nav-item">
                            <a href="${pageContext.request.contextPath}/prescription/list" class="nav-link active">
                                <fmt:message key="navbar.link.viewPrescriptions"/>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="${pageContext.request.contextPath}/invoice/list" class="nav-link active">
                                <fmt:message key="navbar.link.viewInvoices"/>
                            </a>
                        </li>
                    </c:when>

                    <c:when test="${role.getName().equals(RoleEnum.RECEPTIONIST)}">
                        <li class="nav-item">
                            <a href="${pageContext.request.contextPath}/invoice/doctor" class="nav-link active">
                                <fmt:message key="navbar.link.createInvoice"/>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="${pageContext.request.contextPath}/invoice/list" class="nav-link active">
                                <fmt:message key="navbar.link.viewInvoices"/>
                            </a>
                        </li>
                    </c:when>

                    <c:when test="${role.getName().equals(RoleEnum.ADMIN)}">
                        <li class="nav-item">
                            <a href="${pageContext.request.contextPath}/person/list" class="nav-link active">
                                <fmt:message key="navbar.link.viewUsers"/>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="${pageContext.request.contextPath}/person/save" class="nav-link active">
                                <fmt:message key="navbar.link.createUser"/>
                            </a>
                        </li>
                    </c:when>
                </c:choose>
            </ul>
            <a href="${pageContext.request.contextPath}/logout" class="btn btn-danger">
                <fmt:message key="navbar.link.logout"/>
            </a>
        </div>
    </nav>
    <hr class="my-0"/>
</header>
