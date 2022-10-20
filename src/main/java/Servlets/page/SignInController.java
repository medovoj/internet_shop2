package Servlets.page;

import Servlets.AbstractController;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.RoutingUtils;
import utils.SessionUtils;
import Constants.Constants;
import java.io.IOException;

@WebServlet("/sign-in")
public class SignInController extends AbstractController {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (SessionUtils.isCurrentAccountCreated(req)) {
            RoutingUtils.redirect("/my-orders", req, resp);
        } else {
            RoutingUtils.forwardToPage("sign-in.jsp", req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (SessionUtils.isCurrentAccountCreated(req)) {
            RoutingUtils.redirect("/my-orders", req, resp);
        } else {
            String targetUrl = req.getParameter("target");
            if (targetUrl != null) {
                req.getSession().setAttribute(Constants.SUCCESS_REDIRECT_URL_AFTER_SIGNIN, targetUrl);
            }
            RoutingUtils.redirect(getSocialService().getAuthorizeUrl(), req, resp);
        }
    }
}