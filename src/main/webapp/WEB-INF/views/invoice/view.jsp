<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

    <h2 class="text-center py-3"> Invoice </h2>
    <div class="w-50 mx-auto">

        <%--@elvariable id="invoice" type="net.therap.viewModel.InvoiceViewModel"--%>
        <form:form method="post" modelAttribute="invoice">

            <div class="mb-3">
                <form:label path="patient" cssClass="form-label">
                    Patient
                </form:label>

                <form:select path="patient"
                             cssClass="form-select form-select-lg mb-3">

                    <form:option value="">
                        Select
                    </form:option>
                    <form:options items="${patients}"
                                  itemValue="id"
                                  itemLabel="person.name"/>
                </form:select>

                <form:errors path="patient" cssClass="invalid-feedback d-block"/>
            </div>

            <div class="mb-3">
                <form:label path="doctors"> Select Doctors </form:label>

                <div class="form-check d-grid">
                    <form:checkboxes class="form-check-input"
                                     items="${doctors}"
                                     itemValue="id"
                                     itemLabel="person.name"
                                     path="doctors"/>
                </div>

                <form:errors path="doctors" cssClass="invalid-feedback d-block"/>
            </div>

            <div class="mb-3">
                <form:label path="medicines"> Select Medicines </form:label>

                <div class="form-check d-grid">
                    <form:checkboxes class="form-check-input"
                                     items="${medicines}"
                                     itemValue="id"
                                     itemLabel="name"
                                     path="medicines"/>
                </div>

                <form:errors path="doctors" cssClass="invalid-feedback d-block"/>
            </div>

            <div class="mb-3">
                <form:label path="facilities"> Select Facilities </form:label>

                <div class="form-check d-grid">
                    <form:checkboxes class="form-check-input"
                                     items="${facilities}"
                                     itemValue="id"
                                     itemLabel="name"
                                     path="facilities"/>
                </div>

                <form:errors path="facilities" cssClass="invalid-feedback d-block"/>
            </div>


<%--

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
