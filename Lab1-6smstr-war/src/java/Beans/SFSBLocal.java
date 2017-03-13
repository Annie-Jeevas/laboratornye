/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.sql.SQLException;
import javax.ejb.Local;
import model.Book;

/**
 *
 * @author Анюта
 */
@Local
public interface SFSBLocal {
     public double getBookMark ();
}
