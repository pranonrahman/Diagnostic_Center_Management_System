<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  * @author khandaker.maruf
  * @since 02/08/2022
--%>

<html>
<head>
    <title>Prescription</title>

    <link type="text/css" href="<c:url value="../../../assets/css/bootstrap.min.css"/>" rel="stylesheet"/>
    <link type="text/css" href="<c:url value="../../../assets/css/style.css"/>" rel="stylesheet"/>
    <script type="text/javascript" src="<c:url value="../../../assets/js/jquery-3.6.0.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="../../../assets/js/bootstrap.bundle.min.js"/>"></script>
    <jsp:include page="../header.jsp"/>
</head>
<body>

<div class="container-fluid bg-primary-custom h-100">

    <h2 class="text-center py-3"> Invoice </h2>
    <div class="w-50 mx-auto">

        <%--@elvariable id="doctorVisit" type="net.therap.viewModel.DoctorVisit"--%>
        <form:form method="post" modelAttribute="doctorVisit">

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
            <form:label path="doctors"> Select visiting doctors: </form:label>

            <div class="form-check d-grid">
                <form:checkboxes class="form-check-input"
                                 items="${doctors}"
                                 itemValue="id"
                                 itemLabel="person.name"
                                 path="doctors"/>
            </div>

            <form:errors path="doctors" cssClass="invalid-feedback d-block"/>
        </div>

        <button type="submit"
                class="btn btn-primary">
            NEXT
        </button>

    </div>
    </form:form>
</div>
</div>

</body>
</html>
