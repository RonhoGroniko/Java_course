package com.example.lab_5;

public class Book {
    private final int id;
    private final String title;
    private final String authorName;
    private final int year;
    private final String genre;
    private final int quantity;

    public Book(int id, String title, String authorName, int year, String genre, int quantity) {
        this.id = id;
        this.title = title;
        this.authorName = authorName;
        this.year = year;
        this.genre = genre;
        this.quantity = quantity;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthorName() { return authorName; }
    public int getYear() { return year; }
    public String getGenre() { return genre; }
    public int getQuantity() { return quantity; }
}