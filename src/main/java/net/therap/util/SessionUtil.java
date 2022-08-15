package net.therap.util;

import net.therap.entity.User;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author khandaker.maruf
 * @since 8/15/22
 */
public class SessionUtil {

    private static final String INVOICE_CMD = "invoice";

    public static User getUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute("user");
    }

    public static void removeInvoice(ModelMap model, SessionStatus status, WebRequest webRequest) {
        if (model.containsAttribute(INVOICE_CMD)) {
            status.setComplete();
            webRequest.removeAttribute(INVOICE_CMD, WebRequest.SCOPE_SESSION);
        }
    }
}
