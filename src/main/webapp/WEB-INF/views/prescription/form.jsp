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
    <title><fmt:message key="title.prescription"/></title>

    <link type="text/css" href="<c:url value="../../../assets/css/bootstrap.min.css"/>" rel="stylesheet"/>
    <link type="text/css" href="<c:url value="../../../assets/css/style.css"/>" rel="stylesheet"/>
    <script type="text/javascript" src="<c:url value="../../../assets/js/jquery-3.6.0.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="../../../assets/js/bootstrap.bundle.min.js"/>"></script>
</head>
<body>
<jsp:include page="../header.jsp"/>

<div class="container-fluid bg-primary-custom h-100">

    <h2 class="text-center py-3">
        <fmt:message key="text.prescriptionOf"/>
        <c:out value="${prescription.patient.user.name}"/>
    </h2>

    <div class="w-50 mx-auto">
        <c:if test="${param.success != null}">
            <div class="alert alert-success" role="alert">
                <fmt:message key="prescription.updated.message"/>
            </div>
        </c:if>

        <form:form method="post"
                   modelAttribute="prescription">

            <c:set var="readonly"
                   value="${(doctorId != null && prescription.doctor.id == doctorId) ? false : true}"/>

            <form:input path="patient.id"
                        value="${prescription.patient.id}"
                        hidden="true"/>

            <form:input path="doctor.id"
                        value="${prescription.doctor.id}"
                        hidden="true"/>

            <p>
                <strong>
                    <fmt:message key="text.consultingDoctor"/> :
                </strong>
                <c:out value="${prescription.doctor.user.name}"/>
            </p>

            <div class="mb-3">
                <form:label path="symptoms"
                            cssClass="form-label">
                    <fmt:message key="label.symptoms"/>
                </form:label>

                <form:textarea rows="3"
                               cssClass="form-control"
                               path="symptoms"
                               readonly="${readonly}"
                />

                <form:errors path="symptoms"
                             cssClass="invalid-feedback d-block"/>
            </div>

            <fieldset class="form-group mb-3">
                <label class="col-form-label col-sm-2">
                    <fmt:message key="label.recommendations"/>
                </label>

                <div class="col-sm-10">
                    <form:checkboxes path="facilities"
                                     items="${facilities}"
                                     itemLabel="name"
                                     itemValue="id"
                                     cssClass="form-check-inline ms-3"
                                     disabled="${readonly}"
                    />

                    <form:errors path="facilities"
                                 cssClass="invalid-feedback d-block"/>
                </div>
            </fieldset>

            <div class="mb-3">
                <form:label path="diagnosis"
                            cssClass="form-label">

                    <fmt:message key="label.diagnosis"/>
                </form:label>

                <form:textarea rows="3"
                               cssClass="form-control"
                               path="diagnosis"
                               readonly="${readonly}"
                />

                <form:errors path="diagnosis"
                             cssClass="invalid-feedback d-block"/>
            </div>

            <div class="mb-3">
                <form:label path="medicines"
                            cssClass="form-label">

                    <fmt:message key="label.medicines"/>
                </form:label>

                <form:textarea rows="3"
                               cssClass="form-control"
                               path="medicines"
                               readonly="${readonly}"
                />

                <small id="medicineHelp"
                       class="form-text text-muted">

                    please separate each medicine by a semicolon (;)
                </small>

                <form:errors path="medicines"
                             cssClass="invalid-feedback d-block"/>
            </div>

            <div class="mb-3">
                <form:label path="comment"
                            cssClass="form-label">

                    <fmt:message key="label.comments"/>
                </form:label>

                <form:textarea rows="3"
                               cssClass="form-control"
                               path="comment"
                               readonly="${readonly}"
                />

                <form:errors path="comment" cssClass="invalid-feedback d-block"/>
            </div>


            <div class="d-grid">
                <c:if test="${!readonly}">
                    <button type="submit"
                            class="btn btn-primary mb-2">
                        <fmt:message key="button.label.update"/>
                    </button>
                </c:if>
            </div>
        </form:form>
    </div>
</div>

<jsp:include page="../footer.jsp"/>
</body>
</html>
