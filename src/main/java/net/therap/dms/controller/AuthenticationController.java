package net.therap.dms.controller;

import net.therap.dms.editor.RoleEditor;
import net.therap.dms.entity.Role;
import net.therap.dms.entity.User;
import net.therap.dms.service.UserService;
import net.therap.dms.util.WebUtil;
import net.therap.dms.validationGroup.UserAuthenticationGroup;
import net.therap.dms.validator.AuthenticationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static net.therap.dms.constant.URL.LOGIN;
import static net.therap.dms.constant.URL.LOGOUT;
import static net.therap.dms.util.WebUtil.redirect;

/**
 * @author raian.rahman
 * @since 8/3/22
 */
@Controller
public class AuthenticationController {

    private static final String USER = "user";
    private static final String FORM_PAGE = "/authentication/login";
    private static final String USER_CMD = "userCmd";
    @Autowired
    private RoleEditor roleEditor;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationValidator authenticationValidator;

    @InitBinder
    private void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(Role.class, roleEditor);
        webDataBinder.addValidators(authenticationValidator);
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping(LOGIN)
    public String show(ModelMap model) {

        setUpReferenceData(model);

        return FORM_PAGE;
    }

    @PostMapping(LOGIN)
    public String process(@Validated(UserAuthenticationGroup.class) @ModelAttribute(USER_CMD) User user,
                          BindingResult result,
                          HttpServletRequest request) {

        if (result.hasErrors()) {
            return FORM_PAGE;
        }

        request.getSession().setAttribute(USER, userService.findByUserName(user.getUserName()));

        return redirect("/");
    }

    @RequestMapping(LOGOUT)
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();

        return redirect("/login");
    }

    private void setUpReferenceData(ModelMap model) {
        model.put(USER_CMD, new User());
    }
}