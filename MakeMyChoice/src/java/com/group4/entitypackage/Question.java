/*
 * Created by Brian Green on 2014.11.18  * 
 * Copyright © 2014 Brian Green. All rights reserved. * 
 */
package com.group4.entitypackage;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Brian
 */
@Entity
@Table(name = "question")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Question.findAll", query = "SELECT q FROM Question q"),
    @NamedQuery(name = "Question.findById", query = "SELECT q FROM Question q WHERE q.id = :id"),
    @NamedQuery(name = "Question.findByTitle", query = "SELECT q FROM Question q WHERE q.title = :title"),
    @NamedQuery(name = "Question.findByDescription", query = "SELECT q FROM Question q WHERE q.description = :description"),
    @NamedQuery(name = "Question.findByAskerID", query = "SELECT q FROM Question q WHERE q.askerID = :askerID"),
    @NamedQuery(name = "Question.findByLeftOptionDescription", query = "SELECT q FROM Question q WHERE q.leftOptionDescription = :leftOptionDescription"),
    @NamedQuery(name = "Question.findByRightOptionDescription", query = "SELECT q FROM Question q WHERE q.rightOptionDescription = :rightOptionDescription"),
    @NamedQuery(name = "Question.findByNumberLeftVotes", query = "SELECT q FROM Question q WHERE q.numberLeftVotes = :numberLeftVotes"),
    @NamedQuery(name = "Question.findByNumberRightVotes", query = "SELECT q FROM Question q WHERE q.numberRightVotes = :numberRightVotes"),
    @NamedQuery(name = "Question.findByDueDate", query = "SELECT q FROM Question q WHERE q.dueDate = :dueDate"),
    @NamedQuery(name = "Question.findByTimeStamp", query = "SELECT q FROM Question q WHERE q.timeStamp = :timeStamp"),
    @NamedQuery(name = "Question.findByOpenClosed", query = "SELECT q FROM Question q WHERE q.openClosed = :openClosed")})
public class Question implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Title")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1024)
    @Column(name = "Description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Asker_ID")
    private int askerID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "Left_Option_Description")
    private String leftOptionDescription;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "Right_Option_Description")
    private String rightOptionDescription;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Number_Left_Votes")
    private int numberLeftVotes;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Number_Right_Votes")
    private int numberRightVotes;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Due_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dueDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TimeStamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeStamp;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "OpenClosed")
    private String openClosed;

    public Question() {
    }

    public Question(Integer id) {
        this.id = id;
    }

    public Question(Integer id, String title, String description, int askerID, String leftOptionDescription, String rightOptionDescription, int numberLeftVotes, int numberRightVotes, Date dueDate, Date timeStamp, String openClosed) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.askerID = askerID;
        this.leftOptionDescription = leftOptionDescription;
        this.rightOptionDescription = rightOptionDescription;
        this.numberLeftVotes = numberLeftVotes;
        this.numberRightVotes = numberRightVotes;
        this.dueDate = dueDate;
        this.timeStamp = timeStamp;
        this.openClosed = openClosed;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAskerID() {
        return askerID;
    }

    public void setAskerID(int askerID) {
        this.askerID = askerID;
    }

    public String getLeftOptionDescription() {
        return leftOptionDescription;
    }

    public void setLeftOptionDescription(String leftOptionDescription) {
        this.leftOptionDescription = leftOptionDescription;
    }

    public String getRightOptionDescription() {
        return rightOptionDescription;
    }

    public void setRightOptionDescription(String rightOptionDescription) {
        this.rightOptionDescription = rightOptionDescription;
    }

    public int getNumberLeftVotes() {
        return numberLeftVotes;
    }

    public void setNumberLeftVotes(int numberLeftVotes) {
        this.numberLeftVotes = numberLeftVotes;
    }

    public int getNumberRightVotes() {
        return numberRightVotes;
    }

    public void setNumberRightVotes(int numberRightVotes) {
        this.numberRightVotes = numberRightVotes;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getOpenClosed() {
        return openClosed;
    }

    public void setOpenClosed(String openClosed) {
        this.openClosed = openClosed;
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
        if (!(object instanceof Question)) {
            return false;
        }
        Question other = (Question) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.group4.entitypackage.Question[ id=" + id + " ]";
    }
    
}
