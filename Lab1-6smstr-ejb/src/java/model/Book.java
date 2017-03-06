/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Анюта
 */
public class Book implements Serializable{

    private int ID;
    private String name;
    private String author;
    private Date year;
    private String bookfile;
    private String ganre;
    //private double avrGrade;

    public Book(int ID) {
        this.ID = ID;
    }

    public Book(int ID, String name, String author, Date year, String bookfile, String ganre/*, double avr*/) {
        this.ID = ID;
        this.name = name;
        this.author = author;
        this.year = year;
        this.bookfile = bookfile;
        //this.avrGrade = avr;
        this.ganre = ganre;
    }

    public Book() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getYear() {
        return year;
    }

    public void setYear(Date year) {
        this.year = year;
    }

    public String getBookfile() {
        return bookfile;
    }

    public void setBookfile(String bookfile) {
        this.bookfile = bookfile;
    }

    public String getGanre() {
        return ganre;
    }

    public void setGanre(String ganre) {
        this.ganre = ganre;
    }

    
    @Override
    public String toString() {
        return "Book{" + "ID=" + ID + ", name=" + name + ", author=" + author + ", year=" + year + ", bookfile=" + bookfile + ", ganre=" + ganre + '}';
    }

    
    

}
