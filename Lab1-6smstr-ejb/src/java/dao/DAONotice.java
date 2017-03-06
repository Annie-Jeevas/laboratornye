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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import model.*;

/**
 *
 * @author Анюта
 */
public class DAONotice extends Dao {

    static String tb_name = "notice";

    static Statement stmt;
    static PreparedStatement pstmt;
    static ResultSet result;
    
    static Logger log = Logger.getLogger(DAOComment.class.getName());

    //показать все уведомления этого пользователя
    public ArrayList<Notice> read(Reader r) throws SQLException, ClassNotFoundException, NamingException {
        connect();
        ArrayList<Notice> vs = new ArrayList<Notice>();
        String query = "SELECT * FROM " + tb_name + " where id_sender=" + r.getID();
        stmt = con.createStatement();
        result = stmt.executeQuery(query);
        DAOBook db = new DAOBook();
        DAOVisitor dv = new DAOVisitor();
        DAORequest drq = new DAORequest();
        DAOComment dc = new DAOComment();
        
        while (result.next()) {
            int id = result.getInt(1);
            int id_sender = result.getInt(2);

            String text = result.getString(4);
            int id_comment = result.getInt(5);
            int id_request = result.getInt(6);
            Visitor sender = dv.read(new Visitor(id_sender)).get(0);
            Comment noticedComment;
            Request noticedRequest;
            if (id_comment != 0) {
                noticedComment = dc.read(new Comment(id_comment)).get(0);
            } else {
                noticedComment = null;
            }
            if (id_request != 0) {
                noticedRequest = drq.read(new Request(id_request)).get(0);
            } else {
                noticedRequest = null;
            }

            vs.add(new Notice(id, sender, r, text, noticedComment, noticedRequest));
        }
        disconnect();
        return vs;
    }

    //создать уведомление    
     public boolean create(Notice v) throws SQLException {

        try {

            String query = "INSERT INTO " + tb_name + " (id_author, id_addressee, text, id_comment, id_request) VALUES(?, ?, ?, ?, ?)";
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, v.getSender().getID());
            pstmt.setInt(2, v.getAddressee().getID());
            pstmt.setString(3, v.getText());
            pstmt.setInt(4, v.getNoticedComment().getID());
            pstmt.setInt(5, v.getNoticedRequest().getID());
            pstmt.executeUpdate();
            log.log(Level.INFO, "Insert into {0}.", tb_name);
            return true;
        } catch (SQLException ex) {
            log.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            return false;
        }

    }

}


