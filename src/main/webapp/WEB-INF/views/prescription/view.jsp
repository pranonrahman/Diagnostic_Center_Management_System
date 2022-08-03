<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  * @author amimul.ehsan
  * @since 02/08/2022
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

    <h2 class="text-center my-3"> Student </h2>
    <div class="w-50">

        <%--@elvariable id="prescription" type="net.therap.model.Prescription"--%>
        <form:form method="post"
                   modelAttribute="prescription">

            <div class="mb-3">
                <form:label path="symptoms" cssClass="form-label">
                    Student Name
                </form:label>

                <form:input type="text"
                            minlength="2"
                            maxlength="50"
                            cssClass="form-control"
                            path="symptoms"/>

                <form:errors path="symptoms" cssClass="invalid-feedback d-block"/>
            </div>

            <div class="mb-3">
                <form:label path="diagnosis" cssClass="form-label">
                    Department
                </form:label>

                <form:input type="text"
                            minlength="2"
                            maxlength="20"
                            class="form-control"
                            path="diagnosis"/>

                <form:errors path="diagnosis" cssClass="invalid-feedback d-block"/>
            </div>

            <div class="d-grid">
                    <%--                <c:choose>--%>
                    <%--                    <c:when test="${!student.isNew()}">--%>
                <button type="submit"
                        class="btn btn-primary mb-2"
                        name="action"
                        value="UPDATE">
                    UPDATE
                </button>

                <button type="submit"
                        class="btn btn-danger"
                        name="action"
                        value="DELETE">
                    DELETE
                </button>
                    <%--                    </c:when>--%>

                    <%--                    <c:otherwise>--%>
                <button type="submit"
                        class="btn btn-primary"
                        name="action"
                        value="CREATE">
                    CREATE
                </button>
                    <%--                    </c:otherwise>--%>
                    <%--                </c:choose>--%>
            </div>
        </form:form>
    </div>
</div>

</body>
</html>
