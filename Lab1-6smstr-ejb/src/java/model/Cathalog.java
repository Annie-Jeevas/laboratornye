/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Arrays;
import java.util.Objects;

/**
 *
 * @author Анюта
 */
public class Cathalog {
    private Book[] books;
    private String genre;

    public Book[] getBooks() {
        return books;
    }

    public void setBooks(Book[] books) {
        this.books = books;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Arrays.deepHashCode(this.books);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cathalog other = (Cathalog) obj;
        if (!Objects.equals(this.genre, other.genre)) {
            return false;
        }
        if (!Arrays.deepEquals(this.books, other.books)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Cathalog{" + "genre=" + genre + '}';
    }
    
}
