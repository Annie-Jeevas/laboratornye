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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import model.*;

/**
 *
 * @author Анюта
 */
public class DAOBook extends Dao {

    static String tb_name = "book";

    static Statement stmt;
    static PreparedStatement pstmt;
    static ResultSet result;
    static final Logger log = Logger.getLogger(DAOBook.class.getName());

    public ArrayList<Book> readAll() throws SQLException, ClassNotFoundException, NamingException {
        connect();
        ArrayList<Book> vs = new ArrayList<>();
        String query = "SELECT * FROM " + tb_name;
        stmt = con.createStatement();
        result = stmt.executeQuery(query);
        while (result.next()) {
            int id = result.getInt(1);
            String name = result.getString(2);
            String author = result.getString(3);
            Date year = new java.util.Date(result.getDate(4).getTime());
            String file = result.getString(5);
            String ganre = result.getString(6);
            //Book b = new Book(id);
            //vs.add(new Book(id, name, author, year, file, ganre, averageGrade(b)));
            vs.add(new Book(id, name, author, year, file, ganre));
        }
        disconnect();
        return vs;
    }

    public ArrayList<Book> read(Book v) throws SQLException, ClassNotFoundException {
        connect();
        ArrayList<Book> vs = new ArrayList<Book>();
        String query = "SELECT * FROM " + tb_name + " where id_book=?";

        pstmt = con.prepareStatement(query);
        pstmt.setInt(1, v.getID());
        result = pstmt.executeQuery();
        while (result.next()) {
            int id = result.getInt(1);
            String name = result.getString(2);
            String author = result.getString(3);
            Date year = result.getDate(4);
            String file = result.getString(5);
            String ganre = result.getString(6);
            
            vs.add(new Book(id, name, author, year, file, ganre/*, averageGrade(b)*/));
        }
        
        disconnect();
        return vs;
    }



public void update(Object o) throws SQLException {
        connect();
        try {
            Book book = (Book) o;
            String sql = "UPDATE `book` SET Name=?, Author=?, Year=?, File=?, Ganre=? WHERE ID_Book=?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, book.getName());
            stm.setString(2, book.getAuthor());
            stm.setDate(3, new java.sql.Date(book.getYear().getTime()));
            stm.setString(4, book.getBookfile());
            stm.setString(5, book.getGanre());
            stm.setInt(6, book.getID());
            stm.executeUpdate();
        } catch (Exception e) {
            log.info(e.getMessage());
        } finally {
            disconnect();
        }

    }

    public boolean delete(Book v) throws SQLException {
        connect();
        try {
            String query = "DELETE FROM " + tb_name + " where id_book= and name= and author= and year= and file= and ganre=";
            if ((v.getID() == 0) && (v.getName() == null) && (v.getAuthor() == null) && (v.getYear() == null) && (v.getBookfile() == null) && (v.getGanre() == null)) {
                return false;
            } //иначе проверяем другие поля на пустоту 
            if (v.getID() == 0) {
                query = query.replace("id_book= and ", "");
            } else {
                query = query.replace("id_book=", "id_book=\"" + v.getID() + "\"");
            }
            if (v.getName() == null) {
                query = query.replace("name= and ", "");
            } else {
                query = query.replace("name=", "name=\"" + v.getName() + "\"");
            }
            if (v.getAuthor() == null) {
                query = query.replace("author= and ", "");
            } else {
                query = query.replace("author=", "author=\"" + v.getAuthor() + "\"");
            }
            if (v.getYear() == null) {
                query = query.replace("year= and ", "");
            } else {
                query = query.replace("year=", "year=\"" + v.getYear() + "\"");
            }
            if (v.getBookfile() == null) {
                query = query.replace("file= and ", "");
            } else {
                query = query.replace("file=", "file=\"" + v.getBookfile() + "\"");
            }
            if (v.getGanre() == null) {
                query = query.replace(" and ganre=", "");
            } else {
                query = query.replace("ganre=", "ganre=\"" + v.getGanre() + "\"");
            }
            stmt = con.createStatement();
            stmt.executeUpdate(query);

            return true; //вернули да, все прошло хорошо 
        } catch (SQLException e) {
            log.info(e.getMessage());
            return false;
        } finally {
            disconnect();
        }

    }

    public boolean deleteAll() throws SQLException {
        connect();
        try {
            tb_name = "book";
            String query = "DELETE FROM " + tb_name;
            stmt = con.createStatement();
            stmt.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            log.info(e.getMessage());
            return false;
        } finally {
            disconnect();
        }
    }
     public boolean create(Book v) throws SQLException {

        try {
            connect();
            String query = "INSERT INTO " + tb_name + " (name, author, year, file, ganre) VALUES(?, ?, ?, ?, ?)";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, v.getName());
            pstmt.setString(2, v.getAuthor());
            pstmt.setDate(3, new java.sql.Date(0,0,0));
            pstmt.setString(4, v.getBookfile());
            pstmt.setString(5, v.getGanre());
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
    public double averageGrade(Book b) throws ClassNotFoundException, SQLException, NamingException {
        DAOComment daoc = new DAOComment();
        ArrayList<Comment> bookComms = daoc.read(b);
        double avr = 0;
        for (Comment com : bookComms) {
            avr += com.getGrade();
            avr = avr / 2;
        }
        return avr;
    }
}
