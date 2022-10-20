package Servlets.ajax;

import Entity.Product;
import Servlets.AbstractController;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import utils.RoutingUtils;
import Constants.Constants;

import java.io.IOException;
import java.util.List;


@WebServlet("/ajax/html/more/products/*")
public class ProductsByCategoryMoreController extends AbstractController {
    private static final int SUBSTRING_INDEX = "/ajax/html/more/products".length();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String categoryUrl = req.getRequestURI().substring(SUBSTRING_INDEX);
        List<Product> products = getProductService().listProductsByCategory(categoryUrl, getPage(req), Constants.MAX_PRODUCTS_PER_HTML_PAGE);
        req.setAttribute("products", products);
        RoutingUtils.forwardToFragment("product-list.jsp", req, resp);
    }
}
