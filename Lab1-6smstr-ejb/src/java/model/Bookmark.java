/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Анюта
 */
public class Bookmark implements Serializable{

    private int id;
    private Book markedBook;
    private int page;
    private Reader owner;

    public Bookmark() {
    }

    public Bookmark(int id, Reader owner, Book markedBook, int page) {
        this.id = id;
        this.markedBook = markedBook;
        this.page = page;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Book getMarkedBook() {
        return markedBook;
    }

    public void setMarkedBook(Book markedBook) {
        this.markedBook = markedBook;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public Reader getOwner() {
        return owner;
    }

    public void setOwner(Reader owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Bookmark{" + "id=" + id + ", markedBook=" + markedBook.getName() + ", page=" + page + ", owner=" + owner.getLogin() + '}';
    }

}
