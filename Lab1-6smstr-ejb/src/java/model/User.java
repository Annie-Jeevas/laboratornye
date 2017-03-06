/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author Анюта
 */
public class User implements Serializable{
    private int id_visitor;
    private String role;
    private String login;

    public User() {
    }

    public User(int id_visitor, String role, String login) {
        this.id_visitor = id_visitor;
        this.role = role;
        this.login = login;
    }

    public int getId_visitor() {
        return id_visitor;
    }

    public void setId_visitor(int id_visitor) {
        this.id_visitor = id_visitor;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

   
}
