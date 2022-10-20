package Servlets.page;

import Servlets.AbstractController;
import utils.SessionUtils;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import utils.RoutingUtils;

import java.io.IOException;

@WebServlet("/shopping-cart")
public class ShowShoppingCartController extends AbstractController {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (SessionUtils.isCurrentShoppingCartCreated(req)) {
            RoutingUtils.forwardToPage("shopping-cart.jsp", req, resp);
        } else {
            RoutingUtils.redirect("/products", req, resp);
        }
    }
}
