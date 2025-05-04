package com.example.lab_5;

public class Author {
    private final int id;
    private final String name;
    private final String surname;
    private final String patronymic;
    private final String birthDate;
    private final String country;

    public Author(int id, String surname, String name, String patronymic, String birthDate, String country) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.birthDate = birthDate;
        this.country = country;
    }


    public int getId() { return id; }
    public String getName() { return name; }
    public String getBirthDate() { return birthDate; }
    public String getCountry() { return country; }
    public String getSurname() { return surname; }
    public String getPatronymic() { return patronymic; }

    @Override
    public String toString() {
        return getFullName();
    }

    public String getFullName() {
        return String.format("%s %s %s", surname, name, patronymic).trim();
    }
}