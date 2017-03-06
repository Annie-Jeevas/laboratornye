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
public class DAOReader extends Dao {

    static String tb_name = "reader";

    static Statement stmt;
    static PreparedStatement pstmt;
    static ResultSet result;
    
    static Logger log = Logger.getLogger(DAOComment.class.getName());

   
    //список всех пользователей
    public ArrayList<Reader> readAll() throws SQLException, ClassNotFoundException, NamingException {
        ArrayList<Reader> vs = new ArrayList<Reader>();
        String query = "SELECT * FROM " + tb_name;

        DAOVisitor dv = new DAOVisitor();
        DAORequest drq = new DAORequest();
        DAOComment dc = new DAOComment();
        DAOMyBooks dmb = new DAOMyBooks();
        DAONotice dn = new DAONotice();
       
        stmt = con.createStatement();
        result = stmt.executeQuery(query);
        while (result.next()) {
            Reader reader = new Reader();
            reader.setID(result.getInt(1));
            Visitor father = dv.read(reader).get(0);

            reader.setLogin(father.getLogin());
            reader.setPassword(father.getPassword());
            reader.setDate_of_last_visit(father.getDate_of_last_visit());

            reader.setBan(result.getBoolean("Ban"));
            reader.setInSystem(result.getBoolean("InSystem"));
            //тут все листы          

            //книги
            ArrayList<Book> myBooks = dmb.read(reader);
            reader.setMyBooks(myBooks);
            //заявки
            ArrayList<Request> myRequests = drq.read(reader);
            reader.setMyRequests(myRequests);
            //комментарии
            ArrayList<Comment> myComments = dc.read(reader);
            reader.setMyComments(myComments);
            //уведомления
            ArrayList<Notice> myNotices = dn.read(reader);
            reader.setMyNotices(myNotices);
            vs.add(reader);
        }
        
        return vs;
    }

    //поиск по ИД
    public ArrayList<Reader> read(int ID) throws SQLException, ClassNotFoundException, NamingException {
        connect();
        ArrayList<Reader> vs = new ArrayList<Reader>();
        String query = "SELECT * FROM " + tb_name + " where id_reader=" + ID;

        DAOVisitor dv = new DAOVisitor();
        DAORequest drq = new DAORequest();
        DAOComment dc = new DAOComment();
        DAOMyBooks dmb = new DAOMyBooks();
        DAONotice dn = new DAONotice();
        

        stmt = con.createStatement();
        result = stmt.executeQuery(query);
        while (result.next()) {
            Reader reader = new Reader();
            reader.setID(result.getInt(1));
            Visitor father = dv.read(reader).get(0);

            reader.setLogin(father.getLogin());
            reader.setPassword(father.getPassword());
            reader.setDate_of_last_visit(father.getDate_of_last_visit());

            reader.setBan(result.getBoolean("Ban"));
            reader.setInSystem(result.getBoolean("InSystem"));
            //тут все листы          

            //книги
            ArrayList<Book> myBooks = dmb.read(reader);
            reader.setMyBooks(myBooks);
            //заявки
            ArrayList<Request> myRequests = drq.read(reader);
            reader.setMyRequests(myRequests);
            //комментарии
            ArrayList<Comment> myComments = dc.read(reader);
            reader.setMyComments(myComments);
            //уведомления
            ArrayList<Notice> myNotices = dn.read(reader);
            reader.setMyNotices(myNotices);
            vs.add(reader);
        }
       disconnect();
        return vs;
    }

    //создать читателя
    public boolean create(Reader v) throws SQLException, ClassNotFoundException {
        //получили целого читателя без ИД, с пустыми списками, "чистого"
        connect();
        DAOVisitor dv = new DAOVisitor();
        
        Visitor father = new Visitor(0, v.getLogin(), v.getPassword(), v.getDate_of_last_visit());
        try {
            dv.create(father); //создали посетителя для этого читателя
            //получить тут макс ид из визитор
            String getMaxID = "select max(id_visitor) from visitor";
            stmt = con.createStatement();
            ResultSet result2 = stmt.executeQuery(getMaxID);
            int last_id = 0;
            while (result2.next()){
            last_id = result2.getInt(1);} //вытащили ИД созданного посетителя

            String query = "INSERT INTO " + tb_name + " (id_reader, insystem, ban) VALUES(?, ?, ?)";
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, last_id); //передали макс ИД
            pstmt.setBoolean(2, v.getInSystem());
            pstmt.setBoolean(3, v.getBan());
            pstmt.executeUpdate();

            log.log(Level.INFO, "Insert into {0}.", tb_name);
            return true;
        } catch (SQLException ex) {
            log.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            return false;
        } finally {disconnect();}

    }

    //обновить статус
    public boolean update(boolean online, Reader r) {
        String query = "UPDATE " + tb_name;
        String set_ = " SET inSystem=?" + " where id_reader=?";
        try {
            pstmt = con.prepareStatement(query + set_);
            pstmt.setBoolean(1, online);
            pstmt.setInt(2, r.getID());
            pstmt.executeUpdate();
            log.log(Level.INFO, "Update {0}.", tb_name);
            return true;
        } catch (SQLException ex) {
            log.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            return false;
        }
    }

    //обновить последний вход
    public boolean update(Date visit, Reader r) throws ClassNotFoundException, SQLException {
        DAOVisitor dv = new DAOVisitor();
        
        try {
            dv.update(r, new Visitor(r.getID(), r.getLogin(), r.getPassword(), visit));
            log.log(Level.INFO, "Update {0}.", "visitor");
            return true;
        } catch (SQLException ex) {
            log.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            return false;
        } 
    }

    
    //добавить книгу в мои книги
    //удалить книгу из моих книг
    //см. дао mybooks
}
