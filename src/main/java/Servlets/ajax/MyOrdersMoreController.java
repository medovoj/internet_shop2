package Servlets.ajax;

import Entity.Order;
import Servlets.AbstractController;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.RoutingUtils;
import utils.SessionUtils;

import java.io.IOException;
import java.util.List;
import Constants.Constants;

@WebServlet("/ajax/html/more/my-orders")
public class MyOrdersMoreController extends AbstractController {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Order> orders = getOrderService().listMyOrders(SessionUtils.getCurrentAccount(req), getPage(req), Constants.ORDERS_PER_PAGE);
        req.setAttribute("orders", orders);
        RoutingUtils.forwardToFragment("my-orders-tbody.jsp", req, resp);
    }
}
