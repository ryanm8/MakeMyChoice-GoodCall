/*
 * Created by Brian Green on 2014.11.18  * 
 * Copyright © 2014 Brian Green. All rights reserved. * 
 */
package com.group4.entitypackage;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Brian
 */
@SqlResultSetMapping(
  name="user-map",
  entities={
    @EntityResult(
      entityClass=User.class,
      fields={
        @FieldResult(name="ID", column="ID"),
        @FieldResult(name="Email", column="Email"),
        @FieldResult(name="PID", column="PID"),
        @FieldResult(name="Password", column="Password"),
        @FieldResult(name="First_Name", column="First_Name"),
        @FieldResult(name="Last_Name", column="Last_Name"),
        @FieldResult(name="Cell_Number", column="Cell_Number")
  }) }
)

@Entity
@Table(name = "user")
@XmlRootElement
@NamedNativeQueries({
    @NamedNativeQuery(name = "user.findAll", query = "SELECT * FROM user", resultSetMapping="user-map"),
    @NamedNativeQuery(name = "user.findById", query = "SELECT * FROM user u WHERE u.id = ?", resultSetMapping="user-map"),
    @NamedNativeQuery(name = "user.findByPid", query = "SELECT * FROM user u WHERE u.PID = ?", resultSetMapping="user-map"),
    //@NamedNativeQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
    //@NamedNativeQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password"),
    //@NamedNativeQuery(name = "User.findByFirstName", query = "SELECT u FROM User u WHERE u.firstName = :firstName"),
    /*@NamedNativeQuery(name = "User.findByLastName", query = "SELECT u FROM User u WHERE u.lastName = :lastName")*/})
//@Entity
//@Table(name = "user")
//@XmlRootElement
//@NamedQueries({
//    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
//    @NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id"),
//    @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
//    @NamedQuery(name = "User.findByFirstName", query = "SELECT u FROM User u WHERE u.firstName = :firstName"),
//    @NamedQuery(name = "User.findByLastName", query = "SELECT u FROM User u WHERE u.lastName = :lastName"),
//    @NamedQuery(name = "User.findByCellNumber", query = "SELECT u FROM User u WHERE u.cellNumber = :cellNumber"),
//    @NamedQuery(name = "User.findByPid", query = "SELECT u FROM User u WHERE u.pid = :pid"),
//    @NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password")})
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Email")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "First_Name")
    private String firstName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Last_Name")
    private String lastName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Cell_Number")
    private String cellNumber;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "PID")
    private String pid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Password")
    private String password;

    public User() {
    }

    public User(Integer id) {
        this.id = id;
    }

    public User(Integer id, String email, String firstName, String lastName, String cellNumber, String pid, String password) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cellNumber = cellNumber;
        this.pid = pid;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCellNumber() {
        return cellNumber;
    }

    public void setCellNumber(String cellNumber) {
        this.cellNumber = cellNumber;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.group4.entitypackage.User[ id=" + id + " ]";
    }
    
}
