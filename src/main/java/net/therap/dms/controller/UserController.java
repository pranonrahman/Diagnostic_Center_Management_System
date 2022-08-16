package net.therap.dms.controller;

import net.therap.dms.editor.*;
import net.therap.dms.entity.*;
import net.therap.dms.service.AccessManager;
import net.therap.dms.service.RoleService;
import net.therap.dms.service.UserService;
import net.therap.dms.util.WebUtil;
import net.therap.dms.validationGroup.UserValidationGroup;
import net.therap.dms.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static net.therap.dms.entity.Action.SAVE;
import static net.therap.dms.entity.Action.VIEW;
import static net.therap.dms.util.SessionUtil.getUser;
import static net.therap.dms.util.UserUtil.isSessionUser;

/**
 * @author raian.rahman
 * @since 8/4/22
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private static final String USER_CMD = "user";
    private static final String FORM_PAGE = "user/form";
    private static final String LIST_PAGE = "user/list";
    private static final String SUCCESS_REDIRECT_PATH = WebUtil.redirect("/success");

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

    @Autowired
    private AccessManager accessManager;

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
    public String showForm(@RequestParam(value = "id", defaultValue = "0") long id,
                           ModelMap model,
                           HttpServletRequest request) {

        accessManager.checkUserAccess(request);

        setupReferenceData(id, SAVE, request, model);

        return FORM_PAGE;
    }

    @PostMapping
    public String process(@Validated(UserValidationGroup.class) @ModelAttribute("userData") User user,
                          BindingResult result,
                          ModelMap model,
                          RedirectAttributes redirectAttributes,
                          HttpServletRequest request,
                          SessionStatus sessionStatus) {

        accessManager.checkUserAccess(request);

        setupReferenceData(user.getId(), VIEW, request, model);

        if (result.hasErrors()) {
            return FORM_PAGE;
        }

        user = userService.saveOrUpdate(user);

        sessionStatus.setComplete();

        User sessionUser = getUser(request);

        if (sessionUser.getUserName().equals(user.getUserName())) {
            model.replace(USER_CMD, user);
        }

        redirectAttributes.addAttribute("id", user.getId());

        return SUCCESS_REDIRECT_PATH;
    }

    @RequestMapping("/list")
    public String showList(@RequestParam(value = "filterBy", required = false) String filterBy,
                           ModelMap model,
                           HttpServletRequest request) {

        accessManager.checkUserAccess(request);

        setupReferenceDataForList(filterBy, model);

        return LIST_PAGE;
    }

    @RequestMapping(value = "/delete")
    public String deleteUser(@RequestParam(value = "id", defaultValue = "0") long id,
                             HttpServletRequest request) throws RuntimeException {

        accessManager.checkUserAccess(request);

        User user = userService.findById(id);

        if (isSessionUser(request, user)) {
            throw new RuntimeException(msa.getMessage("user.selfDelete.message"));
        }

        userService.delete(user);

        return SUCCESS_REDIRECT_PATH;
    }

    private void setupReferenceDataForList(String filterBy, ModelMap model) {
        model.put("users", userService.findAll(filterBy));
    }

    private void setupReferenceData(long id,
                                    Action action,
                                    HttpServletRequest request,
                                    ModelMap model) {

        model.put("genderList", Gender.values());
        model.put("seedRoleList", roleService.findAll());

        if (SAVE.equals(action)) {
            model.put("userData", id == 0 ? new User() : userService.findById(id));
        }

        model.put("isDeletable", id != 0 && !getUser(request).getUserName().equals(
                userService.findById(id).getUserName()));
    }
}
