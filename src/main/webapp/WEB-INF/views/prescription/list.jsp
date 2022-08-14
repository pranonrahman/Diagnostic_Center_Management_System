<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  * @author amimul.ehsan
  * @since 08/08/2022
--%>
<html>
<head>
    <title><fmt:message key="title.prescriptions"/></title>

    <link type="text/css" href="<c:url value="../../../assets/css/bootstrap.min.css"/>" rel="stylesheet"/>
    <link type="text/css" href="<c:url value="../../../assets/css/style.css"/>" rel="stylesheet"/>
    <script type="text/javascript" src="<c:url value="../../../assets/js/jquery-3.6.0.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="../../../assets/js/bootstrap.bundle.min.js"/>"></script>
</head>
<body>
<jsp:include page="../header.jsp"/>

<div class="container-fluid bg-primary-custom h-100 w-75">

    <h2 class="text-center py-3">
        <fmt:message key="text.prescriptionsOf"/>
        <c:out value="${user.name}"/>
    </h2>

    <div class="list-group">
        <c:forEach items="${prescriptions}"
                   var="prescription">

            <c:url var="prescriptionViewPage"
                   value="/prescription">

                <c:param name="id"
                         value="${prescription.id}"/>

            </c:url>

            <a href="${prescriptionViewPage}"
               class="list-group-item list-group-item-dark mb-2 ">

                <div class="d-flex w-100 justify-content-between">
                    <h5 class="card-title">
                        <fmt:message key="text.diagnosis"/>:
                        <c:out value="${prescription.diagnosis == null ?
                        'N/A' : prescription.diagnosis}"/>
                    </h5>
                    <small>
                        <fmt:message key="label.createdOn"/>:
                        <fmt:formatDate value="${prescription.created}"/>
                        <br>
                        <fmt:message key="label.updatedOn"/>:
                        <fmt:formatDate value="${prescription.updated}"/>
                    </small>
                </div>
                <p class="card-text">
                    <fmt:message key="text.symptoms"/>:
                    <c:out value="${prescription.symptoms == null ?
                    'N/A' : prescription.symptoms}"/>
                </p>
            </a>
        </c:forEach>
    </div>
</div>
<jsp:include page="../footer.jsp"/>
</body>
</html>