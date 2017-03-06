/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;
import model.Visitor;

/**
 *
 * @author Анюта
 */
public class DAOVisitor extends Dao {

    static String tb_name = "visitor";

    static Statement stmt;
    static PreparedStatement pstmt;
    static ResultSet result;
    static Logger log = Logger.getLogger(DAOComment.class.getName());

    public ArrayList<Visitor> readAll() throws SQLException {
        ArrayList<Visitor> vs = new ArrayList<Visitor>();
        String query = "SELECT * FROM " + tb_name;
        stmt = con.createStatement();
        result = stmt.executeQuery(query);
        while (result.next()) {
            int id = result.getInt(1);
            String login = result.getString(2);
            String password = result.getString(3);
            Date data = result.getDate(4);
            vs.add(new Visitor(id, login, password, data.toString()));
        }
        return vs;
    }

    public ArrayList<Visitor> read(Visitor v) throws SQLException {
        connect();
        ArrayList<Visitor> vs = new ArrayList<Visitor>();

        String query = "SELECT * FROM " + tb_name + " where id_visitor=?";
        pstmt = con.prepareStatement(query);
        pstmt.setInt(1, v.getID());

        result = pstmt.executeQuery();
        while (result.next()) {
            int id = result.getInt(1);
            String login = result.getString(2);
            String password = result.getString(3);
            Date data = result.getDate(4);
            vs.add(new Visitor(id, login, password, data.toString()));
        }
        disconnect();
        return vs;
    }

    public boolean create(Visitor v) throws SQLException {
        connect();
        try {

            String query = "INSERT INTO " + tb_name + " (login,password,date_of_last_visit) VALUES(?, ?, ?)";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, v.getLogin());
            pstmt.setString(2, v.getPassword());
            pstmt.setDate(3, v.getDate_of_last_visit());
            pstmt.executeUpdate();
            log.info("Visitor added");
            return true;
        } catch (SQLException ex) {
            log.severe(ex.getLocalizedMessage());
            return false;
        } finally {
            disconnect();
        }

    }

    public boolean update(Visitor old, Visitor new_) throws SQLException {
        String query = "UPDATE " + tb_name;
        String set_ = " SET login=?, password=?, date_of_last_visit=?"; //new
        String where = " where id_visitor= and login= and password= and date_of_last_visit=";//old
        if ((new_.getLogin() == null) || (new_.getPassword() == null) || (new_.getDate_of_last_visit() == null)) {
            return false;

        }
        if ((old.getID() == 0) && (old.getLogin() == null) && (old.getPassword() == null) && (old.getDate_of_last_visit() == null)) {
            return false;

        } else {
            if (old.getID() == 0) {
                where = where.replace("id_visitor= and ", "");
            } else {
                where = where.replace("id_visitor=", "id_visitor=\"" + old.getID() + "\"");
            }

            if (old.getLogin() == null) {
                where = where.replace("login= and ", "");
            } else {
                where = where.replace("login=", "login=\"" + old.getLogin() + "\"");
            }

            if (old.getPassword() == null) {
                where = where.replace("password= and ", "");
            } else {
                where = where.replace("password=", "password=\"" + old.getPassword() + "\"");
            }

            if (old.getDate_of_last_visit() == null) {
                where = where.replace(" and date_of_last_visit=", "");
            } else {
                where = where.replace("date_of_last_visit=", "date_of_last_visit=\"" + old.getDate_of_last_visit() + "\"");
            }

        }
        query = query + set_ + where;
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, new_.getLogin());
            pstmt.setString(2, new_.getPassword());
            pstmt.setDate(3, new_.getDate_of_last_visit());
            pstmt.executeUpdate();
            return true;

        } catch (SQLException ex) {
            return false;
        }

    }

    public boolean delete(Visitor v) throws SQLException {
        try {

            String query = "DELETE FROM " + tb_name + " where id_visitor= and login= and password= and date_of_last_visit=";
            if ((v.getID() == 0) && (v.getLogin() == null) && (v.getPassword() == null) && (v.getDate_of_last_visit() == null)) {
                return false;   //если пришел полностью пустой - не выполняем
            } else {            //иначе проверяем другие поля на пустоту
                if (v.getID() == 0) {
                    query = query.replace("id_visitor= and ", "");
                } else {
                    query = query.replace("id_visitor=", "id_visitor=\"" + v.getID() + "\"");
                }

                if (v.getLogin() == null) {
                    query = query.replace("login= and ", "");
                } else {
                    query = query.replace("login=", "login=\"" + v.getLogin() + "\"");
                }

                if (v.getPassword() == null) {
                    query = query.replace("password= and ", "");
                } else {
                    query = query.replace("password=", "password=\"" + v.getPassword() + "\"");
                }

                if (v.getDate_of_last_visit() == null) {
                    query = query.replace(" and date_of_last_visit=", "");
                } else {
                    query = query.replace("date_of_last_visit=", "date_of_last_visit=\"" + v.getDate_of_last_visit() + "\"");
                }
            }
            stmt = con.createStatement();
            stmt.executeUpdate(query);

            return true; //вернули да, все прошло хорошо
        } catch (SQLException ex) {
            return false;
        }

    }

    public boolean deleteAll() throws SQLException {
        try {

            String query = "DELETE FROM " + tb_name;
            stmt = con.createStatement();
            stmt.executeUpdate(query);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    public Visitor getVisitorByUsername(String name) throws SQLException {

        connect();
        Visitor vs = null;
        try {

            String query = "select * from " + tb_name + " where login=\"" + name + "\"";
            stmt = con.createStatement();

            result = stmt.executeQuery(query);
            while (result.next()) {
                int id = result.getInt(1);
                vs = new Visitor(id);
            }

        } catch (Exception e) {
            log.info(e.getMessage());
        } finally {
            disconnect();
            return this.read(vs).get(0);
        }
    }
}
