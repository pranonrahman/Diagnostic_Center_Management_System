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
</head>
<body>

<div class="container-fluid bg-primary-custom h-100">
    <%--@elvariable id="invoiceView" type="net.therap.model.Invoice"--%>
    <div class="card">
        <h5 class="card-header">
            Invoice of <c:out value="${invoiceView.patient.person.name}"/>
        </h5>
        <div class="card-body">
            <h5 class="card-title">
                Services:
            </h5>


            <table class="table">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Service Name</th>
                    <th scope="col">Unit price</th>
                    <th scope="col">Units</th>
                    <th scope="col">Price</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${invoiceView.particulars}" var="particular" varStatus="loop">
                    <p class="card-text">
                        <tr>
                            <th scope="row">${loop.index + 1}</th>
                            <td><c:out value="${particular.name}"/></td>
                            <td><c:out value="${particular.unitPrice}"/></td>
                            <td><c:out value="${particular.units}"/></td>
                            <td><c:out value="${particular.units * particular.unitPrice}"/></td>
                        </tr>
                    </p>
                </c:forEach>

                <tr>
                    <td colspan="5"> <c:out value="${invoiceView.totalCost}"/> </td>
                </tr>

                </tbody>
            </table>
            <form:form method="post">
                <c:choose>
                    <c:when test="${action == 'VIEW'}">
                        <a href="/invoice/list" class="btn btn-primary">
                            GO TO INVOICE LIST
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a href="/invoice/facility" class="btn btn-primary" type="submit">
                            PREVIOUS
                        </a>
                        <button class="btn btn-primary" type="submit">
                            CREATE INVOICE
                        </button>
                    </c:otherwise>
                </c:choose>
            </form:form>
        </div>
    </div>
    
</div>

</body>
</html>
