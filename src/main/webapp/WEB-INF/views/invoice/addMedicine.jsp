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

    <h2 class="text-center py-3"> Invoice </h2>
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

        <div class="d-grid">
            <button type="submit"
                    class="btn btn-primary"
                    value="ADD"
                    name="action">
                Add more
            </button>
            <button type="submit"
                    class="btn btn-primary"
                    value="SUBMIT"
                    name="action">
                NEXT
            </button>
        </div>


    </div>
    </form:form>
</div>
</div>

</body>
</html>
