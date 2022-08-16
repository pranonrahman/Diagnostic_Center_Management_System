<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="net.therap.dms.entity.RoleEnum" %>
<%@ page import="net.therap.dms.util.RoleUtil" %>
<%--
  @author: khandaker.maruf
  @since: 6/20/22
--%>
<link type="text/css" href="<c:url value="../../assets/css/bootstrap.min.css"/>" rel="stylesheet"/>
<link type="text/css" href="<c:url value="../../assets/css/style.css"/>" rel="stylesheet"/>
<header>
    <nav class="px-4 navbar sticky-top navbar-expand-lg">
        <a class="navbar-brand"
           href="/">

            <img src="../../assets/logo.png"
                 alt="" width="55" height="55">
        </a>

        <div class="collapse navbar-collapse justify-content-end"
             id="navbarNavDropdown">

            <ul class="navbar-nav mx-3" ${param.linksStatus}>
                <c:if test="${RoleUtil.hasRole(user, RoleEnum.DOCTOR)}">

                    <li class="nav-item">
                        <a href="/patient/list" class="nav-link active">
                            <fmt:message key="navbar.link.yourPatients"/>
                        </a>
                    </li>
                    <li class="my-auto">|</li>
                </c:if>

                <c:if test="${RoleUtil.hasRole(user, RoleEnum.PATIENT)}">
                    <li class="nav-item">
                        <a href="/prescription/list" class="nav-link active">
                            <fmt:message key="navbar.link.yourPrescriptions"/>
                        </a>
                    </li>
                    <li class="nav-item">
                        <c:url var="patientInvoiceList" value="/invoice/list">
                            <c:param name="patientId" value="${user.patient.id}"/>
                        </c:url>
                        <a href="${patientInvoiceList}" class="nav-link active">
                            <fmt:message key="navbar.link.yourInvoices"/>
                        </a>
                    </li>
                    <li class="my-auto">|</li>
                </c:if>

                <c:if test="${RoleUtil.hasRole(user, RoleEnum.RECEPTIONIST)}">
                    <li class="nav-item">
                        <a href="/invoice/doctor"
                           class="nav-link active">

                            <fmt:message key="navbar.link.createInvoice"/>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a href="/invoice/list"
                           class="nav-link active">

                            <fmt:message key="navbar.link.viewAllInvoices"/>
                        </a>
                    </li>
                    <li class="my-auto">|</li>
                </c:if>

                <c:if test="${RoleUtil.hasRole(user, RoleEnum.ADMIN)}">
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

                            <a href="<c:url value="/user/list"/>"
                               class="dropdown-item">

                                <fmt:message key="navbar.link.viewUsers"/>
                            </a>

                            <c:url var="adminList"
                                   value="/user/list">

                                <c:param name="filterBy"
                                         value="${RoleEnum.ADMIN}"/>
                            </c:url>
                            <a href="${adminList}"
                               class="dropdown-item">

                                <fmt:message key="navbar.link.viewAdmins"/>
                            </a>

                            <c:url var="doctorList"
                                   value="/user/list">

                                <c:param name="filterBy"
                                         value="${RoleEnum.DOCTOR}"/>
                            </c:url>
                            <a href="${doctorList}"
                               class="dropdown-item">

                                <fmt:message key="navbar.link.viewDoctors"/>
                            </a>

                            <c:url var="receptionistList"
                                   value="/user/list">

                                <c:param name="filterBy"
                                         value="${RoleEnum.RECEPTIONIST}"/>
                            </c:url>
                            <a href="${receptionistList}"
                               class="dropdown-item">

                                <fmt:message key="navbar.link.viewReceptionists"/>
                            </a>

                            <c:url var="patientList"
                                   value="/user/list">

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
                        <a href="/user/" class="nav-link active">
                            <fmt:message key="navbar.link.createUser"/>
                        </a>
                    </li>
                    <li class="my-auto">|</li>
                </c:if>
            </ul>
        </div>

        <a href="/logout"
           class="btn btn-danger">

            <fmt:message key="navbar.link.logout"/>
        </a>
    </nav>
    <hr class="my-0"/>
</header>
