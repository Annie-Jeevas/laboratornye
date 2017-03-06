/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

/**
 *
 * @author Анюта
 */
public class Visitor implements Serializable{

    protected int ID;
    protected String login;
    protected String password;
    protected Date date_of_last_visit;

    public Visitor(int ID, String login, String password, String date_of_last_visit) {
        this.ID = ID;
        this.login = login;
        this.password = password;
        this.setDate_of_last_visit(date_of_last_visit);
    }

    public Visitor(int ID, String login, String password, Date date_of_last_visit) {
        this.ID = ID;
        this.login = login;
        this.password = password;
        this.date_of_last_visit = date_of_last_visit;
    }

    public Visitor() {
    }

    public Visitor(int id_sender) {
        this.ID = id_sender;
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

    public void setDate_of_last_visit(String date_of_last_visit) {
        if (date_of_last_visit != null) {
            this.date_of_last_visit = Date.valueOf(date_of_last_visit);
        } else {
            this.date_of_last_visit = null;
        }
    }

    public void setDate_of_last_visit(Date date_of_last_visit) {

        this.date_of_last_visit = date_of_last_visit;

    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.password);
        return hash;
    }

    @Override
    public String toString() {
        return "Visitor{" + "ID=" + ID + ", login=" + login + ", password=" + password + ", date_of_last_visit=" + date_of_last_visit + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Visitor other = (Visitor) obj;
        if (!Objects.equals(this.login, other.login)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        return true;
    }

}
