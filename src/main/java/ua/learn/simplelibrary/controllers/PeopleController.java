package ua.learn.simplelibrary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.learn.simplelibrary.dao.PersonDAO;
import ua.learn.simplelibrary.models.Person;
import ua.learn.simplelibrary.utils.PersonValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private PersonDAO personDAO;
    private PersonValidator personValidator;
    @Autowired
    public PeopleController(PersonDAO personDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String index(Model model) {
        // получить всех людей из DAO, передать на отображение
        model.addAttribute("people", personDAO.index());
        return "people/index"; // воздращаем шаблон, содержаюий список людей
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        // получить одного по id из DAO, передать на отображение
        model.addAttribute("person", personDAO.show(id));
        return "people/show";// воздращаем шаблон, содержаюий 1 человека
    }


    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "/people/new";
    }

    @PostMapping()
    //PostM потому что отправляем на запись то, что получим в форме "/people/new"
    public String create(@ModelAttribute("person")
                         @Valid Person person,
                         BindingResult bindingResult) {
        personValidator.validate(person,bindingResult);
        if (bindingResult.hasErrors()) {
            return "/people/new";
        }
        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        // получить одного по id из DAO, передать на отображение
        model.addAttribute("person", personDAO.show(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {
        // @ModelAttribute сама все создаст
        if (bindingResult.hasErrors()) {
            return "/people/edit";
        }
        personDAO.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@ModelAttribute("person") Person person, @PathVariable("id") int id) {

        personDAO.delete(id);
        return "redirect:/people";
    }
}