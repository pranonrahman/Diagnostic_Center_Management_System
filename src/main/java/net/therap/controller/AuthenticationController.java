package net.therap.controller;

import net.therap.command.UserCmd;
import net.therap.editor.RoleEditor;
import net.therap.entity.Role;
import net.therap.service.AuthenticationService;
import net.therap.service.UserService;
import net.therap.service.RoleService;
import net.therap.validator.UserCmdValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static java.util.Objects.nonNull;

/**
 * @author raian.rahman
 * @since 8/3/22
 */
@Controller
public class AuthenticationController {

    private static final String LOGIN_REDIRECT_PATH = "redirect:/login";
    private static final String DASHBOARD_REDIRECT_PATH = "redirect:/home";
    private static final String FORM_PAGE = "/authentication/form";
    private static final String USER_CMD = "userCmd";
    private static final String ROLE = "role";
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
    public String showLoginForm(ModelMap model, HttpSession session) {
        if (nonNull(session.getAttribute(USER)) || nonNull(session.getAttribute(ROLE))) {
            return DASHBOARD_REDIRECT_PATH;
        }

        model.put(USER_CMD, new UserCmd());

        return FORM_PAGE;
    }

    @PostMapping("/login")
    public String processLoginForm(@Valid @ModelAttribute(USER_CMD) UserCmd userCmd,
                                   BindingResult result,
                                   HttpSession session,
                                   ModelMap model) {

        if (result.hasErrors()) {
            return FORM_PAGE;
        }

        if (!authenticationService.isValidCredential(userCmd)) {
            model.put(MESSAGE, msa.getMessage("login.invalidCredentials"));
            return FORM_PAGE;
        }

        session.setAttribute(USER, userService.findByUserName(userCmd.getUserName()));

        return DASHBOARD_REDIRECT_PATH;
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session, ModelMap model) {
        session.invalidate();

        model.put(SEED_ROLE_LIST, roleService.findAll());
        model.put(USER_CMD, new UserCmd());

        return LOGIN_REDIRECT_PATH;
    }
}