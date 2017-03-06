/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author Анюта
 */
public class Comment implements Serializable{
    private int ID;
    private Reader commentAuthor;
    private String text;
    private int grade;
    private Book commentedBook;
    private String status;
    

    public Comment(int ID, Reader commentAuthor, String text, int grade, Book commentedBook, String status) {
        this.ID = ID;
        this.commentAuthor = commentAuthor;
        this.text = text;
        this.grade = grade;
        this.commentedBook = commentedBook;
        this.status = status;
        
        
    }

    public Comment(int ID) {
        this.ID = ID;
    }

    public Comment() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Reader getCommentAuthor() {
        return commentAuthor;
    }

    public void setCommentAuthor(Reader commentAuthor) {
        this.commentAuthor = commentAuthor;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public Book getCommentedBook() {
        return commentedBook;
    }

    public void setCommentedBook(Book commentedBook) {
        this.commentedBook = commentedBook;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Comment{" + "ID=" + ID + ", commentAuthor=" + commentAuthor + ", text=" + text + ", grade=" + grade + ", commentedBook=" + commentedBook + ", status=" + status + '}';
    }

  
    
}
