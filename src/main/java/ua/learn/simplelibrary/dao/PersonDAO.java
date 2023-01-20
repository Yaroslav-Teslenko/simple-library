package  ua.learn.simplelibrary.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.learn.simplelibrary.models.Person;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// общение со списком(базой данных), поиск, извлечение, удаление, добавление людей
@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Person> index() {

        return jdbcTemplate.query("select * from person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {

        return jdbcTemplate.query("select * from person where id_person=?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("insert into person (fio, year_person) values( ?, ?)",
                person.getFio(), person.getYearPerson());
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("update person set fio=?,year_person=?  where id_person=?",
                updatedPerson.getFio(), updatedPerson.getYearPerson(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("delete from person where id_person=?", id);
    }

    ///////Validate
    public Optional<Person> selectFIO(String fio) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE fio=?", new Object[]{fio},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }


}
