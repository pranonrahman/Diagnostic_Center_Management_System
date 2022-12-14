<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  * @author raian.rahman
  * @since 03/08/2022
--%>

<html>
<head>
    <title> <fmt:message key="authentication.title"/> </title>

    <link type="text/css" href="<c:url value="../../../assets/css/bootstrap.min.css"/>" rel="stylesheet"/>
    <link type="text/css" href="<c:url value="../../../assets/css/style.css"/>" rel="stylesheet"/>
    <script type="text/javascript" src="<c:url value="../../../assets/js/jquery-3.6.0.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="../../../assets/js/bootstrap.bundle.min.js"/>"></script>
</head>
<body>

<div class="container-fluid bg-primary-custom h-100">

    <h2 class="text-center py-3">
        <fmt:message key="header.login"/>
    </h2>

    <div class="w-50 mx-auto">

        <form:form method="post" modelAttribute="userCmd">
            <form:errors path="" cssClass="alert alert-danger d-block"/>

            <div class="mb-3">
                <form:label path="userName">
                    <fmt:message key="authentication.form.userName"/>
                </form:label>

                <form:input path="userName" type="text" cssClass="w-100"/>
                <form:errors path="userName" cssClass="invalid-feedback d-block"/>
            </div>

            <div class="mb-3">
                <form:label path="password">
                    <fmt:message key="authentication.form.password"/>
                </form:label>

                <form:input path="password" type="password" cssClass="w-100"/>
                <form:errors path="password" cssClass="invalid-feedback d-block"/>
            </div>

            <button type="submit" value="submit" class="btn btn-primary w-100">
                <fmt:message key="authentication.form.submit"/>
            </button>
        </form:form>
    </div>
</div>
<jsp:include page="../footer.jsp"/>

</body>
</html>
