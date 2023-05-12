package com.myco.record_repair.entity;



import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

import com.myco.record_repair.model.Gender;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	private String elasticId;
	private String firstName;
	private String lastName;
	private long mobile;
	private String email;
	private Gender gender;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date created;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date updated;
	
	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", mobile=" + mobile
				+ ", email=" + email + ", gender=" + gender + ", created=" + created + ", updated=" + updated + "]";
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	public long getMobile() {
		return mobile;
	}
	public void setMobile(long mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getUpdate() {
		return updated;
	}
	public void setUpdate(Date updated) {
		this.updated = updated;
	}

	public String getElasticId() {
		return elasticId;
	}

	public void setElasticId(String elasticId) {
		this.elasticId = elasticId;
	}
}
