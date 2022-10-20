package Servlets.ajax;

import Entity.Product;
import Form.SearchForm;
import Servlets.AbstractController;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import utils.RoutingUtils;
import Constants.Constants;

import java.io.IOException;
import java.util.List;

@WebServlet("/ajax/html/more/search")
public class SearchResultsMoreController extends AbstractController {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SearchForm searchForm = createSearchForm(req);
        List<Product> products = getProductService().listProductsBySearchForm(searchForm, getPage(req), Constants.MAX_PRODUCTS_PER_HTML_PAGE);
        req.setAttribute("products", products);
        RoutingUtils.forwardToFragment("product-list.jsp", req, resp);
    }
}
