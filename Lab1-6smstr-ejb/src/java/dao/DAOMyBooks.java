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
import java.util.logging.Logger;
import model.Book;
import model.Reader;

/**
 *
 * @author Анюта
 */
public class DAOMyBooks extends Dao {

    static String tb_name = "readers_book";

    static Statement stmt;
    static PreparedStatement pstmt;
    static ResultSet result;

    static Logger log = Logger.getLogger(DAOComment.class.getName());

    //добавить книгу в мои книги
    public boolean add(Book v, Reader r) throws SQLException {
        try {
            connect();
            String query = "INSERT INTO " + tb_name + " (Id_reader, id_book) VALUES(?, ?)";
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, r.getID());
            pstmt.setInt(2, v.getID());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            return false;
        } finally {
            disconnect();
        }

    }

    //удалить из моих книг
    public boolean delete(Book v, Reader r) throws SQLException {
        try {
            connect();
            String query = "Delete from " + tb_name + " where id_reader=? and id_book=?";
            pstmt = con.prepareStatement(query);
            pstmt.setInt(2, v.getID());
            pstmt.setInt(1, r.getID());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            return false;
        } finally {
            disconnect();
        }

    }

    //посмотреть все мои книги
    public ArrayList<Book> read(Reader r) throws SQLException, ClassNotFoundException {
        connect();
        ArrayList<Book> vs = new ArrayList<Book>();
        String query = "SELECT * FROM " + tb_name + " where id_reader=" + r.getID();
        stmt = con.createStatement();
        result = stmt.executeQuery(query);
        DAOBook db = new DAOBook();

        while (result.next()) {
            int id_book = result.getInt(2);
            int id_sender = result.getInt(1);

            Book book = db.read(new Book(id_book)).get(0);

            vs.add(book);
        }
        disconnect();
        return vs;
    }

}
