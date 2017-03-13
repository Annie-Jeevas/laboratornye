/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import dao.DAORemote;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.NamingException;
import model.Book;

/**
 *
 * @author Анюта
 */
@ConversationScoped
@Named(value = "SFSB")
@Stateful
public class SFSB implements Serializable, SFSBLocal {
    
    @Inject
    private Conversation conv;
    @EJB
    private DAORemote daob;
    private int idBookForReading;
   

    public int getIdBookForReading() {
        return idBookForReading;
    }
    

    public String setIdBookForReading(int idBookForReading) {
        conv.begin();
        this.idBookForReading = idBookForReading;
        conv.end();
        return "grade";
    }

    
    @Override
    public double getBookMark () {
        conv.begin();
        double mark = 0;
        try {
            Book b = daob.read(new Book(idBookForReading)).get(0);
            mark = daob.averageGrade(b);
        } catch (SQLException | ClassNotFoundException | NamingException ex) {
            Logger.getLogger(SFSB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conv.end();
            return mark;
        }
        
    }
}
