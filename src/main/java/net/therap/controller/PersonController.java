package net.therap.controller;

import net.therap.editor.DateEditor;
import net.therap.model.Gender;
import net.therap.model.Person;
import net.therap.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

import static java.util.Objects.isNull;

/**
 * @author raian.rahman
 * @since 8/4/22
 */
@Controller
@RequestMapping("person")
public class PersonController {

    private static final String FORM_PAGE = "person/form";
    private static final String LIST_PAGE = "person/list";

    private static final String VIEW_REDIRECT_PATH = "redirect:/person/view?id=";
    private static final String LIST_REDIRECT_PATH = "redirect:/person/list";

    @Autowired
    private DateEditor dateEditor;

    @Autowired
    private PersonService personService;

    @InitBinder
    private void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(Date.class, dateEditor);
    }

    @GetMapping("createOrUpdate")
    public String showForm(@RequestParam(value = "id", required = false) Long id, ModelMap modelMap) {
        modelMap.put("readOnly", false);
        modelMap.put("genderList", Gender.values());

        modelMap.put("person", isNull(id) ? new Person() : personService.findById(id));

        return FORM_PAGE;
    }

    @PostMapping("createOrUpdate")
    public String processPersonForm(@ModelAttribute Person person, BindingResult bindingResult, ModelMap modelMap) {
        modelMap.put("readOnly", false);
        modelMap.put("genderList", Gender.values());

        if(bindingResult.hasErrors()) {
            return FORM_PAGE;
        }

        person = personService.saveOrUpdate(person);

        return VIEW_REDIRECT_PATH + person.getId();
    }

    @GetMapping("view")
    public String showView(@RequestParam(value = "id") Long id, ModelMap modelMap) {
        modelMap.put("readOnly", true);
        modelMap.put("genderList", Gender.values());

        if(isNull(id)) {
            return LIST_REDIRECT_PATH;
        }

        Person person = personService.findById(id);

        if(isNull(person)) {
            throw new RuntimeException("Person not found");
        }

        modelMap.addAttribute("person", person);

        return FORM_PAGE;
    }

    @RequestMapping
    public String showList(ModelMap modelMap) {
        modelMap.put("personList", personService.findAll());
        return LIST_PAGE;
    }
}
