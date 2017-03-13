/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.ejb.Remote;
import javax.naming.NamingException;
import model.Book;

/**
 *
 * @author Анюта
 */
@Remote
public interface DAORemote {
    public void initConnection();
    public void connect();
    public void disconnect();
    public ArrayList<Book> readAll() throws SQLException, ClassNotFoundException, NamingException;
    public ArrayList<Book> read(Book v) throws SQLException, ClassNotFoundException;
    public void update(Object o) throws SQLException;
    public boolean delete(Book v) throws SQLException;
    public boolean deleteAll() throws SQLException;
    public boolean create(Book v) throws SQLException;
    public double averageGrade(Book b) throws ClassNotFoundException, SQLException, NamingException;
}
