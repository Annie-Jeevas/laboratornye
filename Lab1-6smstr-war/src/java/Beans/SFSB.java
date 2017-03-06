/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import dao.DAORemote;
import java.io.Serializable;
import java.sql.SQLException;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.LocalBean;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import model.User;

/**
 *
 * @author Анюта
 */
@ConversationScoped
@Named(value = "SFSB")
@Stateful
public class SFSB implements Serializable, SFSBLocal {

    @EJB
    DAORemote daouser;
    @EJB
    Conversation cvs;
    
    public String getRole(String username) throws SQLException{
    cvs.begin();
    User u = daouser.getUserByUsername(username);
    cvs.end();
    return u.getRole();
    }
}
