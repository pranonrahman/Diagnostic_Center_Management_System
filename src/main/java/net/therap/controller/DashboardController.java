package net.therap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author raian.rahman
 * @since 8/11/22
 */
@Controller
@RequestMapping({"/","/home"})
public class DashboardController {

    private static final String DASHBOARD = "dashboard";

    @GetMapping
    public String showDashboard() {
        return DASHBOARD;
    }
}
