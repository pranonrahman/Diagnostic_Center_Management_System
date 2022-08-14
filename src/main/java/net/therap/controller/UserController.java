package net.therap.controller;

import net.therap.editor.*;
import net.therap.entity.*;
import net.therap.service.RoleService;
import net.therap.service.UserService;
import net.therap.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
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
import static net.therap.controller.UserController.USER_CMD;

/**
 * @author raian.rahman
 * @since 8/4/22
 */
@Controller
@RequestMapping("/user")
@SessionAttributes(USER_CMD)
public class UserController {

    private static final String FORM_PAGE = "user/form";
    private static final String LIST_PAGE = "user/list";

    private static final String VIEW_REDIRECT_PATH = "redirect:/user";
    private static final String LIST_REDIRECT_PATH = "redirect:/user/list";

    public static final String USER_CMD = "user";
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
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping
    public String showForm(@RequestParam(value = "id", required = false, defaultValue = "0") long id,
                           ModelMap model) {
        model.put("genderList", Gender.values());
        model.put("seedRoleList", roleService.findAll());
        model.put("userData", id == 0 ? new User() : userService.findById(id));

        return FORM_PAGE;
    }

    @PostMapping
    public String process(@Validated @ModelAttribute("userData") User user,
                          BindingResult result,
                          RedirectAttributes redirectAttributes,
                          ModelMap model) {

        if (result.hasErrors()) {
            setupReferenceData(model);

            return FORM_PAGE;
        }

        if (user.isNew()) {
            user = userService.saveOrUpdate(user);
        }

        user = userService.updateRole(user);
        User sessionUser = (User) model.get("user");

        if (sessionUser.getUserName().equals(user.getUserName())) {
            model.replace(USER_CMD, user);
        }

        redirectAttributes.addAttribute(ID, user.getId());
        redirectAttributes.addAttribute(SUCCESS, true);

        return VIEW_REDIRECT_PATH;
    }

    @RequestMapping("/list")
    public String showList(@RequestParam(value = FILTER_BY, required = false) String filterBy,
                           ModelMap model) {

        if (isNull(filterBy)) {
            setupReferenceData(userService.findAll(), model);
        } else {
            Role role = roleService.findByName(RoleEnum.valueOf(filterBy));
            List<User> userList = new ArrayList<>();
            userService.findAll().stream().filter(user -> user.getRoles().contains(role)).forEach(userList::add);

            setupReferenceData(userList, model);
        }
        return LIST_PAGE;
    }

    @PostMapping(value = "/delete")
    public String deleteUser(@RequestParam(value = "id", defaultValue = "0") long id,
                             @ModelAttribute("user") User sessionUser) throws RuntimeException {
        User user = userService.findById(id);

        if (sessionUser.getUserName().equals(user.getUserName())) {
            throw new RuntimeException(msa.getMessage("user.selfDelete.message"));
        }

        userService.delete(user);

        return LIST_REDIRECT_PATH;
    }

    private void setupReferenceData(ModelMap model) {
        model.put("genderList", Gender.values());
        model.put("seedRoleList", roleService.findAll());
    }

    private void setupReferenceData(List<User> users, ModelMap model) {
        model.put("users", users);
    }
}
