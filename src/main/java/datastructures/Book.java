package datastructures;

import java.util.ArrayList;
import java.util.List;

  class Book {
    private String title;
    private String author;
    private String isbn;
    private int publicationYear;
    private String genre;

    public Book(String title, String author, String isbn, int publicationYear, String genre) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.genre = genre;
    }

    // Getters
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getIsbn() { return isbn; }
    public int getPublicationYear() { return publicationYear; }

    @Override
    public String toString() {
        return String.format("ISBN: %-10s | %-20s | %-15s | %d | %s", 
                isbn, title, author, publicationYear, genre);
    }
}
