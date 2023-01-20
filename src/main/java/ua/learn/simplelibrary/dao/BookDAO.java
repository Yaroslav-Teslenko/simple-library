package ua.learn.simplelibrary.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.learn.simplelibrary.models.Book;


import java.util.List;
import java.util.Optional;

// общение со списком(базой данных), поиск, извлечение, удаление, добавление людей
@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Book> index() {

        return jdbcTemplate.query("select * from book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(int id) {

        return jdbcTemplate.query("select * from book where id_book=?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }

    public void save(Book book) {
        jdbcTemplate.update("insert into book (name_book, year_book, author, id_person) values( ?, ?,?, ?)",
                book.getNameBook(), book.getYearBook(),book.getAuthor(), book.getIdPerson());
    }

    public void update(int id, Book updatedBook) {
        jdbcTemplate.update("update book set name_book=?,year_book=?, author=?, id_person=?  where id_book=?",
                updatedBook.getNameBook(), updatedBook.getYearBook(), updatedBook.getAuthor(), updatedBook.getIdPerson(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("delete from book where id_book=?", id);
    }

//    ///////Validate
//    public Optional<Book> selectFIO(String fio) {
//        return jdbcTemplate.query("SELECT * FROM Book WHERE fio=?", new Object[]{fio},
//                new BeanPropertyRowMapper<>(Book.class)).stream().findAny();
//    }


}
