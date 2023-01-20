package ua.learn.simplelibrary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.learn.simplelibrary.dao.BookDAO;
import ua.learn.simplelibrary.models.Book;
import ua.learn.simplelibrary.utils.BookValidator;
import ua.learn.simplelibrary.utils.BookValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/book")
public class BookController {

    private BookDAO bookDAO;
    private BookValidator bookValidator;
    @Autowired
    public BookController(BookDAO bookDAO,BookValidator bookValidator) {
        this.bookDAO = bookDAO;
        this.bookValidator = bookValidator;
    }

    @GetMapping()
    public String index(Model model) {
       
        model.addAttribute("book", bookDAO.index());
        return "book/index"; 
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
       
        model.addAttribute("book", bookDAO.show(id));
        return "book/show";
    }


    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "/book/new";
    }

    @PostMapping()
  
    public String create(@ModelAttribute("book")
                         @Valid Book book,
                         BindingResult bindingResult) {
        bookValidator.validate(book,bindingResult);
        if (bindingResult.hasErrors()) {
            return "/book/new";
        }
        bookDAO.save(book);
        return "redirect:/book";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
       
        model.addAttribute("book", bookDAO.show(id));
        return "book/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {
        
        if (bindingResult.hasErrors()) {
            return "/book/edit";
        }
        bookDAO.update(id, book);
        return "redirect:/book";
    }

    @DeleteMapping("/{id}")
    public String delete(@ModelAttribute("book") Book book, @PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/book";
    }
}
