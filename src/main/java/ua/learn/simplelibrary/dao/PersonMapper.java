package ua.learn.simplelibrary.dao;

import org.springframework.jdbc.core.RowMapper;

import ua.learn.simplelibrary.models.Person;

import java.sql.ResultSet;
import java.sql.SQLException;


public class PersonMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet resultSet, int i) throws SQLException {
        System.out.println(resultSet);
        Person person = new Person();
        person.setIdPerson(resultSet.getInt("id_person"));
        person.setYearPerson(resultSet.getInt("year_person"));
        return person;
    }
}
