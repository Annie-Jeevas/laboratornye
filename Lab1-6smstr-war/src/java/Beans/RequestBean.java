package Beans;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import dao.*;
import javax.naming.NamingException;
import model.*;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Yakov
 */
public class RequestBean {

    private FacesMessage msg;
    private ArrayList<Book> books;
    private DAOBook dao;
    private ArrayList<Book> selectedBooks;

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    public RequestBean() throws ClassNotFoundException {
        this.dao = new DAOBook();
        
    }

    public FacesMessage getMsg() {
        return msg;
    }

    public void setMsg(FacesMessage msg) {
        this.msg = msg;
    }

    public DAOBook getDao() {
        return dao;
    }

    public void setDao(DAOBook dao) {
        this.dao = dao;
        
    }

    public ArrayList<Book> getSelectedBooks() {
        return selectedBooks;
    }

    public void setSelectedBooks(ArrayList<Book> selectedBooks) {
        this.selectedBooks = selectedBooks;
    }

    
    public String delete() throws SQLException {
        for (Book ss : selectedBooks) {
            dao.delete(ss);
        }
        return "deleted";
    }

    @PostConstruct
    public void allBooks() {
        try {
            books = dao.readAll();
        } catch (SQLException | ClassNotFoundException | NamingException ex) {
            Logger.getLogger(RequestBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

  public void edit(RowEditEvent event) throws SQLException {
        msg = new FacesMessage("Edit made", String.valueOf(((Book) event.getObject()).getID()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
        dao.update(event.getObject());
    }
    public void cancel(RowEditEvent event) {
        msg = new FacesMessage("Edit cancelled", String.valueOf(((Book) event.getObject()).getID()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

}
