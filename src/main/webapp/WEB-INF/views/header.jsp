<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        <a class="navbar-brand" href="${pageContext.request.contextPath}/doctor/patients">
            <img src="../../assets/logo.png" alt="" width="55" height="55">
        </a>
        <div class="collapse navbar-collapse justify-content-end" id="navbarNavDropdown">
            <ul class="navbar-nav mx-3">
                <li class="nav-item">
                    <a href="${pageContext.request.contextPath}/doctor/patients" class="nav-link active">View
                        Patients</a>
                </li>
            </ul>
            <a href="${pageContext.request.contextPath}/logout" class="btn btn-danger">Logout</a>
        </div>
    </nav>
    <hr class="my-0"/>
</header>
