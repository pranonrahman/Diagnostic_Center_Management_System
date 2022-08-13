package net.therap.controller;

import net.therap.command.UserCmd;
import net.therap.editor.RoleEditor;
import net.therap.entity.Role;
import net.therap.service.AuthenticationService;
import net.therap.service.RoleService;
import net.therap.service.UserService;
import net.therap.validator.UserCmdValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;

/**
 * @author raian.rahman
 * @since 8/3/22
 */
@Controller
@SessionAttributes({"user"})
public class AuthenticationController {

    private static final String LOGIN_REDIRECT_PATH = "redirect:/login";
    private static final String DASHBOARD_REDIRECT_PATH = "redirect:/home";
    private static final String FORM_PAGE = "/authentication/form";
    private static final String USER_CMD = "userCmd";
    private static final String USER = "user";
    private static final String SEED_ROLE_LIST = "seedRoleList";
    private static final String MESSAGE = "message";

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleEditor roleEditor;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserCmdValidator userCmdValidator;

    @Autowired
    private MessageSourceAccessor msa;

    @InitBinder
    private void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(Role.class, roleEditor);
    }

    @InitBinder(USER_CMD)
    private void userCmdInitBinder(WebDataBinder binder) {
        binder.addValidators(userCmdValidator);
    }

    @GetMapping({"/login"})
    public String showLoginForm(ModelMap model) {

        model.put(USER_CMD, new UserCmd());

        return FORM_PAGE;
    }

    @PostMapping("/login")
    public String processLoginForm(@Valid @ModelAttribute(USER_CMD) UserCmd userCmd,
                                   BindingResult result,
                                   ModelMap model) {

        if (result.hasErrors()) {
            return FORM_PAGE;
        }

        if (!authenticationService.isValidCredential(userCmd)) {
            model.put(MESSAGE, msa.getMessage("login.invalidCredentials"));
            return FORM_PAGE;
        }

        model.put(USER, userService.findByUserName(userCmd.getUserName()));

        return DASHBOARD_REDIRECT_PATH;
    }

    @RequestMapping("/logout")
    public String logout(SessionStatus status, ModelMap model) {
        status.setComplete();

        model.put(SEED_ROLE_LIST, roleService.findAll());
        model.put(USER_CMD, new UserCmd());

        return LOGIN_REDIRECT_PATH;
    }
}