package Servlets.page;

import Model.CurrentAccount;
import Model.SocialAccount;
import Servlets.AbstractController;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.RoutingUtils;
import utils.SessionUtils;

import java.io.IOException;

@WebServlet("/from-social")
public class FromSocialController extends AbstractController {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");
        if (code != null) {
            SocialAccount socialAccount = getSocialService().getSocialAccount(code);
            CurrentAccount currentAccount = getOrderService().authenticate(socialAccount);
            SessionUtils.setCurrentAccount(req, currentAccount);
            RoutingUtils.redirect("/my-orders", req, resp);
        } else {
            RoutingUtils.redirect("/sign-in", req, resp);
        }
    }
}
