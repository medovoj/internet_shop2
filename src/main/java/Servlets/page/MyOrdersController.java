package Servlets.page;

import Entity.Order;
import Model.CurrentAccount;
import Servlets.AbstractController;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.RoutingUtils;
import utils.SessionUtils;
import Constants.Constants;

import java.io.IOException;
import java.util.List;

@WebServlet("/my-orders")
public class MyOrdersController extends AbstractController {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CurrentAccount currentAccount = SessionUtils.getCurrentAccount(req);
        List<Order> orders = getOrderService().listMyOrders(currentAccount, 1, Constants.ORDERS_PER_PAGE);
        req.setAttribute("orders", orders);
        int orderCount = getOrderService().countMyOrders(currentAccount);
        req.setAttribute("pageCount", getPageCount(orderCount, Constants.ORDERS_PER_PAGE));
        RoutingUtils.forwardToPage("my-orders.jsp", req, resp);
    }

}
