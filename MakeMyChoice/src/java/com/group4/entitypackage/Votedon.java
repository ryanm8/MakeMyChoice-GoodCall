/*
 * Created by Brian Green on 2014.11.18  * 
 * Copyright © 2014 Brian Green. All rights reserved. * 
 */
package com.group4.entitypackage;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Brian
 */
@Entity
@Table(name = "votedon")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Votedon.findAll", query = "SELECT v FROM Votedon v"),
    @NamedQuery(name = "Votedon.findById", query = "SELECT v FROM Votedon v WHERE v.id = :id"),
    @NamedQuery(name = "Votedon.findByUserID", query = "SELECT v FROM Votedon v WHERE v.userID = :userID"),
    @NamedQuery(name = "Votedon.findByQuestionID", query = "SELECT v FROM Votedon v WHERE v.questionID = :questionID"),
    @NamedQuery(name = "Votedon.findByLeftRight", query = "SELECT v FROM Votedon v WHERE v.leftRight = :leftRight")})
public class Votedon implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "User_ID")
    private int userID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Question_ID")
    private int questionID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "LeftRight")
    private String leftRight;

    public Votedon() {
    }

    public Votedon(Integer id) {
        this.id = id;
    }

    public Votedon(Integer id, int userID, int questionID, String leftRight) {
        this.id = id;
        this.userID = userID;
        this.questionID = questionID;
        this.leftRight = leftRight;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public String getLeftRight() {
        return leftRight;
    }

    public void setLeftRight(String leftRight) {
        this.leftRight = leftRight;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Votedon)) {
            return false;
        }
        Votedon other = (Votedon) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.group4.entitypackage.Votedon[ id=" + id + " ]";
    }
    
}
