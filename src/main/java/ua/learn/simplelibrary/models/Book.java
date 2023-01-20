package ua.learn.simplelibrary.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class Book {
    private int idBook;
    @NotEmpty(message = "Book's name must be not empty")
    private String nameBook;
    @Min(value = 0, message = "Year must be > 0")
    private int yearBook;
    @NotEmpty(message = "Author's name must be not empty")
    private String author;
    private int idPerson;


    public Book() {
    }

    public Book(int idBook, String nameBook, int yearBook, String author, int idPerson) {
        this.idBook = idBook;
        this.nameBook = nameBook;
        this.yearBook = yearBook;
        this.author = author;
        this.idPerson = idPerson;
    }

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public String getNameBook() {
        return nameBook;
    }

    public void setNameBook(String nameBook) {
        this.nameBook = nameBook;
    }

    public int getYearBook() {
        return yearBook;
    }

    public void setYearBook(int yearBook) {
        this.yearBook = yearBook;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(int idPerson) {
        this.idPerson = idPerson;
    }
}
