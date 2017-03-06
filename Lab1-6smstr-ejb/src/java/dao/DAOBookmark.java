/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import model.*;

/**
 *
 * @author Анюта
 */
public class DAOBookmark extends Dao {

    static String tb_name = "bookmark";

    static Statement stmt;
    static PreparedStatement pstmt;
    static ResultSet result;
    
    static Logger log = Logger.getLogger(DAOComment.class.getName());

    //создать закладку
    public String create(Bookmark bm) throws SQLException {
        try {
            String query = "INSERT INTO " + tb_name + " (ID_Reader,  ID_Book,  page) VALUES(?, ?, ?)";
            pstmt = con.prepareStatement(query);

            pstmt.setInt(1, bm.getOwner().getID());
            pstmt.setInt(2, bm.getMarkedBook().getID());
            pstmt.setInt(3, bm.getPage());

            pstmt.executeUpdate();
            //return true;
            return "ok";
        } catch (SQLException ex) {
            //return false;
            return ex.getMessage();
        }
    }

    //удалить закладку
    public String delete(Bookmark v) throws SQLException {
        String query = "DELETE FROM " + tb_name + " where id_mark= and id_reader= and id_book= and page=";
        try {
            //String query = "DELETE FROM " + tb_name + " where id_mark= and id_reader= and ID_book= and page=";
            if ((v.getId() == 0) && (v.getMarkedBook() == null) && (v.getOwner() == null) && (v.getPage() == 0)) {
                //return false;
                return "mark is empty";
            }  //иначе проверяем другие поля на пустоту
            if (v.getId() == 0) {
                query = query.replace("id_mark= and ", "");
            } else {
                query = query.replace("id_mark=", "id_mark=\"" + v.getId() + "\"");
            }
            if (v.getMarkedBook() == null) {
                query = query.replace("id_book= and ", "");
            } else {
                query = query.replace("id_book=", "id_book=\"" + v.getMarkedBook().getID() + "\"");
                
            }
            if (v.getOwner() == null) {
                query = query.replace("id_reader= and ", "");
            } else {
                query = query.replace("id_reader=", "id_reader=\"" + v.getOwner().getID() + "\"");
            }
            if (v.getPage() == 0) {
                query = query.replace("page= and ", "");
            } else {
                query = query.replace("page=", "page=\"" + v.getPage() + "\"");
            }

            stmt = con.createStatement();
            stmt.executeUpdate(query);

            //return true; //вернули да, все прошло хорошо
            return "ok";
        } catch (SQLException ex) {
            //return false;
            return ex.getMessage()+"\n "+query;
        }

    }

    //вернуть все закладки этого пользователя
    public ArrayList<Bookmark> getMarks(Reader rd) throws SQLException, ClassNotFoundException {
        ArrayList<Bookmark> bml = new ArrayList<Bookmark>();

        if (rd == null) {
            throw new NullPointerException("Reader is null.");

        } else {
            DAOBook daob = new DAOBook();
            
            String query = "select * from " + tb_name + " where id_reader=?";
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, rd.getID());
            result = pstmt.executeQuery();
            Book b;
            while (result.next()) {
                int id_mark = result.getInt("id_mark");
                int page = result.getInt("page");
                int id_book = result.getInt("id_book");
                b = new Book(id_book);                
                bml.add(new Bookmark(id_mark, rd, daob.read(b).get(0), page));
            }
            
            return bml;
        }
    }
    //вернуть все закладки этого пользователя на эту книгу
     public ArrayList<Bookmark> getMarks(Reader rd, Book book) throws SQLException, ClassNotFoundException {
        ArrayList<Bookmark> bml = new ArrayList<Bookmark>();

        if ((rd == null)||(book == null)) {
            throw new NullPointerException("Reader or book is null.");

        } else {
            DAOBook daob = new DAOBook();
            
            String query = "select * from " + tb_name + " where id_reader=? and id_book=?";
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, rd.getID());
            pstmt.setInt(2, book.getID());
            result = pstmt.executeQuery();
            while (result.next()) {
                int id_mark = result.getInt("id_mark");
                int page = result.getInt("page");                               
                bml.add(new Bookmark(id_mark, rd, book, page));
            }
            
            return bml;
        }
    }
}
