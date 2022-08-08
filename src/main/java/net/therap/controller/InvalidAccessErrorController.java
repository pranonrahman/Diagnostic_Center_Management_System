package net.therap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author raian.rahman
 * @since 8/3/22
 */
@Controller
public class InvalidAccessErrorController {

    private static final String INVALID_PAGE = "invalidPage";

    @RequestMapping("invalidPage")
    public String showInvalidErrorPage() {
        return INVALID_PAGE;
    }
}
