package Servlets.page;

import Entity.Product;
import Servlets.AbstractController;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import utils.RoutingUtils;

import java.io.IOException;
import java.util.List;

import Constants.Constants;

@WebServlet("/products/*")
public class ProductsByCategoryController extends AbstractController {
    private static final int SUBSTRING_INDEX = "/products".length();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String categoryUrl = req.getRequestURI().substring(SUBSTRING_INDEX);
        List<Product> products = getProductService().listProductsByCategory(categoryUrl, 1, Constants.MAX_PRODUCTS_PER_HTML_PAGE);
        req.setAttribute("products", products);
        int totalCount = getProductService().countProductsByCategory(categoryUrl);
        req.setAttribute("pageCount", getPageCount(totalCount, Constants.MAX_PRODUCTS_PER_HTML_PAGE));
        req.setAttribute("selectedCategoryUrl", categoryUrl);
        RoutingUtils.forwardToPage("products.jsp", req, resp);
    }
}

