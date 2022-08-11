<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  * @author raian.rahman
  * @since 11/08/2022
--%>
<html>
<head>
    <title>
<%--        <fmt:message key="dashboard.title"/>--%>
        Dashboard
    </title>

    <link type="text/css" href="<c:url value="../../assets/css/bootstrap.min.css"/>" rel="stylesheet"/>
    <link type="text/css" href="<c:url value="../../assets/css/style.css"/>" rel="stylesheet"/>
    <script type="text/javascript" src="<c:url value="../../assets/js/jquery-3.6.0.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="../../assets/js/bootstrap.bundle.min.js"/>"></script>
</head>
<body>

<jsp:include page="header.jsp"/>
<div class="container-fluid bg-primary-custom h-100 w-75">

    <h2 class="text-center py-3">
<%--        <fmt:message key="dashboard.head"/>--%>
        Welcome to Diagnostic Center Management
    </h2>


</div>
<div class="container-fluid h-100 bg-primary-custom w-50 mx-auto">
    <a href="" class="btn btn-primary"></a>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>