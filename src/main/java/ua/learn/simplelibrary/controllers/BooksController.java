package ua.learn.simplelibrary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.learn.simplelibrary.dao.BookDAO;
import ua.learn.simplelibrary.dao.PersonDAO;
import ua.learn.simplelibrary.models.Book;
import ua.learn.simplelibrary.models.Person;
import ua.learn.simplelibrary.utils.BookValidator;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {

    private BookDAO bookDAO;
    private BookValidator bookValidator;
    private PersonDAO personDAO;
    @Autowired
    public BooksController(BookDAO bookDAO, BookValidator bookValidator, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.bookValidator = bookValidator;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookDAO.index());

        return "books/index"; 
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id,
                       Model model,
                       @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookDAO.show(id));
        Optional<Person> bookOwner=bookDAO.getBookOwner(id);
        if (bookOwner.isPresent()) model.addAttribute("owner", bookOwner.get());
        else {model.addAttribute("people", personDAO.index());}
        return "books/show";
    }


    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "/books/new";
    }

    @PostMapping()
  
    public String create(@ModelAttribute("book")
                         @Valid Book book,
                         BindingResult bindingResult) {
        bookValidator.validate(book,bindingResult);
        if (bindingResult.hasErrors()) {
            return "/books/new";
        }
        bookDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model,
                       @ModelAttribute("person")Person person) {
       
        model.addAttribute("book", bookDAO.show(id));
        model.addAttribute("people", personDAO.index());

        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {
        
        if (bindingResult.hasErrors()) {
            return "/books/edit";
        }
        bookDAO.update(id, book);
        return "redirect:/books/"+id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        bookDAO.relese(id);
        return "redirect:/books/"+id;
    }
    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id,
                         @ModelAttribute("person")Person selectedPerson) {
        bookDAO.assign(id, selectedPerson);
        return "redirect:/books/"+id;
    }
}
