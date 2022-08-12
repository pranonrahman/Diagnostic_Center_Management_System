<%@ page import="net.therap.entity.Role" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="net.therap.entity.RoleEnum" %>
<%--
  @author: khandaker.maruf
  @since: 6/20/22
--%>
<link type="text/css" href="<c:url value="../../assets/css/bootstrap.min.css"/>" rel="stylesheet"/>
<link type="text/css" href="<c:url value="../../assets/css/style.css"/>" rel="stylesheet"/>
<header>
    <nav class="px-4 navbar sticky-top navbar-expand-lg">
        <a class="navbar-brand"
           href="${pageContext.request.contextPath}/">

            <img src="../../assets/logo.png"
                 alt="" width="55" height="55">
        </a>
        <div class="collapse navbar-collapse justify-content-end"
             id="navbarNavDropdown">

            <ul class="navbar-nav mx-3">
                <c:forEach items="${user.roles}" var="role">
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
                                <a href="${pageContext.request.contextPath}/invoice/doctor"
                                   class="nav-link active">

                                    <fmt:message key="navbar.link.createInvoice"/>
                                </a>
                            </li>
                            <li class="nav-item">
                                <a href="${pageContext.request.contextPath}/invoice/list"
                                   class="nav-link active">

                                    <fmt:message key="navbar.link.viewInvoices"/>
                                </a>
                            </li>
                        </c:when>

                        <c:when test="${role.getName().equals(RoleEnum.ADMIN)}">
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle"
                                   data-bs-toggle="dropdown"
                                   href="#"
                                   id="navbarAdminMenuLink"
                                   aria-haspopup="true" aria-expanded="false">

                                    <fmt:message key="text.view"/>
                                </a>
                                <div class="dropdown-menu"
                                     aria-labelledby="navbarAdminMenuLink">

                                    <a href="<c:url value="${pageContext.request.contextPath}/user/list"/>"
                                       class="dropdown-item">

                                        <fmt:message key="navbar.link.viewUsers"/>
                                    </a>

                                    <c:url var="adminList"
                                           value="${pageContext.request.contextPath}/user/list">

                                        <c:param name="filterBy"
                                                 value="${RoleEnum.ADMIN}"/>
                                    </c:url>
                                    <a href="${adminList}"
                                       class="dropdown-item">

                                        <fmt:message key="navbar.link.viewAdmins"/>
                                    </a>

                                    <c:url var="doctorList"
                                           value="${pageContext.request.contextPath}/user/list">

                                        <c:param name="filterBy"
                                                 value="${RoleEnum.DOCTOR}"/>
                                    </c:url>
                                    <a href="${doctorList}"
                                       class="dropdown-item">

                                        <fmt:message key="navbar.link.viewDoctors"/>
                                    </a>

                                    <c:url var="receptionistList"
                                           value="${pageContext.request.contextPath}/user/list">

                                        <c:param name="filterBy"
                                                 value="${RoleEnum.RECEPTIONIST}"/>
                                    </c:url>
                                    <a href="${receptionistList}"
                                       class="dropdown-item">

                                        <fmt:message key="navbar.link.viewReceptionists"/>
                                    </a>

                                    <c:url var="patientList"
                                           value="${pageContext.request.contextPath}/user/list">

                                        <c:param name="filterBy"
                                                 value="${RoleEnum.PATIENT}"/>
                                    </c:url>
                                    <a href="${patientList}"
                                       class="dropdown-item">

                                        <fmt:message key="navbar.link.viewAllPatients"/>
                                    </a>
                                </div>
                            </li>

                            <li class="nav-item">
                                <a href="${pageContext.request.contextPath}/user/" class="nav-link active">
                                    <fmt:message key="navbar.link.createUser"/>
                                </a>
                            </li>
                        </c:when>
                    </c:choose>
                </c:forEach>
            </ul>
            <a href="${pageContext.request.contextPath}/logout"
               class="btn btn-danger">

                <fmt:message key="navbar.link.logout"/>
            </a>
        </div>
    </nav>
    <hr class="my-0"/>
</header>
