package Servlets.page;


import Constants.Constants;
import Entity.Order;
import Model.ShoppingCart;
import Servlets.AbstractController;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.RoutingUtils;
import utils.SessionUtils;

import java.io.IOException;

@WebServlet("/order")
public class OrderController extends AbstractController {
    public final static String CURRENT_MESSAGE = "CURRENT_MESSAGE";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ShoppingCart shoppingCart = SessionUtils.getCurrentShoppingCart(req);
        long idOrder = getOrderService().makeOrder(shoppingCart, SessionUtils.getCurrentAccount(req));
        SessionUtils.clearCurrentShoppingCart(req, resp);
        req.getSession().setAttribute(CURRENT_MESSAGE, "Order created successfully. Please wait for our reply.");
        RoutingUtils.redirect("/order?id=" + idOrder, req, resp );
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String message = (String) req.getSession().getAttribute("CURRENT_MESSAGE");
        req.getSession().removeAttribute("CURRENT_MESSAGE");
        req.setAttribute("CURRENT_MESSAGE", message);
        Order order = getOrderService().findOrderById(Long.parseLong(req.getParameter("id")), SessionUtils.getCurrentAccount(req));
        req.setAttribute("order", order);
        RoutingUtils.forwardToPage("order.jsp", req, resp);
    }
}
