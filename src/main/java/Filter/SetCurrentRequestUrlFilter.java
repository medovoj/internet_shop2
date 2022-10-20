package Filter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.WebUtils;
import Constants.Constants;

import java.io.IOException;

@WebFilter(filterName="SetCurrentRequestUrlFilter")
public class SetCurrentRequestUrlFilter extends AbstractFilter {

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        req.setAttribute(Constants.CURRENT_REQUEST_URL, WebUtils.getCurrentRequestUrl(req));
        chain.doFilter(req, resp);
    }
}
