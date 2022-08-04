package net.therap.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author amimul.ehsan
 * @since 04/08/2022
 */
@Controller
public class CustomErrorController implements ErrorController {

    @GetMapping("/error")
    public String showErrorPage(HttpServletRequest httpServletRequest, ModelMap modelMap) {
        String errorMessage = "";
        int httpErrorCode = (Integer) httpServletRequest.getAttribute("javax.servlet.error.status_code");

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

        modelMap.put("httpErrorCode", httpErrorCode);
        modelMap.put("errorMessage", errorMessage);

        return "errorPage";
    }
}
