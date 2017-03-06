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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import model.Comment;
import java.util.logging.*;
import javax.naming.NamingException;
import model.*;

/**
 *
 * @author Анюта
 */
public class DAOComment extends Dao {

    static String tb_name = "comment";

    static Statement stmt;
    static PreparedStatement pstmt;
    static ResultSet result;
    
    static Logger log = Logger.getLogger(DAOComment.class.getName());

    static Map comment_statuses = new HashMap<Integer, String>();

    public DAOComment() throws ClassNotFoundException, SQLException {
        connect();
        //получить список имен статусов
        String query = "SELECT * FROM comment_status";
        stmt = con.createStatement();
        result = stmt.executeQuery(query);
        while (result.next()) {
            int id_status = result.getInt(1);
            String status = result.getString(2);
            comment_statuses.put(id_status, status);
        }
        disconnect();
    }

    //создать комментарий
    public boolean create(Comment v) throws SQLException {
        connect();
        try {

            String query = "INSERT INTO " + tb_name + " (id_author, text, grade, id_book, id_comm_status) VALUES(?, ?, ?, ?, ?)";
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, v.getCommentAuthor().getID());
            pstmt.setString(2, v.getText());
            pstmt.setInt(4, v.getCommentedBook().getID());
            pstmt.setInt(3, v.getGrade());
            pstmt.setInt(5, getStatusByName(v.getStatus()));
            pstmt.executeUpdate();
            log.log(Level.INFO, "Insert into {0}.", tb_name);
            return true;
        } catch (SQLException ex) {
            log.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            return false;
        } finally {disconnect();}

    }

    //изменить статус комментария
    public boolean updateStatus(Comment v, String status) {
        connect();
        try {
            String query = "update " + tb_name + " set id_comm_status=? where id_comment=?";
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, getStatusByName(status));
            pstmt.setInt(2, v.getID());
            pstmt.executeUpdate();
            log.log(Level.INFO, "Status in {0} was updated", tb_name);
            return true;

        } catch (SQLException e) {
            log.log(Level.SEVERE, e.getLocalizedMessage(), e);
            return false;
        } finally{disconnect();}

    }

    //посмотреть все комментарии
    public ArrayList<Comment> readAll() throws SQLException, ClassNotFoundException, NamingException {
        connect();
        ArrayList<Comment> vs = new ArrayList<Comment>();
        String query = "SELECT * FROM " + tb_name;
        stmt = con.createStatement();
        ResultSet result = stmt.executeQuery(query);
        DAOBook db = new DAOBook();
        DAOReader dr = new DAOReader();
        while (result.next()) {
            int id = result.getInt(1);
            int id_author = result.getInt(2);
            String text = result.getString(3);
            int grade = result.getInt(4);
            int id_book = result.getInt(5);
            String stat = getStatusByID(result.getInt(6));

            Reader commentAuthor = dr.read(id_author).get(0);
            Book commentedBook = db.read(new Book(id_book)).get(0);
            vs.add(new Comment(id, commentAuthor, text, grade, commentedBook, stat));
        }
        disconnect();
        return vs;
    }

    //поиск по ИД
    public ArrayList<Comment> read(Comment c) throws SQLException, ClassNotFoundException, NamingException {
        connect();
        ArrayList<Comment> vs = new ArrayList<Comment>();
        String query = "SELECT * FROM " + tb_name + " where id_comment=" + c.getID();
        stmt = con.createStatement();
        result = stmt.executeQuery(query);
        DAOBook db = new DAOBook();
        DAOReader dr = new DAOReader();
        while (result.next()) {
            int id = result.getInt(1);
            int id_author = result.getInt(2);
            String text = result.getString(3);
            int grade = result.getInt(4);
            int id_book = result.getInt(5);
            String stat = getStatusByID(result.getInt(6));

            Reader commentAuthor = dr.read(id_author).get(0);
            Book commentedBook = db.read(new Book(id_book)).get(0);
            vs.add(new Comment(id, commentAuthor, text, grade, commentedBook, stat));
        }
        disconnect();
        return vs;
    }

    //все комментарии пользователя
    public ArrayList<Comment> read(Reader r) throws SQLException, ClassNotFoundException {
        connect();
        ArrayList<Comment> vs = new ArrayList<Comment>();
        String query = "SELECT * FROM " + tb_name + " where id_author=" + r.getID();
        stmt = con.createStatement();
        result = stmt.executeQuery(query);
        DAOBook db = new DAOBook();
        while (result.next()) {
            int id = result.getInt(1);

            String text = result.getString(3);
            int grade = result.getInt(4);
            int id_book = result.getInt(5);
            String stat = getStatusByID(result.getInt(6));
            Book commentedBook = db.read(new Book(id_book)).get(0);
            vs.add(new Comment(id, r, text, grade, commentedBook, stat));
        }
        disconnect();
        return vs;
    }
    //комментарии на эту книгу

    public ArrayList<Comment> read(Book b) throws SQLException, ClassNotFoundException, NamingException {
        connect();
        ArrayList<Comment> vs = new ArrayList<Comment>();
        String query = "SELECT * FROM " + tb_name + " where id_book=?";
        pstmt = con.prepareStatement(query);
        pstmt.setInt(1, b.getID());
        ResultSet result = pstmt.executeQuery();
        DAOReader db = new DAOReader();
        while (result.next()) {
            int id = result.getInt(1);
            int id_author = result.getInt(2);
            String text = result.getString(3);
            int grade = result.getInt(4);
            String stat = getStatusByID(result.getInt(6));
            Reader r = db.read(id_author).get(0);
            vs.add(new Comment(id, r, text, grade, b, stat));
        }
        disconnect();
        return vs;
    }

    //статус по имени
    private static int getStatusByName(String status) {

        Set<Map.Entry<Integer, String>> entrySet = comment_statuses.entrySet();
        for (Map.Entry<Integer, String> pair : entrySet) {
            if (status.equals(pair.getValue())) {
                return pair.getKey();// нашли наше значение и возвращаем  ключ
            }
        }
        return -1;
    }

    private static String getStatusByID(int status) {

        return (String) comment_statuses.get(status);
    }

}
