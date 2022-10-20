<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: medovoy
  Date: 11.10.2022
  Time: 19:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<h4 class="text-center">My orders</h4>
<hr />
<table id="myOrders" class="table table-bordered" data-page-number="1" data-page-count="${pageCount }">
    <thead>
    <tr>
        <th>Order id</th>
        <th>Date</th>
    </tr>
    </thead>
    <tbody>
    <c:if test="${empty orders }">
        <tr>
            <td colspan="2" class="text-center">No orders found</td>
        </tr>
    </c:if>
    <jsp:include page="../fragment/my-orders-tbody.jsp" />
    </tbody>
</table>
<div class="text-center hidden-print">
    <c:if test="${pageCount > 1 }">
        <a id="loadMoreMyOrders" class="btn btn-success">Load more my orders</a>
    </c:if>
</div>
