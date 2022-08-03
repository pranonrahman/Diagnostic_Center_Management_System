package net.therap.controller;

import net.therap.model.Prescription;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author amimul.ehsan
 * @since 02/08/2022
 */
@Controller
public class PrescriptionController {

    @GetMapping("/")
    public String view(ModelMap modelMap){
        modelMap.put("prescription", new Prescription());

        return "prescription/view";
    }
}
