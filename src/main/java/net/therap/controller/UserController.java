package net.therap.controller;

import net.therap.editor.*;
import net.therap.entity.*;
import net.therap.service.RoleService;
import net.therap.service.UserService;
import net.therap.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
@SessionAttributes({"user"})
public class UserController {

    private static final String FORM_PAGE = "user/form";
    private static final String LIST_PAGE = "user/list";

    private static final String VIEW_REDIRECT_PATH = "redirect:/user";
    private static final String LIST_REDIRECT_PATH = "redirect:/user/list";

    private static final String USER = "user";
    private static final String ID = "id";
    private static final String SUCCESS = "success";
    private static final String FILTER_BY = "filterBy";
    private static final String USERS = "users";

    @Autowired
    private DateEditor dateEditor;

    @Autowired
    private RoleEditor roleEditor;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private AdminEditor adminEditor;

    @Autowired
    private ReceptionistEditor receptionistEditor;

    @Autowired
    private PatientEditor patientEditor;

    @Autowired
    private DoctorEditor doctorEditor;

    @Autowired
    private MessageSourceAccessor msa;

    @InitBinder
    private void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(Date.class, dateEditor);
    }

    @InitBinder({"userData", "user"})
    private void userInitBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(Role.class, roleEditor);
        webDataBinder.registerCustomEditor(Admin.class, adminEditor);
        webDataBinder.registerCustomEditor(Receptionist.class, receptionistEditor);
        webDataBinder.registerCustomEditor(Doctor.class, doctorEditor);
        webDataBinder.registerCustomEditor(Patient.class, patientEditor);
        webDataBinder.addValidators(userValidator);
    }

    @GetMapping
    public String showForm(@RequestParam(value = "id", required = false) Long id, ModelMap model) {
        model.put("genderList", Gender.values());
        model.put("seedRoleList", roleService.findAll());
        model.put("userData", isNull(id) ? new User() : userService.findById(id));

        return FORM_PAGE;
    }

    @PostMapping
    public String processPersonForm(@Validated @ModelAttribute("userData") User user,
                                    BindingResult userResult,
                                    RedirectAttributes attributes,
                                    ModelMap model) {

        if (userResult.hasErrors()) {
            model.put("genderList", Gender.values());
            model.put("seedRoleList", roleService.findAll());
            return FORM_PAGE;
        }

        if (user.isNew()) {
            user = userService.saveOrUpdate(user);
        }

        user = userService.updateRole(user);
        User sessionUser = (User) model.get("user");

        if (sessionUser.getUserName().equals(user.getUserName())) {
            model.replace(USER, user);
        }

        attributes.addAttribute(ID, user.getId());
        attributes.addAttribute(SUCCESS, true);

        return VIEW_REDIRECT_PATH;
    }

    @RequestMapping("/list")
    public String showList(@RequestParam(value = FILTER_BY, required = false) String filterBy, ModelMap model) {

        if (isNull(filterBy)) {
            model.put("users", userService.findAll());
        } else {
            Role role = roleService.findByName(RoleEnum.valueOf(filterBy));
            List<User> userList = new ArrayList<>();
            userService.findAll().stream().filter(user -> user.getRoles().contains(role)).forEach(userList::add);

            model.put(USERS, userList);
        }
        return LIST_PAGE;
    }

    @PostMapping(value = "/delete")
    public String deleteUser(@RequestParam(value = "id") Long id,
                             @ModelAttribute("user") User sessionUser) throws RuntimeException {
        User user = userService.findById(id);

        if (isNull(user)) {
            throw new RuntimeException(msa.getMessage("user.notFound.message"));
        }

        if (sessionUser.getUserName().equals(user.getUserName())) {
            throw new RuntimeException(msa.getMessage("user.selfDelete.message"));
        }

        userService.delete(user);

        return LIST_REDIRECT_PATH;
    }
}
