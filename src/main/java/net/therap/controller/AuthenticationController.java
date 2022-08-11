package net.therap.controller;

import net.therap.command.UserCmd;
import net.therap.editor.RoleEditor;
import net.therap.model.User;
import net.therap.model.Role;
import net.therap.service.AuthenticationService;
import net.therap.service.UserService;
import net.therap.service.RoleService;
import net.therap.validator.UserViewModelValidator;
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

    private static final String ADMIN_DASHBOARD_REDIRECT_PATH = "redirect:/user/list";
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
    private UserService userService;

    @Autowired
    private UserViewModelValidator userViewModelValidator;

    @InitBinder
    private void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(Role.class, roleEditor);
    }

    @InitBinder("userViewModel")
    private void userViewModelInitBinder(WebDataBinder binder) {
        binder.addValidators(userViewModelValidator);
    }

    @GetMapping({"/login","/"})
    public String showLoginForm(ModelMap model, HttpSession session) {
        if (nonNull(session.getAttribute("user"))) {
            if(isNull(session.getAttribute("role"))) {
                return LOGIN_ROLE_REDIRECT_PATH;
            }

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

        model.put("userViewModel", new UserCmd());

        return FORM_PAGE;
    }

    @PostMapping("/login")
    public String processLoginForm(@Valid @ModelAttribute("userViewModel") UserCmd userCmd,
                                   BindingResult result,
                                   ModelMap model,
                                   HttpSession session) {

        if (result.hasErrors()) {
            return FORM_PAGE;
        }

        if (!authenticationService.authenticateByPassword(userCmd)) {
            model.put("message", INVALID_CREDENTIALS_PROVIDED);
            return FORM_PAGE;
        }

        session.setAttribute("user", userService.findByUserName(userCmd.getUserName()));

        model.put("seedRoleList", userService.findByUserName(userCmd.getUserName()).getRoles());

        return LOGIN_ROLE_REDIRECT_PATH;
    }

    @GetMapping("/login/role")
    public String showRoleForm(ModelMap model, HttpSession session) {

        if (nonNull(session.getAttribute("user")) && nonNull(session.getAttribute("role"))) {
            return LOGIN_REDIRECT_PATH;
        }

        if (isNull(session.getAttribute("user"))) {
            return LOGIN_REDIRECT_PATH;
        }

        User user = (User) session.getAttribute("user");

        model.put("userViewModel", new UserCmd());
        model.put("seedRoleList", user.getRoles());

        return FORM_PAGE;
    }

    @PostMapping("/login/role")
    public String loginByRole(@ModelAttribute UserCmd userCmd,
                              BindingResult bindingResult,
                              HttpSession session) {

        if (bindingResult.hasErrors()) {
            return FORM_PAGE;
        }

        if (!authenticationService.authenticateByRole(userCmd, (User) session.getAttribute("user"))) {
            return FORM_PAGE;
        }

        session.setAttribute("role", userCmd.getRole());

        switch (userCmd.getRole().getName()) {
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
    public String logout(HttpSession session, ModelMap model) {
        session.removeAttribute("user");
        session.removeAttribute("role");

        setUpReferenceData(model);
        model.put("userViewModel", new UserCmd());

        return LOGIN_REDIRECT_PATH;
    }

    private void setUpReferenceData(ModelMap model) {
        model.put("seedRoleList", roleService.findAll());
    }
}