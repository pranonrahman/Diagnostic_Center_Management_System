package net.therap.controller;

import net.therap.command.UserCmd;
import net.therap.editor.RoleEditor;
import net.therap.entity.Role;
import net.therap.util.AuthenticationUtil;
import net.therap.service.RoleService;
import net.therap.service.UserService;
import net.therap.validator.UserCmdValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
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
    private static final String MESSAGE = "message";

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleEditor roleEditor;

    @Autowired
    private AuthenticationUtil authenticationUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private UserCmdValidator userCmdValidator;

    @Autowired
    private MessageSourceAccessor msa;

    @InitBinder
    private void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(Role.class, roleEditor);
        webDataBinder.addValidators(userCmdValidator);
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping({"/login"})
    public String show(ModelMap model) {

        model.put(USER_CMD, new UserCmd());

        return FORM_PAGE;
    }

    @PostMapping("/login")
    public String process(@Valid @ModelAttribute(USER_CMD) UserCmd userCmd,
                          BindingResult result,
                          ModelMap model) {

        if (result.hasErrors()) {
            return FORM_PAGE;
        }

        if (!authenticationUtil.isValidCredential(userCmd)) {
            model.put(MESSAGE, msa.getMessage("login.invalidCredentials"));
            return FORM_PAGE;
        }

        model.put(USER, userService.findByUserName(userCmd.getUserName()));

        return DASHBOARD_REDIRECT_PATH;
    }

    @RequestMapping("/logout")
    public String logout(SessionStatus sessionStatus, ModelMap model) {
        sessionStatus.setComplete();

        model.put("seedRoleList", roleService.findAll());
        model.put(USER_CMD, new UserCmd());

        return LOGIN_REDIRECT_PATH;
    }
}