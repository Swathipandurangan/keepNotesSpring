package com.stackroute.keepnote.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/*
 * The class "User" will be acting as the data model for the User Table in the database. 
 * Please note that this class is annotated with @Entity annotation. 
 * Hibernate will scan all package for any Java objects annotated with the @Entity annotation. 
 * If it finds any, then it will begin the process of looking through that particular 
 * Java object to recreate it as a table in your database.
 */

@Entity
@Table(name = "user")
public class User {

    /*
	 * This class should have five fields (userId,firstName,lastName,
	 * userPassword,userRole,userAddedDate). Out of these five fields, the field
	 * userId should be the primary key. This class should also contain the getters
	 * and setters for the fields, along with the no-arg , parameterized constructor
	 * and toString method.The value of userAddedDate should not be accepted from
	 * the user but should be always initialized with the system date
	 */
	@Id
	@Column(name = "userId")    
    private String userId;
	@Column(name = "firstName")
	private String firstName;
	@Column(name = "lastName")
	private String lastName;
	@Column(name = "userPassword")
	private String userPassword;
	@Column(name = "userRole")
	private String userRole;
	@Column(name = "userAddedDate")
	private Date userAddedDate;
	
	public User() {
		
	}
   
	public User(String string, String string1, String string2, String string3, String string4, Date date) {
		this.userId = string;
		this.firstName = string1;
		this.lastName = string2;
		this.userPassword = string3;
		this.userRole = string4;
		this.userAddedDate = new Date();
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public Date getUserAddedDate() {
		return userAddedDate;
	}

	public void setUserAddedDate(Date userAddedDate) {
		this.userAddedDate = new Date();
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", userPassword="
				+ userPassword + ", userRole=" + userRole + ", userAddedDate=" + userAddedDate + "]";
	}

}
