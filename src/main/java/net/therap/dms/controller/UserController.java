package net.therap.dms.controller;

import net.therap.dms.editor.*;
import net.therap.dms.entity.*;
import net.therap.dms.service.AccessManager;
import net.therap.dms.service.RoleService;
import net.therap.dms.service.UserService;
import net.therap.dms.validation.UserValidationGroup;
import net.therap.dms.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static net.therap.dms.controller.UserController.USER_DATA;
import static net.therap.dms.entity.Action.*;
import static net.therap.dms.util.SessionUtil.isLoggedInUser;
import static net.therap.dms.util.WebUtil.redirect;

/**
 * @author raian.rahman
 * @since 8/4/22
 */
@Controller
@RequestMapping("/user")
@SessionAttributes({USER_DATA})
public class UserController {

    public static final String USER_DATA = "userData";

    private static final String USER_CMD = "user";
    private static final String FORM_PAGE = "user/form";
    private static final String LIST_PAGE = "user/list";
    private static final String SUCCESS = "/success";

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
    private AccessManager accessManager;

    @InitBinder
    private void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(Date.class, new DateEditor());
        webDataBinder.registerCustomEditor(Role.class, roleEditor);
        webDataBinder.registerCustomEditor(Admin.class, adminEditor);
        webDataBinder.registerCustomEditor(Receptionist.class, receptionistEditor);
        webDataBinder.registerCustomEditor(Doctor.class, doctorEditor);
        webDataBinder.registerCustomEditor(Patient.class, patientEditor);
        webDataBinder.addValidators(userValidator);
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping
    public String showForm(@RequestParam(value = "id", defaultValue = "0") long id,
                           ModelMap model,
                           HttpServletRequest request) {

        accessManager.checkUserAccess(request);

        User user = id == 0 ? new User() : userService.findById(id);

        setupReferenceData(user, SAVE, request, model);

        return FORM_PAGE;
    }

    @PostMapping
    public String process(@Validated(UserValidationGroup.class) @ModelAttribute("userData") User user,
                          BindingResult result,
                          @RequestParam("action") Action action,
                          ModelMap model,
                          HttpServletRequest request,
                          SessionStatus sessionStatus) {

        accessManager.checkUserAccess(request);

        setupReferenceData(user, VIEW, request, model);

        if (result.hasErrors()) {
            return FORM_PAGE;
        }

        if(DELETE.equals(action)) {
            accessManager.checkUserDeleteAccess(user, request);

            userService.delete(user);
        } else {
            user = userService.saveOrUpdate(user);

            if (isLoggedInUser(user, request)) {
                model.replace(USER_CMD, user);
            }
        }

        sessionStatus.setComplete();

        return redirect(SUCCESS);
    }

    @GetMapping("/list")
    public String showList(@RequestParam(value = "filterBy", required = false) String filterBy,
                           ModelMap model,
                           HttpServletRequest request) {

        accessManager.checkUserAccess(request);

        setupReferenceDataForList(filterBy, model);

        return LIST_PAGE;
    }

    private void setupReferenceDataForList(String filterBy, ModelMap model) {
        model.put("users", userService.findAll(filterBy));
    }

    private void setupReferenceData(User user,
                                    Action action,
                                    HttpServletRequest request,
                                    ModelMap model) {

        model.put("genderList", Gender.values());
        model.put("seedRoleList", roleService.findAll());

        if (SAVE.equals(action)) {
            model.put(USER_DATA, user);
        }

        model.put("isDeletable", !user.isNew() && !isLoggedInUser(user, request));
    }
}
