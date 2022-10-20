package Filter;

import Constants.Constants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ProductService;
import service.impl.ServiceManager;

import java.io.IOException;

public class CategoryProducerFilter extends AbstractFilter{

    private ProductService productService;

    @Override
    public void init(FilterConfig filterConfig){
        productService = ServiceManager.getInstance(filterConfig.getServletContext()).getProductService();
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setAttribute(Constants.CATEGORY_LIST, productService.listAllCategories());
        request.setAttribute(Constants.PRODUCER_LIST, productService.listAllProducers());
        chain.doFilter(request, response);
    }
}
