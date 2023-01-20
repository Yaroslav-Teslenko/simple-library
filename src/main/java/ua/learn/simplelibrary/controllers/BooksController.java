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
@RequestMapping("/books")
public class BooksController {

    private BookDAO bookDAO;
    private BookValidator bookValidator;
    @Autowired
    public BooksController(BookDAO bookDAO, BookValidator bookValidator) {
        this.bookDAO = bookDAO;
        this.bookValidator = bookValidator;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookDAO.index());
        return "books/index"; 
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
       
        model.addAttribute("book", bookDAO.show(id));
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
    public String edit(@PathVariable("id") int id, Model model) {
       
        model.addAttribute("book", bookDAO.show(id));
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
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }
}