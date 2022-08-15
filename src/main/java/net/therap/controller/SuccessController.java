package net.therap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author amimul.ehsan
 * @since 15/08/2022
 */
@Controller
@RequestMapping("/success")
public class SuccessController {

    @GetMapping
    public String showSuccess(){
        return "successPage";
    }
}
