<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        <fmt:message key="user.form.title"/>
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
        <fmt:message key="user.form.header"/>
    </h2>

    <div class="w-50 mx-auto">
        <form:form action="/user"
                   method="POST"
                   modelAttribute="userData">

            <div class="mb-3">
                <form:input hidden="hidden"
                            path="id"
                            type="text"
                            value="${userData.id}"
                            cssClass="form-control"
                />

                <form:input hidden="hidden"
                            path="doctor"
                            type="text"
                            value="${userData.doctor.id}"
                            cssClass="form-control"
                />

                <form:input hidden="hidden"
                            path="patient"
                            type="text"
                            value="${userData.patient.id}"
                            cssClass="form-control"
                />

                <form:input hidden="hidden"
                            path="receptionist"
                            type="text"
                            value="${userData.receptionist.id}"
                            cssClass="form-control"
                />

                <form:input hidden="hidden"
                            path="admin"
                            type="text"
                            value="${userData.admin.id}"
                            cssClass="form-control"
                />

                <c:set var="existingUser" scope="page" value="${not userData.isNew()}"/>

                <form:label path="userName"
                            cssClass="form-label">

                    <fmt:message key="user.form.userName"/>
                </form:label>

                <form:input readonly="${existingUser}"
                            path="userName"
                            type="text"
                            value="${userData.userName}"
                            cssClass="form-control"
                />

                <form:errors path="userName"
                             cssClass="invalid-feedback d-block"/>
            </div>

            <div class="mb-3">
                <form:label path="password"
                            cssClass="form-label">

                    <fmt:message key="user.form.password"/>
                </form:label>

                <form:input path="password"
                            type="password"
                            cssClass="form-control"
                />

                <form:errors path="password"
                             cssClass="invalid-feedback d-block"
                />
            </div>

            <div class="mb-3">
                <form:label path="name"
                            cssClass="form-label">

                    <fmt:message key="user.form.name"/>
                </form:label>

                <form:input path="name"
                            type="text"
                            value="${userData.name}"
                            cssClass="form-control"
                />

                <form:errors path="name"
                             cssClass="invalid-feedback d-block"
                />
            </div>

            <div class="mb-3">
                <form:label path="phone"
                            cssClass="form-label">

                    <fmt:message key="user.form.phone"/>
                </form:label>

                <form:input path="phone"
                            type="text"
                            value="${userData.phone}"
                            cssClass="form-control"
                />

                <form:errors path="phone"
                             cssClass="invalid-feedback d-block"
                />
            </div>

            <div class="mb-3">
                <form:label path="email"
                            cssClass="form-label">

                    <fmt:message key="user.form.email"/>
                </form:label>

                <form:input path="email"
                            type="text"
                            value="${userData.email}"
                            cssClass="form-control"
                />

                <form:errors path="email"
                             cssClass="invalid-feedback d-block"
                />
            </div>

            <div class="mb-3">
                <form:label path="gender"
                            cssClass="form-label">

                    <fmt:message key="user.form.gender"/>
                </form:label>

                <c:forEach var="gender"
                           items="${genderList}">

                    <div class="form-check">
                        <form:radiobutton disabled="${readOnly}"
                                          cssClass="form-check-input"
                                          value="${gender}"
                                          label="${gender.displayName}"
                                          path="gender"
                        />
                    </div>
                </c:forEach>

                <form:errors path="gender"
                             cssClass="d-block invalid-feedback"
                />
            </div>

            <div class="mb-3">
                <form:label path="dateOfBirth"
                            cssClass="form-label">

                    <fmt:message key="user.form.dateOfBirth"/>
                </form:label>

                <fmt:formatDate value="${userData.dateOfBirth}"
                                var="dateString"
                                pattern="dd-MM-yyyy"
                />

                <form:input path="dateOfBirth"
                            type="text"
                            value="${dateString}"
                            cssClass="form-control"
                />

                <form:errors path="dateOfBirth"
                             cssClass="invalid-feedback d-block"
                />
            </div>

            <div class="mb-3">
                <form:label path="roles"
                            cssClass="form-label w-100">

                    <fmt:message key="user.form.roles"/>
                </form:label>
                <form:checkboxes path="roles"
                                 items="${seedRoleList}"
                                 itemValue="id"
                                 itemLabel="name"
                                 cssClass="w-50"
                />
            </div>

            <c:if test="${not empty userData.doctor}">
                <div class="mb-3">
                    <form:label path="doctor.fee"
                                cssClass="form-label w-100">

                        <fmt:message key="user.form.fee"/>
                    </form:label>

                    <form:input path="doctor.fee"
                                type="number"
                                name="fee"
                                step="0.1"
                                value="${userData.doctor.fee}"
                    />
                    <form:errors path="doctor.fee"
                                 cssClass="invalid-feedback d-block"
                    />
                </div>
            </c:if>



            <button type="submit"
                    value="submit"
                    class="btn btn-primary w-100">

                <c:choose>
                    <c:when test="${existingUser}">
                        <fmt:message key="user.form.update"/>
                    </c:when>
                    <c:otherwise>
                        <fmt:message key="user.form.submit"/>
                    </c:otherwise>
                </c:choose>
            </button>
        </form:form>

        <c:if test="${user.userName!=userData.userName and not userData.isNew()}">
            <div class="mb-3">
                <form action="/user/delete" method="post">
                    <input name="id"
                           hidden="hidden"
                           value="${userData.id}">

                    <button type="submit"
                            value="submit"
                            class="btn btn-danger w-100">

                        <fmt:message key="user.form.delete"/>
                    </button>
                </form>
            </div>
        </c:if>
    </div>
</div>
</body>
</html>
