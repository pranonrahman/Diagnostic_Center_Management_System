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
    <title>User form</title>
    <link type="text/css" href="<c:url value="../../../assets/css/bootstrap.min.css"/>" rel="stylesheet"/>
    <link type="text/css" href="<c:url value="../../../assets/css/style.css"/>" rel="stylesheet"/>
    <script type="text/javascript" src="<c:url value="../../../assets/js/jquery-3.6.0.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="../../../assets/js/bootstrap.bundle.min.js"/>"></script>
</head>
<body>

<div class="container-fluid bg-primary-custom h-100">

    <h2 class="text-center py-3"> Person list </h2>
    <div class="w-50 mx-auto">
       <table class="table text-center">
           <thead>
           <tr>
               <th scope="col">Username</th>
               <th scope="col">Name</th>
               <th scope="col">Phone</th>
               <th scope="col">Email</th>
               <th scope="col">Gender</th>
           </tr>
           </thead>
           <tbody>
               <%--@elvariable id="persons" type="java.util.List"--%>
               <c:forEach items="${persons}" var="person">
                   <tr>
                       <td>
                           <a href="${pageContext.request.contextPath}/person/view?id=${person.id}"><c:out value="${person.userName}"/></a>
                       </td>

                       <td>
                           <c:out value="${person.name}"/>
                       </td>

                       <td>
                           <c:out value="${person.phone}"/>
                       </td>

                       <td>
                           <c:out value="${person.email}"/>
                       </td>

                       <td>
                           <c:out value="${person.gender}"/>
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
