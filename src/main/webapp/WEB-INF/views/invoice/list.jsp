<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  * @author khandaker.maruf
  * @since 03/08/2022
--%>

<html>
<head>
    <title> <fmt:message key="title.invoice.list"/> </title>

    <link type="text/css" href="<c:url value="../../../assets/css/bootstrap.min.css"/>" rel="stylesheet"/>
    <link type="text/css" href="<c:url value="../../../assets/css/style.css"/>" rel="stylesheet"/>
    <script type="text/javascript" src="<c:url value="../../../assets/js/jquery-3.6.0.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="../../../assets/js/bootstrap.bundle.min.js"/>"></script>
</head>

<body>
<jsp:include page="../header.jsp"/>

<div class="container-fluid bg-primary-custom h-100 w-75">
    <h2 class="text-center py-3">
        <fmt:message key="header.table.invoices"/>
        <c:if test="${isPatient}">
            <fmt:message key="text.of"/> <c:out value="${user.name}"/>
        </c:if>
    </h2>

    <c:choose>
        <c:when test="${empty invoices}">
            <p class="card text-center">
                <fmt:message key="text.recordsNotFound"/>
            </p>
        </c:when>

        <c:otherwise>
            <table class="table text-center">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col"><fmt:message key="table.column.invoiceId"/> </th>
                    <th hidden="${isPatient}" scope="col"> <fmt:message key="table.column.customerName"/> </th>
                    <th scope="col"> <fmt:message key="table.column.date"/> </th>
                    <th scope="col"> <fmt:message key="table.column.totalBill"/> </th>
                </tr>
                </thead>

                <tbody>
                <c:forEach var="invoice" items="${invoices}" varStatus="loop">
                    <tr>
                        <th scope="row">${loop.index + 1}</th>
                        <td class="d-inline-block text-truncate" style="max-width: 150px">
                            <c:out value="${invoice.invoiceId}"/>
                        </td>
                        <td hidden="${isPatient}"><c:out value="${invoice.patient.user.name}"/></td>
                        <td> <fmt:formatDate value="${invoice.created}"/> </td>
                        <td> <fmt:formatNumber value="${invoice.totalCost}"/> </td>

                        <c:url var="invoiceLink" value="/invoice">
                            <c:param name="id" value="${invoice.id}"/>
                        </c:url>
                        <td>
                            <a href="${invoiceLink}"><fmt:message key="button.label.details"/> </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>

</div>

<jsp:include page="../footer.jsp"/>
</body>
</html>

