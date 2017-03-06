/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.SQLException;
import javax.ejb.Remote;
import model.User;

/**
 *
 * @author Анюта
 */
@Remote
public interface DAORemote {
    public void initConnection();
    public void connect();
    public void disconnect();
    public User getUserByUsername(String name) throws SQLException;
    public void Create(User u);
}
