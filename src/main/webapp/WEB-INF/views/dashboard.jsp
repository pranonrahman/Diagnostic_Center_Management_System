<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  * @author raian.rahman
  * @since 11/08/2022
--%>
<html>
<head>
    <title><fmt:message key="dashboard.title"/> </title>

    <link type="text/css" href="<c:url value="../../assets/css/bootstrap.min.css"/>" rel="stylesheet"/>
    <link type="text/css" href="<c:url value="../../assets/css/style.css"/>" rel="stylesheet"/>
    <script type="text/javascript" src="<c:url value="../../assets/js/jquery-3.6.0.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="../../assets/js/bootstrap.bundle.min.js"/>"></script>
</head>
<body>

<div class="container-fluid bg-primary-custom h-100 w-75">

    <h2 class="text-center py-3">
        <fmt:message key="dashboard.head"/>
    </h2>


</div>
<jsp:include page="footer.jsp"/>

</body>
</html>