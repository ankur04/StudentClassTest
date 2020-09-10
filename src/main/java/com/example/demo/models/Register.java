package com.example.demo.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Entity
public class Register {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer registrationid;
	private Integer studentid;
	private Integer classid;
	private String grade;

	
	
	public Register(Integer registrationid, Integer studentid, Integer classid, String grade) {
		super();
		this.registrationid = registrationid;
		this.studentid = studentid;
		this.classid = classid;
		this.grade = grade;
	}

	public Register() {
	
	}

	public Integer getRegistrationid() {
		return registrationid;
	}

	public void setRegistrationid(Integer registrationid) {
		this.registrationid = registrationid;
	}
	
	public Integer getStudentid() {
		return studentid;
	}


	public void setStudentid(Integer studentid) {
		this.studentid = studentid;
	}


	public Integer getClassid() {
		return classid;
	}


	public void setClassid(Integer classid) {
		this.classid = classid;
	}


	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

}
