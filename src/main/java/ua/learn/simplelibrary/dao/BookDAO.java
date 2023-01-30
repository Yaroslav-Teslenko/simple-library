package ua.learn.simplelibrary.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.learn.simplelibrary.models.Book;
import ua.learn.simplelibrary.models.Person;


import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Book> index() {
        //  return jdbcTemplate.query("select * from book", new BeanPropertyRowMapper<>(Book.class));
        return jdbcTemplate.query("select * from book", new BookMapper());
    }

    public Book show(int id) {
        return jdbcTemplate.query("select * from book where id_book=?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }

    // у новой книги id_person = null.
    public void save(Book book) {
        jdbcTemplate.update("insert into book (name_book, year_book, author) values( ?, ?,?)",
                book.getNameBook(), book.getYearBook(), book.getAuthor());
    }

    public void update(int id, Book updatedBook) {
        System.out.println("updatedBook " + updatedBook.getIdPerson());
        jdbcTemplate.update("update book set name_book=?,year_book=?, author=? where id_book=?",
                updatedBook.getNameBook(), updatedBook.getYearBook(), updatedBook.getAuthor(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("delete from book where id_book=?", id);
    }

    public Optional<Person> getBookOwner(int id) {
        return jdbcTemplate.query("select person.* from book join person on book.id_person=person.id_person " +
                        "where book.id_book=?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public void assign(int id, Person selectedPerson) {
        jdbcTemplate.update("update book set id_person=?  where id_book=?",
                selectedPerson.getIdPerson(), id);
    }

    public void relese(int id) {
        jdbcTemplate.update("update book set id_person=NULL where id_book=?", id);
    }

//    ///////Validate
//    public Optional<Book> selectFIO(String fio) {
//        return jdbcTemplate.query("SELECT * FROM Book WHERE fio=?", new Object[]{fio},
//                new BeanPropertyRowMapper<>(Book.class)).stream().findAny();
//    }


}
