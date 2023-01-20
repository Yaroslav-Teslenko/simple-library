package ua.learn.simplelibrary.dao;

import org.springframework.jdbc.core.RowMapper;
import ua.learn.simplelibrary.models.Book;
import ua.learn.simplelibrary.models.Person;

import java.sql.ResultSet;
import java.sql.SQLException;


public class BookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet resultSet, int i) throws SQLException {
        System.out.println(resultSet);
        Book book = new Book();
        book.setIdBook(resultSet.getInt("id_book"));
        book.setNameBook(resultSet.getString("name_book"));
        book.setYearBook(resultSet.getInt("year_book"));
        book.setAuthor(resultSet.getString("author"));
        book.setIdPerson(resultSet.getInt("id_person"));
        return book;
    }
}
