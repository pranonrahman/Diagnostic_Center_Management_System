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
    <title> <fmt:message key="title.invoice.addFacility"/> </title>

    <link type="text/css" href="<c:url value="../../../assets/css/bootstrap.min.css"/>" rel="stylesheet"/>
    <link type="text/css" href="<c:url value="../../../assets/css/style.css"/>" rel="stylesheet"/>
    <script type="text/javascript" src="<c:url value="../../../assets/js/jquery-3.6.0.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="../../../assets/js/bootstrap.bundle.min.js"/>"></script>
</head>

<body>
<jsp:include page="../header.jsp"/>

<div class="container-fluid bg-primary-custom h-100">

    <h2 class="text-center py-3">
        <fmt:message key="title.invoice"/>
    </h2>

    <div class="w-50 mx-auto">
        <form:form method="post"
                   modelAttribute="facilityItemCmd">

        <div class="mb-3">
            <form:label path="facility"
                        cssClass="form-label">

                <fmt:message key="label.facility"/>
            </form:label>

            <form:select path="facility"
                         cssClass="form-select form-select-lg mb-3">

                <form:option value="">
                    <fmt:message key="label.select"/>
                </form:option>

                <form:options items="${facilities}"
                              itemValue="id"
                              itemLabel="name"
                />
            </form:select>

            <form:errors path="facility"
                         cssClass="invalid-feedback d-block"
            />
        </div>

        <div class="mb-3">
            <form:label path="quantity"
                        cssClass="form-label">

                <fmt:message key="label.quantity"/>
            </form:label>

            <form:input path="quantity"
                        type="number"
                        cssClass="form-select form-select-lg mb-3"
            />

            <form:errors path="quantity"
                         cssClass="invalid-feedback d-block"
            />
        </div>

        <div class="d-flex justify-content-between">
            <a href="<c:url value="/invoice/medicine"/>"
               class="btn btn-primary">

                <fmt:message key="button.label.previous"/>
            </a>

            <button type="submit"
                    class="btn btn-primary flex-grow-1 mx-3"
                    value="ADD"
                    name="action">

                <fmt:message key="button.label.add"/>
            </button>

            <button type="submit"
                    class="btn btn-primary"
                    value="NEXT"
                    name="action">

                <fmt:message key="button.label.next"/>
            </button>
        </div>

        </form:form>
    </div>

    <c:if test="${invoice.facilities.size() > 0}">
        <div class="w-50 mx-auto mt-4 fw-light">
            <hr>

            <h4 class="text-center fw-light">
                <fmt:message key="header.table.AddedFacilities"/>
            </h4>

            <table class="table">

                <thead>
                <tr>
                    <th class="fw-light" scope="col">#</th>
                    <th class="fw-light" scope="col"> <fmt:message key="table.column.facility"/> </th>
                    <th class="fw-light" scope="col"> <fmt:message key="table.column.unitPrice"/> </th>
                    <th class="fw-light" scope="col"> <fmt:message key="table.column.units"/> </th>
                    <th class="fw-light" scope="col"> <fmt:message key="table.column.price"/> </th>
                    <th class="fw-light" scope="col"> <fmt:message key="table.column.action"/> </th>
                </tr>
                </thead>

                <tbody>
                <c:forEach items="${invoice.facilities}" var="item" varStatus="loop">
                    <p class="card-text">
                        <tr>
                            <th scope="row">${loop.index + 1}</th>
                            <td><c:out value="${item.facility.name}"/></td>
                            <td><c:out value="${item.facility.price}"/></td>
                            <td><c:out value="${item.quantity}"/></td>
                            <td><c:out value="${item.facility.price * item.quantity}"/></td>
                            <td>

                                <form:form method="post" modelAttribute="removeCmd" action="/invoice/facility/remove">
                                    <input type="hidden" name="id" value="${item.facility.id}">
                                    <button type="submit"
                                            class="btn btn-primary flex-grow-1 mx-3"
                                            value="REMOVE"
                                            name="action">

                                        <fmt:message key="button.label.remove"/>
                                    </button>
                                </form:form>
                            </td>
                        </tr>
                    </p>
                </c:forEach>

                </tbody>
            </table>
        </div>
    </c:if>

</div>

</body>
</html>
