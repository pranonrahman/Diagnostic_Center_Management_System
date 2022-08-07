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

    <h2 class="text-center py-3"> Prescription of ${prescription.patient.person.name} </h2>
    <div class="w-50 mx-auto">

        <%--@elvariable id="prescription" type="net.therap.model.Prescription"--%>
        <form:form method="post"
                   modelAttribute="prescription">

            <c:set var="readonly" value="${(action != null && action == 'edit') ? false : true}"/>

            <form:input path="patient.id" value="${prescription.patient.id}" hidden="true"/>
            <form:input path="doctor.id" value="${prescription.doctor.id}" hidden="true"/>

            <div class="mb-3">
                <form:label path="symptoms" cssClass="form-label">Symptoms</form:label>

                <form:textarea rows="3"
                               cssClass="form-control"
                               path="symptoms"
                               readonly="${readonly}"
                />

                <form:errors path="symptoms" cssClass="invalid-feedback d-block"/>
            </div>

            <fieldset class="form-group mb-3">
                <label class="col-form-label col-sm-2">Recommendations</label>
                <div class="col-sm-10">
                    <form:checkboxes path="facilities"
                                     items="${facilities}"
                                     itemLabel="name"
                                     itemValue="id"
                                     cssClass="form-check-inline ms-3"
                                     disabled="${readonly}"
                    />

                    <form:errors path="facilities" cssClass="invalid-feedback d-block"/>
                </div>
            </fieldset>

            <div class="mb-3">
                <form:label path="diagnosis" cssClass="form-label">Diagnosis</form:label>

                <form:textarea rows="3"
                               cssClass="form-control"
                               path="diagnosis"
                               readonly="${readonly}"
                />

                <form:errors path="diagnosis" cssClass="invalid-feedback d-block"/>
            </div>

            <div class="mb-3">
                <form:label path="medicines" cssClass="form-label">Medicines</form:label>

                <form:textarea rows="3"
                               cssClass="form-control"
                               path="medicines"
                               readonly="${readonly}"
                />

                <small id="medicineHelp" class="form-text text-muted">
                    please separate each medicine by a semicolon (;)</small>

                <form:errors path="medicines" cssClass="invalid-feedback d-block"/>
            </div>

            <div class="mb-3">
                <form:label path="comment" cssClass="form-label">Comments</form:label>

                <form:textarea rows="3"
                               cssClass="form-control"
                               path="comment"
                               readonly="${readonly}"
                />

                <form:errors path="comment" cssClass="invalid-feedback d-block"/>
            </div>


            <div class="d-grid">
                <c:choose>
                    <c:when test="${!readonly}">
                        <button type="submit"
                                class="btn btn-primary mb-2"
                                name="action"
                                value="UPDATE">
                            UPDATE
                        </button>
                    </c:when>

                    <c:when test="${prescription.doctor.id == doctorId}">
                        <c:url var="prescriptionEditPage" value="/prescription/edit">
                            <c:param name="id" value="${prescription.id}"/>
                        </c:url>
                        <a href="${prescriptionEditPage}" class="btn btn-primary">EDIT</a>
                    </c:when>
                </c:choose>
            </div>
        </form:form>
    </div>
</div>

<jsp:include page="../footer.jsp"/>
</body>
</html>
