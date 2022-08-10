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
    <jsp:include page="../header.jsp"/>
</head>
<body>

<div class="container-fluid bg-primary-custom h-100">

    <h2 class="text-center py-3">
        <fmt:message key="user.form.header"/>
    </h2>
    <div class="w-50 mx-auto">
        <%--@elvariable id="userData" type="net.therap.model.User"--%>
        <form:form action="/user/save" method="POST" modelAttribute="userData">
            <div class="mb-3">
                <form:input hidden="hidden" path="id" type="text" value="${userData.id}" cssClass="form-control"/>

                <c:choose>
                    <c:when test="${userData.isNew()}">
                        <form:label path="userName" cssClass="form-label">
                            <fmt:message key="user.form.userName"/>
                        </form:label>
                        <form:input readonly="${readOnly}" path="userName" type="text" value="${userData.userName}"
                                    cssClass="form-control"/>
                        <form:errors path="userName" cssClass="invalid-feedback d-block"/>
                    </c:when>
                    <c:otherwise>
                        <form:input hidden="hidden" readonly="${readOnly}" path="userName" type="text" value="${userData.userName}"
                                    cssClass="form-control"/>
                    </c:otherwise>
                </c:choose>
            </div>

            <c:if test="${not readOnly}">
                <div class="mb-3">
                    <form:label path="password" cssClass="form-label">
                        <fmt:message key="user.form.password"/>
                    </form:label>
                    <form:input path="password" type="password" cssClass="form-control"/>
                    <form:errors path="password" cssClass="invalid-feedback d-block"/>
                </div>
            </c:if>

            <div class="mb-3">
                <form:label path="name" cssClass="form-label">
                    <fmt:message key="user.form.name"/>
                </form:label>
                <form:input readonly="${readOnly}" path="name" type="text" value="${userData.name}"
                            cssClass="form-control"/>
                <form:errors path="name" cssClass="invalid-feedback d-block"/>
            </div>

            <div class="mb-3">
                <form:label path="phone" cssClass="form-label">
                    <fmt:message key="user.form.phone"/>
                </form:label>
                <form:input readonly="${readOnly}" path="phone" type="text" maxlength="11" value="${userData.phone}"
                            cssClass="form-control"/>
                <form:errors path="phone" cssClass="invalid-feedback d-block"/>
            </div>

            <div class="mb-3">
                <form:label path="email" cssClass="form-label">
                    <fmt:message key="user.form.email"/>
                </form:label>
                <form:input readonly="${readOnly}" path="email" type="email" value="${userData.email}"
                            cssClass="form-control"/>
                <form:errors path="email" cssClass="invalid-feedback d-block"/>
            </div>

            <div class="mb-3">
                <form:label path="gender" cssClass="form-label">
                    <fmt:message key="user.form.gender"/>
                </form:label>
                <c:forEach var="gender" items="${genderList}">
                    <div class="form-check">
                        <form:radiobutton disabled="${readOnly}" cssClass="form-check-input" value="${gender}"
                                          path="gender"/>
                        <form:label path="gender" cssClass="form-check-label text-capitalize">
                            <c:out value="${gender}"/>
                        </form:label>
                    </div>
                </c:forEach>
                <form:errors path="gender" cssClass="d-block invalid-feedback"/>
            </div>

            <div class="mb-3">
                <c:choose>
                    <c:when test="${not readOnly}">
                        <form:label path="dateOfBirth" cssClass="form-label">
                            <fmt:message key="user.form.dateOfBirth"/>
                        </form:label>
                        <fmt:formatDate value="${userData.dateOfBirth}" var="dateString" pattern="dd/MM/yyyy"/>
                        <form:input path="dateOfBirth" type="date" value="${dateString}" cssClass="form-control"/>
                        <form:errors path="dateOfBirth" cssClass="invalid-feedback d-block"/>
                    </c:when>
                    <c:when test="${not empty userData.dateOfBirth}">
                        <form:label path="dateOfBirth">
                            <fmt:message key="user.form.dateOfBirth"/>
                        </form:label>
                        <input type="text" disabled="disabled" class="form-control"
                               value="<fmt:formatDate value="${userData.dateOfBirth}"/>">
                    </c:when>
                </c:choose>
            </div>
            <c:if test="${not readOnly}">
                <button type="submit" value="submit" class="btn btn-primary w-100">
                    <fmt:message key="user.form.submit"/>
                </button>
            </c:if>
        </form:form>

        <c:if test="${readOnly}">
            <c:if test="${userData.roles.size() > 0}">
                <div class="mb-3">
                    <fmt:message key="user.form.roles"/> <br>
                    <ul>
                        <c:forEach var="role" items="${userData.roles}">
                            <li><c:out value="${role.name}"/></li>
                        </c:forEach>
                    </ul>
                </div>
            </c:if>

            <c:if test="${not empty userData.doctor}">
                <div class="mb-3">
                    <b>
                        <fmt:message key="user.form.fee"/>
                    </b>
                    <c:out value="${userData.doctor.fee}"/>
                </div>
            </c:if>

            <div class="mb-3">
                <form action="/user/updateRole" method="get">
                    <input name="id" hidden="hidden" value="${userData.id}">
                    <button type="submit" value="submit" class="btn btn-dark w-100">
                        <fmt:message key="user.form.updateRole"/>
                    </button>
                </form>
            </div>

            <div class="mb-3">
                <form action="/user/save" method="get">
                    <input name="id" hidden="hidden" value="${userData.id}">
                    <button type="submit" value="submit" class="btn btn-primary w-100">
                        <fmt:message key="user.form.editInformation"/>
                    </button>
                </form>
            </div>

            <div class="mb-3">
                <form action="/user/delete" method="post">
                    <input name="id" hidden="hidden" value="${userData.id}">
                    <button type="submit" value="submit" class="btn btn-danger w-100">
                        <fmt:message key="user.form.delete"/>
                    </button>
                </form>
            </div>
        </c:if>
    </div>
</div>
<jsp:include page="../footer.jsp"/>
</body>
</html>
