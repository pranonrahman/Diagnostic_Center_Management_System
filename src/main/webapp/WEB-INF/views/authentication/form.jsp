<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  * @author raian.rahman
  * @since 03/08/2022
--%>

<html>
<head>
    <title>Prescription</title>

    <link type="text/css" href="<c:url value="../../../assets/css/bootstrap.min.css"/>" rel="stylesheet"/>
    <link type="text/css" href="<c:url value="../../../assets/css/style.css"/>" rel="stylesheet"/>
    <script type="text/javascript" src="<c:url value="../../../assets/js/jquery-3.6.0.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="../../../assets/js/bootstrap.bundle.min.js"/>"></script>
</head>
<body>

<div class="container-fluid bg-primary-custom h-100">

    <h2 class="text-center py-3"> Login </h2>
    <div class="w-50 mx-auto">
        <c:if test="${empty user}">
            <form:form action="/login" method="post" modelAttribute="personViewModel">
                <div class="mb-3">
                    <form:label path="userName">Enter user name</form:label>
                    <form:input path="userName" type="text" required="required" cssClass="w-100"/>
                    <form:errors path="userName"/>
                </div>

                <div class="mb-3">
                    <form:label path="password">Enter password</form:label>
                    <form:input path="password" type="password" required="required" cssClass="w-100"/>
                    <form:errors path="password"/>
                </div>
                <button type="submit" value="submit" class="btn btn-primary w-100"><fmt:message key="authentication.form.nextPage"/></button>
            </form:form>
            <c:if test="${not empty message}">
                <div class="alert alert-danger">
                    <c:out value="${message}"/>
                </div>
            </c:if>
        </c:if>

        <c:if test="${not empty user and empty role}">
            <form:form action="/login/role" method="post" modelAttribute="personViewModel">
                <div class="mb-3">
                    <form:label path="role">Enter role</form:label>
                    <form:select path="role" cssClass="form-control">
                        <form:option value="">
                            Select
                        </form:option>
                        <form:options items="${seedRoleList}" itemLabel="name" itemValue="id"/>
                    </form:select>
                    <form:errors path="role"/>
                </div>

                <button type="submit" value="submit" class="btn btn-primary w-100"><fmt:message key="authentication.form.submit"/></button>
            </form:form>
        </c:if>
    </div>
</div>
<jsp:include page="../footer.jsp"/>
</body>
</html>
