<%@ page contentType="text/html;charset=UTF-8" %>
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
        <fmt:message key="user.list.title"/>
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
         <fmt:message key="user.list.header"/>
    </h2>
    <div class="w-50 mx-auto">
        <table class="table text-center">
            <thead>
            <tr>
                <th scope="col">
                    <fmt:message key="user.list.userName"/>
                </th>
                <th scope="col">
                    <fmt:message key="user.list.name"/>
                </th>
                <th scope="col">
                    <fmt:message key="user.list.phone"/>
                </th>
                <th scope="col">
                    <fmt:message key="user.list.email"/>
                </th>
                <th scope="col">
                    <fmt:message key="user.list.gender"/>
                </th>
            </tr>
            </thead>
            <tbody>

            <c:forEach items="${users}" var="user">
                <tr>
                    <td>
                        <c:url var="userForm"
                               value="${pageContext.request.contextPath}/user/">

                            <c:param name="id"
                                     value="${user.id}"/>
                        </c:url>
                        <a href="${userForm}">
                            <c:out value="${user.userName}"/>
                        </a>
                    </td>

                    <td>
                        <c:out value="${user.name}"/>
                    </td>

                    <td>
                        <c:out value="${user.phone}"/>
                    </td>

                    <td>
                        <c:out value="${user.email}"/>
                    </td>

                    <td>
                        <c:out value="${user.gender.displayName}"/>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<jsp:include page="../footer.jsp"/>

</body>
</html>
