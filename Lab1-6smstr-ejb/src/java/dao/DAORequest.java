/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import model.*;

/**
 *
 * @author Анюта
 */
public class DAORequest extends Dao {

    static String tb_name = "request";

    static Statement stmt;
    static PreparedStatement pstmt;
    static ResultSet result;

    static Logger log = Logger.getLogger(DAOComment.class.getName());

    public DAORequest() throws ClassNotFoundException, SQLException, NamingException {

    }

    //список всех заявок
    public ArrayList<Request> readAll() throws SQLException, ClassNotFoundException, NamingException {
        connect();
        ArrayList<Request> vs = new ArrayList<Request>();
        String query = "SELECT * FROM " + tb_name;
        stmt = con.createStatement();
        ResultSet result = stmt.executeQuery(query);
        DAOBook db = new DAOBook();
        DAOReader dr = new DAOReader();

        while (result.next()) {
            int id = result.getInt(1);
            int id_author = result.getInt(2);
            int id_book = result.getInt(3);
            String stat = result.getString(5);
            Date dat = result.getDate(4);
            Reader commentAuthor = dr.read(id_author).get(0);
            Book commentedBook = db.read(new Book(id_book)).get(0);
            vs.add(new Request(id, commentAuthor, commentedBook, stat, dat));
        }
        disconnect();
        return vs;
    }

    //поиск по ИД
    public ArrayList<Request> read(Request r) throws SQLException, ClassNotFoundException, NamingException {
        connect();
        ArrayList<Request> vs = new ArrayList<Request>();
        String query = "SELECT * FROM " + tb_name + " where id_request=" + r.getID();
        stmt = con.createStatement();
        result = stmt.executeQuery(query);
        DAOBook db = new DAOBook();
        DAOReader dr = new DAOReader();

        while (result.next()) {
            int id = result.getInt(1);
            int id_author = result.getInt(2);
            int id_book = result.getInt(3);
            String stat = result.getString(5);
            Date dat = result.getDate(4);

            Reader commentAuthor = dr.read(id_author).get(0);
            Book commentedBook = db.read(new Book(id_book)).get(0);
            vs.add(new Request(id, commentAuthor, commentedBook, stat, dat));
        }
        disconnect();
        return vs;
    }

    //список заявок этого читателя
    public ArrayList<Request> read(Reader r) throws SQLException, ClassNotFoundException {
        connect();
        ArrayList<Request> vs = new ArrayList<Request>();
        String query = "SELECT * FROM " + tb_name + " where id_sender=" + r.getID();
        stmt = con.createStatement();
        result = stmt.executeQuery(query);
        DAOBook db = new DAOBook();

        while (result.next()) {
            int id = result.getInt(1);

            int id_book = result.getInt(3);
            String stat = result.getString(5);
            Date dat = result.getDate(4);

            Book commentedBook = db.read(new Book(id_book)).get(0);
            vs.add(new Request(id, r, commentedBook, stat, dat));
        }
        disconnect();
        return vs;
    }

    //создать заявку
    public boolean create(Request v) throws SQLException {

        try {
            connect();
            String query = "INSERT INTO " + tb_name + " (id_sender, id_book, status, date_of_request) VALUES(?, ?, ?, ?)";
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, v.getSender().getID());
            pstmt.setInt(2, v.getDesiredBook().getID());
            pstmt.setString(3, v.getStatus());
            pstmt.setDate(4, v.getDateOfAdding());
            pstmt.executeUpdate();
            log.log(Level.INFO, "Insert into {0}.", tb_name);
            return true;
        } catch (SQLException ex) {
            log.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            return false;
        } finally {
            disconnect();
        }

    }
    //изменить статус заявки

    public boolean updateStatus(Request v, String status) {
        try {
            connect();
            String query = "update " + tb_name + " set status=? where id_request=?";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, status);
            pstmt.setInt(2, v.getID());
            pstmt.executeUpdate();
            log.log(Level.INFO, "Status in {0} was updated", tb_name);
            return true;

        } catch (SQLException e) {
            log.log(Level.SEVERE, e.getLocalizedMessage(), e);
            return false;
        } finally {
            disconnect();
        }

    }

}
