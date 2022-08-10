<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="net.therap.model.RoleEnum" %>
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
    <jsp:include page="../header.jsp"/>
</head>

<body>
<div class="container-fluid bg-primary-custom h-100 w-75">

    <h2 class="text-center py-3"> <fmt:message key="header.table.invoices"/> </h2>

    <table class="table text-center">

        <thead>
        <tr>
<%--            <th scope="col">#</th>--%>
<%--            <th scope="col">Invoice Id</th>--%>
<%--            <th scope="col">--%>
<%--                ${role.getName().equals(RoleEnum.RECEPTIONIST) ?--%>
<%--                        'Customer Name' :--%>
<%--                        'Received By'}--%>
<%--            </th>--%>
<%--            <th scope="col">Date</th>--%>
<%--            <th scope="col">Total bill</th>--%>
            <th scope="col"><fmt:message key="column.table.invoiceId"/> </th>
            <th scope="col"> <fmt:message key="column.table.customerName"/> </th>
            <th scope="col"> <fmt:message key="column.table.date"/> </th>
            <th scope="col"> <fmt:message key="column.table.totalBill"/> </th>
        </tr>
        </thead>

        <tbody>
        <c:forEach var="invoice" items="${invoices}" varStatus="loop">
            <tr>
                <th scope="row">${loop.index + 1}</th>
                <td class="d-inline-block text-truncate" style="max-width: 150px">
                    <c:out value="${invoice.invoiceId}"/>
                </td>
                <td><c:out value="${invoice.patient.person.name}"/></td>
                <td> <fmt:formatDate value="${invoice.generationDate}"/> </td>
                <td> <fmt:formatNumber value="${invoice.totalCost}"/> </td>
<%--                <td><c:out value="${invoice.invoiceId}"/></td>--%>
<%--                <td>--%>
<%--                    <c:out value="${role.getName().equals(RoleEnum.RECEPTIONIST) ?--%>
<%--                        invoice.patient.person.name :--%>
<%--                        invoice.generatedBy.name}"--%>
<%--                    />--%>
<%--                </td>--%>
<%--                <td><fmt:formatDate value="${invoice.generationDate}"/></td>--%>
<%--                <td><fmt:formatNumber value="${invoice.totalCost}"/></td>--%>
                <c:url var="invoiceLink" value="/invoice/view">
                    <c:param name="id" value="${invoice.id}"/>
                </c:url>
                <td><a href="${invoiceLink}"> <fmt:message key="button.label.details"/> </a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</div>

<jsp:include page="../footer.jsp"/>
</body>
</html>

