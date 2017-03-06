/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import model.*;

/**
 *
 * @author Анюта
 */
public class Reader extends Visitor implements Serializable{
    private ArrayList<Book> myBooks;
    private ArrayList<Request> myRequests;
    private ArrayList<Comment> myComments;
    private ArrayList<Notice> myNotices;
    private Boolean inSystem;
    private Boolean ban;   

    public ArrayList<Book> getMyBooks() {
        return myBooks;
    }

    public void setMyBooks(ArrayList<Book> myBooks) {
        this.myBooks = myBooks;
    }

    public ArrayList<Request> getMyRequests() {
        return myRequests;
    }

    public void setMyRequests(ArrayList<Request> myRequests) {
        this.myRequests = myRequests;
    }

    public ArrayList<Comment> getMyComments() {
        return myComments;
    }

    public void setMyComments(ArrayList<Comment> myComments) {
        this.myComments = myComments;
    }

    public ArrayList<Notice> getMyNotices() {
        return myNotices;
    }

    public void setMyNotices(ArrayList<Notice> myNotices) {
        this.myNotices = myNotices;
    }

    public Boolean getInSystem() {
        return inSystem;
    }

    public void setInSystem(Boolean inSystem) {
        this.inSystem = inSystem;
    }

    public Boolean getBan() {
        return ban;
    }

    public void setBan(Boolean ban) {
        this.ban = ban;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDate_of_last_visit() {
        return date_of_last_visit;
    }

    public void setDate_of_last_visit(Date date_of_last_visit) {
        this.date_of_last_visit = date_of_last_visit;
    }

   
    
}
