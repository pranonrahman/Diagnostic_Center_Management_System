package net.therap.controller;

import net.therap.editor.RoleEditor;
import net.therap.model.Person;
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
import javax.validation.Valid;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * @author raian.rahman
 * @since 8/3/22
 */
@Controller
public class AuthenticationController {

    private static final String ADMIN_DASHBOARD_REDIRECT_PATH = "redirect:/person/list";
    private static final String DOCTOR_DASHBOARD_REDIRECT_PATH = "redirect:/patient/list";
    private static final String PATIENT_DASHBOARD_REDIRECT_PATH = "redirect:/prescription/list";
    private static final String RECEPTIONIST_DASHBOARD_REDIRECT_PATH = "redirect:/invoice/doctor";
    private static final String LOGIN_REDIRECT_PATH = "redirect:/login";
    private static final String LOGIN_ROLE_REDIRECT_PATH = "redirect:/login/role";

    private static final String FORM_PAGE = "/authentication/form";

    private static final String INVALID_CREDENTIALS_PROVIDED = "Invalid credentials provided";

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

    @GetMapping({"/login","/"})
    public String showLoginForm(ModelMap modelMap, HttpSession session) {

        if (nonNull(session.getAttribute("user")) || nonNull(session.getAttribute("role"))) {
            switch (((Role) session.getAttribute("role")).getName()) {
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

        modelMap.put("personViewModel", new PersonViewModel());

        return FORM_PAGE;
    }

    @PostMapping("/login")
    public String processLoginForm(@ModelAttribute PersonViewModel personViewModel, BindingResult result, ModelMap modelMap, HttpSession session) {

        if (result.hasErrors()) {
            return FORM_PAGE;
        }

        if (!authenticationService.authenticateByPassword(personViewModel)) {
            modelMap.put("message", INVALID_CREDENTIALS_PROVIDED);
            return FORM_PAGE;
        }

        session.setAttribute("user", personService.findByUserName(personViewModel.getUserName()));

        modelMap.put("seedRoleList", personService.findByUserName(personViewModel.getUserName()).getRoles());

        return LOGIN_ROLE_REDIRECT_PATH;
    }

    @GetMapping("/login/role")
    public String showRoleForm(ModelMap modelMap, HttpSession session) {

        if (nonNull(session.getAttribute("user")) && nonNull(session.getAttribute("role"))) {
            return LOGIN_REDIRECT_PATH;
        }

        if (isNull(session.getAttribute("user"))) {
            return LOGIN_REDIRECT_PATH;
        }

        Person person = (Person) session.getAttribute("user");

        modelMap.put("personViewModel", new PersonViewModel());
        modelMap.put("seedRoleList", person.getRoles());

        return FORM_PAGE;
    }

    @PostMapping("/login/role")
    public String loginByRole(@Valid @ModelAttribute PersonViewModel personViewModel, BindingResult bindingResult, HttpSession session) {

        if (bindingResult.hasErrors()) {
            return FORM_PAGE;
        }

        if (!authenticationService.authenticateByRole(personViewModel, (Person) session.getAttribute("user"))) {
            return FORM_PAGE;
        }

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

    @RequestMapping("/logout")
    public String logout(HttpSession session, ModelMap modelMap) {
        session.removeAttribute("user");
        session.removeAttribute("role");

        setUpReferenceData(modelMap);
        modelMap.put("personViewModel", new PersonViewModel());

        return LOGIN_REDIRECT_PATH;
    }

    private void setUpReferenceData(ModelMap modelMap) {
        modelMap.put("seedRoleList", roleService.findAll());
    }
}