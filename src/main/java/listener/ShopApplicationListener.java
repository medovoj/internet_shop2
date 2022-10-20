package listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import service.impl.ServiceManager;
import Constants.Constants;

@WebListener
public class ShopApplicationListener implements ServletContextListener {

    private ServiceManager serviceManager;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        serviceManager = ServiceManager.getInstance(sce.getServletContext());
        sce.getServletContext().setAttribute(Constants.CATEGORY_LIST, serviceManager.getProductService().listAllCategories());
        sce.getServletContext().setAttribute(Constants.PRODUCER_LIST, serviceManager.getProductService().listAllProducers());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        serviceManager.close();
    }
}