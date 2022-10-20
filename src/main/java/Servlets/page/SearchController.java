package Servlets.page;

import Constants.Constants;
import Entity.Product;
import Form.SearchForm;
import Servlets.AbstractController;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import utils.RoutingUtils;

import java.io.IOException;
import java.util.List;

@WebServlet("/search")
public class SearchController extends AbstractController {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SearchForm searchForm = createSearchForm(req);
        List<Product> products = getProductService().listProductsBySearchForm(searchForm, 1, Constants.MAX_PRODUCTS_PER_HTML_PAGE);
        req.setAttribute("products", products);
        int totalCount = getProductService().countProductsBySearchForm(searchForm);
        req.setAttribute("pageCount", getPageCount(totalCount, Constants.MAX_PRODUCTS_PER_HTML_PAGE));
        req.setAttribute("productCount", totalCount);
        req.setAttribute("searchForm", searchForm);
         RoutingUtils.forwardToPage("search-result.jsp", req, resp);
    }
}
