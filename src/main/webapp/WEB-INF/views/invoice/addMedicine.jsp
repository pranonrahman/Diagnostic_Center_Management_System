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
    <title>Invoice :: Medicine</title>

    <link type="text/css" href="<c:url value="../../../assets/css/bootstrap.min.css"/>" rel="stylesheet"/>
    <link type="text/css" href="<c:url value="../../../assets/css/style.css"/>" rel="stylesheet"/>
    <script type="text/javascript" src="<c:url value="../../../assets/js/jquery-3.6.0.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="../../../assets/js/bootstrap.bundle.min.js"/>"></script>
    <jsp:include page="../header.jsp"/>
</head>
<body>

<div class="container-fluid bg-primary-custom h-100">

    <h2 class="text-center py-3"> Invoice </h2>
    <%--@elvariable id="medicineItem" type="net.therap.viewModel.MedicineItem"--%>
    <div class="w-50 mx-auto">

        <form:form method="post" modelAttribute="medicineItem">

        <div class="mb-3">
            <form:label path="medicine" cssClass="form-label">
                Medicine
            </form:label>

            <form:select path="medicine"
                         cssClass="form-select form-select-lg mb-3">

                <form:option value="">
                    Select
                </form:option>
                <form:options items="${medicines}"
                              itemValue="id"
                              itemLabel="name"/>
            </form:select>

            <form:errors path="medicine" cssClass="invalid-feedback d-block"/>
        </div>

        <div class="mb-3">
            <form:label path="quantity" cssClass="form-label">
                Quantity
            </form:label>

            <form:input path="quantity"
                        type="number"
                        cssClass="form-select form-select-lg mb-3"/>

            <form:errors path="quantity" cssClass="invalid-feedback d-block"/>
        </div>

        <div class="d-flex justify-content-between">
            <a href="/invoice/doctor" class="btn btn-primary">
                PREVIOUS
            </a>
            <button type="submit"
                    class="btn btn-primary flex-grow-1 mx-3"
                    value="ADD"
                    name="action">
                Add
            </button>
            <button type="submit"
                    class="btn btn-primary"
                    value="NEXT"
                    name="action">
                NEXT
            </button>
        </div>

    </div>
    </form:form>

    \<%--@elvariable id="invoice" type="net.therap.viewModel.InvoiceViewModel"--%>
<c:if test="${invoice.medicines.size() > 0}">
    <div class="w-50 mx-auto mt-4 fw-light">
        <hr>
        <h4 class="text-center fw-light">Added medicines</h4>
        <table class="table">
            <thead>
            <tr>
                <th class="fw-light" scope="col">#</th>
                <th class="fw-light" scope="col">Medicine</th>
                <th class="fw-light" scope="col">Unit price</th>
                <th class="fw-light" scope="col">Units</th>
                <th class="fw-light" scope="col">Price</th>
                <th class="fw-light" scope="col">Action</th>
            </tr>
            </thead>

            <tbody>
            <c:forEach items="${invoice.medicines}" var="item" varStatus="loop">
                <p class="card-text">
                    <tr>
                        <th scope="row">${loop.index + 1}</th>
                        <td><c:out value="${item.medicine.name}"/></td>
                        <td><c:out value="${item.medicine.unitPrice}"/></td>
                        <td><c:out value="${item.quantity}"/></td>
                        <td><fmt:formatNumber value="${item.medicine.unitPrice * item.quantity}"/></td>
                        <td>
                                <%--@elvariable id="removeModel" type="net.therap.viewModel.RemoveModel"--%>
                            <form:form method="post" modelAttribute="removeModel" action="/invoice/medicine/remove">
                                <input type="hidden" name="id" value="${item.medicine.id}">
                                <button type="submit"
                                        class="btn btn-primary flex-grow-1 mx-3"
                                        value="REMOVE"
                                        name="action">
                                    REMOVE
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
</div>

</body>
</html>
