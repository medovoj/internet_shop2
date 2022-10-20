<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: medovoy
  Date: 12.10.2022
  Time: 17:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:forEach var="order" items="${orders }">
  <tr class="item">
    <td><a href="/order?id=${order.id }">Order # ${order.id }</a></td>
    <td><fmt:formatDate value="${order.created }" pattern="yyyy-MM-dd HH:mm" /></td>
  </tr>
</c:forEach>
