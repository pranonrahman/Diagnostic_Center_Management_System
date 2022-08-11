package net.therap.controller;

import net.therap.command.RoleUpdateCmd;
import net.therap.editor.DateEditor;
import net.therap.entity.Gender;
import net.therap.entity.User;
import net.therap.entity.Role;
import net.therap.entity.RoleEnum;
import net.therap.service.UserService;
import net.therap.service.RoleService;
import net.therap.service.RoleUpdateCmdService;
import net.therap.validator.PersonValidator;
import net.therap.validator.RoleUpdateCmdValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.util.Objects.isNull;

/**
 * @author raian.rahman
 * @since 8/4/22
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private static final String FORM_PAGE = "user/form";
    private static final String LIST_PAGE = "user/list";
    private static final String ROLE_UPDATE_PAGE = "user/role";

    private static final String VIEW_REDIRECT_PATH = "redirect:/user/view?id=";
    private static final String LIST_REDIRECT_PATH = "redirect:/user/list";

    private static final String PERSON_NOT_FOUND_EXCEPTION_ERROR_MESSAGE = "User not found";

    @Autowired
    private DateEditor dateEditor;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleUpdateCmdService roleUpdateCmdService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleUpdateCmdValidator roleUpdateCmdValidator;

    @Autowired
    private PersonValidator userValidator;

    @InitBinder
    private void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(Date.class, dateEditor);
    }

    @InitBinder("userData")
    private void userInitBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(userValidator);
    }

    @InitBinder("roleUpdateCmd")
    private void roleUpdateCmdInitBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(roleUpdateCmdValidator);
    }

    @GetMapping("/save")
    public String showForm(@RequestParam(value = "id", required = false) Long id, ModelMap model) {
        model.put("readOnly", false);
        model.put("genderList", Gender.values());

        model.put("userData", isNull(id) ? new User() : userService.findById(id));

        return FORM_PAGE;
    }

    @PostMapping("/save")
    public String processPersonForm(@Valid @ModelAttribute("userData") User user,
                                    BindingResult bindingResult,
                                    ModelMap model) {

        model.put("readOnly", false);
        model.put("genderList", Gender.values());

        if (bindingResult.hasErrors()) {
            return FORM_PAGE;
        }

        user = userService.saveOrUpdate(user);

        return VIEW_REDIRECT_PATH + user.getId();
    }

    @GetMapping("/view")
    public String showView(@RequestParam(value = "id") Long id,
                           ModelMap model) {

        model.put("readOnly", true);
        model.put("genderList", Gender.values());

        if (isNull(id)) {
            return LIST_REDIRECT_PATH;
        }

        User user = userService.findById(id);

        if (isNull(user)) {
            throw new RuntimeException(PERSON_NOT_FOUND_EXCEPTION_ERROR_MESSAGE);
        }

        model.addAttribute("userData", user);

        return FORM_PAGE;
    }

    @RequestMapping("/list")
    public String showList(@RequestParam(value = "filterBy", required = false) String filterBy,
                           ModelMap model) {

        if (isNull(filterBy)) {
            model.put("users", userService.findAll());
        } else {
            Role role = roleService.findByName(RoleEnum.valueOf(filterBy));
            List<User> userList = new ArrayList<>();
            userService.findAll()
                    .stream()
                    .filter(user -> user.getRoles().contains(role))
                    .forEach(userList::add);

            model.put("users", userList);
        }
        return LIST_PAGE;
    }

    @PostMapping("/delete")
    public String deletePerson(@RequestParam(value = "id") Long id) {
        User user = userService.findById(id);

        if (isNull(user)) {
            throw new RuntimeException(PERSON_NOT_FOUND_EXCEPTION_ERROR_MESSAGE);
        }

        userService.delete(user);

        return LIST_REDIRECT_PATH;
    }

    @GetMapping("/updateRole")
    public String showUpdateRoleForm(@RequestParam(value = "id") Long id, ModelMap model) {
        User user = userService.findById(id);

        if (isNull(user)) {
            throw new RuntimeException(PERSON_NOT_FOUND_EXCEPTION_ERROR_MESSAGE);
        }

        model.put("userData", user);

        RoleUpdateCmd roleUpdateCmd = roleUpdateCmdService.getRoleUpdateCmd(user);

        model.put("roleUpdateCmd", roleUpdateCmd);

        return ROLE_UPDATE_PAGE;
    }

    @PostMapping("/updateRole")
    public String processUpdateRoleForm(@Valid @ModelAttribute RoleUpdateCmd roleUpdateCmd,
                                        BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ROLE_UPDATE_PAGE;
        }

        User user = userService.findById(roleUpdateCmd.getId());

        if (isNull(user)) {
            throw new RuntimeException(PERSON_NOT_FOUND_EXCEPTION_ERROR_MESSAGE);
        }

        user = userService.updateRole(user, roleUpdateCmd);

        return VIEW_REDIRECT_PATH + user.getId();
    }
}
