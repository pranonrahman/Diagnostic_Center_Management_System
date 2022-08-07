package net.therap.controller;

import net.therap.editor.RoleEditor;
import net.therap.model.Role;
import net.therap.service.AuthenticationService;
import net.therap.service.PersonService;
import net.therap.service.RoleService;
import net.therap.viewModel.PersonViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author raian.rahman
 * @since 8/3/22
 */
@Controller
public class AuthenticationController {

    private static final String ADMIN_DASHBOARD_REDIRECT_PATH = "redirect:/admin";
    private static final String DOCTOR_DASHBOARD_REDIRECT_PATH = "redirect:/doctor";
    private static final String PATIENT_DASHBOARD_REDIRECT_PATH = "redirect:/patient";
    private static final String RECEPTIONIST_DASHBOARD_REDIRECT_PATH = "redirect:/invoice";

    private static final String FORM_PAGE = "/authentication/form";

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleEditor roleEditor;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private PersonService personService;

    @InitBinder
    private void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(Role.class, roleEditor);
    }

    @GetMapping("login")
    public String showLoginForm(ModelMap modelMap) {
        setUpReferenceData(modelMap);

        modelMap.put("personViewModel", new PersonViewModel());

        return FORM_PAGE;
    }

    @PostMapping("login")
    public String processLoginForm(@ModelAttribute PersonViewModel personViewModel,
                                   BindingResult result,
                                   ModelMap modelMap,
                                   HttpSession session) {

        setUpReferenceData(modelMap);

        if (result.hasErrors()) {
            return FORM_PAGE;
        }

        if (!authenticationService.authenticate(personViewModel)) {
            return FORM_PAGE;
        }

        session.setAttribute("user", personService.findByUserName(personViewModel.getUserName()));
        session.setAttribute("role", personViewModel.getRole());

        switch (personViewModel.getRole().getName()) {
            case ADMIN:
                return ADMIN_DASHBOARD_REDIRECT_PATH;
            case PATIENT:
                return PATIENT_DASHBOARD_REDIRECT_PATH;
            case DOCTOR:
                return DOCTOR_DASHBOARD_REDIRECT_PATH;
            case RECEPTIONIST:
                return RECEPTIONIST_DASHBOARD_REDIRECT_PATH;
            default:
                return FORM_PAGE;
        }
    }

    @RequestMapping("logout")
    public String logout(HttpSession session, ModelMap modelMap) {
        session.removeAttribute("user");
        session.removeAttribute("role");

        setUpReferenceData(modelMap);
        modelMap.put("personViewModel", new PersonViewModel());

        return FORM_PAGE;
    }

    private void setUpReferenceData(ModelMap modelMap) {
        modelMap.put("seedRoleList", roleService.findAll());
    }
}