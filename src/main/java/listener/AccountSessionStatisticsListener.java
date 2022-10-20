package listener;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import Constants.Constants;


import java.util.List;

@WebListener
@SuppressWarnings("unchecked")
public class AccountSessionStatisticsListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        List<String> actions = (List<String>) se.getSession().getAttribute(Constants.ACCOUNT_ACTIONS_HISTORY);
        if (actions != null) {
            logCurrentActionHistory(se.getSession().getId(), actions);
        }
    }

    private void logCurrentActionHistory(String sessionId, List<String> actions) {
        System.out.println(sessionId + " ->\n\t" + String.join("\n\t", actions));
    }
}
