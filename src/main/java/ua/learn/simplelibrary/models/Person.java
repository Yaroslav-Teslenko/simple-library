package ua.learn.simplelibrary.models;

import javax.validation.constraints.*;

public class Person {
    private int idPerson;
    @NotEmpty(message = "FIO must be not empty")
//    @Size(min = 2, max = 30, message = "2-30 length name")
    private String fio;
    @Min(value = 1900, message = "Year must be > 1900")
    private int yearPerson;


    public Person() {
    }

    public Person(int idPerson, String fio, int yearPerson) {
        this.idPerson = idPerson;
        this.fio = fio;
        this.yearPerson = yearPerson;

    }

    public int getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(int idPerson) {
        this.idPerson = idPerson;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public int getYearPerson() {
        return yearPerson;
    }

    public void setYearPerson(int yearPerson) {
        this.yearPerson = yearPerson;
    }
}
