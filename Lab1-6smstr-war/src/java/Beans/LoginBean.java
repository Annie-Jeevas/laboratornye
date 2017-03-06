package Beans;

import java.io.IOException;
import java.security.Principal;
import java.sql.SQLException;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import dao.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import model.*;

public class LoginBean {
    private final Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
    private String username;
    private String role;
    
    @EJB
    private DAORemote dao;

    public LoginBean() {
        if (principal != null) {
            username = principal.getName();
            try {
                role = dao.getUserByUsername(username).getRole();
            } catch (SQLException ex) {
                Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public User getUserInfoByUsername() throws SQLException, Exception {
        if (principal != null) {
            username = principal.getName();
        }
        User user = dao.getUserByUsername(username);
        return user;
    }

    public boolean getUserIn() {
        if (principal != null) {
            return true;
        } else {
            return false;
        }
    }

    public void logout() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        try {
            request.logout();
            session.invalidate();
            FacesContext.getCurrentInstance().getExternalContext().redirect("./");
        } catch (ServletException | IOException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
