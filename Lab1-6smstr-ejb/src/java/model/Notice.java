/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Arrays;
import java.util.Objects;
import model.*;

/**
 *
 * @author Анюта
 */
public class Notice {
    private int ID;
    private Visitor sender;
    private Reader addressee;
    private String text;
    private Comment noticedComment;
    private Request noticedRequest;

   
    public Notice(int ID, Visitor sender, Reader addressee, String text, Comment noticedComment, Request noticedRequest) {
        this.ID = ID;
        this.sender = sender;
        this.addressee = addressee;
        this.text = text;
        this.noticedComment = noticedComment;
        this.noticedRequest = noticedRequest;
    }

    public Notice(int ID) {
        this.ID = ID;
    }

    public Notice() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Visitor getSender() {
        return sender;
    }

    public void setSender(Visitor sender) {
        this.sender = sender;
    }

    public Reader getAddressee() {
        return addressee;
    }

    public void setAddressee(Reader addressee) {
        this.addressee = addressee;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Comment getNoticedComment() {
        return noticedComment;
    }

    public void setNoticedComment(Comment noticedComment) {
        this.noticedComment = noticedComment;
    }

    public Request getNoticedRequest() {
        return noticedRequest;
    }

    public void setNoticedRequest(Request noticedRequest) {
        this.noticedRequest = noticedRequest;
    }

    @Override
    public String toString() {
        return "Notice{" + "ID=" + ID + ", sender=" + sender + ", addressee=" + addressee + ", text=" + text + ", noticedComment=" + noticedComment + ", noticedRequest=" + noticedRequest + '}';
    }

    

   
    
    
}
