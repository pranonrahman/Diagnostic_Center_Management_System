package net.therap.dms.controller;

import net.therap.dms.command.UserCmd;
import net.therap.dms.editor.RoleEditor;
import net.therap.dms.entity.Role;
import net.therap.dms.service.UserService;
import net.therap.dms.validator.UserCmdValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static net.therap.dms.constant.URL.LOGIN;
import static net.therap.dms.constant.URL.LOGOUT;
import static net.therap.dms.controller.AuthenticationController.USER;

/**
 * @author raian.rahman
 * @since 8/3/22
 */
@Controller
@SessionAttributes(USER)
public class AuthenticationController {

    private static final String LOGIN_REDIRECT_PATH = "redirect:/login";
    private static final String DASHBOARD_REDIRECT_PATH = "redirect:/";
    private static final String FORM_PAGE = "/authentication/form";

    private static final String USER_CMD = "userCmd";

    public static final String USER = "user";

    @Autowired
    private RoleEditor roleEditor;

    @Autowired
    private UserService userService;

    @Autowired
    private UserCmdValidator userCmdValidator;

    @InitBinder
    private void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(Role.class, roleEditor);
        webDataBinder.addValidators(userCmdValidator);
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping(LOGIN)
    public String show(ModelMap model) {

        setUpReferenceData(model);

        return FORM_PAGE;
    }

    @PostMapping(LOGIN)
    public String process(@Valid @ModelAttribute(USER_CMD) UserCmd userCmd,
                          BindingResult result,
                          HttpServletRequest request) {

        if (result.hasErrors()) {
            return FORM_PAGE;
        }

        request.getSession().setAttribute(USER, userService.findByUserName(userCmd.getUserName()));

        return DASHBOARD_REDIRECT_PATH;
    }

    @RequestMapping(LOGOUT)
    public String logout(SessionStatus sessionStatus, ModelMap model) {
        sessionStatus.setComplete();

        setUpReferenceData(model);

        return LOGIN_REDIRECT_PATH;
    }

    private void setUpReferenceData(ModelMap model) {
        model.put(USER_CMD, new UserCmd());
    }
}