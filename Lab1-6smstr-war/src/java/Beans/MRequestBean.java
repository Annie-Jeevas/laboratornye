/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import dao.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import model.*;

/**
 *
 * @author Анюта
 */
public class MRequestBean {

    
    private DAOUser dAOUser = new DAOUser();

    @EJB
    private DAORemote daob;
    private HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    private ArrayList<Book> allBooks;
    private Visitor newVisitor;
    public ArrayList<Book> getAllBooks() {
        return allBooks;
    }

    public Visitor getNewVisitor() {
        return newVisitor;
    }

    public void setNewVisitor(Visitor newVisitor) {
        this.newVisitor = newVisitor;
    }

    public void setAllBooks(ArrayList<Book> allBooks) {
        this.allBooks = allBooks;
    }

    public MRequestBean() {        
        newVisitor = new Visitor();
    }

    @PostConstruct
    public void getBooks() {
        try {
            allBooks = daob.readAll();
        } catch (SQLException | ClassNotFoundException | NamingException ex) {
            Logger.getLogger(MRequestBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Book> getMyFavourite() {
        ArrayList<Book> books = null;
        try {
            DAOMyBooks daom = new DAOMyBooks();
            User u = dAOUser.getUserByUsername(req.getRemoteUser());
            Reader r = new Reader(); 
            r.setID(u.getId_visitor());
            books = daom.read(r);
        } catch (SQLException ex) {
            Logger.getLogger(MRequestBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return books;
        }
    }
    

     public ArrayList<Book>  getAllAllBooks(){
        ArrayList<Book> books = null;
        try {
            books = daob.readAll();
        } catch (SQLException ex) {
            Logger.getLogger(MRequestBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return books;
        }
    }
     
     public ArrayList<Request> getAllRequests() throws ClassNotFoundException, SQLException {
        ArrayList<Request> reqs = new ArrayList<>();

        try {
            DAORequest daor = new DAORequest();            
            reqs = daor.readAll();
        } catch (SQLException ex) {
            Logger.getLogger(MRequestBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return reqs;
        }
    }
     
      public ArrayList<Comment> getAllComments() throws ClassNotFoundException, SQLException {
        ArrayList<Comment> comms = new ArrayList<>();

        try {
            DAOComment daoc = new DAOComment();            
            comms = daoc.readAll();
        } catch (SQLException ex) {
            Logger.getLogger(MRequestBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return comms;
        }
    }
     
    public ArrayList<Request> getMyRequests() throws ClassNotFoundException, SQLException {
        ArrayList<Request> reqs = new ArrayList<>();

        try {
            DAORequest daor = new DAORequest();
            User u = dAOUser.getUserByUsername(req.getRemoteUser());
            Reader r = new Reader(); 
            r.setID(u.getId_visitor());
            reqs = daor.read(r);
        } catch (SQLException ex) {
            Logger.getLogger(MRequestBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return reqs;
        }
    }

    public String addVisitor(){
        try {
            DAOReader daor = new DAOReader();
            DAOVisitor daov = new DAOVisitor();
            Reader r = new Reader();
            r.setLogin(newVisitor.getLogin());
            r.setPassword(newVisitor.getPassword());
            r.setDate_of_last_visit(new java.sql.Date(2016,12,25));
            r.setInSystem(Boolean.FALSE);
            r.setBan(Boolean.FALSE);
            daor.create(r);
            Visitor v = daov.getVisitorByUsername(newVisitor.getLogin());
            User u = new User(v.getID(), "reader", v.getLogin());
            dAOUser.Create(u);
            Logger.getLogger(MRequestBean.class.getName()).log(Level.INFO,"Added visitor {0}",v.getLogin());
            return "addingVisitorOk";
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(MRequestBean.class.getName()).log(Level.SEVERE, null, ex);
            return "addingVisitorFail";
        }
        
    
    }
}
