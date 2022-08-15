package net.therap.dms.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author amimul.ehsan
 * @since 04/08/2022
 */
@Controller
public class CustomErrorController implements ErrorController {

    private static final String INVALID_PAGE = "invalidPage";
    private static final String ERROR_PAGE = "errorPage";
    private static final String ERROR_STATUS_CODE_ATTRIBUTE = "javax.servlet.error.status_code";

    @GetMapping("/error")
    public String showErrorPage(HttpServletRequest httpServletRequest, ModelMap model) {
        String errorMessage = "";
        int httpErrorCode = (Integer) httpServletRequest.getAttribute(ERROR_STATUS_CODE_ATTRIBUTE);

        switch (httpErrorCode) {
            case 400: {
                errorMessage = "Bad Request";
                break;
            }
            case 401: {
                errorMessage = "Unauthorized";
                break;
            }
            case 404: {
                errorMessage = "Resource not found";
                break;
            }
            case 500: {
                errorMessage = "Internal Server Error";
                break;
            }
        }

        model.put("httpErrorCode", httpErrorCode);
        model.put("errorMessage", errorMessage);

        return ERROR_PAGE;
    }

    @RequestMapping("invalidPage")
    public String showInvalidErrorPage() {
        return INVALID_PAGE;
    }
}
