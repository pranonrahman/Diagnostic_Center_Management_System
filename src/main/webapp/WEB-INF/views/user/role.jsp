<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  * @author raian.rahman
  * @since 04/08/2022
--%>

<html>
<head>
    <title>
        <fmt:message key="user.role.title"/>
    </title>

    <link type="text/css" href="<c:url value="../../../assets/css/bootstrap.min.css"/>" rel="stylesheet"/>
    <link type="text/css" href="<c:url value="../../../assets/css/style.css"/>" rel="stylesheet"/>
    <script type="text/javascript" src="<c:url value="../../../assets/js/jquery-3.6.0.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="../../../assets/js/bootstrap.bundle.min.js"/>"></script>
</head>
<body>
<jsp:include page="../header.jsp"/>

<div class="container-fluid bg-primary-custom h-100">

    <h2 class="text-center py-3">
        <fmt:message key="user.role.header"/>
        <c:out value="${user.userName}"/>
    </h2>
    <div class="w-50 mx-auto">
        <form:form action="/user/updateRole"
                   method="POST"
                   modelAttribute="roleUpdateCmd">

            <form:input hidden="hidden"
                        path="id"
                        type="text"
                        value="${userData.id}"
                        cssClass="form-control"/>

            <div class="mb-3">
                <form:checkbox path="admin"
                               value="${isAdmin}"
                               cssClass="form-check-inline"/>

                <form:label path="admin"
                            cssClass="form-check-label">

                    <fmt:message key="user.role.admin"/>
                </form:label>

                <form:errors path="admin"
                             cssClass="invalid-feedback d-block w-100"/>
            </div>

            <div class="mb-3">
                <form:checkbox path="receptionist"
                               value="${isReceptionist}"
                               cssClass="form-check-inline"/>

                <form:label path="receptionist"
                            cssClass="form-check-label">

                    <fmt:message key="user.role.receptionist"/>
                </form:label>

                <form:errors path="receptionist"
                             cssClass="invalid-feedback d-block w-100"/>
            </div>

            <div class="mb-3">
                <form:checkbox path="patient"
                               value="${isPatient}"
                               cssClass="form-check-inline"/>

                <form:label path="patient"
                            cssClass="form-check-label">

                    <fmt:message key="user.role.patient"/>
                </form:label>

                <form:errors path="patient"
                             cssClass="invalid-feedback d-block w-100"/>
            </div>

            <div class="mb-3">
                <form:checkbox path="doctor"
                               value="${isDoctor}"
                               cssClass="form-check-inline"/>

                <form:label path="doctor"
                            cssClass="form-check-label w-25">

                    <fmt:message key="user.role.doctor"/>
                </form:label>

                <form:errors path="doctor"
                             cssClass="invalid-feedback d-block"/>

                <form:label path="fee"
                            cssClass="form-check-label">

                     <fmt:message key="user.role.fee"/>
                </form:label>

                <form:input path="fee"
                            cssClass="form-text"
                            type="number"
                            min="0"
                            step="0.1"/>

                <form:errors path="fee"
                             cssClass="invalid-feedback d-block"/>
            </div>

            <button type="submit"
                    value="submit"
                    class="btn btn-primary w-100">

                <fmt:message key="user.button.submit"/>
            </button>
        </form:form>
    </div>
</div>

<jsp:include page="../footer.jsp"/>

</body>
</html>