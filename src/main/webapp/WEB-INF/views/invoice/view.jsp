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
    <title> <fmt:message key="title.invoice"/> </title>

    <link type="text/css" href="<c:url value="../../../assets/css/bootstrap.min.css"/>" rel="stylesheet"/>
    <link type="text/css" href="<c:url value="../../../assets/css/style.css"/>" rel="stylesheet"/>
    <script type="text/javascript" src="<c:url value="../../../assets/js/jquery-3.6.0.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="../../../assets/js/bootstrap.bundle.min.js"/>"></script>
    <jsp:include page="../header.jsp"/>
</head>
<body>

<div class="container-fluid bg-primary-custom h-100">

    <%--@elvariable id="invoiceView" type="net.therap.model.Invoice"--%>
    <div class="card">
        <h5 class="card-header">
            <fmt:message key="text.invoiceOf"/> <c:out value="${invoiceView.patient.user.name}"/>
        </h5>
        <div class="card-body">
            <p class="card-title">
                <strong><fmt:message key="text.invoiceId"/>:</strong> <c:out value="${invoiceView.invoiceId}"/>
            </p>
            <p class="card-title">
                <strong><fmt:message key="text.receivedBy"/>:</strong> <c:out value="${invoiceView.generatedBy.name}"/>
            </p>

            <table class="table">

                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col"> <fmt:message key="table.column.serviceName"/> </th>
                    <th scope="col"> <fmt:message key="table.column.unitPrice"/> </th>
                    <th scope="col"> <fmt:message key="table.column.units"/> </th>
                    <th scope="col" class="text-end"> <fmt:message key="table.column.priceInBDT"/> </th>
                </tr>
                </thead>

                <tbody>
                <c:forEach items="${invoiceView.particulars}" var="particular" varStatus="loop">
                    <p class="card-text">
                        <tr>
                            <th scope="row">${loop.index + 1}</th>
                            <td><c:out value="${particular.name}"/></td>
                            <td><fmt:formatNumber value="${particular.unitPrice}"/></td>
                            <td><fmt:formatNumber value="${particular.units}"/></td>
                            <td class="text-end"><fmt:formatNumber value="${particular.units * particular.unitPrice}"/></td>
                        </tr>
                    </p>
                </c:forEach>

                <tr>
                    <td colspan="5" class="text-end"><fmt:formatNumber value="${invoiceView.totalCost}"/> </td>
                </tr>

                </tbody>
            </table>

            <p class="text-danger text-center py-2">${errorMessage}</p>

            <form:form method="post">
                <c:choose>
                    <c:when test="${action == 'VIEW'}">
                        <a href="/invoice/list" class="btn btn-primary">
                            <fmt:message key="button.label.invoiceList"/>
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a href="/invoice/facility" class="btn btn-primary" type="submit">
                            <fmt:message key="button.label.previous"/>
                        </a>
                        <button class="btn btn-primary" type="submit">
                            <fmt:message key="button.label.createInvoice"/>
                        </button>
                    </c:otherwise>
                </c:choose>
            </form:form>
        </div>
    </div>
    
</div>

</body>
</html>
