package net.therap.controller;

import net.therap.model.Role;
import net.therap.validator.PersonViewModelValidator;
import net.therap.viewModel.PersonViewModel;
import net.therap.editor.RoleEditor;
import net.therap.service.AuthenticationService;
import net.therap.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

/**
 * @author raian.rahman
 * @since 8/3/22
 */
@Controller
@RequestMapping("login")
public class AuthenticationController {

    private static final String ADMIN_DASHBOARD_REDIRECT_PATH = "redirect:/admin";
    private static final String DOCTOR_DASHBOARD_REDIRECT_PATH = "redirect:/doctor";
    private static final String PATIENT_DASHBOARD_REDIRECT_PATH = "redirect:/patient";
    private static final String RECEPTIONIST_DASHBOARD_REDIRECT_PATH = "redirect:/invoice";

    private static final String VIEW_PAGE = "authentication/view";

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleEditor roleEditor;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private PersonViewModelValidator personViewModelValidator;

    @InitBinder
    private void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(Role.class, roleEditor);
        webDataBinder.addValidators(personViewModelValidator);
    }

    @GetMapping
    public String showLoginForm(ModelMap modelMap) {
        modelMap.put("personViewModel", new PersonViewModel());
        modelMap.put("seedRoleList", roleService.findAll());
        return VIEW_PAGE;
    }

    @PostMapping
    public String processLoginForm(@ModelAttribute PersonViewModel personViewModel,
                                   BindingResult result,
                                   ModelMap modelMap) {
        setUpReferenceData(modelMap);

        if(result.hasErrors()) {
            return VIEW_PAGE;
        }

        if(!authenticationService.authenticate(personViewModel)) {
            return VIEW_PAGE;
        }

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
                return VIEW_PAGE;
        }
    }

    private void setUpReferenceData(ModelMap modelMap) {
        modelMap.put("seedRoleList", roleService.findAll());
    }
}