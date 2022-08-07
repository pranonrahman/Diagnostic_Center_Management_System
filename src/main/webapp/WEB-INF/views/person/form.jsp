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
    <title>User form</title>
    <link type="text/css" href="<c:url value="../../../assets/css/bootstrap.min.css"/>" rel="stylesheet"/>
    <link type="text/css" href="<c:url value="../../../assets/css/style.css"/>" rel="stylesheet"/>
    <script type="text/javascript" src="<c:url value="../../../assets/js/jquery-3.6.0.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="../../../assets/js/bootstrap.bundle.min.js"/>"></script>
</head>
<body>

<div class="container-fluid bg-primary-custom h-100">

    <h2 class="text-center py-3"> User form </h2>
    <div class="w-50 mx-auto">
        <%--@elvariable id="person" type="net.therap.model.Person"--%>
        <form:form action="/person/createOrUpdate" method="POST" modelAttribute="person">
            <div class="mb-3">
                <form:input hidden="hidden" path="id" type="text" value="${person.id}" cssClass="form-control"/>
                <form:label path="userName" cssClass="form-label">Username</form:label>

                <form:input readonly="${readOnly}" path="userName" type="text" value="${person.userName}"
                            cssClass="form-control"/>
                <form:errors path="userName" cssClass="invalid-feedback d-block"/>
            </div>

            <c:if test="${not readOnly}">
                <div class="mb-3">
                    <form:label path="password" cssClass="form-label">Password</form:label>
                    <form:input path="password" type="password" cssClass="form-control"/>
                    <form:errors path="password" cssClass="invalid-feedback d-block"/>
                </div>
            </c:if>

            <div class="mb-3">
                <form:label path="name" cssClass="form-label">Name</form:label>
                <form:input readonly="${readOnly}" path="name" type="text" value="${person.name}"
                            cssClass="form-control"/>
                <form:errors path="name" cssClass="invalid-feedback d-block"/>
            </div>

            <div class="mb-3">
                <form:label path="phone" cssClass="form-label">Phone</form:label>
                <form:input readonly="${readOnly}" path="phone" type="text" maxlength="11" value="${person.phone}"
                            cssClass="form-control"/>
                <form:errors path="phone" cssClass="invalid-feedback d-block"/>
            </div>

            <div class="mb-3">
                <form:label path="email" cssClass="form-label">Email</form:label>
                <form:input readonly="${readOnly}" path="email" type="email" value="${person.email}"
                            cssClass="form-control"/>
                <form:errors path="email" cssClass="invalid-feedback d-block"/>
            </div>

            <div class="mb-3">
                <form:label path="gender" cssClass="form-label">Gender</form:label>
                <c:forEach var="gender" items="${genderList}">
                    <div class="form-check">
                        <form:radiobutton disabled="${readOnly}" cssClass="form-check-input" value="${gender}"
                                          path="gender"/>
                        <form:label path="gender" cssClass="form-check-label text-capitalize">
                            <c:out value="${gender}"/>
                        </form:label>
                    </div>
                </c:forEach>
            </div>

            <div class="mb-3">
                <c:choose>
                    <c:when test="${not readOnly}">
                        <form:label path="dateOfBirth" cssClass="form-label">Date of birth</form:label>
                        <fmt:formatDate value="${person.dateOfBirth}" var="dateString" pattern="dd/MM/yyyy"/>
                        <form:input path="dateOfBirth" type="date" value="${dateString}" cssClass="form-control"/>
                        <form:errors path="dateOfBirth" cssClass="invalid-feedback d-block"/>
                    </c:when>
                    <c:when test="${not empty person.dateOfBirth}">
                        <form:label path="dateOfBirth">Date of birth</form:label>
                        <input type="text" disabled="disabled" class="form-control"
                               value="<fmt:formatDate value="${person.dateOfBirth}"/>">
                    </c:when>
                </c:choose>
            </div>
            <c:if test="${not readOnly}">
                <button type="submit" value="submit" class="btn btn-primary w-100">Submit</button>
            </c:if>
        </form:form>

        <c:if test="${readOnly}">
            <c:if test="${person.roles.size() > 0}">
                <div class="mb-3">
                    Roles: <br>
                    <ul>
                        <c:forEach var="role" items="${person.roles}">
                            <li><c:out value="${role.name}"/></li>
                        </c:forEach>
                    </ul>
                </div>
            </c:if>

            <c:if test="${not empty person.doctor}">
                <div class="mb-3">
                    <b>Fee: </b> <c:out value="${person.doctor.fee}"/>
                </div>
            </c:if>

            <div class="mb-3">
                <form action="/person/updateRole" method="get">
                    <input name="id" hidden="hidden" value="${person.id}">
                    <button type="submit" value="submit" class="btn btn-dark w-100">Update Role</button>
                </form>
            </div>

            <div class="mb-3">
                <form action="/person/createOrUpdate" method="get">
                    <input name="id" hidden="hidden" value="${person.id}">
                    <button type="submit" value="submit" class="btn btn-primary w-100">Edit information</button>
                </form>
            </div>

            <div class="mb-3">
                <form action="/person/delete" method="post">
                    <input name="id" hidden="hidden" value="${person.id}">
                    <button type="submit" value="submit" class="btn btn-danger w-100">Delete person</button>
                </form>
            </div>
        </c:if>
    </div>
</div>
<jsp:include page="../footer.jsp"/>
</body>
</html>
