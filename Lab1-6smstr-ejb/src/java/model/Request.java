/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author Анюта
 */
public class Request implements Serializable{

    private int ID;
    private Reader sender;
    private Book desiredBook;
    private String status;
    private Date dateOfAdding;

    public Request(int ID, Reader sender, Book desiredBook, String status, Date dateOfAdding) {
        this.ID = ID;
        this.sender = sender;
        this.desiredBook = desiredBook;
        this.status = status;
        this.dateOfAdding = dateOfAdding;
    }

    public Request(int ID, Reader sender, Book desiredBook, String status, String dateOfAdding) {
        this.ID = ID;
        this.sender = sender;
        this.desiredBook = desiredBook;
        this.status = status;
        this.dateOfAdding = Date.valueOf(dateOfAdding);
    }

    public Request(int ID) {
        this.ID = ID;
    }

    public Request() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Reader getSender() {
        return sender;
    }

    public void setSender(Reader sender) {
        this.sender = sender;
    }

    public Book getDesiredBook() {
        return desiredBook;
    }

    public void setDesiredBook(Book desiredBook) {
        this.desiredBook = desiredBook;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDateOfAdding() {
        return dateOfAdding;
    }

    public void setDateOfAdding(Date dateOfAdding) {
        this.dateOfAdding = dateOfAdding;
    }

    public void setDateOfAdding(String dateOfAdding) {
        this.dateOfAdding = Date.valueOf(dateOfAdding);
    }
    @Override
    public String toString() {
        return "Request{" + "ID=" + ID + ", sender=" + sender.getLogin() + ", desiredBook=" + desiredBook.getName() + ", status=" + status + ", dateOfAdding=" + dateOfAdding + '}';
    }

}
