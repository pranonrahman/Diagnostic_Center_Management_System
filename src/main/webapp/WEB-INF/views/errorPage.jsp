<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  @author: amimul.ehsan
  @since: 04/08/2022
--%>

<html>
<head>
    <title> Error</title>
    <link type="text/css" href="<c:url value="../../assets/css/bootstrap.min.css"/>" rel="stylesheet"/>
    <link type="text/css" href="<c:url value="../../assets/css/style.css"/>" rel="stylesheet"/>
    <script type="text/javascript" src="<c:url value="../../assets/js/jquery-3.6.0.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="../../assets/js/bootstrap.bundle.min.js"/>"></script>
</head>

<body>
<div class="d-flex align-items-center justify-content-center h-100 alert-danger">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-12 text-center">
                <h1 class="display-1 d-block">${httpErrorCode}</h1>
                <h4 class="mb-4 lead"><c:out value="${errorMessage}"/></h4>
                <a href="${pageContext.request.contextPath}/" class="btn btn-danger">Back to Home</a>
            </div>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp"/>
</body>
</html>